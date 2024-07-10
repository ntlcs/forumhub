package br.com.ncs.forumhub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Tag(name = "Hello")
public class HelloController {

    @GetMapping
    @Operation(summary = "Dá um oi", description = "Dá um oi para o usuário. Serve como um end point público para verrificar se a API está em execução.")
    public String olaMundo(){
        return "Oi meu chapa";
    }

}
