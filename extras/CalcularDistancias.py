# -*- coding: utf-8 -*-
"""
Created on Sun Nov  1 23:35:55 2020

@author: Facu
"""

import mysql.connector
from math import radians, cos, sin, asin, sqrt, atan2


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


print("Conectando a la db...")
mydb = mysql.connector.connect(
  host="ip-localizator-db.cznoljerzjkp.us-east-2.rds.amazonaws.com",
  user="admin",
  password="omEPALfrznAn8N0RIZzw",
  database="iplocalizator"
)


mycursor = mydb.cursor()

mycursor.execute("SELECT codigo, latitud, longitud FROM paises")

myresult = mycursor.fetchall()

for x in myresult:
  pais, latitud, longitud = x
  print("pais: " + pais)
  if(latitud is not None and longitud is not None):
      d = haversine((-34.0, -64.0), (latitud, longitud))
      query = "UPDATE paises SET distanciaBA = %s WHERE codigo = %s "
      mycursor.execute(query,(d,pais))
      mydb.commit()
  