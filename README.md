# AsterixDB-EKETAL
En este repositorio se encuentra el código fuente de AsterixDB compilado con la herramienta para eventos distribuidos.

# Construcción desde código fuente
Para construir AsterixDB desde el origen, debe tener una plataforma con lo siguiente:

* Un ambiente Unix (Linux, OS X, etc).
* git
* Maven 3.3.9 o más nuevo.
* Oracle JDK 8 o más nuevo.
* Ambiente EKETAL en: https://github.com/unicesi/eketal

Instrucciones para construir desde master:

* Checkout AsterixDB master:

        $git clone https://github.com/BIGDATA-EKETAL/AsterixDB-EKETAL.git

* Build AsterixDB master:

        $cd asterixdb
        $mvn clean install -DskipTests -Drat.skip=true
        
# Ejecute la construcción en su máquina
Estos son los pasos para ejecutar AsterixDB y un ejmplo básico con EKETAL en su máquina local:

* Inicie una instancia de AsterixDB de una sola máquina:
        
        $cd asterixdb/asterix-server/target/asterix-server-*-binary-assembly/
        $./opt/local/bin/start-sample-cluster.sh
        
* Ya puede ejecutar y realizar consultas en su navegador en:

        http://localhost:19001
        
* Lea más [documentaciones] (https://ci.apache.org/projects/asterixdb/index.html) para conocer el modelo de datos, el lenguaje de consulta y cómo crear una instancia de clúster de AsterixDB.
