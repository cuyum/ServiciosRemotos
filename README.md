Servicios Remotos
============

REST Service para acceso a listas externas para uso de CNC2, tales como:

- Lista de provincias
- Lista de departamentos/partidos
- Lista de localidades
- Lista de área Local
- Lista de área Local2
- Lista de prestadores
- Lista de servicios
- Lista de accesos
- Lista de conceptos
- Lista de accesos_servicios
- Lista de naps
- Lista de paramentros
- lista de periodos

1.Objetivo del documento 
-------------------------

Especificar los requerimientos de sistema necesarios para poder instalar y ejecutar la aplicación.

2.Configuración
---------------
Componentes necesarios para poder ejecutar la aplicación:
*	JDK 1.6.x
*	Jboss-as-7.1.0.Final
*	Maven 3.0.4
*	PostgreSQL 1.16

2.2	Configuracion JBoss

Agregar al archivo <jboss-as-7.1.0.Final>\standalone\configuration\standalone.xml

2.3	Configuracion BD  

2.3.1) Instalar driver de base de datos

   Para instalar driver de postgresql en jboss-as-7.1.0.Final se deben crear 2 carpetas (postgres y main) en 
   
   <jboss-as-7.1.0.Final>\modules\org  debiendo quedar la siguiente estructura:
   
   <jboss-as-7.1.0.Final>\modules\org\postgresql\main 
   
   Dentro de la carpeta main copiar el archivo postgresql-9.1-902.jdbc4.jar y crear un archivo module.xml cuyo
   contenido debe ser
   
	<?xml version="1.0" encoding="UTF-8"?>
	<module xmlns="urn:jboss:module:1.0" name="org.postgresql">
	 <resources>
	 <resource-root path="postgresql-9.1-902.jdbc4.jar"/>
	 </resources>
	 <dependencies>
	 <module name="javax.api"/>
	 <module name="javax.transaction.api"/>
	 </dependencies>
	</module>
	
2.3.2) Crear base de datos BD

Crear base de datos BD "servicios" en postgres

A continuacion se especifica el datasource que debe ser usado, en este caso para una BD Postgres.

Agregar al archivo <jboss-as-7.1.0.Final>\standalone\configuration\standalone.xml en la sección <datasources> 
la siguiente entrada, especificando usuario y password correspondiente:

	...
	<datasources>
	...
                <datasource jta="true" jndi-name="java:jboss/datasources/ServiciosRemotosDS" pool-name="ServiciosRemotosDS" enabled="true" use-java-context="true" use-ccm="true">
					<connection-url>jdbc:postgresql://localhost:5432/servicios</connection-url>
					<driver-class>org.postgresql.Driver</driver-class>
					<driver>postgresql</driver>
					<security>
						<user-name>postgres</user-name>
						<password>cuyum123</password>
					</security>
				</datasource>
        ...        
                <drivers>
        ...
                    <driver name="h2" module="com.h2database.h2">
                        <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                    </driver>
                    <driver name="mysql" module="com.mysql">
                        <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                    </driver>
                    <driver name="postgresql" module="org.postgresql">
                        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
                    </driver>
                </drivers>
        ...        
        </datasources>
	...	 
	
3) Ejecutar los scripts de estructura y datos en la BD "servicios" creada

Los scripts de carga inicial están ubicados en ServiciosRemotos/sql/ deben ejecutarse los scripts de estructura y luego los scripts de datos:
* Para crear las tablas ejecutar:
- ServiciosRemotos/sql/estructuras.sql (crea las tablas)
- ServiciosRemotos/sql/estructuras-delta.sql 
- ServiciosRemotos/sql/updateEstructuras110414.sql 
- ServiciosRemotos/sql/updateEstructuras150414.sql
* Para cargar los datos ejecutar:
- ServiciosRemotos/sql/geograficas.sql (inserción de datos de provincias, partidos, localidades y áreas locales)
- ServiciosRemotos/sql/prestadores.sql (inserción de datos de proveedores)
- ServiciosRemotos/sql/otrasTablas.sql (inserción de datos de accesos y servicios)
- ServiciosRemotos/sql/periodos.sql (insercion de datos de periodicidad y parametros)
- ServiciosRemotos/sql/naps.sql (inserción de datos de naps)
- ServiciosRemotos/sql/otrosConceptos.sql (inserción de datos de canal, contacto, tipo informacion1, tipo_informacion2, red interna, tecnologia, unidad, medio, tendido, aplicacion, modalidad1, segmento, modalidad2, recarga tipo, anio, institucion, velocidad, velocidadSubida, velocidadBajada, abono, destino, tarifa acceso, plane acceso)
ServiciosRemotos/sql/updatePeriodos.sql (inserción de datos anual, trimestral ...)


