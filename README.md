# EgenMicroservice
A microservice which consumes the sensor-emulator and creates metrics and alerts in MongoDB. It provides a REST API to view the metrics and alerts as well as query them.

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
