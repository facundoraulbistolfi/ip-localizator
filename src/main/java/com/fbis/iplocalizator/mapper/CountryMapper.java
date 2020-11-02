package com.fbis.iplocalizator.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fbis.iplocalizator.models.Country;

public class CountryMapper implements RowMapper<Country> {

	@Override
	public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
		Country pais = new Country();
		pais.setCodigo(rs.getString("codigo"));
		pais.setCodigo3(rs.getString("codigo3"));
		pais.setCodigoNum(rs.getString("codigoNum"));
		pais.setNombre(rs.getString("nombre"));
		pais.setNombreNativo(rs.getString("nombreNat"));
		pais.setLatitud(rs.getDouble("latitud"));
		pais.setLongitud(rs.getDouble("longitud"));
		pais.setDistanciaBA(rs.getDouble("distanciaBA"));
		return pais;
	}
}
