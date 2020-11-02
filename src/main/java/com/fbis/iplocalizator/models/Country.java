package com.fbis.iplocalizator.models;

import java.util.List;

public class Country {

	private String codigo, codigo3, codigoNum;
	private String nombre, nombreNativo;
	private Double latitud, longitud, distanciaBA;
	private List<String> timezones;
	private List<Currency> monedas;
	private List<Language> idiomas;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCodigo3() {
		return codigo3;
	}
	public void setCodigo3(String codigo3) {
		this.codigo3 = codigo3;
	}
	public String getCodigoNum() {
		return codigoNum;
	}
	public void setCodigoNum(String codigoNum) {
		this.codigoNum = codigoNum;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreNativo() {
		return nombreNativo;
	}
	public void setNombreNativo(String nombreNativo) {
		this.nombreNativo = nombreNativo;
	}
	public Double getLatitud() {
		return latitud;
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public List<String> getTimezones() {
		return timezones;
	}
	public void setTimezones(List<String> timezones) {
		this.timezones = timezones;
	}
	public List<Currency> getMonedas() {
		return monedas;
	}
	public void setMonedas(List<Currency> monedas) {
		this.monedas = monedas;
	}
	public List<Language> getIdiomas() {
		return idiomas;
	}
	public void setIdiomas(List<Language> idiomas) {
		this.idiomas = idiomas;
	}
	
	public Long getDistanciaBsAs() {
		return 0L;
	}
	
	@Override
	public String toString() {
		return "Country [codigo=" + codigo + ", codigo3=" + codigo3 + ", codigoNum=" + codigoNum + ", nombre=" + nombre
				+ ", nombreNativo=" + nombreNativo + ", latitud=" + latitud + ", longitud=" + longitud + ", timezones="
				+ timezones + ", monedas=" + monedas + ", idiomas=" + idiomas + "]";
	}
	public Double getDistanciaBA() {
		return distanciaBA;
	}
	public void setDistanciaBA(Double distanciaBA) {
		this.distanciaBA = distanciaBA;
	}
	
	
}
