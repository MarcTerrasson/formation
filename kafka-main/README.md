# kafka

## slides

See 'resources/formation Kafka débutant.pdf'.

File at [https://docs.google.com/presentation/d/1Dk1hG252Pse4cnZv1sd_hBQGMYKVLdp2OFMZlrv_FcA/edit?usp=sharing](https://docs.google.com/presentation/d/1Dk1hG252Pse4cnZv1sd_hBQGMYKVLdp2OFMZlrv_FcA/edit?usp=sharing)

## Getting started

Install :
- Docker 
- Docker-compose
- Java 17
- Kafka : [https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)

## Launch

### Components launched by files

No option needed.

resources/docker-compose.yml :  
- ZooKeeper
- 1 broker Kafka 
- the schema-registry
- AKHQ [https://akhq.io/](https://akhq.io/)
- One network to bring them all, and in the darkness bind them

resources/producer-compose.yml :  
A Java producer using the schema registry and the network created in docker compose.

#### Image on dockerhub 
`docker pull matersmile/producer:latest`

resources/consumer-compose.yml :  
A Java consumer using the schema registry and the network created in docker compose.

#### Image on dockerhub 
`docker pull matersmile/consumer:latest`

## Components

### AKHQ
[http://localhost:8081/ui](http://localhost:8081/ui)

### Producer
Go to the project swagger to post msg in Kafka :  
[localhost:8080/swagger-ui/index.html](localhost:8080/swagger-ui/index.html)

### Consumer
Read messages and log them.

