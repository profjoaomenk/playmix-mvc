package com.app.playmix.service;

import com.app.playmix.model.Playlist;
import com.app.playmix.repository.IPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private final IPlaylistRepository playlistRepository;

    public PlaylistService(IPlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    // Retorna todas as playlists
    public List<Playlist> findAllPlaylists() {
        return playlistRepository.findAll();
    }

    // Busca uma playlist pelo ID
    public Playlist findByIdPlaylist(Long id) {
        return playlistRepository.findById(id).orElse(null);
    }

    // Salva ou atualiza uma playlist
    public Playlist savePlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    // Remove uma playlist pelo ID
    public void deleteByIdPlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}
