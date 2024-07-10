package br.com.ncs.forumhub.domain.resposta.validacoes.atualizacao;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.resposta.DadosRespostaAtualizacao;
import br.com.ncs.forumhub.domain.resposta.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoRespostaMensagemRepetida implements ValidadorDeAtualizacaoResposta {

    @Autowired
    private RespostaRepository respostaRepository;

    @Override
    public void validar(Long idResposta, DadosRespostaAtualizacao dadosRespostaAtualizacao) {
        var resposta = respostaRepository.getReferenceById(idResposta);
        if (dadosRespostaAtualizacao.mensagem() != null && respostaRepository.optionalRespostaByIdTopicoAndMensagemIgnoreCase(resposta.getTopico().getId(), dadosRespostaAtualizacao.mensagem().trim()).isPresent()){
            throw new ValidacaoException("Já existe uma resposta para este tópico com essa mensagem!");
        }
    }
}