4.Despliegue
------------

4.1 Pasos

1) Este proyecto usa git. Para bajarse el proyecto, hacer 

	$>git clone git@cluster.softwarepublico.gob.ar:cnc_ict.git
	
2) Ubicados sobre el proyecto maven tipeamos
	$>mvn clean package
	Esto genera un archivo war en "ServiciosRemotos\target\servicios.war"
3) Deployar el archivo "servicios.war" generado, para ello
   en JBoss 7.1.0 copiar el archivo al directorio <jboss-as-7.1.0.Final>\standalone\deployments 
4) Iniciar el server (standalone.bat en windows o standalone.sh unix)


5. Ejemplo Peticiones y resultados

* Para obtener la lista de prestadores.

Hacer un request del tipo POST a:
http://<localhost:8080>/servicios/rest/prestadores?page={LONG}&limit={INTEGER}[&term={STRING}]

como respuesta se deberá obtener como mínimo un JSON con la siguiente estructura:
{"total":_number_,"success":_boolean_,"result":[{"id":_num_,"text":_string_}]}


* Para obtener la lista de provincias.

Hacer un request del tipo POST a:
http://<localhost:8080>/servicios/rest/localizaciones/provincias
usando form-data sin parámetros

obteniendo como RESPONSE:
{"success":true,"result":[{"id":2,"nombre":"BUENOS AIRES"},...,...,{"id":6,"nombre":"TUCUMAN"}],"msg":null}


* Para obtener la lista de partidos de una provincia

Hacer un request del tipo POST a:
http://<localhost:8080>/servicios/rest/localizaciones/partidos

usando x-www-form-urlencoded
con FormParam nombre fkey y valor el id de la provincia

* Para obtener la lista de localidades de un partido

Hacer un request del tipo POST a:
http://<localhost:8080>/servicios/rest/localizaciones/localidades

usando x-www-form-urlencoded
con FormParam nombre fkey y valor el id del partido, ejemplo fkey  43,
para obtener todos los datos de la localidad, fkey -1


* Para obtener la lista de áreas locales de una localidad

usando x-www-form-urlencoded
con FormParam nombre fkey y valor el id de la localidad,
para obtener todos los datos del área usar fkey -1
Hacer un request del tipo POST a:
http://<localhost:8080>/servicios/rest/localizaciones/areas

* Para obtener la lista de áreas locales2 de una localidad (area local2 lo utilizan los formularios de ict4.2....)

usando x-www-form-urlencoded
con FormParam nombre fkey y valor el id de la localidad,
para obtener todos los datos del área usar fkey -1
Hacer un request del tipo POST a:
http://<localhost:8080>/servicios/rest/localizaciones/areas2

Hacer un request del tipo POST a:
http://<localhost:8080>/servicios/rest/localizaciones/servicios

obteniendo como RESPONSE:
{"success":true,"result":[{"id":"610","nombre":"610"},...,...,{"id":"OTROS","nombre":"OTROS"}],"msg":null}

* Para Obtener datos de la lista Concepto:


-Para obtener los datos de lista velocidad
Usando Post, con opcion: form-data sin parametros

http://<localhost:8080>/servicios/rest/listas/velocidad

* Para Obtener datos de la lista Periodicidad:

usando x-www-form-urlencoded
con FormParam nombre fkey y valor anual ó trimestral,

http://<localhost:8080>/servicios/rest/periodicidad


