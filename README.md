# Stellar Burgers — API Test Automation

Automated API tests for the [Stellar Burgers](https://stellarburgers.nomoreparties.site) backend service.

## Tech stack
Java 11 · JUnit 5 · REST Assured · Allure · Maven

## Test coverage
| Area | Scenarios |
|---|---|
| User registration | unique user, duplicate user, missing required fields |
| Login | valid credentials, invalid credentials |
| User data update | each field, with and without authorization |
| Order creation | with/without auth, with/without ingredients, invalid ingredient hash |
| User orders | authorized and unauthorized requests |

## Architecture
- **Client layer** — base `Client` with shared request spec; `UserClient`, `OrderClient` per API domain
- **Checker classes** — assertions separated from test logic for readability and reuse
- Test data generated per run → tests are independent and repeatable

## Run tests
​```bash
mvn clean test
mvn allure:serve
​```
