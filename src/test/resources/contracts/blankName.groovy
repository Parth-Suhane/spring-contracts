package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return 400 for blank name")

    request {
        method POST()
        url("/api/persons")
        body([
            name: "",
            email: "valid@example.com"
        ])
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status 400
    }
}
