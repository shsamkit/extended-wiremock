package com.wm.extendedwiremock.stub.defaultinitializr;

import com.wm.extendedwiremock.stub.WiremockStubInitializr;
import com.wm.extendedwiremock.stub.remoteinitializr.WiremockStubInitializrFromRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WiremockStubInitializrDefault implements WiremockStubInitializr {

	private static final Logger logger = LoggerFactory.getLogger(WiremockStubInitializrFromRemote.class);

	public void initialize() {
		logger.info("Using default initializr, nothing to do.");
		return;
	}


}
