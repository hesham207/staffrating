# Testing

This project includes automated tests for validation, controller behavior, and persistence. The tests are located in:

```text
src/test/java/com/example/staffrating
```

## Test Types

### Validation Tests

File:

```text
StaffRatingValidationTest.java
```

These tests check that invalid input is rejected before a rating is saved.

Covered cases:

* Invalid email is rejected
* Out-of-range score is rejected
* Missing required fields are rejected

### Controller Tests

File:

```text
StaffRatingControllerTest.java
```

These tests use MockMvc to check the web/controller behavior.

Covered cases:

* GET `/ratings` returns a successful page
* The ratings model attribute exists
* Creating a valid rating redirects correctly
* Creating an invalid rating returns the form with errors

### Persistence Tests

File:

```text
StaffRatingRepositoryTest.java
```

These tests check that the repository can save, retrieve, and delete staff ratings.

Covered cases:

* Saving and retrieving a rating works
* Deleting a rating removes it from the database

## Test Database

The main application uses PostgreSQL, but the tests use an in-memory H2 database. This keeps tests separate from the real production database.

The test database configuration is stored in:

```text
src/test/resources/application.properties
```

## How to Run Tests

Run this command from the project root:

```powershell
.\mvnw.cmd test
```

Latest test result:

```text
Tests run: 9
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```
