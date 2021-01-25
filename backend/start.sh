#!/bin/sh
gnome-terminal -x bash -c "java -jar ./gestao-consultorias/target/gestao-consultorias-0.0.1-SNAPSHOT.jar; exec bash"
gnome-terminal -x bash -c "java -jar ./gestao-normas/target/gestao-normas-0.0.1-SNAPSHOT.jar; exec bash"
gnome-terminal -x bash -c "java -jar ./gestao-processos/target/gestao-processos-0.0.1-SNAPSHOT.jar; exec bash"

