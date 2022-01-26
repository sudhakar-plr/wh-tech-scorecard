package com.williamhiill.football.scoreboard.domain;

import java.util.UUID;


public class Soccer extends Sport {
    private final String name = "Soccer";

    public Soccer(UUID id, String homeTeamName, int homeTeamScore, String awayTeamName, int awayTeamScore) {
        super(id, homeTeamName, homeTeamScore, awayTeamName, awayTeamScore);
    }
}
