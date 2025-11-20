package contracts
import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Create person successfully"
    request {
        method POST()
        url("/api/persons")
        body([
                name: "John",
                email: "john@test.com"
        ])
        headers {
            contentType applicationJson()
        }
    }
    response {
        status CREATED()
        body([
                id: 1,
                name: "John",
                email: "john@test.com"
        ])
        headers {
            contentType applicationJson()
        }
    }
}
