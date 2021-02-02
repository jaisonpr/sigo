# SIGO - External

Para representar os sistemas externos e legados, e atestar a interoperabilidade, foram criados dois sistemas mock desenvolvidos com tecnologias diferentes:

## ERP-Mock

Tecnologias utilizadas:
* Java;
* Spring boot, com Tomcat;
* WSDL;
* SOAP.

>URL: http://localhost:9000/ws/contratos

obs.:
Deve ser instalado o *lombok* no Eclipse para evitar erros de compilação (https://stackoverflow.com/questions/45461777/lombok-problems-with-eclipse-oxygen).

Para iniciar o container: ./start.sh

## Catálogo de Normas Mock

Tecnologias utilizadas:
* Javascript;
* Node.js;
* MongoDB;
* JSON;
* REST.

>URL: http://localhost:3000/api/v1/normas/{orgao}/{numero}

Para iniciar o container: ./start.sh

