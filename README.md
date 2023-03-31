![GitHub language count](https://img.shields.io/github/languages/count/GutoFaria365/CodeQuizChallenge?color=blue) ![GitHub top language](https://img.shields.io/github/languages/top/GutoFaria365/CodeQuizChallenge?color=green) ![GitHub repo size](https://img.shields.io/github/repo-size/GutoFaria365/CodeQuizChallenge?color=yellow)

# Code.mindera.academy

**[MindSwap Bootcamp's](https://mindswap.academy/)** project by **[Augusto Faria](https://github.com/GutoFaria365)**, **[Nuno Martins](https://github.com/nunomartins78)**, **[Lina Balciunaite](https://github.com/LittleBlueDot)** and  **[Sara Brandão](https://github.com/saratcb)**.


### Project description

The scope of the projet was, using Quarkus, to create an access point that would allow teachers to create coding challenges, and for students to access such challenges and solve them. The access point is connected to a noSQL database (namely DynamoDb), where the info of both teaechers and studenst alike is stored, as well as the challenges designed by the former and solutions proposed by the latter. This access point will later be joined with an ongoing platform AWS based currently being developed.

### Installation

You will need to:

- Clone the repository
- Install Java 17
- Install Maven
- Install Docker Desktop
- Setup the container using the following commands:
    ```
    docker pull amazon/dynamodb-local
    docker run --publish 4566:8000 amazon/dynamodb-local:1.19.0 -jar DynamoDBLocal.jar -inMemory -sharedDb
    ```
- Build the project using the following command:

    ```
    mvn clean install
    ```
- Run the application using the following command:

    ```
    mvn spring-boot:run
    ```

### Usage

Once the application is up and running, you can access the API at the following URL:

    http://localhost:8080/q/swagger-ui/

The API has the following endpoints:

- GET /challenges: returns a list of stored challenges
- POST /challenges: adds a new challenge to the database
- GET /challenges/creator/{creator}: returns a challenge with the given creator
- DELETE /challenges/delete/{name}: deletes a challenge from the database with the given name
- GET /challenges/difficulty/{difficulty}: returns a challenge with the given difficulty
- GET /challenges/language/{language}: returns a challenge with the given language
- GET /challenges/name/{name}: returns a challenge with the given name  
- POST /challenges/repos/{owner}/{repo}/forks: creates a fork of a given challenge for a given user
- GET /challenges/search/{attribute}: returns a challenge with the given attribute
- PATCH /challenges/update/{challengeName}: edits a given challenge (the challenge name cannot be changed since it's used as a partition key
- GET /challenges/user: returns a given user
- POST /challenges/user/repos: allows a user to post a new repository
