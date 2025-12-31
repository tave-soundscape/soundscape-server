package com.soundscape.playlist.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

//파이썬 랭서브 엔진(/music/invoke)의 응답을 받기 위한 DTO
@Getter
@Setter
@ToString
public class EngineResponse {

    // output: 랭서브 응답의 최상위 키
    private OutputData output;

    @Getter
    @Setter
    @ToString
    public static class OutputData {
        // server.py 지정한 output_type의 필드명
        private List<TrackDto> final_tracks;
    }

    @Getter
    @Setter
    @ToString
    public static class TrackDto {
        private String tid;       // Track ID
        private String tn;        // Track Name
        private String tu;        // Track URI
        private String turl;
        private int ms;           // Duration in ms
        private String img;       // Album Image URL
        private List<ArtistDto> at;

        private String ai;        // Album ID
        private String an;        // Album Name
        private String au;        // Album URI
    }

    @Getter
    @Setter
    @ToString
    public static class ArtistDto {
        private String atid;      // Artist ID
        private String atn;       // Artist Name
    }
}