docker network create --driver bridge dac-net

docker-compose -f start-postgres.yml up -d
sleep 5
docker-compose -f start-mongodb.yml up -d
sleep 5
docker-compose -f start-rabbitmq.yml up -d
sleep 5

cd ms-orchestrator
mvn spring-boot:build-image
cd ..

docker-compose -f start-orchestrator.yml up -d
sleep 10

cd ms-auth
mvn spring-boot:build-image
cd ..

cd ms-cliente
mvn spring-boot:build-image
cd ..

cd ms-voo
mvn spring-boot:build-image
cd ..

cd ms-reserva
mvn spring-boot:build-image
cd ..

cd ms-funcionario
mvn spring-boot:build-image
cd ..

cd gateway
docker build -t dac-gateway .
cd ..

cd ui
docker build -t dac-front .
cd ..

docker-compose -f start-services.yml up -d