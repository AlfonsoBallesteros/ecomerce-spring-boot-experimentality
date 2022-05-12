# test ecomerce experimentality

## Desarrollo

Documentacion: 
http://localhost:8080/swagger-ui/index.html

Para iniciar su aplicación en el perfil de desarrollador, ejecute:

```
./mvnw
```
## Construccion para produccion

### Packaging como jar

To build the final jar and optimize the product application for production, run:

```
./mvnw -Pprod
```

Para asegurarse de que todo funcionó, ejecute:

```
java -jar target/*.jar
```

### Packaging as war

Para empaquetar su aplicación como una war para implementarla en un servidor de aplicaciones, ejecute:

```
./mvnw -Pprod
```

## Uso de Docker para simplificar el desarrollo (opcional)

A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```