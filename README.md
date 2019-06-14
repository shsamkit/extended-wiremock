# Extended Wiremock 
This project wraps wiremock in an easily-configurable spring project and containerizes it.

## Features
   - ### Configurable properties
       - Using spring's external properties, you can configure wiremock server according to your need.
       - Refer [this](./src/main/resources/application.yml) sample file for the list of configurable properties.
       - Refer to [wiremock's official document](http://wiremock.org/docs/configuration/) for the details and default values of each.
   
   - ### Source controlled mappings
       - Keep the [Wiremock stubs](http://wiremock.org/docs/stubbing/) source controlled.
       - Configure `remote.git` properties to load the stubs dynamically at start-up or at restart
       - Currently supports only SSH authentication which should be suitable for almost any platform VMs or containers or bare-metal.   
       - SSH keys are configurable via properties too, no need to copy ssh keys to the machine. 
   
   - ### Upcoming features
       - Other validation options.
       - Hot reload on stubs.
       - UI to view the stubs.      
   
## How to use
- Use the project in following two ways,
1. ### Build on local and use the executable jar file.
   ```
   - Clone this repository
   - Extend, make your changes
   
   - Build with maven
   ./mvnw clean package
   
   - Use the generated jar file for deploy
   ls -la target/extended-wiremock.jar 
   
   - To build your own docker image 
   ./mvnw package dockerfile:build
   ```   

2. ### Use the ready-made docker image
    - Refer the [dockerhub page](https://cloud.docker.com/repository/docker/shsamkit/extended-wiremock) for instructions about the image
    - Running the docker image,
    ```batch
     docker run -p 8080:8080 shsamkit/extended-wiremock:latest
    ```       