package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return 400 for invalid email format")

    request {
        method POST()
        url("/api/persons")
        body([
            name: "John",
            email: "invalid-email"
        ])
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status 400
    }
}
