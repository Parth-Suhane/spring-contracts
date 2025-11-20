package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return 400 when required fields are missing")

    request {
        method POST()
        url("/api/persons")
        body([
            name: null,
            email: null
        ])
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status 400
    }
}
