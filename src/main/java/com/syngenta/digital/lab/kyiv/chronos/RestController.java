package com.syngenta.digital.lab.kyiv.chronos;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/hi")
    public String sayHi (){
        return "Hi";
    }
}
