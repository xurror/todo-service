A Simple TODO Service
============

A simple todo service API that allows the creation and update of todos.
The service assumes a todo cannot be deleted.

Requirements
============
* Java >= 17 

Instructions how to run for local development
============

Run the following command:

    ./gradlew bootRun

Build the application with:

    ./gradlew clean build

Develop faster with docker
===========

You run application faster with docker.

To build an image Run:

    ./gradlew clean jibDockerBuild

Startup the service with:

    docker-compose up -d

Integration Test
===========

The service contains integration tests to cover edge cases and functional requirements.
To run the tests, you can run the following command in your terminal:

    ./gradlew clean test
