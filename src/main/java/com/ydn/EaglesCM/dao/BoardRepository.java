package com.ydn.EaglesCM.dao;

import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByName(String name);

    @Query(value = "SELECT * FROM `board` ORDER BY `reg_date` DESC LIMIT 3", nativeQuery = true)
    List<Board> find3LatestBoard();
}