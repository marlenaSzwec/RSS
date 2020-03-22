#!/usr/bin/env bash
mvn clean package
jar -xf target/marlena-0.0.1-SNAPSHOT.jar
java -cp BOOT-INF/classes:BOOT-INF/lib/* com.exam.marlena.MarlenaApplication