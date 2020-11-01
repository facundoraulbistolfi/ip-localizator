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

#Actualizar las cotizaciones de monedas
def actualizarMoneda(codigo, nombre):
    try:
        mycursor.execute("INSERT INTO monedas"+
                     "(codigo, nombre, cambio)"+
                     " VALUES (%s,%s,0)",
                     (codigo, nombre))
        mydb.commit()
    except:
        print()
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
response = requests.get('http://api.currencylayer.com/list?access_key=dc772d6daf6d73bca0e4c7cc0894386e')
if response.status_code == 200:
    print("Conectado")
    todo = json.loads(response.content.decode('utf-8'))
    print("Actualizando lista de monedas...")
    for moneda in todo['currencies']:
        actualizarMoneda(moneda, todo['currencies'][moneda])
    print("Actualizados")
else:
    print('There was an error connecting to the API')

mydb.close()

print("Total time: " + str(datetime.now() - t_ini))