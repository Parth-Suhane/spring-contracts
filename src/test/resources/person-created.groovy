import org.springframework.cloud.contract.spec.Contract

Contract.make {
  description "should create a person and return 201 with body"
  request {
    method 'POST'
    url '/api/persons'
    headers {
      contentType(applicationJson())
    }
    body(
      name: "ContractUser",
      email: "contract@example.com"
    )
  }
  response {
    status 201
    headers {
      contentType(applicationJson())
      header('Location': value(consumer(regex('/api/persons/\\d+')), producer('/api/persons/100')))
    }
    body(
      id: anyNumber(),
      name: "ContractUser",
      email: "contract@example.com"
    )
  }
}