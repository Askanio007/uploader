echo "============> Build application"
mvn --batch-mode package -f ./core/pom.xml
docker build -f ./core/Dockerfile -t uploader .
echo "============> Down container"
docker-compose -f docker-compose-prod.yml down
echo "============> Start container"
docker-compose -f docker-compose-prod.yml up -d --force-recreate