package br.com.ncs.forumhub.domain.topico;

import br.com.ncs.forumhub.domain.Curso;
import br.com.ncs.forumhub.domain.resposta.Resposta;
import br.com.ncs.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", fetch = FetchType.LAZY)
    private List<Resposta> respostas;

    public Topico(DadosTopicoPostagem dados, Usuario usuario, Curso curso) {
        this.titulo = dados.titulo().trim();
        this.mensagem = dados.mensagem().trim();
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusTopico.NAO_RESOLVIDO;
        this.usuario = usuario;
        this.curso = curso;
    }

    public void atualizarInformacoes(DadosTopicoAtualizacao dados, Curso curso) {
        if (dados.titulo() != null) this.titulo = dados.titulo().trim();
        if (dados.mensagem() != null) this.mensagem = dados.mensagem().trim();
        if (curso != null) this.curso = curso;
    }

    public void finalizarTopico() {
        this.status = StatusTopico.RESOLVIDO;
    }
}
