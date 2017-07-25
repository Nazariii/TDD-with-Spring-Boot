package com.nivashkiv.demotdd.web;

import com.nivashkiv.demotdd.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping(path = "playlist.htm")
    String getAllSongs(Map<String, Object> model) {
        model.put("songs", songService.findAll());
        model.put("author", "All");
        return "playlist";
    }

}
