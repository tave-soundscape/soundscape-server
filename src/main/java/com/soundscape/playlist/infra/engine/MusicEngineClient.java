package com.soundscape.playlist.infra.engine;

import com.soundscape.playlist.infra.engine.dto.EngineRequest;
import com.soundscape.playlist.infra.engine.dto.EngineResponse;
import com.soundscape.playlist.service.command.PlaylistCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MusicEngineClient {

    private final RestClient restClient;

    @Value("${soundscape.music-agent}")
    private String musicAgentUrl;

    public EngineResponse recommend(PlaylistCommand command) {
        EngineRequest engineRequest = new EngineRequest(
                new EngineRequest.Input(
                        new EngineRequest.UserContext(
                                command.getLocation().toLowerCase(),
                                command.getGoal().toLowerCase(),
                                command.getDecibel().toLowerCase()
                        ),
                        List.of()
                )
        );

        return restClient.post()
                .uri(musicAgentUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(engineRequest)
                .retrieve()
                .body(EngineResponse.class);
    }
}
