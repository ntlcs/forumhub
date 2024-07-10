package br.com.ncs.forumhub.domain.usuario;

import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.usuario.validacoes.atualizar.ValidadorDeAtualizacaoDeUsuario;
import br.com.ncs.forumhub.domain.usuario.validacoes.cadastrar.ValidadorDeCadastroDeUsuario;
import br.com.ncs.forumhub.domain.usuario.validacoes.desativar.ValidadorDeDesativacaoDeUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private List<ValidadorDeCadastroDeUsuario> validadorDeCadastroDeUsuarios;

    @Autowired
    private List<ValidadorDeAtualizacaoDeUsuario> validadorDeAtualizacaoUsuario;

    @Autowired
    private List<ValidadorDeDesativacaoDeUsuario> validadorDeDesativacaoDeUsuarios;

    public Page listar(Pageable paginacao, Boolean ativo) {
        Page page = null;
        if (ativo){
            page = usuarioRepository.findAllByAtivoTrue(paginacao).map(DadosDetalhamentoUsuario::new);
        } else {
            page  = usuarioRepository.findAll(paginacao).map(DadosDetalhamentoUsuario::new);
        }
        return page;
    }

    public DadosDetalhamentoUsuario cadastrar(DadosUsuarioCadastro dados) {
        validadorDeCadastroDeUsuarios.forEach(validador -> validador.validar(dados));

        var usuario = new Usuario(dados);
        usuarioRepository.save(usuario);
        return new DadosDetalhamentoUsuario(usuario);
    }

    public void desativar(Long id) {
        if (!usuarioRepository.existsById(id)) throw new ValidacaoException("Nenhum usuário encontrado com o ID fornecido!");
        validadorDeDesativacaoDeUsuarios.forEach(validador -> validador.validar(id));
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desativar();
    }

    public DadosDetalhamentoUsuario atualizar(Long id, DadosUsuarioAtualizacao dados) {
        if (!usuarioRepository.existsById(id)) throw new ValidacaoException("Nenhum usuário encontrado com o ID fornecido!");
        validadorDeAtualizacaoUsuario.forEach(validador -> validador.validar(dados));
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.atualizar(dados);
        return new DadosDetalhamentoUsuario(usuario);
    }
}