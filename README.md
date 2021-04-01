# poc-bank-api-kotlin

`Project creation Date: 2018`

Application that simulates a bank api, allowing the registration of customers, and the operations of deposits, withdrawals, transfers, balance inquiries and account statements.

## Business Rules

- The account balance can't be negative;
- It is not possible to make a withdrawal or transfer when the account balance is insufficient;
- Accounts involved in any operation must be valid;
- The customer can only have one account (validate by CPF for example);
- The account Id for future transactions must be included in the creation request response;
- An extract should return all account movements (transfers, deposits and withdrawals);
- It is not possible to make a transfer to yourself (the source account cannot be the same as the destination account);

## Run the API locally

### Premisses

- [Kotlin (Java JDK)](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Docker](https://docs.docker.com/install/#backporting)
- [Docker-Compose](https://docs.docker.com/compose/install/#uninstallation)
- [Maven](https://maven.apache.org/)
- [Git](https://git-scm.com/)
- [TDD](http://agiledata.org/essays/tdd.html)
- [Spring](https://spring.io/projects/spring-boot)
- [Mock MVC](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
- [PostgreSQL](https://www.postgresql.org/)

### Docker commands

Enter the root directory and run the following commands:

To start Docker:
```bash
systemctl start docker.service
```

To manage the containers:
```bash
docker-compose up
```
and
```bash
docker-compose down
```

You should now be able to start the application in the IDE. It will be possible to test the application at: `localhost:8080/`
