package com.app.playmix.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da música é obrigatório.")
    @Size(min = 2, max = 100, message = "O título da música deve ter entre 2 e 100 caracteres.")
    private String titulo;

    @NotBlank(message = "O nome do artista é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome do artista deve ter entre 2 e 100 caracteres.")
    private String artista;

    private int anoLancamento;

    @NotBlank(message = "O nome do álbum é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome do álbum deve ter entre 2 e 100 caracteres.")
    private String album;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O gênero musical é obrigatório.")
    private GeneroMusical genero;

    @NotBlank(message = "A URL da capa do álbum é obrigatória.")
    @Size(min = 5, max = 255, message = "A URL da capa do álbum deve ter entre 5 e 255 caracteres.")
    @Column(name = "album_art_url")
    private String albumArtUrl;

    @ManyToMany(mappedBy = "musicas")
    @Valid
    private List<Playlist> playlists;
}
