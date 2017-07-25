package com.nivashkiv.demotdd;


import com.nivashkiv.demotdd.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DemoRunner implements CommandLineRunner {
    @Autowired
    SongService songService;

    @Autowired
    DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> metallicaSong = new HashMap<>();
        String metallicaName = "Metallica";
        metallicaSong.put("One", "Text of One ...");
        metallicaSong.put("Master of Puppets", "Text of Master of Puppets ...");
        metallicaSong.put("Nothing else matters", "Text of Nothing else matters ...");
        songService.addAuthorSongs(metallicaSong, metallicaName);

        Map<String, String> queenSong = new HashMap<>();
        String queenName = "Queen";
        queenSong.put("Killer queen", "Text of Killer queen ...");
        queenSong.put("Bohemian Rhapsody", "Text of Bohemian Rhapsody ...");
        queenSong.put("We will rock you", "Text of We will rock you ...");
        songService.addAuthorSongs(queenSong, queenName);

        System.out.println("DATASOURCE = " + dataSource);
        System.out.println("Connection = " + dataSource.getConnection());
    }


}
