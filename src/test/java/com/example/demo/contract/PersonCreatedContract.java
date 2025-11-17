package com.example.demo.contract;

import org.springframework.cloud.contract.spec.Contract;
import org.springframework.cloud.contract.spec.internal.HttpHeaders;
import org.springframework.http.MediaType;

public class PersonCreatedContract {

    public Contract personCreated() {
        return Contract.make(c -> {
            c.description("should create a person and return 201");

            c.request(r -> {
                r.method("POST");
                r.url("/api/persons");
                r.headers(h -> {
                    h.contentType(MediaType.APPLICATION_JSON_VALUE);
                });
                r.body("""
                    {
                      "name": "ContractUser",
                      "email": "contract@example.com"
                    }
                    """);
            });

            c.response(r -> {
                r.status(201);
                r.headers(h -> {
                    h.contentType(MediaType.APPLICATION_JSON_VALUE);
                    h.header("Location", r.regex("/api/persons/\\\\d+"));
                });
                r.body("""
                    {
                      "id": 100,
                      "name": "ContractUser",
                      "email": "contract@example.com"
                    }
                    """);
            });
        });
    }
}
