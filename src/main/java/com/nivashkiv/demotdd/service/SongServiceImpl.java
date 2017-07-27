package com.nivashkiv.demotdd.service;


import com.nivashkiv.demotdd.domain.Song;
import com.nivashkiv.demotdd.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class SongServiceImpl implements SongService {

    public static final String AUTHOR_IS_NOT_SPECIFIED_MSG = "Author is not specified";
    public static final String SONGS_MAP_IS_NULL_MSG = "Songs map is null";

    private SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public List<Song> findAllByAuthor(String author) {
        validateAuthor(author);
        return songRepository.findByAuthor(author.trim().toLowerCase());
    }

    @Override
    public List<Song> addAuthorSongs(Map<String, String> songs, String author) {
        Assert.notNull(songs, SONGS_MAP_IS_NULL_MSG);
        validateAuthor(author);
        return songs.entrySet().stream()
                .map(entry -> new Song(entry.getKey(), entry.getValue(), author.trim().toLowerCase()))
                .map(songRepository::save)
                .collect(toList());
    }

    private void validateAuthor(String author) {
        Assert.hasText(author, AUTHOR_IS_NOT_SPECIFIED_MSG);
    }
}
