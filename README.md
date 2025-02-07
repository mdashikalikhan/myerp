# myerp
A basic ERP for a starter business. This application requires mysql and REDIS to run. You can use both external or docker image. All paramters are configured by arguments.

##### Docker connect with external local mysql DB. Name: mydb, user/password: root

##### Docker has internal REDIS image which is used by application.

## build

./mvnw clean package

docker build -t myerp:latest .

## start

docker-compose up -d

## logs

docker-compose logs -f

## Rebuild and Restart

docker-compose up -d --build

## stop

docker-compose down

## install REDIS image to use outside container 

docker run --name redis-container -d -p 6379:6379 redis

## check running services

docker ps

## cli

docker exec -it redis-container redis-cli



