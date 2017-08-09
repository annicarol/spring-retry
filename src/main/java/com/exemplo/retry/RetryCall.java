package com.exemplo.retry;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class RetryCall {
	
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
							System.out.println("Data: " + new Date() + " ::: Tentativa: " + (context.getRetryCount() + 1));
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

		//String maxTentativasStr = env.getProperty("maximo.tentativas");
		//int maxTentativas = Integer.valueOf(maxTentativasStr);

		retryPolicy.setMaxAttempts(3);

		return retryPolicy;
	}

	private FixedBackOffPolicy buildBackoffPolice() {
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();

		//String maxTempoStr = env.getProperty("maximo.tempo");
		//int maxTempo = Integer.valueOf(maxTempoStr);

		backOffPolicy.setBackOffPeriod(5000L);

		return backOffPolicy;
	}
	
	private ResponseEntity<String> sendRequestToMock() {
		RestTemplate restTemplate = new RestTemplate();
		String mockUrlErro500 = "http://www.mocky.io/v2/59891678270000800faff032";
		//String mockUrlSucesso200 = "http://www.mocky.io/v2/598a7cd44100000b19821157";
		return restTemplate.getForEntity(mockUrlErro500, String.class);		

	}

}