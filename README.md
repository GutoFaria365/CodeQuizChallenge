![GitHub language count](https://img.shields.io/github/languages/count/GutoFaria365/CodeQuizChallenge?color=blue) ![GitHub top language](https://img.shields.io/github/languages/top/GutoFaria365/CodeQuizChallenge?color=green) ![GitHub repo size](https://img.shields.io/github/repo-size/GutoFaria365/CodeQuizChallenge?color=yellow)

# Code.mindera.academy

**[MindSwap Bootcamp's](https://mindswap.academy/)** project by **[Augusto Faria](https://github.com/GutoFaria365)**, **[Nuno Martins](https://github.com/nunomartins78)**, **[Lina Balciunaite](https://github.com/LittleBlueDot)** and  **[Sara Brandão](https://github.com/saratcb)**.


### Project description

[EDITED]

The scope of the projet was, using Quarkus, to create an access point that would allow teachers to create coding challenges, and for students to access such challenges and solve them. The access point is connected to a noSQL database (namely DynamoDb), where the info of both teaechers and studenst alike is stored, as well as the challenges designed by the former and solutions proposed by the latter. This access point will later be joined with an ongoing platform AWS based currently being developed.


[EXAMPLE FROM CHRISTOPHER´S SIMPLE JAVA SPINGBOOT APPLICATION REPO (later on I'll call "example" refering to this example)]:

```
This is a simple Java SpringBoot API that 
serves as a template for building RESTful APIs 
using SpringBoot framework. 
In this first application, 
we will not cover the security aspect of the API, 
but we will cover the basic topics, such as:
controller, service, repository, and model.
```



### Installation

[TO EDIT]

Explain to people what they need to have in order setup the project.

[EXAMPLE]

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

[EDITED]

Once the application is up and running, you can access the API at the following URL:

    http://localhost:8080/q/swagger-ui/

The API has the following endpoints:

- GET /hello: returns a simple hello message
- GET /hello-list: returns a simple hello message in a JSON List


- GET /challenges: returns a list of stored challenges
- GET /challenges/creator/{creator}: returns a challenge with the given creator
- GET /challenges/difficulty/{difficulty}: returns a challenge with the given difficulty
- GET /challenges/language/{language}: returns a challenge with the given language
- GET /challenges/name/{name}: returns a challenge with the given name  
- POST /challenges: adds a new challenge to the database
- PATCH /challenges/update/{challengeName}: updates a challenge with the given name
-   
[NOTE]

In the applications.properties file Christpher also has some comments-explanations. Look into it and see if there is smth. relevant to add to our project https://github.com/csoares/StudentJavaSpringboot/blob/master/src/main/resources/application.properties[https://github.com/csoares/StudentJavaSpringboot/blob/master/src/main/resources/application.properties]
