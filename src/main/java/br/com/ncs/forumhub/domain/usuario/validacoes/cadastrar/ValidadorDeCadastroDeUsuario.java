package br.com.ncs.forumhub.domain.usuario.validacoes.cadastrar;

import br.com.ncs.forumhub.domain.usuario.DadosUsuarioCadastro;

public interface ValidadorDeCadastroDeUsuario {

    void validar(DadosUsuarioCadastro dados);

}
