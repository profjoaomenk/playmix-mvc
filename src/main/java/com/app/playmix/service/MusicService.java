package com.app.playmix.service;

import com.app.playmix.model.Music;
import com.app.playmix.repository.IMusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {

    @Autowired
    private final IMusicRepository musicRepository;

    public MusicService(IMusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    // Retorna todas as músicas
    public List<Music> findAllMusics() {
        return musicRepository.findAll();
    }

    // Busca uma música pelo ID
    public Music findByIdMusic(Long id) {
        return musicRepository.findById(id).orElse(null);
    }

    // Cria ou atualiza uma música
    public void saveMusic(Music music) {
        musicRepository.save(music);
    }



    // Remove uma música pelo ID
    public void deleteByIdMusic(Long id) {
        Music music = findByIdMusic(id);
        if (music != null && (music.getPlaylists() != null && !music.getPlaylists().isEmpty())) {
            throw new RuntimeException("A música está vinculada a uma playlist. Remova-a da playlist primeiro antes de excluir.");
        }
        musicRepository.deleteById(id);
    }
}



