package com.fbis.iplocalizator.models;

public class Currency {

	private String codigo, nombre;
	private Double cambio;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getCambio() {
		return cambio;
	}
	public void setCambio(Double cambio) {
		this.cambio = cambio;
	}
	@Override
	public String toString() {
		return "Currency [codigo=" + codigo + ", nombre=" + nombre + ", cambio=" + cambio + "]";
	}
	
	
	
}
