package com.fbis.iplocalizator.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fbis.iplocalizator.models.Currency;

public class CurrencyMapper implements RowMapper<Currency> {

	@Override
	public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
		Currency currency = new Currency();
		currency.setCodigo(rs.getString("codigo"));
		currency.setNombre(rs.getString("nombre"));
		currency.setCambio(rs.getDouble("cambio"));
		return currency;
	}
}