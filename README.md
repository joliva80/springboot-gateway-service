https://github.com/jwtk/jjwt#install-jdk-maven


### DOCKER

1. Create jar
sudo ./mvnw clean package -Dmaven.test.skip=true

2. Create image
sudo docker build -t gateway-server:1.0.0 . 

3. Run image
sudo docker network create gwnet
sudo docker run -p 8090:8090 --name gateway-server --network gwnet gateway-server:1.0.0 &

