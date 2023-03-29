![GitHub language count](https://img.shields.io/github/languages/count/GutoFaria365/CodeQuizChallenge?color=blue) ![GitHub top language](https://img.shields.io/github/languages/top/GutoFaria365/CodeQuizChallenge?color=green) ![GitHub repo size](https://img.shields.io/github/repo-size/GutoFaria365/CodeQuizChallenge?color=yellow)

# Code.mindera.academy

**[MindSwap Bootcamp's](https://mindswap.academy/)** project by **[Augusto Faria](https://github.com/GutoFaria365)**, **[Nuno Martins](https://github.com/nunomartins78)**, **[Lina Balciunaite](https://github.com/LittleBlueDot)** and  **[Sara Brandão](https://github.com/saratcb)**.


### Project description

[TO EDIT]

The scope of the projet was ... (smth. explaining which part we did, what it included, in the end what we learnt (like using AWS, Dynamo, Quarkus, etc.))

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
- Install Java 11
- Install Maven
- Build the project using the following command:

    ```
    mvn clean install
    ```
- Run the application using the following command:

    ```
    mvn spring-boot:run
    ```

### Usage

[EXAMPLE]

Once the application is up and running, you can access the API at the following URL:

    http://localhost:8080/api/v1

The API has the following endpoints:

- GET /hello: returns a simple hello message
- GET /hello-list: returns a simple hello message in a JSON List


- GET /students: returns a list of students
- GET /students/{id}: returns a student with the given id
- POST /students: adds a new student to the list
- PUT /students/{id}: updates a student with the given id
- DELETE /students/{id}: deletes a student with the given id

- GET /calculator/{num1}/{num2}: returns the sum of the num1 and num2
  
[NOTE]

In the applications.properties file Christpher also has some comments-explanations. Look into it and see if there is smth. relevant to add to our project https://github.com/csoares/StudentJavaSpringboot/blob/master/src/main/resources/application.properties[https://github.com/csoares/StudentJavaSpringboot/blob/master/src/main/resources/application.properties]


