package br.com.ncs.forumhub.domain.resposta.validacoes.postagem;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.resposta.DadosRespostaPostagem;
import br.com.ncs.forumhub.domain.resposta.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPostagemRespostaMensagemRepetida implements ValidadorDePostagemDeResposta {

    @Autowired
    private RespostaRepository respostaRepository;

    @Override
    public void validar(Long idTopico, DadosRespostaPostagem dadosRespostaPostagem) {
        if (respostaRepository.optionalRespostaByIdTopicoAndMensagemIgnoreCase(idTopico, dadosRespostaPostagem.mensagem().trim()).isPresent()){
            throw new ValidacaoException("Já existe uma resposta para este tópico com essa mensagem!");
        }
    }
}
