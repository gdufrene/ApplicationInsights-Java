package com.microsoft.applicationinsights.internal.channel.common;

import java.io.IOException;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeFunctions;

public class WebClientSender {

	public static HttpSender create() {
		return new SpringHttpSender();
	}

}

class SpringHttpSender implements HttpSender {
	
	ExchangeFunction exchangeFunction = ExchangeFunctions.create(new ReactorClientHttpConnector());

	@Override
	public ClientResponse sendPostRequest(ClientRequest request) throws IOException {
		return exchangeFunction.exchange(request).block();
	}


	@Override
	public void enhanceRequest(ClientRequest request) {
		// TODO Auto-generated method stub
		
	}
	
}