package com.fbis.iplocalizator.models;

public class RestCountriesCurrencyModel {

	private String code, name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RestCountriesCurrencyModel [code=" + code + ", name=" + name + "]";
	}
	
	
	
}
