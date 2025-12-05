package com.soundscape.artist.domain.entity;

import com.soundscape.common.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Artist extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String imageUrl;

    public Artist(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
