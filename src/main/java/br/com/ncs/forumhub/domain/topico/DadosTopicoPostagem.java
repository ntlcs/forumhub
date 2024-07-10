package br.com.ncs.forumhub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DadosTopicoPostagem(@NotNull(message = "Título deve ser informado para criar um tópico") String titulo,
                                  @NotNull(message = "Mensagem deve ser informada para criar um tópico") String mensagem,
                                  @NotNull(message = "ID do curso deve ser informado para criar um tópico") Long idCurso) {
}
