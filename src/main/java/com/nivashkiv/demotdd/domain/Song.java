package com.nivashkiv.demotdd.domain;

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
    private String author;
}
