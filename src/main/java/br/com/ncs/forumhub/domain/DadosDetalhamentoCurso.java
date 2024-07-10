package br.com.ncs.forumhub.domain;

public record DadosDetalhamentoCurso(Long idCurso, String nomeCurso, CategoriaCurso categoriaCurso) {
    public DadosDetalhamentoCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}