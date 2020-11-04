package com.fbis.iplocalizator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fbis.iplocalizator.models.DistanceInfo;
import com.fbis.iplocalizator.models.IpInfoResponse;
import com.fbis.iplocalizator.models.IpInfoRequest;
import com.fbis.iplocalizator.services.IpLocalizatorService;
import com.fbis.iplocalizator.utils.IpUtils;

@Controller
public class IpLocalizatorController {

	IpLocalizatorService service;

	@Autowired
	IpLocalizatorController(IpLocalizatorService service) {
		this.service = service;
	}

	@GetMapping("/")
	public String index(Model model) {
		//Pantalla inicial y que muestra las estadisticas de las consultas
		DistanceInfo info = service.getDistanceInformation();
		model.addAttribute("minDist", info.getMin());
		model.addAttribute("maxDist", info.getMax());
		model.addAttribute("avgDist", info.getAvg());
		return "index";
	}

	@GetMapping("/ipinfo")
	public String ipinfo(Model model, IpInfoRequest req) {

		//Valida de que la IP sea una dirección IPv4 válida
		String ip = req.getIp().trim();
		if(!IpUtils.isValidIP(ip)) {
			model.addAttribute("errorMsj", "La cadena " + ip + " no es una dirección IPv4 válida ");
			return "error";
		}
		
		//Obtiene la información del pais y la moneda
		IpInfoResponse info = service.getIpInformation(ip);

		//Si el pais es nulo, es que no se encontró información del mismo
		if (info.getCountry() == null) {
			model.addAttribute("errorMsj", "La dirección IP " + info.getIp() + " no fue encontrada");
			return "error";
		}
		
		//Cargar vista
		model.addAttribute("ip", info.getIp());
		model.addAttribute("codigo", info.getCountry().getCodigo());
		model.addAttribute("nombre",  info.getCountry().getNombre());
		model.addAttribute("nombreNat", info.getCountry().getNombreNativo());
		model.addAttribute("codigo3",info.getCountry().getCodigo3().toLowerCase());
		model.addAttribute("monedas", info.getCountry().getMonedas());
		model.addAttribute("distancia", info.getCountry().getDistanciaBA());
		model.addAttribute("timezones", info.getCountry().getTimezonesNums());
		model.addAttribute("idiomas", info.getCountry().getIdiomas());
		model.addAttribute("latitud", info.getCountry().getLatitud());
		model.addAttribute("longitud", info.getCountry().getLongitud());
		return "ip-info";
	}

	@GetMapping("/error")
	public String error(Model model) {
		//Pantalla de error
		model.addAttribute("errorMsj", "There was an error");
		return "error";
	}
}
