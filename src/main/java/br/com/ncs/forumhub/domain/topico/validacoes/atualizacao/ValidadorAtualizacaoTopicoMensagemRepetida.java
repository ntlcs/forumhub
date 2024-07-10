package br.com.ncs.forumhub.domain.topico.validacoes.atualizacao;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.topico.DadosTopicoAtualizacao;
import br.com.ncs.forumhub.domain.topico.TopicoRepository;
import br.com.ncs.forumhub.domain.topico.validacoes.atualizacao.ValidadorDeAtualizacaoDeTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoTopicoMensagemRepetida implements ValidadorDeAtualizacaoDeTopico {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(Long id, DadosTopicoAtualizacao dadosTopicoAtualizacao) {
        if (dadosTopicoAtualizacao.mensagem() != null && topicoRepository.existsByMensagemIgnoreCase(dadosTopicoAtualizacao.mensagem().trim())) {
            throw new ValidacaoException("Já existe um tópico criado com essa mensagem!");
        }
    }
}
