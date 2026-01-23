package com.soundscape.playlist.infra.engine;

import com.soundscape.playlist.exception.EngineException;
import com.soundscape.playlist.infra.engine.dto.EngineRequest;
import com.soundscape.playlist.infra.engine.dto.EngineResponse;
import com.soundscape.playlist.service.command.PlaylistCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MusicEngineClient {

    private final RestClient restClient;

    @Value("${soundscape.music-agent}")
    private String musicAgentUrl;

    public EngineResponse recommend(PlaylistCommand command, List<String> favArtists) {
        EngineRequest engineRequest = new EngineRequest(
                new EngineRequest.Input(
                        new EngineRequest.UserContext(
                                command.getLocation().toLowerCase(),
                                command.getGoal().toLowerCase(),
                                command.getDecibel().toLowerCase(),
                                favArtists
                        ),
                        List.of()
                )
        );

        try {
            EngineResponse body = restClient.post()
                    .uri(musicAgentUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(engineRequest)
                    .retrieve()
                    .body(EngineResponse.class);

            return body;
        } catch (Exception e) {
            log.error("추천엔진 호출중 에러 발생");
            throw new EngineException("추천엔진 호출중 에러 발생");
        }
    }
}
