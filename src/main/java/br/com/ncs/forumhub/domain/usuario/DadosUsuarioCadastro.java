package br.com.ncs.forumhub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosUsuarioCadastro(@NotNull(message = "Nome é obrigatório para cadastrar um usuário")
                                   String nome,
                                   @NotNull(message = "Email é obrigatório para cadastrar um usuário")
                                   @Email(message = "Email deve seguir o formato email@email.com")
                                   String email,
                                   @NotNull(message = "Senha é obrigatória para cadastrar um usuário")
                                   String senha) {
}
