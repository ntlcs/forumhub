package br.com.ncs.forumhub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosAutenticacao(@Email(message = "Email deve possuir o formato email@email.com")
                                @NotNull(message = "Email não pode ficar vazio") String email,
                                @NotNull(message = "Senha não pode ficar vazia") String senha) {
}
