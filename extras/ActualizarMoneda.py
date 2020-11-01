# -*- coding: utf-8 -*-
"""
Created on Sat Oct 31 16:32:22 2020

@author: Facu
"""
import json
import requests
import mysql.connector

"""
connect to database
"""
mydb = mysql.connector.connect(
  host="ip-localizator-db.cznoljerzjkp.us-east-2.rds.amazonaws.com",
  user="admin",
  password="omEPALfrznAn8N0RIZzw",
  database="iplocalizator"
)
mycursor = mydb.cursor()

def actualizarPais(pais):
    mycursor.execute(
    "SELECT codigo, COUNT(*) FROM paises WHERE codigo = %s GROUP BY codigo",
    (pais['alpha2Code'],)
    )
    print(pais['alpha2Code'])
    mycursor.fetchall()
    if mycursor.rowcount == 0:
        #No lo tengo
        print("Crear")
        mycursor.execute("INSERT INTO paises"+
                         "(codigo, codigo3, codigoNum, nombre, nombreNat, latitud, longitud)"+
                         " VALUES (%s,%s,%s,%s,%s,%s,%s)",
                         (pais['alpha2Code'],pais['alpha3Code'],pais['numericCode'],
                         pais['name'],pais['nativeName'],
                         float(pais['latlng'][0]) if (pais['latlng'] is None) else None,
                         float(pais['latlng'][1]) if (pais['latlng'] is None) else None
                          ))
        mydb.commit()
    else:
        print("Modificar")
        print("----------------")
        print(pais)
        print(pais['latlng'])
        print(pais['latlng'] == [])
        print("----------------")

        query = "UPDATE paises SET "
        if(pais['alpha3Code']):
            query += "codigo3 = \""+pais['alpha3Code']+ "\", "
        if(pais['numericCode']):
            query += "codigoNum = \""+pais['numericCode']+ "\", "
        if(pais['name']):
            query += "nombre = \""+pais['name']+ "\","
        if(pais['nativeName']):
            query += "nombreNat = \""+pais['nativeName']+ "\", "
        if(pais['latlng'] != []):
            query += "latitud = "+ str(float(pais['latlng'][0]))+ ", "
        if(pais['latlng'] != []):
            query += "longitud = "+str(float(pais['latlng'][1]))+ ", "
        query += "codigo = \""+ pais['alpha2Code'] +"\" where codigo = \"" + pais['alpha2Code'] + "\""
        print(query)
        mycursor.execute(query)
        mydb.commit()
        

    


response = requests.get('https://restcountries.eu/rest/v2/all')
if response.status_code == 200:
    print('jaja si anda')
    todo = json.loads(response.content.decode('utf-8'))
    for pais in todo:
        actualizarPais(pais)
else:
    print('jaja no anda')
