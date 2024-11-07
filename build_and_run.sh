docker network create --driver bridge dac-net

docker-compose -f start-postgres.yml up -d
sleep 5
docker-compose -f start-mongodb.yml up -d
sleep 5

cd ms-auth
mvn spring-boot:build-image
cd ..

cd gateway
docker build -t dac-gateway .
cd ..

docker-compose -f start-services.yml up -d