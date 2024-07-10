package br.com.ncs.forumhub.domain.topico;

import br.com.ncs.forumhub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository <Topico, Long> {

    Boolean existsByTituloIgnoreCase(String titulo);

    Boolean existsByMensagemIgnoreCase(String mensagem);

    @Query("SELECT t FROM Topico t WHERE LOWER(TRIM(t.curso.nome)) = LOWER(TRIM(:curso)) AND YEAR(t.dataCriacao) = :ano")
    Page<Topico> findAllByAnoAndCursoIngoreCase(Pageable paginacao, String curso, Integer ano);
}
