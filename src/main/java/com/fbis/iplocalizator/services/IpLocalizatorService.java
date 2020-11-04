package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fbis.iplocalizator.models.Country;
import com.fbis.iplocalizator.models.DistanceInfo;
import com.fbis.iplocalizator.models.Ip2CountryModel;
import com.fbis.iplocalizator.models.IpInfoResponse;

@Component
public class IpLocalizatorService {

	@Autowired
	Ip2CountryService ip2CountryService;
	@Autowired
	CountryInfoService countryInfoService;
	@Autowired
	RegistroService registroService;
	
	public IpInfoResponse getIpInformation(String ip) {
		IpInfoResponse info = new IpInfoResponse();
		info.setIp(ip);
		
		//Reviso primero si no está entre las ultimas consultas de la BD
		String codigoPais = registroService.getPaisIP(ip);
		boolean enDB = true;
		if(codigoPais == null) {
			//Sino, busco la IP en la API
			Ip2CountryModel ip2Country = this.ip2CountryService.getIpCountry(ip);
			codigoPais = ip2Country.getCountryCode();
			enDB = false;
		}
		
		//Reviso si se me devolvio algun codigo de pais
		if(StringUtils.hasText(codigoPais)) {
			//Obtengo la información del pais
			Country c = countryInfoService.getCountryInfo(codigoPais);
			if(c != null) { //Si la IP corresponde a un pais
				info.setCountry(c);
				//Registrar consulta
				registroService.registrarConsulta(enDB?ip+"'":ip, c.getCodigo());
			}
		}
		return info;
	}
	
	public DistanceInfo getDistanceInformation() {
		return new DistanceInfo(registroService.distanciaMasLarga(), registroService.distanciaMasCorta(), registroService.distanciaPromedio());
		
	}
}
