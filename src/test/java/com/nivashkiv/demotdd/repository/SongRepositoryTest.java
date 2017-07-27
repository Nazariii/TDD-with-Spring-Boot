package com.nivashkiv.demotdd.repository;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.nivashkiv.demotdd.domain.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@ExtendWith(DBUnitExtension.class)
public class SongRepositoryTest {
    private static long ID = 1;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    //Required by db-rider
    public ConnectionHolder connectionHolder = () -> jdbcTemplate.getDataSource().getConnection();

    @Autowired
    protected TestEntityManager em;

    @Autowired
    protected SongRepository songRepository;

    @DataSet//Now missing @DataSet causing NPE. Will be fixed in new release
    @Test
    public void ifThereIsNoSongWithSuchAuthorEmptyListIsReturned() {
        assertThat(songRepository.findByAuthor("unknown"), is(empty()));
    }

    @DataSet
    @Test
    public void ifSongsByAuthorAreFoundTheyAreReturned() {
        long id = addRecordToDatabase("Title", "text", "author");

        Song song = new Song("Title", "text", "author");
        song.setId(id);
        assertThat(songRepository.findByAuthor("author"), hasItem(samePropertyValuesAs(song)));
    }

    @Test
    @DataSet("stored-songs.yml")
    public void ifSongAlreadyExistsItMayBeFoundUsingSeveralMethods() {
        Song song = new Song("Song title", "Song text", "Unknown");
        song.setId(25L);
        assertThat(songRepository.findAll(), hasItem(samePropertyValuesAs(song)));
        assertThat(songRepository.getOne(25L), samePropertyValuesAs(song));
        assertThat(songRepository.findById(25L).get(), samePropertyValuesAs(song));
        assertThat(songRepository.findByAuthor("Unknown"), hasItem(samePropertyValuesAs(song)));
    }

    long addRecordToDatabase(String title, String text, String author) {
        long id = ID++;

        jdbcTemplate.update("INSERT INTO Song " +
                        " (id, title, text, author" +
                        ") VALUES (? , ?, ?, ? )"
                , id, title, text, author);
        return id;
    }
}