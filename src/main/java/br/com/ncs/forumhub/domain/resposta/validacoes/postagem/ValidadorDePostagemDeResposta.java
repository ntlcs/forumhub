package br.com.ncs.forumhub.domain.resposta.validacoes.postagem;

import br.com.ncs.forumhub.domain.resposta.DadosRespostaPostagem;

public interface ValidadorDePostagemDeResposta {

    void validar(Long idTopico, DadosRespostaPostagem dadosRespostaPostagem);

}
