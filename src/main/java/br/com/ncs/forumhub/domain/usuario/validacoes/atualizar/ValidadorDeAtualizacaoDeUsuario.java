package br.com.ncs.forumhub.domain.usuario.validacoes.atualizar;

import br.com.ncs.forumhub.domain.usuario.DadosUsuarioAtualizacao;

public interface ValidadorDeAtualizacaoDeUsuario {

    void validar(DadosUsuarioAtualizacao dados);
}
