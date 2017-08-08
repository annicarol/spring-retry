package com.exemplo.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class RetryCall {

	@Autowired
	private Environment env;

	@Autowired
	private RetryTemplate retryTemplate;

	public void chamadaComRetry() {
		retryTemplate.setBackOffPolicy(buildBackoffPolice());
		retryTemplate.setRetryPolicy(buildRetryPolice());

		RuntimeException resultException = retryTemplate
				.execute(new RetryCallback<RuntimeException, RuntimeException>() {

					@Override
					public RuntimeException doWithRetry(RetryContext context) throws RuntimeException {
						RuntimeException exception = null;
						try {

							sendRequestToMock();

						} catch (HttpStatusCodeException e) {
							exception = e;
							if (!e.getStatusCode().is2xxSuccessful()) {
								throw e;
							}
						}
						return exception;
					}
				});
		if (resultException != null) {
			throw resultException;
		}
	}

	private SimpleRetryPolicy buildRetryPolice() {
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();

		String maxTentativasStr = env.getProperty("maximo.tentativas");
		int maxTentativas = Integer.valueOf(maxTentativasStr);

		retryPolicy.setMaxAttempts(maxTentativas);

		return retryPolicy;
	}

	private FixedBackOffPolicy buildBackoffPolice() {
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();

		String maxTempoStr = env.getProperty("maximo.tempo");
		int maxTempo = Integer.valueOf(maxTempoStr);

		backOffPolicy.setBackOffPeriod(maxTempo);

		return backOffPolicy;
	}
	
	private void sendRequestToMock() {
		
		RestTemplate restTemplate = new RestTemplate();
		String mockUrlErro500 = "http://www.mocky.io/v2/59891678270000800faff032";
		ResponseEntity<String> response = restTemplate.getForEntity(mockUrlErro500, String.class);		
		if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
		}
	}

}