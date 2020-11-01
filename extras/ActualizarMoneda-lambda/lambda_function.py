import json
import urllib3
import pymysql
import rds_config
import sys
import logging

def lambda_handler(event, context):
    
    rds_host = "ip-localizator-db.cznoljerzjkp.us-east-2.rds.amazonaws.com"
    name = rds_config.db_username
    password = rds_config.db_password
    db_name = rds_config.db_name
    
    logger = logging.getLogger()
    logger.setLevel(logging.INFO)
    
    logger.info("Connecting to RDS...")
    try:
        conn = pymysql.connect(rds_host, user=name, passwd=password, db=db_name, connect_timeout=5)
    except pymysql.MySQLError as e:
        logger.error("ERROR: Unexpected error: Could not connect to MySQL instance.")
        logger.error(e)
        return {
            'statusCode': 500,
            'body': json.dumps('ERROR: Unexpected error: Could not connect to MySQL instance')
        }
        
    logger.info("SUCCESS: Connection to RDS MySQL instance succeeded")
    
    logger.info("Connecting to API...")
    try:
        http = urllib3.PoolManager()
        response = http.request('GET','http://api.currencylayer.com/live?access_key=dc772d6daf6d73bca0e4c7cc0894386e')
    except pymysql.MySQLError as e:
        logger.error("ERROR: Unexpected error: Could not connect to currency API.")
        logger.error(e)
        return {
            'statusCode': 500,
            'body': json.dumps('ERROR: Unexpected error: Could not connect to currency API')
        }
        
    logger.info("SUCCESS: Connection to currency API succeeded")
        
    if response.status == 200:
        todo = json.loads(response.data.decode('utf-8'))
        logger.info("Actualizando lista de monedas...")
        for moneda in todo['quotes']:
            with conn.cursor() as cur:
                cur.execute("UPDATE monedas SET cambio = %s WHERE codigo = %s",(todo['quotes'][moneda], moneda[3:]))
                conn.commit()
        logger.info("Actualizados")
    else:
        logger.error('There was an error connecting to the API')
    
    return {
        'statusCode': 200,
        'body': json.dumps('Funcion ejecutada correctamente!')
    }