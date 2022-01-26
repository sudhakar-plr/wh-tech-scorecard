package com.williamhiill.football.scoreboard.resource;

import com.williamhiill.football.scoreboard.domain.Sport;
import com.williamhiill.football.scoreboard.service.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/soccer/scoreboard")
@Validated
public class SoccerScoreboardResource {

    @Autowired
    private ScoreboardService scoreboardService;

    @PostMapping(path = "/", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = CREATED)
    public Sport createSoccerScoreboard(@RequestBody @NotNull @Valid SoccerTeamBoard soccerTeamBoard) {
        return scoreboardService.createScoreboard(soccerTeamBoard.getHomeTeamName(), soccerTeamBoard.getAwayTeamName());
    }

    @PutMapping(path = "/", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = NO_CONTENT)
    public void updateSoccerScoreboard(@RequestBody @NotNull @Valid Sport soccer) throws Exception {
        scoreboardService.updateScore(soccer);
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = OK)
    public Sport getSoccerScoreboard(@PathVariable UUID id) throws Exception {
        return scoreboardService.getScoreboard(id);
    }
}
