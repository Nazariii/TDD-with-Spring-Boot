package com.nivashkiv.demotdd.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.nivashkiv.demotdd.domain.Song;
import com.nivashkiv.demotdd.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SongController.class)
public class SongControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    private WebClient webClient;

    @MockBean
    private SongService songService;

    private List<Song> songs = asList(new Song("testTitle", "test song text", "testAuthor"));

    @BeforeEach
    public void setUp() throws Exception {
        when(songService.findAll()).thenReturn(songs);
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc)
                .useMockMvcForHosts("mysongs.org")
                .build();
    }

    @Test
    public void getAllSongs() throws Exception {
        mockMvc.perform(get("/playlist.htm"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"))
                .andExpect(content().string(allOf(
                        containsString("Title: testTitle"),
                        containsString("Text: test song text")
                )));
    }

    @Test
    public void playlistPageContentIsRenderedAsHtmlWithListOfSongs() throws Exception {
        HtmlPage page = webClient.getPage("http://mysongs.org/playlist.htm");
        List<String> songList = page.getElementsByTagName("li").stream()
                .map(DomNode::asText)
                .collect(Collectors.toList());
        assertThat(songList, hasItems("Title: testTitle \r\n Text: test song text"));
    }
}