package br.com.ncs.forumhub.domain.resposta.validacoes.exclusao;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.resposta.RespostaRepository;
import br.com.ncs.forumhub.domain.topico.StatusTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorExclusaoRespostaTopicoResolvido implements ValidadorDeExclusaoResposta {

    @Autowired
    private RespostaRepository respostaRepository;

    @Override
    public void validar(Long idResposta) {
        if (StatusTopico.RESOLVIDO.equals(respostaRepository.getReferenceById(idResposta).getTopico().getStatus())){
            throw new ValidacaoException("Tópicos resolvidos não permitem exlusão de respostas!");
        }
    }
}
