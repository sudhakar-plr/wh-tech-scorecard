package com.williamhiill.football.scoreboard.resource;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamhiill.football.scoreboard.Application;
import com.williamhiill.football.scoreboard.domain.Sport;
import com.williamhiill.football.scoreboard.entity.Scoreboard;
import com.williamhiill.football.scoreboard.repository.ScoreBoardJPARepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class SoccerScoreboardResourceIntegrationTest {

    private static final String SPORT_GET_SCOREBOARD_URL = "/soccer/scoreboard/%s";
    private static final String SPORT_CREATE_SCOREBOARD_URL = "/soccer/scoreboard/";
    private static final String SPORT_UPDATE_SCOREBOARD_URL = "/soccer/scoreboard/";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ScoreBoardJPARepository scoreBoardJPARepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldSuccessfullyGetScoreBoard() throws Exception {
        UUID id = UUID.randomUUID();
        Scoreboard sc = new Scoreboard(id, "Arsenal", 3, "Chelsea", 1);
        String expectedScoreboard = objectMapper.writeValueAsString(new Sport(sc.getId(), sc.getHomeTeamName(), sc.getHomeTeamScore(), sc.getAwayTeamName(), sc.getAwayTeamScore()));
        BDDMockito.given(scoreBoardJPARepository.findById(id)).willReturn(Optional.of(sc));

        this.mockMvc
                .perform(get(String.format(SPORT_GET_SCOREBOARD_URL, id)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedScoreboard));

        verify(scoreBoardJPARepository).findById(id);
    }

    @Test
    public void shouldThrowBadRequestWhenGetScoreboardCalledWithInvalidUUID() throws Exception {
        this.mockMvc
                .perform(get(String.format(SPORT_GET_SCOREBOARD_URL, 10)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(scoreBoardJPARepository);
    }

    @Test
    public void shouldSuccessfullyCreateScoreBoard() throws Exception {
        SoccerTeamBoard sc = new SoccerTeamBoard("Arsenal", "Chelsea");
        BDDMockito.given(scoreBoardJPARepository.save(any(Scoreboard.class))).willReturn(new Scoreboard(UUID.randomUUID(), sc.getHomeTeamName(), 0, sc.getAwayTeamName(), 0));

        ResultActions resultActions = this.mockMvc
                .perform(post(SPORT_CREATE_SCOREBOARD_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sc)))
                .andExpect(status().isCreated());

        String response = resultActions.andReturn().getResponse().getContentAsString();
        Sport sport = objectMapper.readValue(response, Sport.class);
        assertThat(sport).isNotNull();
        assertThat(sport.getId()).isNotNull();
        assertThat(sport.getHomeTeamName()).isEqualTo(sc.getHomeTeamName());
        assertThat(sport.getHomeTeamScore()).isEqualTo(0);
        assertThat(sport.getAwayTeamName()).isEqualTo(sc.getAwayTeamName());
        assertThat(sport.getAwayTeamScore()).isEqualTo(0);
    }

    @Test
    public void shouldThrowBadRequestWhenCreateScoreBoardCalledWithMissingMandatoryElement() throws Exception {
        String invalidCreateScoreboardRequest = "{\n" +
                "    \"homeTeamName\": \"Arsenal\"\n" +
                "}";

        this.mockMvc
                .perform(post(SPORT_CREATE_SCOREBOARD_URL)
                        .contentType(APPLICATION_JSON)
                        .content(invalidCreateScoreboardRequest))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(scoreBoardJPARepository);
    }

    @Test
    public void shouldThrowBadRequestWhenCreateScoreBoardCalledWithEmptyRequest() throws Exception {
        this.mockMvc
                .perform(post(SPORT_CREATE_SCOREBOARD_URL)
                        .contentType(APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(scoreBoardJPARepository);
    }

    @Test
    public void shouldThrowMethodNotAllowedExceptionWhenCreateScoreBoardCalledWrongHttpMethod() throws Exception {
        SoccerTeamBoard sc = new SoccerTeamBoard("Arsenal", "Chelsea");
        this.mockMvc
                .perform(get(SPORT_CREATE_SCOREBOARD_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sc)))
                .andExpect(status().isMethodNotAllowed());

        verifyNoInteractions(scoreBoardJPARepository);
    }

    @Test
    public void shouldSuccessfullyUpdateScoreBoard() throws Exception {
        Sport soccer = new Sport(UUID.randomUUID(), "Arsenal", 3, "Chelsea", 0);
        Scoreboard scoreboard = new Scoreboard(soccer.getId(), soccer.getHomeTeamName(), soccer.getHomeTeamScore(), soccer.getAwayTeamName(), soccer.getAwayTeamScore());
        BDDMockito.given(scoreBoardJPARepository.save(any(Scoreboard.class))).willReturn(scoreboard);

        this.mockMvc
                .perform(put(SPORT_UPDATE_SCOREBOARD_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(soccer)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenUpdateScoreBoardCalledWithMissingMandatoryElement() throws Exception {
        String invalidRequest = "{\n" +
                "    \"homeTeamName\": \"Arsenal\",\n" +
                "    \"homeTeamScore\": 3,\n" +
                "    \"awayTeamName\": \"Chelsea\",\n" +
                "    \"awayTeamScore\": 1\n" +
                "}";

        this.mockMvc
                .perform(put(SPORT_UPDATE_SCOREBOARD_URL)
                        .contentType(APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(scoreBoardJPARepository);
    }

}