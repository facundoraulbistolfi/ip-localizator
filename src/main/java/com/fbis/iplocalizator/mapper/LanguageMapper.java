package com.fbis.iplocalizator.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fbis.iplocalizator.models.Language;

public class LanguageMapper implements RowMapper<Language> {

	@Override
	public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
		Language language = new Language();
		language.setCodigo(rs.getString("codigo"));
		language.setCodigo3(rs.getString("codigo3"));
		language.setNombre(rs.getString("nombre"));
		language.setNombreNativo(rs.getString("nombreNat"));
		return language;
	}
}