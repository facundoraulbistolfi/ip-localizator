package com.fbis.iplocalizator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fbis.iplocalizator.models.DistanceInfo;
import com.fbis.iplocalizator.models.IpInfo;
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
		DistanceInfo info = service.getDistanceInformation();
		model.addAttribute("minDist", String.format("%.2f",info.getMin()) + " Km");
		model.addAttribute("maxDist", String.format("%.2f",info.getMax()) + " Km");
		model.addAttribute("avgDist", String.format("%.2f",info.getAvg()) + " Km");
		return "index";
	}

	@PostMapping("/ipinfo")
	public String ipinfo(Model model, IpInfoRequest req) {

		String ip = req.getIp_address().trim();
		if(!IpUtils.isValidIP(ip)) {
			model.addAttribute("errorMsj", "La cadena " + ip + " no es una dirección IPv4 válida ");
			return "error";
		}
		
		IpInfo info = service.getIpInformation(ip);

		if (info.getCountry() == null) {
			model.addAttribute("errorMsj", "La dirección IP " + info.getIp() + " no fue encontrada");
			return "error";
		}
		
		model.addAttribute("ip", info.getIp());
		model.addAttribute("codigo", info.getCountry().getCodigo());
		model.addAttribute("nombre",  info.getCountry().getNombre());
		model.addAttribute("nombreNat", info.getCountry().getNombreNativo());
		model.addAttribute("bandera",
				"https://restcountries.eu/data/" + info.getCountry().getCodigo3().toLowerCase() + ".svg");
		model.addAttribute("monedas", info.getCountry().getMonedas());
		model.addAttribute("distancia", String.format("%.2f",info.getCountry().getDistanciaBA()));
		model.addAttribute("timezones", info.getCountry().getTimezonesNums());
		model.addAttribute("idiomas", info.getCountry().getIdiomas());
		model.addAttribute("latitud", String.format("%.2f",info.getCountry().getLatitud()));
		model.addAttribute("longitud", String.format("%.2f",info.getCountry().getLongitud()));
		
		
		return "ip-info";
	}

	@GetMapping("/error")
	public String error(Model model) {
		model.addAttribute("errorMsj", "There was an error");
		return "error";
	}
}
