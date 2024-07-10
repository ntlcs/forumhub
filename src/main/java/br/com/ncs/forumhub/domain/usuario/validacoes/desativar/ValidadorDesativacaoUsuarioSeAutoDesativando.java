package br.com.ncs.forumhub.domain.usuario.validacoes.desativar;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.usuario.AutenticacaoService;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDesativacaoUsuarioSeAutoDesativando implements ValidadorDeDesativacaoDeUsuario {

    @Override
    public void validar(Long id) {
        if (id.equals(AutenticacaoService.getUsuarioLogado().getId())){
            throw new ValidacaoException("Um usuário não pode se autodesativar!");
        }
    }
}
