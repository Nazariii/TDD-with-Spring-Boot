package com.nivashkiv.demotdd.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nivashkiv.demotdd.domain.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
@ExtendWith(SpringExtension.class)
class SongAuthorJsonSerializerTest {

    @Autowired
    private JacksonTester<Song> jacksonTester;

    @Test
    void testSerialization() throws IOException {
        Song song = new Song("testTitle", "test text", "author name");
        song.setId(1L);
        jacksonTester.write(song)
                .assertThat()
                .hasJsonPathStringValue("@.author");

        jacksonTester.write(song)
                .assertThat()
                .extractingJsonPathStringValue("@.author")
                .as("Check author's name first letters is capital")
                .isEqualTo("Author name");
    }
}