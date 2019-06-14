package com.wm.extendedwiremock.config.remote;

import com.jcraft.jsch.JSch;
import com.wm.extendedwiremock.config.properties.RepositoryProperties;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(RepositoryProperties.class)
@ConditionalOnProperty(name = "remote.git.uri", matchIfMissing = false)
public class PropertiesBasedSshTransportConfigCallBack implements TransportConfigCallback {

	@Autowired
	public PropertiesBasedSshTransportConfigCallBack(RepositoryProperties repositoryProperties) {
		this.repositoryProperties = repositoryProperties;
	}

	private RepositoryProperties repositoryProperties;

	@Override
	public void configure(Transport transport) {
		if (transport instanceof SshTransport) {
			SshTransport sshTransport = (SshTransport) transport;
			sshTransport.setSshSessionFactory(
					new PropertyBasedSshSessionFactory(repositoryProperties.getGit(), new JSch()));
		}
	}
}
