package br.com.ncs.forumhub.controller;

import br.com.ncs.forumhub.domain.CursoService;
import br.com.ncs.forumhub.domain.DadosCursoCadastro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar Curso", description = "Cadastra um curso no banco de dados. Esses cursos serão usados posteriormente para vincular aos tópicos postados.")
    public ResponseEntity cadastrar (@RequestBody @Valid DadosCursoCadastro dados){
        var dadosDetalhamentoCurso = cursoService.cadastrar(dados);
        return ResponseEntity.ok(dadosDetalhamentoCurso);
    }

}
