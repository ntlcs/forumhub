package br.com.ncs.forumhub.domain.usuario.validacoes.cadastrar;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.usuario.DadosUsuarioCadastro;
import br.com.ncs.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorCadastroUsuarioEmailDuplicado implements ValidadorDeCadastroDeUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void validar(DadosUsuarioCadastro dados) {
        if (usuarioRepository.existsByEmail(dados.email().trim())){
            throw new ValidacaoException("Já existe um usuário cadastrado com o email informado!");
        }
    }
}
