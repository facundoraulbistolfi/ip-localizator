package com.fbis.iplocalizator.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fbis.iplocalizator.mapper.CountryMapper;
import com.fbis.iplocalizator.mapper.CurrencyMapper;
import com.fbis.iplocalizator.mapper.LanguageMapper;
import com.fbis.iplocalizator.models.Country;
import com.fbis.iplocalizator.models.Currency;
import com.fbis.iplocalizator.models.Language;
import com.fbis.iplocalizator.models.RestCountriesCountryModel;
import com.fbis.iplocalizator.models.RestCountriesCurrencyModel;
import com.fbis.iplocalizator.models.RestCountriesLanguageModel;

@Repository
public class PaisesRepository {

	@Autowired
	private JdbcTemplate template;

	public boolean existsPais(String id) {
		String sql = "SELECT count(*) FROM paises WHERE codigo = ?";
		int count = template.queryForObject(sql, new Object[] { id }, Integer.class);
		return count > 0;
	}

	public Country getPais(String id) {
		return template.queryForObject("SELECT * FROM paises WHERE codigo = ?", new Object[] { id },
				new CountryMapper());
	}

	public List<Currency> getMonedas(String id) {
		return template.query(
				"select m.codigo, m.nombre, m.cambio " + "from monedas_por_pais mpp " + "join monedas m "
						+ "on mpp.codigoMoneda = m.codigo " + "where mpp.codigoPais = ?",
				new Object[] { id }, new CurrencyMapper());
	}

	public List<String> getTimezones(String id) {
		return template.queryForList(
				"select t.timezone " + "from timezones_por_pais tpp " + "join timezones t "
						+ "on t.timezone = tpp.timezone " + "where tpp.codigoPais = ? ",
				new Object[] { id }, String.class);
	}

	public List<Language> getIdiomas(String id) {
		return template.query(
				"select i.codigo, i.codigo3, i.nombre, i.nombreNat " + "from idiomas_por_pais ipp " + "join idiomas i "
						+ "on i.codigo = ipp.codigoIdioma " + "where ipp.codigoPais = ? ",
				new Object[] { id }, new LanguageMapper());
	}

	public int registrarConsulta(String ip, String pais) {
		return template.update("INSERT INTO registro(ip,codigoPais) VALUES (?, ?)", ip, pais);
	}

	public int agregarPais(Country c) {
		String sql = "INSERT INTO " + "paises(codigo,codigo3,codigonum,"
				+ "nombre,nombreNat,latitud,longitud,distanciaBA) " + "values (?,?,?,?,?,?,?,?)";
		return template.update(sql, c.getCodigo(), c.getCodigo3(), c.getCodigoNum(), c.getNombre(), c.getNombreNativo(),
				c.getLatitud(), c.getLongitud(), c.getDistanciaBA());
	}

	public Double distanciaMasLejana() {
		return template.queryForObject(
				"select MAX(p.distanciaBA) " + "FROM registro r " + "join paises p " + "on r.codigoPais = p.codigo",
				Double.class);
	}

	public Double distanciaMasCercana() {
		return template.queryForObject(
				"select MIN(p.distanciaBA) " + "FROM registro r " + "join paises p " + "on r.codigoPais = p.codigo",
				Double.class);
	}

	public Double distanciaPromedio() {
		return template.queryForObject("select SUM(p.distanciaBA) / count(1) AS \"promedio\" " + "	FROM registro r "
				+ " join paises p " + " on r.codigoPais = p.codigo", Double.class);
	}

	public void actualizar(RestCountriesCountryModel pais) {
		// Insertar pais en BD
		String sql = "INSERT INTO " + "paises(codigo,codigo3,codigonum,"
				+ "nombre,nombreNat,latitud,longitud,distanciaBA) " + "values (?,?,?,?,?,?,?,?)";
		template.update(sql, pais.getAlpha2Code(), pais.getAlpha3Code(), pais.getNumericCode(), pais.getName(),
				pais.getNativeName(), pais.getLatitud(), pais.getLongitud(), pais.getDistanciaBA());

		// Actualizo tabla de idiomas
		if (pais.getLanguages() != null)
			for (RestCountriesLanguageModel l : pais.getLanguages()) {
				// Si el idioma no existe, lo agrego a la tabla
				if (template.queryForObject("SELECT count(*) FROM idiomas WHERE codigo = ?",
						new Object[] { l.getIso639_1() }, Integer.class) == 0) {
					template.update("INSERT INTO idiomas(codigo, codigo3, nombre, nombreNat) values (?,?,?,?)",
							l.getIso639_1(), l.getIso639_2(), l.getName(), l.getNativeName());
				}
				// Si la relación entre el idioma y el pais no existe, la agrego a la tabla
				if (template.queryForObject(
						"SELECT count(*) FROM idiomas_por_pais WHERE codigoPais = ? AND codigoIdioma = ?",
						new Object[] { pais.getAlpha2Code(), l.getIso639_1() }, Integer.class) == 0) {
					template.update("INSERT INTO idiomas_por_pais(codigoPais, codigoIdioma) values (?,?)",
							pais.getAlpha2Code(), l.getIso639_1());
				}
			}

		// Actualizo tabla de idiomas
		if (pais.getCurrencies() != null)
			for (RestCountriesCurrencyModel c : pais.getCurrencies()) {
				// Si la moneda no existe, la agrego a la tabla
				if (template.queryForObject("SELECT count(*) FROM monedas WHERE codigo = ?",
						new Object[] { c.getCode() }, Integer.class) == 0) {
					template.update("INSERT INTO monedas(codigo, nombre) values (?,?)", c.getCode(), c.getName());
				}
				// Si la relación entre la moneda y el pais no existe, la agrego a la tabla
				if (template.queryForObject(
						"SELECT count(*) FROM monedas_por_pais WHERE codigoPais = ? AND codigoMoneda = ?",
						new Object[] { pais.getAlpha2Code(), c.getCode() }, Integer.class) == 0) {
					template.update("INSERT INTO monedas_por_pais(codigoPais, codigoMoneda) values (?,?)",
							pais.getAlpha2Code(), c.getCode());
				}
			}

		// Actualizo tabla de timezones
		if (pais.getTimezones() != null)
			for (String t : pais.getTimezones()) {
				System.out.println("TIMEZONE " + t);
				// Si la moneda no existe, la agrego a la tabla
				if (template.queryForObject("SELECT count(*) FROM timezones WHERE timezone = ?", new Object[] { t },
						Integer.class) == 0) {
					template.update("INSERT INTO timezones(timezone) values (?)", t);
				}
				// Si la relación entre la moneda y el pais no existe, la agrego a la tabla
				if (template.queryForObject(
						"SELECT count(*) FROM timezones_por_pais WHERE codigoPais = ? AND timezone = ?",
						new Object[] { pais.getAlpha2Code(), t }, Integer.class) == 0) {
					template.update("INSERT INTO timezones_por_pais(codigoPais, timezone) values (?,?)",
							pais.getAlpha2Code(), t);
				}
			}
	}

	public String getPaisPorIPPorUltimosDias(String ip) {

			try {
			     return template.queryForObject(
			    		 "SELECT codigoPais "
			    		 + "FROM registro "
			    		 + "WHERE ip = ? "
			    		 + "AND createdAt >= NOW() - INTERVAL 5 DAY "
			    		 + "ORDER BY createdAt desc LIMIT 0,1 ",new Object[] { ip }, String.class);
			} 
			catch (EmptyResultDataAccessException e) {
			   return null;
			}
	}

}
