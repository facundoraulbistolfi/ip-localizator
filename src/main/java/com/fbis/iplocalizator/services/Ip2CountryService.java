package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fbis.iplocalizator.models.Ip2CountryModel;

@Component
public class Ip2CountryService {

	@Autowired
	private RestTemplate restTemplate;
	
	public Ip2CountryModel getIpCountry(String ip)
	{
	    final String uri = "https://api.ip2country.info/ip?";
	    Ip2CountryModel result = restTemplate.getForObject(uri+ip, Ip2CountryModel.class);
	    return result;
	} 
	
	@Bean
	public RestTemplate restTemplate() {
	 
	    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	    factory.setConnectTimeout(3000);
	    factory.setReadTimeout(3000);
	    return new RestTemplate(factory);
	}
}
