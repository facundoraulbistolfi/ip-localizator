package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fbis.iplocalizator.models.Country;
import com.fbis.iplocalizator.models.DistanceInfo;
import com.fbis.iplocalizator.models.Ip2CountryModel;
import com.fbis.iplocalizator.models.IpInfo;

@Component
public class IpLocalizatorService {

	@Autowired
	Ip2CountryService ip2CountryService;
	@Autowired
	CountryInfoService countryInfoService;
	@Autowired
	RegistroService registroService;
	
	public IpInfo getIpInformation(String ip) {
		IpInfo info = new IpInfo();
		//Set country
		Ip2CountryModel ip2Country = this.ip2CountryService.getIpCountry(ip);
		info.setIp(ip);
		if(StringUtils.hasText(ip2Country.getCountryCode())) {
			//Get country info
			Country c = countryInfoService.getCountryInfo(ip2Country.getCountryCode());
			if(c != null) {
				info.setCountry(c);
				//Registrar consulta
				registroService.registrarConsulta(ip, c.getCodigo());
			}
		}
		return info;
	}
	
	public DistanceInfo getDistanceInformation() {
		return new DistanceInfo(registroService.distanciaMasLarga(), registroService.distanciaMasCorta(), registroService.distanciaPromedio());
		
	}
}
