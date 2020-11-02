package com.fbis.iplocalizator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fbis.iplocalizator.repositories.PaisesRepository;

@Component
public class RegistroService {

	@Autowired
	private PaisesRepository paisesRepository;
	
	public int registrarConsulta(String ip, String pais) {
		return paisesRepository.registrarConsulta(ip, pais);
	}
	
	public double distanciaMasCorta() {
		return 0.;
	}
	
	public double distanciaMasLarga() {
		return 0.;
	}
	
	public double distanciaPromedio() {
		return 0.;
	}
	
}
