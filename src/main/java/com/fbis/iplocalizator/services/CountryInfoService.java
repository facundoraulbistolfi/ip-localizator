package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fbis.iplocalizator.models.Country;
import com.fbis.iplocalizator.repositories.PaisesRepository;

@Component
public class CountryInfoService {

	@Autowired
	private PaisesRepository paisesRepository;
	
	public Country getCountryInfo(String id_country) {
		if(!paisesRepository.existsPais(id_country)) {
			//TODO: obtener pais de la API y actualizar
			System.out.println("EL PAIS NO EXISTE");
			return null;
		}
		Country c = paisesRepository.getPais(id_country);
		c.setMonedas(paisesRepository.getMonedas(id_country));
		c.setTimezones(paisesRepository.getTimezones(id_country));
		c.setIdiomas(paisesRepository.getIdiomas(id_country));
		return c;	
	}
	
}
