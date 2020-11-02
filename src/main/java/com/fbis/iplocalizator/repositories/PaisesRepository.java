package com.fbis.iplocalizator.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fbis.iplocalizator.mapper.CountryMapper;
import com.fbis.iplocalizator.mapper.CurrencyMapper;
import com.fbis.iplocalizator.mapper.LanguageMapper;
import com.fbis.iplocalizator.models.Country;
import com.fbis.iplocalizator.models.Currency;
import com.fbis.iplocalizator.models.Language;

@Repository
public class PaisesRepository {

    @Autowired
    private JdbcTemplate template;

	public boolean existsPais(String id) {
		String sql = "SELECT count(*) FROM paises WHERE codigo = ?";
		int count = template.queryForObject(sql, new Object[] {id}, Integer.class);
		return count > 0;
	}
	
	public Country getPais(String id) {
		return template.queryForObject("SELECT * FROM paises WHERE codigo = ?", new Object[]{id}, new CountryMapper());
	}
	
	public List<Currency> getMonedas(String id) {
		return template.query(
				"select m.codigo, m.nombre, m.cambio "
				+ "from monedas_por_pais mpp "
				+ "join monedas m "
				+ "on mpp.codigoMoneda = m.codigo "
				+ "where mpp.codigoPais = ?", new Object[]{id}, new CurrencyMapper());
	}
	
	public List<String> getTimezones(String id){
		return template.queryForList(
				"select t.timezone "
				+ "from timezones_por_pais tpp "
				+ "join timezones t "
				+ "on t.timezone = tpp.timezone "
				+ "where tpp.codigoPais = ? ", new Object[]{id}, String.class);
	}
	
	public List<Language> getIdiomas(String id){
		return template.query(
				"select i.codigo, i.codigo3, i.nombre, i.nombreNat "
				+ "from idiomas_por_pais ipp "
				+ "join idiomas i "
				+ "on i.codigo = ipp.codigoIdioma "
				+ "where ipp.codigoPais = ? ", new Object[]{id}, new LanguageMapper());
	}
	
	public int registrarConsulta(String ip, String pais) {
		return template.update("INSERT INTO registro(ip,codigoPais) VALUES (?, ?)", ip, pais);
	}
	
	public int agregarPais(Country c) {
		String sql = "INSERT INTO "
				+ "paises(codigo,codigo3,codigonum,"
				+ "nombre,nombreNat,latitud,longitud,distanciaBA) "
				+ "values (?,?,?,?,?,?,?,?)";
		return template.update(sql, c.getCodigo(), c.getCodigo3(), c.getCodigoNum(),
				c.getNombre(), c.getNombreNativo(), c.getLatitud(), c.getLongitud(),
				c.getDistanciaBA());
	}
	
	public Double distanciaMasLejana() {
		return template.queryForObject("select MAX(p.distanciaBA) "
				+ "FROM registro r "
				+ "join paises p "
				+ "on r.codigoPais = p.codigo", Double.class);
	}
	
	public Double distanciaMasCercana() {
		return template.queryForObject("select MIN(p.distanciaBA) "
				+ "FROM registro r "
				+ "join paises p "
				+ "on r.codigoPais = p.codigo", Double.class);
	}
	
	public Double distanciaPromedio() {
		return template.queryForObject("select SUM(p.distanciaBA) / count(1) AS \"promedio\" "
				+ "	FROM registro r "
				+ " join paises p "
				+ " on r.codigoPais = p.codigo", Double.class);
	}
	
}
