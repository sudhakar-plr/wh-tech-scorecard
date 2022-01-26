package com.williamhiill.football.scoreboard.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sport {
    @JsonProperty
    @NotNull
    private final UUID id;
    @JsonProperty
    @NotNull
    private final String homeTeamName;
    @JsonProperty
    @NotNull
    private final int homeTeamScore;
    @JsonProperty
    @NotNull
    private final String awayTeamName;
    @JsonProperty
    @NotNull
    private final int awayTeamScore;

    @JsonCreator
    public Sport(@JsonProperty("id") UUID id, @JsonProperty("homeTeamName") String homeTeamName, @JsonProperty("homeTeamScore") int homeTeamScore, @JsonProperty("awayTeamName") String awayTeamName, @JsonProperty("awayTeamScore") int awayTeamScore) {
        this.id = id;
        this.homeTeamName = homeTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamName = awayTeamName;
        this.awayTeamScore = awayTeamScore;
    }
}
