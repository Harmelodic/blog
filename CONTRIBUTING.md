# Contributing

## Local development

```bash
# Run Tests
mvn clean test

# Run application locally
export CONTENTFUL_TOKEN=xxxxx # replace with a valid Contentful Auth Token
mvn clean spring-boot:run # The spring-boot-maven-plugin is configured to use the `local` profile in the POM.
```
