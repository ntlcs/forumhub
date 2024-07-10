package br.com.ncs.forumhub.domain.topico.validacoes.postagem;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.topico.DadosTopicoPostagem;
import br.com.ncs.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorNovoTopicoMensagemRepetida implements ValidadorDePostagemDeTopico {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DadosTopicoPostagem dadosTopicoPostagem) {
        if (topicoRepository.existsByMensagemIgnoreCase(dadosTopicoPostagem.mensagem().trim())) throw new ValidacaoException("Já existe um tópico criado com essa mensagem!");
    }
}
