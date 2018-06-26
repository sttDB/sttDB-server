# Deploying the API

## Local

To deploy the API, we need to do some local steps to push the work into dockerhub.

The commands we should run are:
```
 mvn install

sudo docker build -t sttdb-server [path to dockerfile]

sudo docker tag sttdb-server sttdb/sttdb-server

sudo docker login

sudo docker push sttdb/sttdb-server
```

The requirement is to have an account with permissions into the dockerhub organization sttdb.

## Server

First, create the docker volumes sttdb-vol and sttdb-mongo-vol:
```
docker volume create --name sttdb-vol
docker volume create --name sttdb-mongo-vol
```
 Second, pull the image using the project docker-compose:
 `docker-compose pull`
 
 Finally, to open the docker containers in background, use `docker-compose up -d`.