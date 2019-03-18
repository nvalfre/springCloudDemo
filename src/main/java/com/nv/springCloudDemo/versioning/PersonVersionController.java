package com.nv.springCloudDemo.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersionController {
    //version
    @GetMapping("v1/person")
    public PersonV1 personV1(){
        return new PersonV1("Pepe", "Argento");
    }

    @GetMapping("v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Pepe", "Argento"));
    }

    //params
    @GetMapping(value="/person/param", params="version=1")
    public PersonV1 personV1param(){
        return new PersonV1("Pepe", "Argento");
    }

    @GetMapping(value="/person/param", params="version=2")
    public PersonV2 personV2param(){
        return new PersonV2(new Name("Pepe", "Argento"));
    }

    //headers
    @GetMapping(value="/person/header", headers="X-API-VERSION=1")
    public PersonV1 personHeaderV1(){
        return new PersonV1("Pepe", "Argento");
    }

    @GetMapping(value="/person/header", headers="X-API-VERSION=2")
    public PersonV2 personHeaderV2(){
        return new PersonV2(new Name("Pepe", "Argento"));
    }

    //produces
    @GetMapping(value="/person/produces", produces="application/vnd.company.app-v1+json")
    public PersonV1 personProducesV1(){
        return new PersonV1("Pepe", "Argento");
    }

    @GetMapping(value="/person/produces", produces="application/vnd.company.app-v2+json")
    public PersonV2 personProducesV2(){
        return new PersonV2(new Name("Pepe", "Argento"));
    }
}
