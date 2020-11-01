# -*- coding: utf-8 -*-
"""
Created on Sat Oct 31 16:32:22 2020

@author: Facu
"""
import json
import requests
import mysql.connector
from datetime import datetime

"""
Funciones
"""
#Actualizar la tabla de idiomas, y la de idiomas por pais
def actualizarIdiomas(country, listaIdiomas):
    for idioma in listaIdiomas:
        if(idioma['iso639_1'] is not None):
            #Si no existe en la tabla de idiomas, lo agrego
            mycursor.execute("SELECT codigo, COUNT(*) FROM idiomas WHERE codigo = %s GROUP BY codigo",(idioma['iso639_1'],))
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

#Actualizar la tabla de paises
def actualizarPais(pais):
    if(pais['alpha2Code'] is not None):
        #Comprueba de que si ya existe un registro en la base de datos
        mycursor.execute("SELECT codigo, COUNT(*) FROM paises WHERE codigo = %s GROUP BY codigo",(pais['alpha2Code'],))
        mycursor.fetchall()
        if mycursor.rowcount == 0:
            #Si no existe, lo crea
            mycursor.execute("INSERT INTO paises"+
                             "(codigo, codigo3, codigoNum, nombre, nombreNat, latitud, longitud)"+
                             " VALUES (%s,%s,%s,%s,%s,%s,%s,%s)",
                             (pais['alpha2Code'],pais['alpha3Code'],pais['numericCode'],
                             pais['name'],pais['nativeName'],
                             float(pais['latlng'][0]) if (pais['latlng'] is None) else None,
                             float(pais['latlng'][1]) if (pais['latlng'] is None) else None,
                             pais['flag']
                              ))
            mydb.commit()
            actualizarIdiomas(pais['alpha2Code'],pais['languages'])
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