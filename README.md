# MovieMicroservice
This is a reactive part of simple "Movie App" that contains movie microservice. In this app user can leave reviews for movies.

## Glossary
Movie - information about movie

## Implemented pattern
### Service registry
The *service registry pattern* is a key part of service discovery. The registry is a database containing the network locations of service instances. A service registry needs to be highly available and up-to-date. One of the most popular implementations of this pattern is *Eureka Service Registry*.

## Apache Kafka
Reactive producer is implemented as a part of Reactor Kafka. The producer sends messages to the consumer from other microservice (see [ReviewMicroservice](https://github.com/hizmailovich/ReviewMicroservice)). The main goal of this implementation is to simplify communication between microservices: if the movie is deleted, all reviews for this movie should be deleted too.
