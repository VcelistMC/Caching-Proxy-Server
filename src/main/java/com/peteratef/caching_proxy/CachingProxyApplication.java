package com.peteratef.caching_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CachingProxyApplication {

	public static void main(String[] args) {
		for(String arg: args){
			System.out.println(arg);
		}
		SpringApplication.run(CachingProxyApplication.class, args);
	}

}
