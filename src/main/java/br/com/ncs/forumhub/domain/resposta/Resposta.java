package br.com.ncs.forumhub.domain.resposta;

import br.com.ncs.forumhub.domain.topico.Topico;
import br.com.ncs.forumhub.domain.usuario.AutenticacaoService;
import br.com.ncs.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity(name = "Resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String solucao;

    public Resposta(Topico topico, DadosRespostaPostagem dados) {
        this.mensagem = dados.mensagem().trim();
        this.topico = topico;
        this.dataCriacao = LocalDateTime.now();
        this.usuario = AutenticacaoService.getUsuarioLogado();
        this.solucao = dados.solucao().trim();
    }

    public void atualizar(DadosRespostaAtualizacao dados){
        if (dados.mensagem() != null) this.mensagem = dados.mensagem();
        if (dados.solucao() != null) this.solucao = dados.solucao();
    }
}
