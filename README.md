# Staff Rating App

## Project Description

I have put together a full-stack Spring Boot web application for this project, one that allows users to rate the computer science teaching staff. With it you can do everything from adding and viewing staff ratings to updating or deleting them as needed. A given rating will have the person’s name, email and role on file along with scores for their knowledge, clarity and niceness; there is also room for an optional comment.

As for the technology behind it, the app uses PostgreSQL for the database and using Thymeleaf on the front end. Spring Data JPA handles the database work while Bean Validation takes care of the forms.

## Features

* View all staff ratings
* Add a new staff rating
* View rating details
* Edit an existing rating
* Delete a rating
* Validate form input before saving
* Prevent duplicate staff email ratings
* Calculate an overall rating score
* Use PostgreSQL as the production database
* Use H2 as the test database
* Include automated validation, repository, and controller tests

## Technologies Used

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Thymeleaf
* PostgreSQL
* H2 Database
* Maven
* JUnit
* MockMvc
* Render

## Project Structure

```text
src/main/java/com/example/staffrating
  controller
    HomeController.java
    StaffRatingController.java
  design
    DefaultStaffProfile.java
    ProfProfile.java
    StaffMemberProfile.java
    StaffProfileFactory.java
    TaProfile.java
  model
    RoleType.java
    StaffRating.java
  repository
    StaffRatingRepository.java
  StaffratingApplication.java

src/main/resources
  templates/ratings
    index.html
    form.html
    detail.html
    delete.html
  static
    style.css
  application.properties

src/test/java/com/example/staffrating
  StaffratingApplicationTests.java
  StaffRatingValidationTest.java
  StaffRatingRepositoryTest.java
  StaffRatingControllerTest.java

src/test/resources
  application.properties
```

## How to Run Locally

First, clone the repository:

```bash
git clone YOUR_GITHUB_REPO_LINK_HERE
cd staffrating
```

Set the database environment variables.

For PowerShell on Windows:

```powershell
$env:DATABASE_URL="your_postgresql_jdbc_url"
$env:DATABASE_USERNAME="your_database_username"
$env:DATABASE_PASSWORD="your_database_password"
```

Then run the application:

```powershell
.\mvnw.cmd spring-boot:run
```

Open the app in the browser:

```text
http://localhost:8080/ratings
```

## Environment Variables

The application uses environment variables so database login information is not hardcoded into the project.

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
PORT
```

Example format:

```text
DATABASE_URL=jdbc:postgresql://host:5432/database_name?sslmode=require
DATABASE_USERNAME=database_username
DATABASE_PASSWORD=database_password
PORT=8080
```

## Database

The main application uses PostgreSQL.

The test environment uses an in-memory H2 database. This lets the tests run without changing or damaging the real PostgreSQL database.

## Validation Rules

The app validates user input before saving a rating.

Validation includes:

* Name is required
* Name must be between 2 and 80 characters
* Email is required
* Email must be valid
* Role type is required
* Clarity score must be between 1 and 10
* Niceness score must be between 1 and 10
* Knowledge score must be between 1 and 10
* Comment must be 500 characters or less

## Design Pattern and Polymorphism

This project uses a simple factory pattern with polymorphism.

The `StaffMemberProfile` interface defines a `displayTitle()` method. Different role profile classes implement this interface, such as `TaProfile`, `ProfProfile`, and `DefaultStaffProfile`.

The `StaffProfileFactory` class creates the correct profile object based on the selected staff role.

This helps keep role display logic separate from the controller. It also makes the code easier to extend if more staff roles are added later.

## Testing

The project includes automated tests for validation, persistence, and controller behavior.

The tests cover:

* Invalid email validation
* Out-of-range score validation
* Missing required fields validation
* Saving and retrieving ratings from the repository
* Deleting ratings from the repository
* Loading the ratings index page
* Creating a rating successfully through the controller
* Returning the form with errors when invalid data is submitted

To run the tests:

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

## Deployment

The app is intended to be deployed on Render using a PostgreSQL database.

Live app link:

```text
https://staffrating-myr7.onrender.com
```

## GitHub Repository

GitHub repository link:

```text
https://github.com/hesham207/staffrating
```

## AI Assistance Declaration

I used AI at times to help debug errors and understand setup issues, especially with controller validation, Thymeleaf form binding, and for Render deployment issues. But I put in the work myself to review, test and make any necessary edits to the finished assignment on my own.
