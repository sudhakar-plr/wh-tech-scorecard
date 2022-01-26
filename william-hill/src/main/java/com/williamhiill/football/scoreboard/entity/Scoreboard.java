package com.williamhiill.football.scoreboard.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "scoreboard")
@Data
@NoArgsConstructor
public class Scoreboard {
    @Id
    @Column(name = "id")
    private UUID id;
    @NotNull(message = "Home team name can 't be empty")
    @Column(name = "home_team_name")
    private String homeTeamName;
    @NotNull(message = "Away team name can 't be empty")
    @Column(name = "away_team_name")
    private String awayTeamName;
    @NotNull(message = "Home team score can 't be empty")
    @Column(name = "home_team_score")
    private int homeTeamScore;
    @NotNull(message = "Away team score can 't be empty")
    @Column(name = "away_team_score")
    private int awayTeamScore;
    @Column(name = "match_date")
    private LocalDateTime matchDateTime;

    public Scoreboard(UUID id, String homeTeamName, int homeTeamScore, String awayTeamName, int awayTeamScore, LocalDateTime matchDateTime) {
        this.id = id;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.matchDateTime = matchDateTime;
    }

    public Scoreboard(UUID id, String homeTeamName, int homeTeamScore, String awayTeamName, int awayTeamScore) {
        this.id = id;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }
}
