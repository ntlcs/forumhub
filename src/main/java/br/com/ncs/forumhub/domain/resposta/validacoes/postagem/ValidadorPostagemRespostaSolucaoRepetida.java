package br.com.ncs.forumhub.domain.resposta.validacoes.postagem;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.resposta.DadosRespostaPostagem;
import br.com.ncs.forumhub.domain.resposta.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPostagemRespostaSolucaoRepetida implements ValidadorDePostagemDeResposta {

    @Autowired
    private RespostaRepository respostaRepository;

    @Override
    public void validar(Long idTopico, DadosRespostaPostagem dadosRespostaPostagem) {
        if (respostaRepository.optionalRespostaByIdTopicoAndSolucaoIgnoreCase(idTopico, dadosRespostaPostagem.solucao().trim()).isPresent()){
            throw new ValidacaoException("Já existe uma resposta para este tópico com essa solução!");
        }
    }
}
