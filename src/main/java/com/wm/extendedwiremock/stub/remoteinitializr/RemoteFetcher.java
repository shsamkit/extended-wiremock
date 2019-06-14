package com.wm.extendedwiremock.stub.remoteinitializr;

import static java.util.Collections.singleton;

import com.wm.extendedwiremock.config.properties.RepositoryProperties;
import com.wm.extendedwiremock.config.properties.WiremockServerConfig;
import com.wm.extendedwiremock.config.remote.PropertiesBasedSshTransportConfigCallBack;
import com.wm.extendedwiremock.exception.StubInitializationException;
import com.wm.extendedwiremock.util.DirectoryUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "remote.git.uri")
public class RemoteFetcher {

	@Autowired
	public RemoteFetcher(PropertiesBasedSshTransportConfigCallBack propertiesBasedSshTransportConfigCallBack, WiremockServerConfig wiremockServerConfig, RepositoryProperties repositoryProperties) {
		this.propertiesBasedSshTransportConfigCallBack = propertiesBasedSshTransportConfigCallBack;
		this.wiremockServerConfig = wiremockServerConfig;
		this.repositoryProperties = repositoryProperties;
	}

	private final PropertiesBasedSshTransportConfigCallBack propertiesBasedSshTransportConfigCallBack;
	private final WiremockServerConfig wiremockServerConfig;
	private final RepositoryProperties repositoryProperties;
	private static final Logger logger = LoggerFactory.getLogger(RemoteFetcher.class);

	private static final String BRANCH_PREFIX = "refs/heads/";
	private static final String CLONE_SUB_DIR = "/remote";

	public Path fetchStubs() {
		try {
			logger.info("Cleaning and Fetching stubs from remote : {}@{}  into : {}", repositoryProperties.getGit().getUri(), repositoryProperties.getGit().getBranch(), wiremockServerConfig.getMappingsFilesDir() + CLONE_SUB_DIR);

			DirectoryUtil.forceDeleteDirectoryIfExists(Paths.get(wiremockServerConfig.getMappingsFilesDir(), CLONE_SUB_DIR).toString());

			Git localRepo = Git.cloneRepository()
					.setURI(repositoryProperties.getGit().getUri())
					.setDirectory(new File(Paths.get(wiremockServerConfig.getMappingsFilesDir(), CLONE_SUB_DIR).toString()))
					.setBranchesToClone(singleton(BRANCH_PREFIX + repositoryProperties.getGit().getBranch()))
					.setBranch(BRANCH_PREFIX + repositoryProperties.getGit().getBranch())
					.setTransportConfigCallback(propertiesBasedSshTransportConfigCallBack)
					.call();

			/**
			 * Repostory::getDirectory returns location to localDir/.git.
			 * We refer to it's parent to get directory to the source.
			 * http://javadox.com/org.eclipse.jgit/org.eclipse.jgit/3.3.2.201404171909-r/org/eclipse/jgit/lib/Repository.html#getDirectory()
			 */
			return Paths.get(localRepo.getRepository().getDirectory().getParent(), repositoryProperties.getGit().getPathToStub());
		} catch (GitAPIException | IOException ex) {
			throw new StubInitializationException(MessageFormat.format("Failed to fetch stubs from remote : {0}", ex.getMessage()),ex);
		}
	}

}
