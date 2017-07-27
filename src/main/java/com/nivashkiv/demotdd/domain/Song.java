package com.nivashkiv.demotdd.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nivashkiv.demotdd.json.SongAuthorJsonSerializer;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String text;

    @NonNull
    @JsonSerialize(using = SongAuthorJsonSerializer.class)
    private String author;
}
