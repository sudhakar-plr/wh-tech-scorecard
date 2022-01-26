package com.williamhiill.football.scoreboard.service;

import com.williamhiill.football.scoreboard.domain.Soccer;
import com.williamhiill.football.scoreboard.domain.Sport;
import com.williamhiill.football.scoreboard.entity.Scoreboard;
import com.williamhiill.football.scoreboard.exception.SportEventNotFoundException;
import com.williamhiill.football.scoreboard.repository.ScoreBoardJPARepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SoccerScoreboardServiceImplTest {

    private static final Sport soccer = new Soccer(UUID.randomUUID(), "Arsenal", 3, "Chelsea", 1);

    @Mock
    private ScoreBoardJPARepository scoreBoardJPARepository;

    @Captor
    private ArgumentCaptor<Scoreboard> scoreboardArgumentCaptor;

    private ScoreboardService scoreboardService;

    @Before
    public void setup() {
        scoreboardService = new SoccerScoreboardServiceImpl(scoreBoardJPARepository);
    }

    @Test
    public void shouldSuccessfullyCreateSoccerScoreboardWhenGivenInputValid() {
        String homeTeamName = "Arsenal";
        String awayTeamName = "Chelsea";
        when(scoreBoardJPARepository.save(any(Scoreboard.class))).thenReturn(null);

        Sport scoreboard = scoreboardService.createScoreboard(homeTeamName, awayTeamName);

        assertThat(scoreboard).isNotNull();
        assertThat(scoreboard.getHomeTeamName()).isEqualTo(homeTeamName);
        assertThat(scoreboard.getAwayTeamName()).isEqualTo(awayTeamName);
        assertThat(scoreboard.getHomeTeamScore()).isEqualTo(0);
        assertThat(scoreboard.getAwayTeamScore()).isEqualTo(0);
        assertThat(scoreboard.getId()).isNotNull();
        verify(scoreBoardJPARepository, times(1)).save(any(Scoreboard.class));
    }

    @Test
    public void shouldSuccessfullyUpdateSoccerScoreboardWhenGivenInputValid() throws Exception {
        Scoreboard scoreboard = new Scoreboard(soccer.getId(), soccer.getHomeTeamName(), soccer.getHomeTeamScore(), soccer.getAwayTeamName(), soccer.getAwayTeamScore());
        when(scoreBoardJPARepository.save(any(Scoreboard.class))).thenReturn(scoreboard);

        scoreboardService.updateScore(soccer);

        verify(scoreBoardJPARepository, times(1)).save(scoreboardArgumentCaptor.capture());
    }

    @Test
    public void shouldSuccessfullyGetSoccerScoreboardWhenGivenInputValid() throws Exception {
        UUID id = UUID.randomUUID();
        Scoreboard scoreboard = new Scoreboard(soccer.getId(), soccer.getHomeTeamName(), soccer.getHomeTeamScore(), soccer.getAwayTeamName(), soccer.getAwayTeamScore());
        when(scoreBoardJPARepository.findById(id)).thenReturn(Optional.of(scoreboard));

        Sport soccer = scoreboardService.getScoreboard(id);

        assertThat(soccer).isNotNull();
        assertThat(soccer.getHomeTeamName()).isEqualTo(scoreboard.getHomeTeamName());
        assertThat(soccer.getAwayTeamName()).isEqualTo(scoreboard.getAwayTeamName());
        assertThat(soccer.getHomeTeamScore()).isEqualTo(scoreboard.getHomeTeamScore());
        assertThat(soccer.getAwayTeamScore()).isEqualTo(scoreboard.getAwayTeamScore());
        assertThat(soccer.getId()).isEqualTo(scoreboard.getId());
        verify(scoreBoardJPARepository, times(1)).findById(id);
    }

    @Test(expected = SportEventNotFoundException.class)
    public void shouldThrowExceptionWhenErrorInSavingSoccerDetailsInDatabase() throws Exception {
        doThrow(IllegalStateException.class).when(scoreBoardJPARepository).save(any(Scoreboard.class));

        scoreboardService.updateScore(soccer);

        verify(scoreBoardJPARepository, times(1)).save(scoreboardArgumentCaptor.capture());
    }

    @Test(expected = SportEventNotFoundException.class)
    public void shouldThrowExceptionWhenSportNotFoundInDatabase() throws Exception {
        UUID id = UUID.randomUUID();
        when(scoreBoardJPARepository.findById(id)).thenReturn(Optional.empty());

        scoreboardService.getScoreboard(id);

        verify(scoreBoardJPARepository, times(1)).findById(id);
    }

}