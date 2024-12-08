docker-compose -f start-postgres.yml up -d
sleep 10
docker-compose -f start-mongodb.yml up -d
sleep 5
docker-compose -f start-rabbitmq.yml up -d
sleep 5

# docker-compose -f start-services.yml up -d