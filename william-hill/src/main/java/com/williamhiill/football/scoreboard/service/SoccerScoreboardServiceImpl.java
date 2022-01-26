package com.williamhiill.football.scoreboard.service;

import com.williamhiill.football.scoreboard.domain.Soccer;
import com.williamhiill.football.scoreboard.domain.Sport;
import com.williamhiill.football.scoreboard.entity.Scoreboard;
import com.williamhiill.football.scoreboard.exception.SportEventNotFoundException;
import com.williamhiill.football.scoreboard.repository.ScoreBoardJPARepository;
import cyclops.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SoccerScoreboardServiceImpl implements ScoreboardService {

    private final ScoreBoardJPARepository scoreBoardJPARepository;

    @Autowired
    public SoccerScoreboardServiceImpl(ScoreBoardJPARepository scoreBoardJPARepository) {
        this.scoreBoardJPARepository = scoreBoardJPARepository;
    }

    @Override
    public Soccer createScoreboard(String homeTeamName, String awayTeamName) {
        Soccer soccer = new Soccer(UUID.randomUUID(), homeTeamName, 0, awayTeamName, 0);
        scoreBoardJPARepository.save(mapToScoreboard(soccer));
        return soccer;
    }

    @Override
    public void updateScore(Sport sport) throws SportEventNotFoundException {
        Try.withCatch(() -> scoreBoardJPARepository.save(mapToScoreboard(sport)), Exception.class).toOptional()
                .orElseThrow(SportEventNotFoundException::new);
    }

    @Override
    public Soccer getScoreboard(UUID id) throws SportEventNotFoundException {
        return scoreBoardJPARepository.findById(id).map(s -> new Soccer(s.getId(), s.getHomeTeamName(), s.getHomeTeamScore(), s.getAwayTeamName(), s.getAwayTeamScore()))
                .orElseThrow(SportEventNotFoundException::new);
    }

    private Scoreboard mapToScoreboard(Soccer soccer) {
        return new Scoreboard(soccer.getId(), soccer.getHomeTeamName(), soccer.getHomeTeamScore(), soccer.getAwayTeamName(), soccer.getAwayTeamScore(), LocalDateTime.now());
    }

    private Scoreboard mapToScoreboard(Sport sport) {
        return new Scoreboard(sport.getId(), sport.getHomeTeamName(), sport.getHomeTeamScore(), sport.getAwayTeamName(), sport.getAwayTeamScore(), LocalDateTime.now());
    }
}
