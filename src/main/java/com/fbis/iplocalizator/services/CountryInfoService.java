package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fbis.iplocalizator.models.Country;
import com.fbis.iplocalizator.models.RestCountriesCountryModel;
import com.fbis.iplocalizator.repositories.PaisesRepository;

@Component
public class CountryInfoService {

	@Autowired
	private PaisesRepository paisesRepository;
	@Autowired
	private RestCountriesService restCountriesService;
	
	public Country getCountryInfo(String id_country) {
		if(!paisesRepository.existsPais(id_country)) {
			//Si el pais no esta en la BD, obtener pais de la API y actualizar
			RestCountriesCountryModel nuevo = restCountriesService.getCountry(id_country);
			//Si el pais tampoco se encontró en la API, informar error
			if(nuevo == null) return null; //Retornar pais nulo equivale a error
			//Sino, actualizar
			paisesRepository.actualizar(nuevo);
		}
		Country c = paisesRepository.getPais(id_country);
		c.setMonedas(paisesRepository.getMonedas(id_country));
		c.setTimezones(paisesRepository.getTimezones(id_country));
		c.setIdiomas(paisesRepository.getIdiomas(id_country));
		return c;	
	}
	
}
