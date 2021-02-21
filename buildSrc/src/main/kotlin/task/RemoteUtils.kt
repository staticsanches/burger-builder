package task

import net.schmizz.sshj.SSHClient
import org.gradle.api.Task
import org.gradle.kotlin.dsl.provideDelegate
import java.io.File
import java.net.ConnectException
import java.nio.file.Files

internal val Task.remoteIP: String
	get() {
		val remoteIP: String by project
		return remoteIP
	}

internal val Task.remoteUser: String
	get() {
		val remoteUser: String by project
		return remoteUser
	}

internal val Task.remoteHomeFolder: String
	get() {
		val remoteHomeFolder: String by project
		return folderName(remoteHomeFolder)
	}

internal val Task.remoteSshPort: Int
	get() {
		val remoteSshPort: String by project
		return remoteSshPort.toInt()
	}

internal val Task.sshKeyFile: String
	get() {
		val sshKeyFile: String by project
		return sshKeyFile
	}

internal val Task.sshKeyPassword: String?
	get() {
		val sshKeyPassword: String? by project
		return if (sshKeyPassword.isNullOrBlank()) null else sshKeyPassword
	}


fun closeSsh() {
	sshClient?.apply {
		println("Closing ssh client!")
		close()
	}
	sshClient = null
	sshWrapper = null
}

internal val Task.ssh: SshWrapper
	get() {
		var wrapper = sshWrapper
		if (wrapper != null) {
			return wrapper
		}

		// Instantiate the wrapper
		val ssh = SSHClient()
		ssh.loadKnownHosts(createKnownHostsFile(remoteIP))
		retryConnection(5) {
			ssh.connect(remoteIP, remoteSshPort)
		}
		ssh.authPublickey(remoteUser, ssh.loadKeys(sshKeyFile, sshKeyPassword?.toCharArray()))
		wrapper = SshWrapper(ssh)
		sshClient = ssh
		sshWrapper = wrapper
		return wrapper
	}
private var sshClient: SSHClient? = null
private var sshWrapper: SshWrapper? = null

private fun Task.createKnownHostsFile(ip: String): File {
	val knownHosts = Files.createTempFile("known", "_hosts").toFile()
	knownHosts.deleteOnExit()
	val content = executeWithReturn("ssh-keyscan -H $ip")
	knownHosts.writeText(content)
	return knownHosts
}

private fun retryConnection(limit: Int, sleep: Long = 4_000, block: () -> Unit) {
	try {
		block()
	} catch (e: ConnectException) {
		if (limit <= 0) {
			throw e
		}
		System.err.println("Failed to connect! Retrying in ${sleep}ms")
		Thread.sleep(sleep)
		retryConnection(limit - 1, sleep * 2, block)
	}
}
