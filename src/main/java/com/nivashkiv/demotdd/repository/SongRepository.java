package com.nivashkiv.demotdd.repository;

import com.nivashkiv.demotdd.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByAuthor(String author);
}
