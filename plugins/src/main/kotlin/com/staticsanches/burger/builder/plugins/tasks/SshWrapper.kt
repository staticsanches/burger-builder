package com.staticsanches.burger.builder.plugins.tasks

import me.tongfei.progressbar.ConsoleProgressBarConsumer
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarBuilder
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.common.StreamCopier
import net.schmizz.sshj.xfer.TransferListener
import java.io.BufferedReader
import java.io.InputStreamReader

internal class SshWrapper(private val ssh: SSHClient) {

	fun execute(command: String) {
		ssh.startSession().use { session ->
			session.exec(command).use { cmd ->
				BufferedReader(InputStreamReader(cmd.inputStream)).use { reader ->
					var line: String? = reader.readLine()
					while (line != null) {
						println(line)
						line = reader.readLine()
					}
				}
			}
		}
	}

	fun executeWithReturn(command: String): String {
		ssh.startSession().use { session ->
			session.exec(command).use { cmd ->
				BufferedReader(InputStreamReader(cmd.inputStream)).use { reader ->
					var content = ""
					var line: String? = reader.readLine()
					var firstLine = true
					while (line != null) {
						if (firstLine) {
							content = line
							firstLine = false
						} else {
							content += "\n$line"
						}
						println(line)
						line = reader.readLine()
					}
					return content
				}
			}
		}
	}

	fun upload(localPath: String, remotePath: String) {
		val scp = ssh.newSCPFileTransfer()
		ScpListener().use { listener ->
			scp.transferListener = listener
			scp.upload(localPath, remotePath)
		}
	}

	private class ScpListener(val relativePath: String = "") :
		TransferListener, AutoCloseable {

		val bars = mutableListOf<ProgressBar>()
		val children = mutableListOf<ScpListener>()

		override fun directory(name: String): TransferListener {
			println("Transferring directory '$relativePath$name'")
			val child = ScpListener("$relativePath$name/")
			children += child
			return child
		}

		override fun file(name: String, size: Long): StreamCopier.Listener {
			val builder = ProgressBarBuilder()
				.setInitialMax(size)
				.showSpeed()
				.setConsumer(object : ConsoleProgressBarConsumer(System.out) {

					override fun accept(str: String?) {
						super.accept(str)
						System.out.flush()
					}

				})
			when {
				size > oneTB -> builder.setUnit("TB", oneTB)
				size > oneGB -> builder.setUnit("GB", oneGB)
				size > oneMB -> builder.setUnit("MB", oneMB)
				size > oneKB -> builder.setUnit("KB", oneKB)
				else -> builder.setUnit("B", oneB)
			}
			val bar = builder.build()
			bars += bar
			print("Transferring $relativePath$name")
			System.out.flush()
			return StreamCopier.Listener { bar.stepTo(it) }
		}

		override fun close() {
			bars.forEach(ProgressBar::close)
			children.forEach(ScpListener::close)
		}

		companion object {

			private const val oneB: Long = 1
			private const val oneKB: Long = 1024 * oneB
			private const val oneMB: Long = 1024 * oneKB
			private const val oneGB: Long = 1024 * oneMB
			private const val oneTB: Long = 1024 * oneGB

		}

	}

}
