package com.ydn.EaglesCM.dao;

import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>{

    Optional<Board> findByName(String name);
}