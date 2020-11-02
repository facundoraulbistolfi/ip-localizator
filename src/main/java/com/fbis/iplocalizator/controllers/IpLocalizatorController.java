package com.fbis.iplocalizator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.fbis.iplocalizator.models.IpInfo;
import com.fbis.iplocalizator.models.IpInfoRequest;
import com.fbis.iplocalizator.services.IpLocalizatorService;

@Controller
public class IpLocalizatorController {

	IpLocalizatorService service;
	
	@Autowired
	IpLocalizatorController(IpLocalizatorService service){
		this.service = service;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@PostMapping("/ipinfo")
	public String ipinfo(Model model, IpInfoRequest req) {
		IpInfo info = service.getIpInformation(req.getIp_address());
		
		if(info.getCountry() == null) {
			model.addAttribute("ip", info.getIp());
			return "ip-notfound";
		}
		
		model.addAttribute("ip", info.getIp());
		model.addAttribute("nombre", info.getCountry().getCodigo() +" - " +info.getCountry().getNombre());
		model.addAttribute("bandera", "https://restcountries.eu/data/"+info.getCountry().getCodigo3().toLowerCase()+".svg");
		model.addAttribute("moneda", "");
		model.addAttribute("distancia", info.getCountry().getDistanciaBsAs());
		return "ip-info";
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}
}
