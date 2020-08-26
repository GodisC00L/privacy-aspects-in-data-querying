# privacy-aspects-in-data-querying-server

## General Info
This repo is using Spring Boot in order to run the application.
In order to run please clone the repo and run using Intellij, this will start the server on localhost:8080

## Running Instructions

### Input Files
In order to run our program you should have 2 files:

1. Dataset for Server:

    * the name and path of the file should be replaced at WorkingWithDataset.java.

    * the dataset format should be: (Timestamp) (Car ID) (X) (Y) (Velocity)

      note: the delimiter should be space.
   

2. Once the server started you can use all the APIs that are described in DatabaseController.java
    * Calling the API can be done using address bar or Postman application.
