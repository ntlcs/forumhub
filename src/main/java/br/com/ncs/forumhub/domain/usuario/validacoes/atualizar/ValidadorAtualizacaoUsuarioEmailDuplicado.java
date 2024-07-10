package br.com.ncs.forumhub.domain.usuario.validacoes.atualizar;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.usuario.DadosUsuarioAtualizacao;
import br.com.ncs.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoUsuarioEmailDuplicado implements ValidadorDeAtualizacaoDeUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DadosUsuarioAtualizacao dados) {
        if (dados.email() != null && usuarioRepository.existsByEmail(dados.email().trim())){
            throw new ValidacaoException("Já existe um usuário cadastrado com o email informado!");
        }
    }

}
