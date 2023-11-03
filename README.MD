# Migration API

Plantilla de desarrollo con Spring Boot.


## Template features


### Definición 

**- Argumentos**

- Argumento 1 => ruta del archivo SQLite .db ejemplo: C:\TEMP\external_risk_evaluator_2.db
- Argumento 2 => columna inicial de tabla la cual se formara el query, (realizado para evitar el pk autogenerado) ejemplo: 1 || 2 || 3 ...
- Argumento 3  => Nombre de la tabla en SQLite, ejemplo: customer
- Argumento 4  => ruta del archivo donde se guardara el sql, ejemplo: C:\TEMP\customer_insert.sql

**- comando de ejecución**

 ***tabla issuer***

        * java -jar migration-0.0.1.jar C:\TEMP\external_risk_evaluator_2.db 2 issuer C:\TEMP\issuer_insert.sql

 ***tabla customer***

        * java -jar migration-0.0.1.jar C:\TEMP\external_risk_evaluator_2.db 1 customer C:\TEMP\customer_insert.sql

 ***tabla risk***

        * java -jar migration-0.0.1.jar C:\TEMP\external_risk_evaluator_2.db 2 risk C:\TEMP\risk_insert.sql

 ***tabla customer_risk***

        * java -jar migration-0.0.1.jar C:\TEMP\external_risk_evaluator_2.db 2 customer_risk C:\TEMP\customer_risk_insert.sql

 ***tabla rule***

        * java -jar migration-0.0.1.jar C:\TEMP\external_risk_evaluator_2.db 2 rule C:\TEMP\rule_insert.sql

 ***tabla field_evaluator***

        * java -jar migration-0.0.1.jar C:\TEMP\external_risk_evaluator_2.db 2 field_evaluator C:\TEMP\field_evaluator_insert.sql


### Dependencias Externas

        <dependency>
			<groupId>org.xerial</groupId>
			<artifactId>sqlite-jdbc</artifactId>
			<version>3.43.2.2</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>


© 2021 NovoPayment Inc. All rights reserved
