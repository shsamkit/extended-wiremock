package com.wm.extendedwiremcok;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExtendedWiremcokApplication implements CommandLineRunner  {

	@Autowired
	private WireMockServer wiremockServer;

	public static void main(String[] args) {
		SpringApplication.run(ExtendedWiremcokApplication.class, args);
	}

	@Override
	public void run(String... args) {
		wiremockServer.start();
	}
}
