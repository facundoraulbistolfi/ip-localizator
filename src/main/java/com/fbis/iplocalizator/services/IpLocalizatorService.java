package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fbis.iplocalizator.models.Ip2CountryModel;
import com.fbis.iplocalizator.models.IpInfo;

@Component
public class IpLocalizatorService {

	@Autowired
	Ip2CountryService ip2CountryService;
	
	public IpInfo getIpInformation(String ip) {
		IpInfo info = new IpInfo();
		Ip2CountryModel ip2Country = this.ip2CountryService.getIpCountry(ip);
		
		info.setIp(ip);
		info.setPais(ip2Country.getCountryCode() + ip2Country.getCountryName());
		info.setDistancia("0 KM");
		info.setMoneda("ARS - Peso argentino (1 USD = 80 ARS)");
		info.setBandera("https://restcountries.eu/data/arg.svg");
		return info;
	}
}
