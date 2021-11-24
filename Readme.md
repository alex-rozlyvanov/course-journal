# Course-journal

## This is course-journal microservice of Course application.

### Main technologies:

- Gradle 7.2
- Java 17
- Postgres 14
- Spring Boot 2.5.5
- Spring Cloud 2020.0.3
- Netflix Eureka Client
- Flyway 7.7+
- Lombok 1.18+
- Slf4j

---

- Junit 5 + Mockito + Assertj
- TestContainers 1.16
- Spring Cloud Contract 3.0.4

### Responsibilities:

- Assigning grade for student per lesson
- Creating course feedback per student
- Saving student "homework" files
- Retrieving student file
- Getting all students files

## How to build:

**Regular build:** `./gradlew clean build`

**Without tests build:** `./gradlew clean build -x test -x integrationTest -x contractTest`

**Run local in docker
build:** `./gradlew clean build -x test -x integrationTest -x contractTest && docker-compose up --build`

**Push docker image to ECR:**

- aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin
  public.ecr.aws/k7s0v3p5
- docker build -t course-journal:0.0.1 .
- docker tag course-journal:0.0.1 public.ecr.aws/k7s0v3p5/course-journal:0.0.1
- docker push public.ecr.aws/k7s0v3p5/course-journal:0.0.1
