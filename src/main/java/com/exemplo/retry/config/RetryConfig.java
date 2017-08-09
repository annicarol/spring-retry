package com.exemplo.retry.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry
@SpringBootApplication
public class RetryConfig {

	@Bean
	public RetryTemplate createRetryTemplate() {
		return new RetryTemplate();
	}
}