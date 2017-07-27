package com.nivashkiv.demotdd.repository;

import com.nivashkiv.demotdd.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByAuthor(String author);
}
