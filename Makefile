DB_NAME ?= 
DB_URL ?= 
DB_USER ?= 
DB_PASSWORD ?= 

run:
	./mvnw spring-boot:run
compile:
	./mvnw compile
package:
	./mvnw package
update_dep:
	./mvnw clean install -U
test:
	./mvnw test
build-image:
	./mvnw spring-boot:build-image
db-clean:
	flyway -url=${DB_URL} -user=${DB_USER} -password=${DB_PASSWORD} -cleanDisabled="false" clean


.PHONY: compile package test run update_dep build-image db-clean