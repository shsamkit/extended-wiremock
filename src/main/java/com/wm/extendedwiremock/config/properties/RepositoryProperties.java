package com.wm.extendedwiremock.config.properties;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "remote")
@ConditionalOnProperty(name = "remote.git.uri")
public class RepositoryProperties {

	private Git git;

	public Git getGit() {
		return git;
	}

	public void setGit(Git git) {
		this.git = git;
	}

	public static class Git {
		String uri;
		String branch = "master";
		String pathToStub =  ".";
		String hostName;
		String hostKeyAlgorithm;
		String hostKey;
		String privateKey;

		public String getPathToStub() { return pathToStub; }

		public void setPathToStub(String pathToStub) { this.pathToStub = pathToStub; }

		public String getBranch() { return branch; }

		public void setBranch(String branch) { this.branch = branch; }

		public String getUri() { return uri; }

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getHostName() {
			return hostName;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		public String getHostKeyAlgorithm() {
			return hostKeyAlgorithm;
		}

		public void setHostKeyAlgorithm(String hostKeyAlgorithm) {
			this.hostKeyAlgorithm = hostKeyAlgorithm;
		}

		public String getHostKey() {
			return hostKey;
		}

		public void setHostKey(String hostKey) {
			this.hostKey = hostKey;
		}

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}
	}

}
