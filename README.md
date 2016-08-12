# EgenMicroservice
A microservice which consumes the sensor-emulator and creates metrics and alerts in MongoDB. It provides a REST API to view the metrics and alerts as well as query them.

## Assumptions and Implementation
When the application is first run, Morphia API creates a MongoDB datastore called EgenMicroservice. On subsequent runs of the application, if the datastore already exists, it does not recreate it. The application is implemented such that whenever a new metric is created, MongoDB is looked up to find the current (latest) metric and the first metric. Once the first metric is loaded it isn't reloaded again for the remainder of the application lifecycle to save needless db queries.

The value in the **first inserted metric is assumed to be the base weight**. This is one quick way of keeping the sensor and the API in sync without passing additional arguments manually while running the application.

## Dependencies
1. Spring Boot 1.4
2. Morphia 1.2.1
3. EasyRules 2.2.0
4. JUnit 4.12
5. Mockito 1.8.4
6. Java 7 or above

### How to run
1. For the [sensor emulator](https://github.com/egen/sensor-emulator): run the program with this command: ```java -jar -Dbase.value=20 -Dapi.url=http://localhost:8080/metrics/create sensor-emulator-0.0.1-SNAPSHOT.jar```. base.value can be of your choice (Please not the path. Rest is the same as original instructions).
2. Build the EgenMicroservice package using this command ```mvn package```
3. This will run the unit tests and build the jar
3. Run using: ```java -jar target/priyankavats-egen-springboot-0.1.0.jar```

## API Endpoints
### 1. Metrics
Stores and shows the emitted data by the sensor.

#### 1.1. Create
```POST http://localhost:8080/metrics/create```

#### 1.2. Read
```POST http://localhost:8080/metrics/read```

**Sample Response:**
```
[{"timeStamp":1470997706296,"value":20},{"timeStamp":1470997712855,"value":21},{"timeStamp":1470997717906,"value":22},{"timeStamp":1470997722949,"value":23},{"timeStamp":1470997728273,"value":24},{"timeStamp":1470997733333,"value":25}]
```

#### 1.3. ReadByTimeRange
```POST http://localhost:8080/metrics/readByTimeRange/1470997712850/1470997763333```

**Sample Response:**
```
[{"timeStamp":1470997706296,"value":20},{"timeStamp":1470997712855,"value":21},{"timeStamp":1470997717906,"value":22},{"timeStamp":1470997722949,"value":23},{"timeStamp":1470997728273,"value":24},{"timeStamp":1470997733333,"value":25}]
```

### 2. Alerts
Stores and shows the alarms for over and underweight scenarios

#### 2.1. Read
```POST http://localhost:8080/metrics/read```

**Sample Response:**
```
[{"timeStamp":1470997722949,"value":23,"type":"Over weight"},{"timeStamp":1470997728273,"value":24,"type":"Over weight"}]
```

#### 2.2. ReadByTimeRange
```POST http://localhost:8080/metrics/readByTimeRange/1470997722940/1470997722950```

**Sample Response:**
```
[{"timeStamp":1470997722949,"value":23,"type":"Over weight"}]
```
