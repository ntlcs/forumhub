package br.com.ncs.forumhub.controller;

import br.com.ncs.forumhub.domain.usuario.DadosAutenticacao;
import br.com.ncs.forumhub.domain.usuario.Usuario;
import br.com.ncs.forumhub.infra.security.DadosTokenJWT;
import br.com.ncs.forumhub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação/Login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Realizar Login", description = "Gera um Token JWT para ser enviado junto com as demais requisições da API. O Token JWT se responsabiliza por autenticar as requisições.")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){
        System.out.println(dados);
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        System.out.println("authenticationToken: " + authenticationToken);
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}