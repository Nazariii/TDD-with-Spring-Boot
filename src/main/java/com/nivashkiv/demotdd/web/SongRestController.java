package com.nivashkiv.demotdd.web;

import com.nivashkiv.demotdd.domain.Song;
import com.nivashkiv.demotdd.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "song")
public class SongRestController {

    @Autowired
    private SongService songService;

    @GetMapping
    List<Song> getAllSongs() {
        return songService.findAll();
    }

    @GetMapping(path = "author")
    List<Song> getAllSongs(@RequestParam String author) {
        return songService.findAllByAuthor(author);
    }
}
