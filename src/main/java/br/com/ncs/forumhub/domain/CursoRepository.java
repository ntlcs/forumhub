package br.com.ncs.forumhub.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository <Curso, Long> {
    Boolean existsByNomeIgnoreCase(String nome);
}
