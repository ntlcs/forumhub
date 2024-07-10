package br.com.ncs.forumhub.domain.resposta;

import jakarta.validation.constraints.NotNull;

public record DadosRespostaPostagem(
        @NotNull(message = "Mensagem deve ser informada para criar uma resposta") String mensagem,
        @NotNull(message = "Solução deve ser informada para criar uma resposta") String solucao) {
}
