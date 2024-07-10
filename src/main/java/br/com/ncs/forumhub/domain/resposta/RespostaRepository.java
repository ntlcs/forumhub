package br.com.ncs.forumhub.domain.resposta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    @Query("SELECT r FROM Resposta r WHERE r.topico.id = :idTopico")
    Page<Resposta> findAllByTopicoId(Pageable paginacao, Long idTopico);

    @Query("SELECT r FROM Resposta r WHERE r.topico.id = :idTopico AND LOWER(TRIM(r.mensagem)) = LOWER(TRIM(:mensagem))")
    Optional<Resposta> optionalRespostaByIdTopicoAndMensagemIgnoreCase(Long idTopico, String mensagem);

    @Query("SELECT r FROM Resposta r WHERE r.topico.id = :idTopico AND LOWER(TRIM(r.solucao)) = LOWER(TRIM(:solucao))")
    Optional<Resposta> optionalRespostaByIdTopicoAndSolucaoIgnoreCase(Long idTopico, String solucao);

    @Query("SELECT r FROM Resposta r WHERE r.topico.id = :idTopico AND r.id = :idResposta")
    Optional<Resposta> getReferenceByIdAndTopicoId(Long idTopico, Long idResposta);
}
