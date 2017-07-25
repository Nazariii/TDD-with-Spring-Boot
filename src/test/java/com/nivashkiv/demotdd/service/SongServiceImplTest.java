package com.nivashkiv.demotdd.service;

import com.nivashkiv.demotdd.domain.Song;
import com.nivashkiv.demotdd.extensions.MockitoExtension;
import com.nivashkiv.demotdd.repository.SongRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nivashkiv.demotdd.service.SongServiceImpl.AUTHOR_IS_NOT_SPECIFIED_MSG;
import static com.nivashkiv.demotdd.service.SongServiceImpl.SONGS_MAP_IS_NULL_MSG;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private SongServiceImpl songService;

    private final String testAuthor = "test Author";
    private final String testAuthorTrimmedLowCase = "test author";
    private final String testTitle = "testTitle";
    private final String testText = "testText";
    private final Song testSong = new Song(testTitle, testText, testAuthorTrimmedLowCase);

    @Test
    public void findAll() throws Exception {
        when(songRepository.findAll()).thenReturn(asList(testSong));
        List<Song> songs = songService.findAll();
        assertThat(songs, hasItem(testSong));
    }

    @Test
    public void findAllByAuthor() throws Exception {
        when(songRepository.findByAuthor(eq(testAuthorTrimmedLowCase))).thenReturn(asList(testSong));
        List<Song> songs = songService.findAllByAuthor(testAuthor);
        assertThat(songs, hasItem(testSong));
    }

    @Test
    public void successfulCreationOfAuthorSongs() throws Exception {
        String secondSongTitle = "Song title 2";
        String secondSongText = "Song text 2";
        Song secondSong = new Song(secondSongTitle, secondSongText, testAuthor);
        when(songRepository.save(any(Song.class))).thenReturn(testSong).thenReturn(secondSong);

        Map<String, String> songsMap = new HashMap<>();
        songsMap.put(testTitle, testText);
        songsMap.put(secondSongTitle, secondSongText);
        List<Song> songs = songService.addAuthorSongs(songsMap, testAuthor);
        assertThat(songs, hasItems(testSong, secondSong));
        verify(songRepository, times(2)).save(any(Song.class));
    }

    @Test
    public void authorIsNull() throws Exception {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> songService.addAuthorSongs(new HashMap<>(), null));
        assertThat(exception.getMessage(), is(AUTHOR_IS_NOT_SPECIFIED_MSG));
    }

    @Test
    public void songListIsEmpty() throws Exception {
        suppose
        Map<String, String> songsMap = new HashMap<>();
        List<Song> songs = songService.addAuthorSongs(songsMap, testAuthor);
        verify(songRepository, times(0)).save(any(Song.class));
        assertThat(songs, is(empty()));
    }

    @Test
    public void songListIsNull() throws Exception {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> songService.addAuthorSongs(null, testAuthor));
        assertThat(exception.getMessage(), is(SONGS_MAP_IS_NULL_MSG));
    }
}