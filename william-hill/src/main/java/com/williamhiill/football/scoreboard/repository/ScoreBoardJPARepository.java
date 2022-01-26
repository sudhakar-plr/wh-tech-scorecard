package com.williamhiill.football.scoreboard.repository;

import com.williamhiill.football.scoreboard.entity.Scoreboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScoreBoardJPARepository extends JpaRepository<Scoreboard, UUID> {
}
