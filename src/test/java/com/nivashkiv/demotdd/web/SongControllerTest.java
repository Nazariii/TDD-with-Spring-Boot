package com.nivashkiv.demotdd.web;

import com.nivashkiv.demotdd.domain.Song;
import com.nivashkiv.demotdd.extensions.MockitoExtension;
import com.nivashkiv.demotdd.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SongControllerTest {

    @Mock
    private SongService songService;

    @InjectMocks
    private SongController songController;

    private MockMvc mockMvc;

    private final String testAuthor = "testAuthor";
    private List<Song> songs = asList(new Song("testTitle", "test song text", testAuthor));


    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(songController).build();
        when(songService.findAll()).thenReturn(songs);
    }

    @Test
    public void allSongsAreAddedToModelForLibraryView() throws Exception {
        Map<String, Object> map = new HashMap<>();
        assertThat(songController.getAllSongs(map), equalTo("playlist"));

        assertAll("entries",
                () -> assertThat(map, hasEntry("author", "All")),
                () -> assertThat(map, hasEntry("songs", songs))
        );
    }

    @Test
    public void requestForAllSongsIsSuccessful() throws Exception {
        mockMvc.perform(get("/playlist.htm"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("songs", equalTo(songs)))
                .andExpect(forwardedUrl("playlist"));
    }
}