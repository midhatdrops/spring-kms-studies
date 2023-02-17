package br.com.midhatdrops.springkvmdemo.controller;

import br.com.midhatdrops.springkvmdemo.utils.DataKeyProvider;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/kms")
public class OrganizationController {


    private DataKeyProvider dataKeyProvider;


    @PostMapping()
    public Mono<String> testEncrypt(@RequestBody String plainText) {
        return Mono.just(dataKeyProvider.encrypt(plainText));
    }

    @GetMapping
    public Mono<String> testDecrypt() {
        return Mono.just(dataKeyProvider.decrypt());
    }


}
