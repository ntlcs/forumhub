package br.com.ncs.forumhub.controller;

import br.com.ncs.forumhub.domain.usuario.DadosUsuarioAtualizacao;
import br.com.ncs.forumhub.domain.usuario.DadosUsuarioCadastro;
import br.com.ncs.forumhub.domain.usuario.UsuarioRepository;
import br.com.ncs.forumhub.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    @Operation(summary = "Listar Usuários Cadastrados", description = "Lista todos os usuários cadastrados, tendo a opção de filtrar por usuários ativos. A orderalção padrão é pelo ID do usuário em páginas de 10 itens.")
    @Parameter(in = ParameterIn.QUERY, description = "Número da página para exibir. Primeira página é 0.", name = "page", content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY, description = "Quantidade de itens exibidos. Valor padrão é 10.", name = "size", content = @Content(schema = @Schema(type = "integer", defaultValue = "10")))
    @Parameter(in = ParameterIn.QUERY, description = "Critério de ordenação dos itens. Campo padrão é ID.", name = "sort", content = @Content(schema = @Schema(type = "string", defaultValue = "id")))
    public ResponseEntity listarTodosUsuarios(@Parameter(hidden = true) @PageableDefault(size = 10, sort = "id") Pageable paginacao,
                                              @RequestParam(required = true, name = "ativo") Boolean ativo){
        var page = usuarioService.listar(paginacao, ativo);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar Usuário", description = "Realiza o cadastro de um usuário.")
    public ResponseEntity cadastrar(@RequestBody @Valid DadosUsuarioCadastro dados, UriComponentsBuilder uriBuilder){
        var dadosDetalhamentoUsuario = usuarioService.cadastrar(dados);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(dadosDetalhamentoUsuario.id()).toUri();
        return ResponseEntity.created(uri).body(dadosDetalhamentoUsuario);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar/Editar Usuário por ID", description = "Atualizar/editar as informações dos usuários cadastrados pelo ID do usuário.")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosUsuarioAtualizacao dados){
        var dadosDetalhamentoUsuario = usuarioService.atualizar(id, dados);
        return ResponseEntity.ok(dadosDetalhamentoUsuario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Exclusão Logica Usuário por ID", description = "Excluir de forma lógica (ativado/desativado) um usuário pelo ID do mesmo.")
    public ResponseEntity desativar(@PathVariable Long id){
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }

}
