package com.example.PerformanceTesting.LoadTesting;

import io.gatling.core.Predef._;
import io.gatling.http.Predef._;
import scala.concurrent.duration._;

class UserSimulation extends Simulation {

    // Define HTTP Protocol Configuration
    val httpProtocol = http
            .baseUrl("https://jsonplaceholder.typicode.com") // Base URL of API
            .acceptHeader("application/json") // Common Headers
            .contentTypeHeader("application/json")

    // Define Scenario: Simulate multiple users hitting the API
    val scn = scenario("User Load Test Scenario")
            .exec(http("Get User List") // Name of request
                    .get("/users") // Endpoint
                    .check(status.is(200)) // Validate response status
            )

// Define Load Configuration: Ramp-up 1000 users over 1 minute
    setUp(
            scn.inject(
            rampUsers(1000).during(60.seconds)
    )
            ).protocols(httpProtocol)
}
