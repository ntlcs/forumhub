package br.com.ncs.forumhub.domain.resposta.validacoes.postagem;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.resposta.DadosRespostaPostagem;
import br.com.ncs.forumhub.domain.topico.StatusTopico;
import br.com.ncs.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPostagemRespostaTopicoResolvido implements ValidadorDePostagemDeResposta {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(Long idTopico, DadosRespostaPostagem dadosRespostaPostagem) {
        if (StatusTopico.RESOLVIDO.equals(topicoRepository.getReferenceById(idTopico).getStatus())){
            throw new ValidacaoException("Tópicos resolvidos não podem receber novas respostas!");
        }
    }
}
