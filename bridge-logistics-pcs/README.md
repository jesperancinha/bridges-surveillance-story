# Build
mvn clean package && docker build -t com.jesperancinha/bridge-logistics-pcs .

# RUN

docker rm -f bridge-logistics-pcs || true && docker run -d -p 8080:8080 -p 4848:4848 --name bridge-logistics-pcs com.jesperancinha/bridge-logistics-pcs 