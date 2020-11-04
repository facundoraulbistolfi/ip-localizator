package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fbis.iplocalizator.models.RestCountriesCountryModel;

@Component
public class RestCountriesService {

	@Autowired
	private RestTemplate restTemplate;
	
	public RestCountriesCountryModel getCountry(String id)
	{
	    final String uri = "https://restcountries.eu/rest/v2/alpha/";
	    if(id == null) return null;
	    try {
	    	RestCountriesCountryModel result = restTemplate.getForObject(uri+id, RestCountriesCountryModel.class);
		    return result;
	    }catch(Exception e) {
	    	System.err.println(e);
	    	return null;
	    }
	} 
	
	
}
