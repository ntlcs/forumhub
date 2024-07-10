package br.com.ncs.forumhub.controller;

import br.com.ncs.forumhub.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Postar Tópico", description = "Realiza a publicação de um tópico no fórum.")
    public ResponseEntity postar(@RequestBody @Valid DadosTopicoPostagem dados, UriComponentsBuilder uriBuilder){
        var dadosDetalhamentoTopico = topicoService.postar(dados);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(dadosDetalhamentoTopico.id()).toUri();
        return ResponseEntity.created(uri).body(dadosDetalhamentoTopico);
    }

    @GetMapping
    @Operation(summary = "Listar Todos os Tópicos Postados", description = "Lista todos os tópicos postados no fórum ordenados pelo ID do tópico em páginas de 10 itens.")
    @Parameter(in = ParameterIn.QUERY, description = "Número da página para exibir. Primeira página é 0.", name = "page", content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY, description = "Quantidade de itens exibidos. Valor padrão é 10.", name = "size", content = @Content(schema = @Schema(type = "integer", defaultValue = "10")))
    @Parameter(in = ParameterIn.QUERY, description = "Critério de ordenação dos itens. Campo padrão é ID.", name = "sort", content = @Content(schema = @Schema(type = "string", defaultValue = "id")))
    public ResponseEntity listarTodosOsTopicos(@Parameter(hidden = true) @PageableDefault(size = 10, sort = "id") Pageable paginacao){
        var page = topicoRepository.findAll(paginacao).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/filtro")
    @Operation(summary = "Listar Todos os Tópicos Postados por Curso e Ano de Publicação", description = "Lista todos os tópicos postados no fórum ordenados pelo ID do tópico em páginas de 10 itens.")
    @Parameter(in = ParameterIn.QUERY, description = "Número da página para exibir. Primeira página é 0.", name = "page", content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY, description = "Quantidade de itens exibidos. Valor padrão é 10.", name = "size", content = @Content(schema = @Schema(type = "integer", defaultValue = "10")))
    @Parameter(in = ParameterIn.QUERY, description = "Critério de ordenação dos itens. Campo padrão é ID.", name = "sort", content = @Content(schema = @Schema(type = "string", defaultValue = "id")))
    public ResponseEntity listarTodosOsTopicosPorAnoECurso(@Parameter(hidden = true) @PageableDefault(size = 10, sort = "id") Pageable paginacao,
                                                           @RequestParam(required = true, name = "curso") String curso,
                                                           @RequestParam(required = true, name = "ano") Integer ano){
        Page<DadosDetalhamentoTopico> page = topicoRepository.findAllByAnoAndCursoIngoreCase(paginacao, curso, ano).map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar Tópico por ID", description = "Listar um tópico postado selecionando o mesmo pelo ID.")
    public ResponseEntity buscarTopicoEspecifico (@PathVariable Long id){
        var dadosDetalhamentoTopico = new DadosDetalhamentoTopico(topicoRepository.getReferenceById(id));
        return ResponseEntity.ok(dadosDetalhamentoTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Atualizar/Editar Tópico por ID", description = "Atualizar/editar as informações publicadas no tópico selecionando o mesmo pelo ID.")
    public ResponseEntity atualizarTopico(@PathVariable Long id, @RequestBody DadosTopicoAtualizacao dados){
        var dadosDetalhamentoTopico = topicoService.atualizar(id, dados);
        return ResponseEntity.ok(dadosDetalhamentoTopico);
    }

    @PutMapping("/finalizar/{id}")
    @Transactional
    @Operation(summary = "Marcar Tópico Como Resolvido", description = "Marca o tópico como resolvido, indicando que o assunto foi concluído e impedindo novas respostas.")
    public ResponseEntity finalizarTopico(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        topico.finalizarTopico();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Excluir Tópico por ID", description = "Excluir um tópico selecionando o mesmo pelo ID.")
    public ResponseEntity excluirTopico(@PathVariable Long id){
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
