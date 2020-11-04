package com.fbis.iplocalizator.models;

import java.util.Arrays;
import java.util.List;

import com.fbis.iplocalizator.utils.HaversineUtils;

public class RestCountriesCountryModel {

	private String alpha2Code, alpha3Code, numericCode, name, nativeName;
	private Double[] latlng;
	List<RestCountriesCurrencyModel> currencies;
	List<RestCountriesLanguageModel> languages;
	List<String> timezones;
	public String getAlpha2Code() {
		return alpha2Code;
	}
	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}
	public String getAlpha3Code() {
		return alpha3Code;
	}
	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}
	public String getNumericCode() {
		return numericCode;
	}
	public void setNumericCode(String numericCode) {
		this.numericCode = numericCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNativeName() {
		return nativeName;
	}
	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}
	public Double[] getLatlng() {
		return latlng;
	}
	public void setLatlng(Double[] latlng) {
		this.latlng = latlng;
	}
	public List<RestCountriesCurrencyModel> getCurrencies() {
		return currencies;
	}
	public void setCurrencies(List<RestCountriesCurrencyModel> currencies) {
		this.currencies = currencies;
	}
	public List<RestCountriesLanguageModel> getLanguages() {
		return languages;
	}
	public void setLanguages(List<RestCountriesLanguageModel> languages) {
		this.languages = languages;
	}
	public List<String> getTimezones() {
		return timezones;
	}
	public void setTimezones(List<String> timezones) {
		this.timezones = timezones;
	}
	
	public Double getLatitud() {
		return ((this.latlng != null) && (this.latlng.length >= 2))?this.latlng[0]:null;
	}
	
	public Double getLongitud() {
		return ((this.latlng != null) && (this.latlng.length >= 2))?this.latlng[1]:null;
	}
	
	public Double getDistanciaBA() {
		return ((this.latlng != null) && (this.latlng.length >= 2))?HaversineUtils.distancia_BuenosAires(this.latlng[0], this.latlng[1]):null;
	}
	
	
	@Override
	public String toString() {
		return "RestCountriesCountryModel [alpha2Code=" + alpha2Code + ", alpha3Code=" + alpha3Code + ", numericCode="
				+ numericCode + ", name=" + name + ", nativeName=" + nativeName + ", latlng=" + Arrays.toString(latlng)
				+ ", currencies=" + currencies + ", languages=" + languages + ", timezones=" + timezones + "]";
	}
	
	
}
