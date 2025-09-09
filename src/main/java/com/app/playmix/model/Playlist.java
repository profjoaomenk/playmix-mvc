package com.app.playmix.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "playlist")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da playlist é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome da playlist deve ter entre 2 e 100 caracteres.")
    private String nome;

    @Column(name = "data_criacao", updatable = false)
    private LocalDate dataCriacao;

    @ManyToMany
    @JoinTable(
            name = "playlist_music",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "music_id")
    )
    @Valid
    @Size(min = 1, message = "É necessário selecionar pelo menos uma música")
    private List<Music> musicas;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

}
