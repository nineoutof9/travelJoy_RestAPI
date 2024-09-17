package com.ict.traveljoy.config;

import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//RestTemplate 커넥션 풀을 사용하기 위한 빈 등록
//사전작업 build.gradle에 아래 등록(RestTemplate 사용시 커넥션 풀을 사용하기 위함)
//implementation 'org.apache.httpcomponents:httpclient:4.5.14'

@Configuration
public class RestTemplateConfig {

	static final int READ_TIMEOUT = 1500;
	static final int CONN_TIMEOUT = 3000;

	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setHttpClient(createHttpClient());
		return new RestTemplate(factory);
	}

	private HttpClient createHttpClient() {
		return HttpClientBuilder.create().setConnectionManager(createHttpClientConnectionManager()).build();
	}

	private HttpClientConnectionManager createHttpClientConnectionManager() {
		return PoolingHttpClientConnectionManagerBuilder.create()
				.setDefaultConnectionConfig(
						ConnectionConfig.custom().setSocketTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
								.setConnectTimeout(CONN_TIMEOUT, TimeUnit.MILLISECONDS).build())
				.build();
	}
}
