package com.peteratef.caching_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class CachingProxyApplication {

	public static void main(String[] args) {
		if(!Arrays.asList(args).contains(Consts.PORT_ARG_NAME)){
			throw new RuntimeException("Please specify port argument");
		}

		if(!Arrays.asList(args).contains(Consts.ORIGIN_ARG_NAME)){
			throw new RuntimeException("Please specify origin url argument");
		}


		if(!Arrays.asList(args).contains(Consts.TTL_ARG_NAME)){
			throw new RuntimeException("Please specify ttl argument");
		}

		var serverPort = args[1];
		var originUrl = args[3];
		var ttl = Long.parseLong(args[5]);
		System.out.println("Server port: " + serverPort);
		System.out.println("Origin url: " + originUrl);

		new SpringApplication(CachingProxyApplication.class)
				.run("--server.port=" + serverPort, "--global.origin=" + originUrl, "--global.ttl=" + ttl);
	}

}
