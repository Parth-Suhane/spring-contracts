# Demo Spring Boot App (with tests, contracts, Sonar)

## Run locally
- Build & run: `mvn clean package spring-boot:run`
- H2 console: http://localhost:8080/h2-console  (jdbc url in `application.properties`)

## Tests
- Unit + Integration + Contract generation:
  - `mvn clean test`
  - Contract generator (spring-cloud-contract-maven-plugin) will convert contracts in `src/test/resources/contracts` to tests.

## Sonar
- To run Sonar analysis (server must be accessible):
  - `mvn clean verify sonar:sonar -Dsonar.host.url=http://your-sonar:9000 -Dsonar.login=<token>`
