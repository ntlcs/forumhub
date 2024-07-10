package br.com.ncs.forumhub.domain.resposta.validacoes.atualizacao;


import br.com.ncs.forumhub.domain.resposta.DadosRespostaAtualizacao;

public interface ValidadorDeAtualizacaoResposta {

    void validar(Long idResposta, DadosRespostaAtualizacao dadosRespostaAtualizacao);

}
