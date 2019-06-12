package com.wm.extendedwiremock.config;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.wm.extendedwiremock.config.WiremockServerConfig.JettyConfig;
import com.wm.extendedwiremock.config.WiremockServerConfig.RequestJournal;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WiremockServerConfigurer {

	@Autowired
	private WiremockServerConfig wiremockServerConfig;


	@Bean(name = "wiremockServer")
	public WireMockServer configureWiremockServer() {
		return new WireMockServer(getWiremockServerConfiguration());
	}

	private WireMockConfiguration getWiremockServerConfiguration() {
		WireMockConfiguration configuration = options();

		applyIntegerConfig(configuration::port, wiremockServerConfig.getPort());

		configureJetty(configuration, wiremockServerConfig.getJetty());
		configureJournal(configuration, wiremockServerConfig.getRequestJournal());

		applyStringConfig(configuration::usingFilesUnderDirectory, wiremockServerConfig.getMappingsFilesDir());

		return configuration;
	}

	private static void configureJetty(WireMockConfiguration configuration, JettyConfig jetty) {
		if (jetty != null) {
			applyIntegerConfig(configuration::containerThreads, jetty.getContainerThreads());
			applyIntegerConfig(configuration::jettyAcceptors, jetty.getAccepter());
			applyIntegerConfig(configuration::jettyAcceptQueueSize, jetty.getAcceptQueueSize());
			applyIntegerConfig(configuration::jettyHeaderBufferSize, jetty.getHeaderBufferSize());
			if (jetty.isAsynchronousResponse()) {
				configuration.asynchronousResponseEnabled(true);
				applyIntegerConfig(configuration::asynchronousResponseThreads, jetty.getAsynchronousThreads());
			}
		};
	}

	private static void configureJournal(WireMockConfiguration configuration, RequestJournal journal) {
		if (journal != null) {
			if (journal.isDisable()) {
				configuration.disableRequestJournal();
			} else {
				applyIntegerConfig(configuration::maxRequestJournalEntries, journal.getMaxEntries());
			}
		}
	}

	private static void applyIntegerConfig(Consumer<Integer> setter, Integer value) {
		Optional.ofNullable(value).ifPresent(setter);
	}

	private static void applyStringConfig(Consumer<String> setter, String value) {
		Optional.ofNullable(value).ifPresent(setter);
	}


}
