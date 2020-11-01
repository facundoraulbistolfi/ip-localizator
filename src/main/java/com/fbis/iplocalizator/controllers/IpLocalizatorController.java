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
		model.addAttribute("ip", info.getIp());
		model.addAttribute("nombre", info.getPais());
		model.addAttribute("bandera", info.getBandera());
		model.addAttribute("moneda", info.getMoneda());
		model.addAttribute("distancia", info.getDistancia());
		return "ip-info";
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}
}
