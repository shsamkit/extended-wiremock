package com.wm.extendedwiremcok.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "wm.server")
public class WiremockServerConfig {

	private Integer port;
	private String mappingsFilesDir;
	private JettyConfig jetty;
	private RequestJournal requestJournal;

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getMappingsFilesDir() {
		return mappingsFilesDir;
	}

	public void setMappingsFilesDir(String mappingsFilesDir) {
		this.mappingsFilesDir = mappingsFilesDir;
	}

	public JettyConfig getJetty() {
		return jetty;
	}

	public void setJetty(JettyConfig jetty) {
		this.jetty = jetty;
	}

	public RequestJournal getRequestJournal() {
		return requestJournal;
	}

	public void setRequestJournal(RequestJournal requestJournal) {
		this.requestJournal = requestJournal;
	}

	/**
	 * Class to accept Jetty server config for wiremock
	 * Refer this page for defaults: http://wiremock.org/docs/configuration/#jetty-configuration
	 */
	public static class JettyConfig {
		private Integer containerThreads;
		private Integer accepter;
		private Integer acceptQueueSize;
		private Integer headerBufferSize;
		private boolean asynchronousResponse = false;
		private Integer asynchronousThreads = 10; //Need to set a default if asynchronousResponse = true

		public Integer getContainerThreads() {
			return containerThreads;
		}

		public Integer getAccepter() {
			return accepter;
		}

		public Integer getAcceptQueueSize() {
			return acceptQueueSize;
		}

		public Integer getHeaderBufferSize() {
			return headerBufferSize;
		}

		public boolean isAsynchronousResponse() {
			return asynchronousResponse;
		}

		public Integer getAsynchronousThreads() {
			return asynchronousThreads;
		}

		public void setContainerThreads(Integer containerThreads) {
			this.containerThreads = containerThreads;
		}

		public void setAccepter(Integer accepter) {
			this.accepter = accepter;
		}

		public void setAcceptQueueSize(Integer acceptQueueSize) {
			this.acceptQueueSize = acceptQueueSize;
		}

		public void setHeaderBufferSize(Integer headerBufferSize) {
			this.headerBufferSize = headerBufferSize;
		}

		public void setAsynchronousResponse(boolean asynchronousResponse) {
			this.asynchronousResponse = asynchronousResponse;
		}

		public void setAsynchronousThreads(Integer asynchronousThreads) {
			this.asynchronousThreads = asynchronousThreads;
		}
	}

	/**
	 * Class to accept request journal settings in wiremock
	 * Refer this page for defaults: http://wiremock.org/docs/configuration/#request-journal
	 */
	public static class RequestJournal {
		public boolean disable = false;
		public Integer maxEntries;

		public boolean isDisable() {
			return disable;
		}

		public void setDisable(boolean disable) {
			this.disable = disable;
		}

		public Integer getMaxEntries() {
			return maxEntries;
		}

		public void setMaxEntries(Integer maxEntries) {
			this.maxEntries = maxEntries;
		}
	}



}
