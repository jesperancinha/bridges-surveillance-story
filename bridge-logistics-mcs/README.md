# Bridge Logistics Merchandise Control Service (MCS)

## Build
mvn clean package && docker build -t com.jesperancinha/bridge-logistics-mcs .

## RUN

docker rm -f bridge-logistics-mcs || true && docker run -d -p 8080:8080 -p 4848:4848 --name bridge-logistics-mcs com.jesperancinha/bridge-logistics-mcs 