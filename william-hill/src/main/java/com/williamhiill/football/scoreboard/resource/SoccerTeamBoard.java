package com.williamhiill.football.scoreboard.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoccerTeamBoard {

    @NotNull
    private String homeTeamName;
    @NotNull
    private String awayTeamName;
}
