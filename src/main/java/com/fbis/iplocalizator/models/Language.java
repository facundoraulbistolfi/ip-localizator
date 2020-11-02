package com.fbis.iplocalizator.models;

public class Language {

	private String codigo, codigo3, nombre, nombreNativo;

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

	@Override
	public String toString() {
		return "Language [codigo=" + codigo + ", codigo3=" + codigo3 + ", nombre=" + nombre + ", nombreNativo="
				+ nombreNativo + "]";
	}
	
	
}
