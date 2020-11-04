# -*- coding: utf-8 -*-
"""
Created on Sat Oct 31 16:32:22 2020

@author: Facu
"""
import json
import requests
import mysql.connector
from datetime import datetime
from math import radians, cos, sin, asin, sqrt, atan2

"""
Funciones
"""
#Funcion para calcular la distancia
def haversine(origin, destination):
    lat1, lon1 = origin
    lat2, lon2 = destination
    radius = 6371 # km

    dlat = radians(lat2-lat1)
    dlon = radians(lon2-lon1)
    a = sin(dlat/2) * sin(dlat/2) + cos(radians(lat1)) \
        * cos(radians(lat2)) * sin(dlon/2) * sin(dlon/2)
    c = 2 * atan2(sqrt(a), sqrt(1-a))
    d = radius * c

    return d

#Actualizar la tabla de idiomas, y la de idiomas por pais
def actualizarIdiomas(country, listaIdiomas):
    for idioma in listaIdiomas:
        if(idioma['iso639_1'] is not None):
            #Si no existe en la tabla de idiomas, lo agrego
            mycursor.execute("SELECT codigo FROM idiomas WHERE codigo = %s ", (idioma['iso639_1'],))
            mycursor.fetchall()
            
            if mycursor.rowcount == 0:
                print("Agregar idioma")
                print(idioma)
                mycursor.execute("INSERT INTO idiomas (codigo, codigo3, nombre, nombreNat) VALUES(%s,%s,%s,%s)", (idioma['iso639_1'],idioma['iso639_2'],idioma['name'],idioma['nativeName']))
                mydb.commit()
            
            #Si no existe la relacion entre el idioma y el pais, la creo
            mycursor.execute("SELECT codigoIdioma, codigoPais FROM idiomas_por_pais WHERE codigoIdioma = %s AND codigoPais = %s",(idioma['iso639_1'],country))
            mycursor.fetchall()
            if mycursor.rowcount == 0:
                mycursor.execute("INSERT INTO idiomas_por_pais (codigoIdioma, codigoPais) VALUES(%s,%s)", (idioma['iso639_1'],country))
                mydb.commit()
                
#Actualizar la tabla de monedas, y la de monedas por pais
def actualizarMonedas(country, listaMonedas):
    for moneda in listaMonedas:
        if(moneda['code'] is not None) and (len(moneda['code']) == 3):
            #Si no existe en la tabla de monedas, lo agrego
            mycursor.execute("SELECT codigo, COUNT(*) FROM monedas WHERE codigo = %s GROUP BY codigo",(moneda['code'],))
            mycursor.fetchall()
            
            if mycursor.rowcount == 0:
                print("Agregar moneda")
                print(moneda)
                mycursor.execute("INSERT INTO monedas (codigo, nombre) VALUES(%s,%s)", (moneda['code'],moneda['name']))
                mydb.commit()
            
            #Si no existe la relacion entre la moneda y el pais, la creo
            mycursor.execute("SELECT codigoMoneda, codigoPais FROM monedas_por_pais WHERE codigoMoneda = %s AND codigoPais = %s",(moneda['code'],country))
            mycursor.fetchall()
            if mycursor.rowcount == 0:
                mycursor.execute("INSERT INTO monedas_por_pais (codigoMoneda, codigoPais) VALUES(%s,%s)", (moneda['code'],country))
                mydb.commit()
                
#Actualizar la tabla de timezones, y la de timezones por pais
def actualizarZonasHorarias(country, listaTimezones):
    for timezone in listaTimezones:
        if(timezone is not None):
            #Si no existe en la tabla de timezones, lo agrego
            mycursor.execute("SELECT timezone, COUNT(*) FROM timezones WHERE timezone = %s GROUP BY timezone",(timezone,))
            mycursor.fetchall()
            
            if mycursor.rowcount == 0:
                print("Agregar timezone")
                print(timezone)
                mycursor.execute("INSERT INTO timezones (timezone) VALUES(%s)", (timezone,))
                mydb.commit()
            
            #Si no existe la relacion entre el timezone y el pais, la creo
            mycursor.execute("SELECT timezone, codigoPais FROM timezones_por_pais WHERE timezone = %s AND codigoPais = %s",(timezone,country))
            mycursor.fetchall()
            if mycursor.rowcount == 0:
                mycursor.execute("INSERT INTO timezones_por_pais (timezone, codigoPais) VALUES(%s,%s)", (timezone,country))
                mydb.commit()

