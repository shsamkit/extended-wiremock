package com.wm.extendedwiremock.stub.remoteinitializr;

import static com.wm.extendedwiremock.util.DirectoryUtil.forceMoveDirectoryIfExists;

import com.wm.extendedwiremock.config.properties.WiremockServerConfig;
import com.wm.extendedwiremock.exception.StubInitializationException;
import com.wm.extendedwiremock.stub.WiremockStubInitializr;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "remote.git.uri")
@Primary
public class WiremockStubInitializrFromRemote implements WiremockStubInitializr {

	@Autowired
	public WiremockStubInitializrFromRemote(RemoteFetcher remoteFetcher, WiremockServerConfig wiremockServerConfig) {
		this.remoteFetcher = remoteFetcher;
		this.wiremockServerConfig = wiremockServerConfig;
	}

	private final RemoteFetcher remoteFetcher;
	private final WiremockServerConfig wiremockServerConfig;
	private static final Logger logger = LoggerFactory.getLogger(WiremockStubInitializrFromRemote.class);

	/**
	 * From Wiremock documentation,
	 * __files: To read the body content from a file, place the file under the __files directory.
	 * mappings: To create the stub described above via the JSON API, the following document can either be posted to http://<host>:<port>/__admin/mappings or placed in a file with a .json extension under the mappings directory:
	 * Reference: http://wiremock.org/docs/stubbing
	 */
	private static final String __FILES = "__files";
	private static final String MAPPINGS = "mappings";


	public void initialize() {
		logger.info("Initializing remote stubs");
		Path pathToStub = remoteFetcher.fetchStubs();

		boolean filesFlag = false;
		boolean mappingsFlag = false;

		try {
			if (Files.exists(Paths.get(pathToStub.toString(), __FILES))) {
				forceMoveDirectoryIfExists(Paths.get(pathToStub.toString(), __FILES).toString(), Paths.get(wiremockServerConfig.getMappingsFilesDir(), __FILES).toString());
				logger.info("Files initializer successfully : {}", wiremockServerConfig.getMappingsFilesDir());
				filesFlag = true;
			}

			if (Files.exists(Paths.get(pathToStub.toString(), MAPPINGS))) {
				forceMoveDirectoryIfExists(Paths.get(pathToStub.toString(), MAPPINGS).toString(), Paths.get(wiremockServerConfig.getMappingsFilesDir(), MAPPINGS).toString());
				logger.info("Mappings initialized successfully : {}", wiremockServerConfig.getMappingsFilesDir());
				mappingsFlag = true;
			}

			if (!(filesFlag || mappingsFlag)) {
				logger.warn("No __files or mappings found in directory : {}", pathToStub);
			}

		} catch (IOException ex) {
			throw new StubInitializationException(MessageFormat.format("Failed to copy the stub files: check if {0} and/or {1} directories exist", MAPPINGS, __FILES),ex);
		}
	}


}
