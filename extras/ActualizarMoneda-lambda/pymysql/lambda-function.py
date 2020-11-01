import json
import urllib3
import pymysql
import rds_config
import sys

rds_host = "ip-localizator-db.cznoljerzjkp.us-east-2.rds.amazonaws.com"
name = rds_config.db_username
password = rds_config.db_password
db_name = rds_config.db_name

try:
    conn = pymysql.connect(rds_host, user=name, passwd=password, db=db_name, connect_timeout=5)
except pymysql.MySQLError as e:
    print("ERROR: Unexpected error: Could not connect to MySQL instance.")
    sys.exit()

def actualizarMoneda(codigo, valor):
    with conn.cursor() as cur:
        codigo = codigo[3:]
        cur.execute("UPDATE monedas SET cambio = %s WHERE codigo = %s",(valor, codigo))
        conn.commit()

def lambda_handler(event, context):
    
    http = urllib3.PoolManager()
    response = http.request('GET','http://api.currencylayer.com/live?access_key=dc772d6daf6d73bca0e4c7cc0894386e')
    if response.status == 200:
        print("Conectado")
        todo = json.loads(response.data.decode('utf-8'))
        print("Actualizando lista de monedas...")
        for moneda in todo['quotes']:
            actualizarMoneda(moneda, todo['quotes'][moneda])
        print("Actualizados")
    else:
        print('There was an error connecting to the API')
    
    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }