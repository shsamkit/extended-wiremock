package com.wm.extendedwiremock.config.remote;

import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.wm.extendedwiremock.config.properties.RepositoryProperties;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.util.Base64;
import org.eclipse.jgit.util.FS;

/**
 * The class configures the ssh authentication for git.
 */
public class PropertyBasedSshSessionFactory extends JschConfigSessionFactory {

	private static final String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
	private static final String YES_OPTION = "yes";
	private static final String NO_OPTION = "no";
	private static final String SERVER_HOST_KEY = "server_host_key";
	private final String hostKey;
	private final String hostName;
	private final String hostKeyAlgorithm;
	private final String privateKey;
	private final JSch jSch;

	public PropertyBasedSshSessionFactory(RepositoryProperties.Git git, JSch jSch) {
		this.hostKey = git.getHostKey();
		this.hostName = git.getHostName();
		this.hostKeyAlgorithm = git.getHostKeyAlgorithm();
		this.privateKey = git.getPrivateKey();
		this.jSch = jSch;
	}

	@Override
	protected void configure(OpenSshConfig.Host hc, Session session) {
		if (hostKeyAlgorithm != null) {
			session.setConfig(SERVER_HOST_KEY, hostKeyAlgorithm);
		}
		if (hostKey == null) {
			session.setConfig(STRICT_HOST_KEY_CHECKING, NO_OPTION);
		} else {
			session.setConfig(STRICT_HOST_KEY_CHECKING, YES_OPTION);
		}
	}

	@Override
	protected Session createSession(OpenSshConfig.Host hc, String user, String host, int port, FS fs) throws JSchException {
		if (host.contentEquals(hostName)) {
			jSch.addIdentity(host, privateKey.getBytes(), (byte[])null, (byte[])null);
			if (hostKey != null) {
				HostKey hostkey = new HostKey(host, Base64.decode(hostKey));
				jSch.getHostKeyRepository().add(hostkey, null);
			}
			return jSch.getSession(user, host, port);
		}
		throw new JSchException("no keys configured for hostname " + host);
	}

}
