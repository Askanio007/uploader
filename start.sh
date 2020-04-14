echo "============> Build application"
mvn --batch-mode package -f ./core/pom.xml
docker build -f ./Dockerfile -t uploader .
echo "============> Down container"
docker-compose -f ./docker-compose.yml down
echo "============> Start container"
docker-compose -f ./docker-compose.yml up -d --force-recreate