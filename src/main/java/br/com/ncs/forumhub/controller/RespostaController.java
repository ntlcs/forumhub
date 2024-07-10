package br.com.ncs.forumhub.controller;

import br.com.ncs.forumhub.domain.resposta.*;
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
@RequestMapping("/respostas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private RespostaService respostaService;

    @GetMapping("/{idTopico}")
    @Operation(summary = "Listar Respostas de Tópicos Específicos", description = "Lista todos as respostas de tópicos específicos por ID. A ordenação padrão é pelo ID da resposta em páginas de 10 itens.")
    @Parameter(in = ParameterIn.QUERY, description = "Número da página para exibir. Primeira página é 0.", name = "page", content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY, description = "Quantidade de itens exibidos. Valor padrão é 10.", name = "size", content = @Content(schema = @Schema(type = "integer", defaultValue = "10")))
    @Parameter(in = ParameterIn.QUERY, description = "Critério de ordenação dos itens. Campo padrão é ID.", name = "sort", content = @Content(schema = @Schema(type = "string", defaultValue = "id")))
    public ResponseEntity listarRespostasTopicoEspecifico(@PathVariable Long idTopico, @Parameter(hidden = true) @PageableDefault(size = 10, sort = "id") Pageable paginacao) {
        var page = respostaRepository.findAllByTopicoId(paginacao, idTopico).map(DadosDetalhamentoResposta::new);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/{idTopico}")
    @Transactional
    @Operation(summary = "Postar Resposta", description = "Realiza a publicação de uma resposta em um tópico específico no fórum.")
    public ResponseEntity postar(@PathVariable Long idTopico, @RequestBody @Valid DadosRespostaPostagem dados, UriComponentsBuilder uriBuilder) {
        var dadosDetalhamentoResposta = respostaService.postar(idTopico, dados);
        var uri = uriBuilder.path("/respostas/{idResposta}").buildAndExpand(dadosDetalhamentoResposta.id()).toUri();
        return ResponseEntity.created(uri).body(dadosDetalhamentoResposta);
    }

    @PutMapping("/{idResposta}")
    @Transactional
    @Operation(summary = "Atualizar/Editar Resposta por ID", description = "Atualizar/editar as respostas dos tópicos filtrando das mesmas pelo ID da resposta.")
    public ResponseEntity atualizar(@PathVariable Long idResposta, @RequestBody DadosRespostaAtualizacao dados) {
        var dadosDetalhamentoResposta = respostaService.atualizar(idResposta, dados);
        return ResponseEntity.ok(dadosDetalhamentoResposta);
    }

    @DeleteMapping("/{idResposta}")
    @Transactional
    @Operation(summary = "Excluir Resposta por ID", description = "Excluir uma resposta selecionando o mesmo pelo ID da resposta.")
    public ResponseEntity excluir(@PathVariable Long idResposta) {
        respostaService.excluir(idResposta);
        return ResponseEntity.noContent().build();
    }

}
