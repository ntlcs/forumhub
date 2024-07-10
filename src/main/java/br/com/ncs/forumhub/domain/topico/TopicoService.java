package br.com.ncs.forumhub.domain.topico;

import br.com.ncs.forumhub.domain.Curso;
import br.com.ncs.forumhub.domain.CursoRepository;
import br.com.ncs.forumhub.domain.ValidacaoException;
import br.com.ncs.forumhub.domain.topico.validacoes.atualizacao.ValidadorDeAtualizacaoDeTopico;
import br.com.ncs.forumhub.domain.topico.validacoes.postagem.ValidadorDePostagemDeTopico;
import br.com.ncs.forumhub.domain.usuario.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidadorDePostagemDeTopico> validadorDePostagemDeTopicos;

    @Autowired
    private List<ValidadorDeAtualizacaoDeTopico> validadorDeAtualizacaoDeTopicos;

    public DadosDetalhamentoTopico postar(DadosTopicoPostagem dados) {
        if (!cursoRepository.existsById(dados.idCurso())) throw new ValidacaoException("Nenhum curso cadastrado com o ID informado!");

        validadorDePostagemDeTopicos.forEach(validador -> validador.validar(dados));

        var curso = cursoRepository.findById(dados.idCurso()).get();
        var usuario = AutenticacaoService.getUsuarioLogado();
        var topico = new Topico(dados, usuario, curso);
        topicoRepository.save(topico);

        return new DadosDetalhamentoTopico(topico);
    }

    public DadosDetalhamentoTopico atualizar(Long id, DadosTopicoAtualizacao dados) {
        if (!topicoRepository.existsById(id)) throw new ValidacaoException("Nenhum tópico encontrado com o ID informado!");

        Curso curso = null;
        if (dados.idCurso() != null){
            if (!cursoRepository.existsById(dados.idCurso())){
                throw new ValidacaoException("Nenhum curso cadastrado com o ID informado!");
            } else {
                curso = cursoRepository.getReferenceById(dados.idCurso());
            }
        }

        validadorDeAtualizacaoDeTopicos.forEach(validador -> validador.validar(id, dados));

        var topico = topicoRepository.getReferenceById(id);
        topico.atualizarInformacoes(dados, curso);

        return new DadosDetalhamentoTopico(topico);
    }
}
