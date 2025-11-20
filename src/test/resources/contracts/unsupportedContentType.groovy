package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return 415 when using unsupported content type")

    request {
        method POST()
        url("/api/persons")
        body("name=John&email=john@test.com")
        headers {
            header("Content-Type", "application/x-www-form-urlencoded")
        }
    }

    response {
        status 415
    }
}
