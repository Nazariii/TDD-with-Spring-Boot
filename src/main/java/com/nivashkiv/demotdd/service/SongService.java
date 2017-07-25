package com.nivashkiv.demotdd.service;


import com.nivashkiv.demotdd.domain.Song;

import java.util.List;
import java.util.Map;

public interface SongService {
    List<Song> findAll();
    List<Song> findAllByAuthor(String author);
    List<Song> addAuthorSongs(Map<String,String> songs, String Author);
}
