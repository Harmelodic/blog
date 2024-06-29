# Contributing

## Local development

```bash
# Run Tests
mvn clean test

# Run application locally
export CONTENTFUL_TOKEN=xxxxx # replace with a valid Contentful Auth Token
mvn clean spring-boot:run -D spring-boot.run.profiles=local
```
