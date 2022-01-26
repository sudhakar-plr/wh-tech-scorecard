package com.williamhiill.football.scoreboard.service;

import com.williamhiill.football.scoreboard.domain.Sport;
import com.williamhiill.football.scoreboard.exception.SportEventNotFoundException;

import java.util.UUID;

public interface ScoreboardService {

    Sport createScoreboard(String homeTeamName, String awayTeamName);

    void updateScore(Sport sport) throws SportEventNotFoundException;

    Sport getScoreboard(UUID id) throws SportEventNotFoundException;
}
