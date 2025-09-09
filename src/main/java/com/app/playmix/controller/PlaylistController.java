package com.app.playmix.controller;

import com.app.playmix.model.Playlist;
import com.app.playmix.service.MusicService;
import com.app.playmix.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private MusicService musicService;

    // Exibe a lista de playlists
    @GetMapping
    public ModelAndView listPlaylists() {
        List<Playlist> playlists = playlistService.findAllPlaylists();
        ModelAndView modelAndView = new ModelAndView("playlist/list");
        modelAndView.addObject("playlists", playlists);
        return modelAndView;
    }

    // Exibe os detalhes de uma playlist pelo ID
    @GetMapping("/{id}")
    public ModelAndView showPlaylistDetails(@PathVariable Long id) {
        Playlist playlist = playlistService.findByIdPlaylist(id);
        ModelAndView modelAndView = new ModelAndView("playlist/details");
        modelAndView.addObject("playlist", playlist);
        return modelAndView;
    }

    // Exibe o formulário para adicionar uma nova playlist
    @GetMapping("/new")
    public ModelAndView showCreatePlaylistForm() {
        ModelAndView modelAndView = new ModelAndView("playlist/form");
        modelAndView.addObject("playlist", new Playlist());
        modelAndView.addObject("musics", musicService.findAllMusics());
        return modelAndView;
    }

    // Processa a criação de uma nova playlist
    @PostMapping
    public String savePlaylist(@Valid @ModelAttribute Playlist playlist) {
        playlistService.savePlaylist(playlist);
        return "redirect:/playlists";
    }

    // Exibe o formulário para editar uma playlist existente
    @GetMapping("/edit/{id}")
    public ModelAndView showEditPlaylistForm(@PathVariable Long id) {
        Playlist playlist = playlistService.findByIdPlaylist(id);
        ModelAndView modelAndView = new ModelAndView("playlist/form");
        modelAndView.addObject("playlist", playlist);
        modelAndView.addObject("musics", musicService.findAllMusics());
        return modelAndView;
    }

    // Processa a atualização de uma playlist existente
    @PostMapping("/{id}")
    public String updatePlaylist(@PathVariable Long id, @ModelAttribute Playlist playlist) {
        playlist.setId(id);
        playlistService.savePlaylist(playlist);
        return "redirect:/playlists";
    }

    // Remove uma playlist pelo ID
    @GetMapping("/delete/{id}")
    public ModelAndView deletePlaylist(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("playlist/list");
        try {
            playlistService.deleteByIdPlaylist(id);
            modelAndView.addObject("message", "Playlist excluída com sucesso.");
        } catch (RuntimeException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.addObject("playlists", playlistService.findAllPlaylists());
        return modelAndView;
    }
}
