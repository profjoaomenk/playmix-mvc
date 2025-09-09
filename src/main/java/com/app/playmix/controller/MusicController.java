package com.app.playmix.controller;

import com.app.playmix.model.GeneroMusical;
import com.app.playmix.model.Music;
import com.app.playmix.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/musics")
public class MusicController {

    @Autowired
    private MusicService musicService;

    // Exibe a lista de músicas
    @GetMapping
    public ModelAndView listMusics() {
        List<Music> musics = musicService.findAllMusics();
        ModelAndView modelAndView = new ModelAndView("music/list");
        modelAndView.addObject("musics", musics);
        return modelAndView;
    }

    // Busca uma música pelo ID
    @GetMapping("/{id}")
    public ModelAndView showMusicDetails(@PathVariable Long id) {
        Music music = musicService.findByIdMusic(id);
        ModelAndView modelAndView = new ModelAndView("music/details");
        modelAndView.addObject("music", music);
        return modelAndView;
    }

    // Exibe o formulário para adicionar uma nova música
    @GetMapping("/new")
    public ModelAndView showCreateMusicForm() {
        ModelAndView modelAndView = new ModelAndView("music/form");
        modelAndView.addObject("music", new Music());
        return modelAndView;
    }

    // Processa a criação de uma nova música
    @PostMapping
    public String saveMusic(@ModelAttribute Music music) {
        musicService.saveMusic(music);
        return "redirect:/musics";
    }

    // Exibe o formulário para editar uma música existente
    @GetMapping("/edit/{id}")
    public ModelAndView showEditMusicForm(@PathVariable Long id) {
        Music music = musicService.findByIdMusic(id);
        ModelAndView modelAndView = new ModelAndView("music/form");
        modelAndView.addObject("music", music);
        return modelAndView;
    }

    // Processa a atualização de uma música existente
    @PostMapping("/{id}")
    public String updateMusic(@PathVariable Long id, @ModelAttribute Music music) {
        music.setId(id);
        musicService.saveMusic(music);
        return "redirect:/musics";
    }

    // Remove uma música pelo ID
    @GetMapping("/delete/{id}")
    public ModelAndView deleteMusic(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("music/list");
        try {
            musicService.deleteByIdMusic(id);

            modelAndView.addObject("message", "Música excluída com sucesso.");
           //  modelAndView.setViewName("redirect:/music");
        } catch (RuntimeException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.addObject("musics", musicService.findAllMusics());
        return modelAndView;
    }

    @ModelAttribute("generos")
    public GeneroMusical[] getGeneros(){
        return GeneroMusical.values();
    }
}