#Actualizar la tabla de paises
def actualizarPais(pais):
    print(pais['alpha2Code'])
    if((pais['alpha2Code'] is not None) and (len(pais['alpha2Code']) == 2)):
        #Comprueba de que si ya existe un registro en la base de datos
        mycursor.execute("SELECT codigo, COUNT(*) FROM paises WHERE codigo = %s GROUP BY codigo",(pais['alpha2Code'],))
        mycursor.fetchall()
        if mycursor.rowcount == 0:
            #Si no existe, lo crea
            mycursor.execute("INSERT INTO paises"+
                             "(codigo, codigo3, codigoNum, nombre, nombreNat, latitud, longitud, distanciaBA)"+
                             " VALUES (%s,%s,%s,%s,%s,%s,%s,%s)",
                             (pais['alpha2Code'],pais['alpha3Code'],pais['numericCode'],
                             pais['name'],pais['nativeName'],
                             float(pais['latlng'][0]) if ((pais['latlng'] is not None)and(len(pais['latlng'])==2)) else None,
                             float(pais['latlng'][1]) if ((pais['latlng'] is not None)and(len(pais['latlng'])==2)) else None,
                             haversine((-34.0, -64.0), (float(pais['latlng'][0]), float(pais['latlng'][1]))) if ((pais['latlng'] is not None)and(len(pais['latlng'])==2)) else None,
                              ))
            mydb.commit()
            actualizarIdiomas(pais['alpha2Code'],pais['languages'])
            actualizarMonedas(pais['alpha2Code'],pais['currencies'])
            actualizarZonasHorarias(pais['alpha2Code'],pais['timezones'])
        """
        else:
            #Si existe, lo actualiza
            query = "UPDATE paises SET "
            if(pais['alpha3Code']):
                query += "codigo3 = \""+pais['alpha3Code']+ "\", "
            if(pais['numericCode']):
                query += "codigoNum = \""+pais['numericCode']+ "\", "
            if(pais['name']):
                query += "nombre = \""+pais['name']+ "\","
            if(pais['nativeName']):
                query += "nombreNat = \""+pais['nativeName']+ "\", "
            if(pais['nativeName']):
                query += "bandera = \""+pais['flag']+ "\", "
            if(pais['latlng'] != []):
                query += "latitud = "+ str(float(pais['latlng'][0]))+ ", "
            if(pais['latlng'] != []):
                query += "longitud = "+str(float(pais['latlng'][1]))+ ", "
            query += "codigo = \""+ pais['alpha2Code'] +"\" where codigo = \"" + pais['alpha2Code'] + "\""
            mycursor.execute(query)
            mydb.commit()
            actualizarIdiomas(pais['alpha2Code'],pais['languages'])
        """

"""
Main code
"""
t_ini = datetime.now()
print("Conectando a la db...")
mydb = mysql.connector.connect(
  host="ip-localizator-db.cznoljerzjkp.us-east-2.rds.amazonaws.com",
  user="admin",
  password="omEPALfrznAn8N0RIZzw",
  database="iplocalizator"
)
mycursor = mydb.cursor()
print("Conectado")

print("Conectando a la API...")
response = requests.get('https://restcountries.eu/rest/v2/all')
if response.status_code == 200:
    print("Conectado")
    todo = json.loads(response.content.decode('utf-8'))
    print("Actualizando lista de paises...")
    for pais in todo:
        actualizarPais(pais)
    print("Actualizados")
else:
    print('There was an error connecting to the API')

mydb.close()

print("Total time: " + str(datetime.now() - t_ini))