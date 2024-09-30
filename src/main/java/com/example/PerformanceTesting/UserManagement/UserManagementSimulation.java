package com.example.PerformanceTesting.UserManagement;

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UserManagementSimulation extends Simulation {

    val httpProtocol = http
            .baseUrl("https://yourapi.com")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json")
}


//Scenario 1: User Registration: A user registers with dynamic email and password.
val registerUser = scenario("User Registration")
        .exec(
                http("Register User")
                        .post("/api/users/register")
                        .body(StringBody(
                                """{
                                  "email": "user_${randomString}@test.com",
                                  "password": "password123"
                                }"""
                        )).asJson
                        .check(status.is(201)) // Expecting HTTP 201 Created
        )
//Scenario 2: User Login: After registration, the user logs in using their credentials.
val loginUser = scenario("User Login")
        .exec(
                http("User Login")
                        .post("/api/users/login")
                        .body(StringBody(
                                """{
                                  "email": "user_${randomString}@test.com",
                                  "password": "password123"
                                }"""
                        )).asJson
                        .check(status.is(200)) // Expecting HTTP 200 OK
        )

//Injection of Dynamic Data: Use Feeder to generate dynamic emails for each user.
val feeder = Iterator.continually(Map("randomString" -> (scala.util.Random.alphanumeric.take(10).mkString)))

val registerWithFeeder = scenario("Register User with Feeder")
        .feed(feeder)
        .exec(
                http("Register User")
                        .post("/api/users/register")
                        .body(StringBody(
                                """{
                                  "email": "user_${randomString}@test.com",
                                  "password": "password123"
                                }"""
                        )).asJson
                        .check(status.is(201))
        )

//Setup Load Simulation:
setUp(
        registerWithFeeder.inject(rampUsers(1000) during (30 seconds)).protocols(httpProtocol),
  loginUser.inject(atOnceUsers(1000)).protocols(httpProtocol)
)
        .assertions(
        global.responseTime.max.lt(2000),  // Max response time less than 2 seconds
    global.successfulRequests.percent.gt(95) // At least 95% successful requests
  )


