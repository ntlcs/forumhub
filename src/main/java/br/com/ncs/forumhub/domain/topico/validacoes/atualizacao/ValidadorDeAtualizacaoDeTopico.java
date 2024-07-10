package br.com.ncs.forumhub.domain.topico.validacoes.atualizacao;

import br.com.ncs.forumhub.domain.topico.DadosTopicoAtualizacao;

public interface ValidadorDeAtualizacaoDeTopico {

    void validar(Long id, DadosTopicoAtualizacao dadosTopicoAtualizacao);

}
