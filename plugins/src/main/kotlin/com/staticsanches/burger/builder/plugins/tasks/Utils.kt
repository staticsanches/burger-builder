package com.staticsanches.burger.builder.plugins.tasks

import org.gradle.api.Task
import java.io.ByteArrayOutputStream
import java.io.OutputStream

internal fun Task.execute(command: String, suppressOutput: Boolean = false) =
	project.exec {
		commandLine("bash", "-c", command)
		standardOutput = if (suppressOutput) IgnoreOS else System.out
	}

internal fun Task.executeWithReturn(command: String, enableDefaultOutput: Boolean = true): String =
	object : OutputStream() {

		private val delegate = ByteArrayOutputStream()

		override fun write(b: Int) {
			if (enableDefaultOutput) {
				System.out.write(b)
			}
			delegate.write(b)
		}

		override fun write(b: ByteArray) {
			if (enableDefaultOutput) {
				System.out.write(b)
			}
			delegate.write(b)
		}

		override fun write(b: ByteArray, off: Int, len: Int) {
			if (enableDefaultOutput) {
				System.out.write(b, off, len)
			}
			delegate.write(b, off, len)
		}

		override fun flush() {
			if (enableDefaultOutput) {
				System.out.flush()
			}
			delegate.flush()
		}

		override fun close() {
			if (enableDefaultOutput) {
				System.out.close()
			}
			delegate.close()
		}

		override fun toString(): String {
			return delegate.toString()
		}

	}.use { os ->
		project.exec {
			commandLine("bash", "-c", command)
			standardOutput = os
		}
		os.toString()
	}

internal fun folderName(name: String): String =
	name + if (name.isEmpty() || name.endsWith("/")) "" else "/"

private object IgnoreOS : OutputStream() {

	override fun write(b: Int) {
		// Does nothing
	}

	override fun write(b: ByteArray) {
		// Does nothing
	}

	override fun write(b: ByteArray, off: Int, len: Int) {
		// Does nothing
	}

}
