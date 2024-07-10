package br.com.ncs.forumhub.domain.usuario;

import br.com.ncs.forumhub.domain.resposta.Resposta;
import br.com.ncs.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topico> topicos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resposta> respostas;

    private Boolean ativo;

    public Usuario(DadosUsuarioCadastro dados) {
        this.nome = dados.nome().trim();
        this.email = dados.email().trim();
        this.senha = encoderSenha().encode(dados.senha().trim());
        this.ativo = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private BCryptPasswordEncoder encoderSenha (){
        return new BCryptPasswordEncoder();
    }

    public void desativar() {
        this.ativo = false;
    }

    public void atualizar(DadosUsuarioAtualizacao dados) {
        if (dados.nome() != null) this.nome = dados.nome().trim();
        if (dados.email() != null) this.email = dados.email().trim();
        if (dados.senha() != null) this.senha = encoderSenha().encode(dados.senha().trim());
    }
}