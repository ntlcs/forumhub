package br.com.ncs.forumhub.domain.topico.validacoes.postagem;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.topico.DadosTopicoPostagem;
import br.com.ncs.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorNovoTopicoTituloRepetiddo implements ValidadorDePostagemDeTopico {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DadosTopicoPostagem dadosTopicoPostagem) {
        if (topicoRepository.existsByTituloIgnoreCase(dadosTopicoPostagem.titulo().trim())) throw new ValidacaoException("Já existe um tópico criado com esse título!");
    }
}
