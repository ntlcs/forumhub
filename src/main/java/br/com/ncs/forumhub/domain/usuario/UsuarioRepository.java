package br.com.ncs.forumhub.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    UserDetails findByEmailAndAtivoTrue(String email);

    Boolean existsByEmail(String email);

    Page<Usuario> findAllByAtivoTrue(Pageable paginacao);
}