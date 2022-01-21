package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.dao.BoardRepository;
import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.dto.board.BoardSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService{

    private final BoardRepository boardRepository;

    @Transactional
    public void save(BoardSaveForm boardSaveForm){

        Board board = Board.createBoard(
                boardSaveForm.getName(),
                boardSaveForm.getDetail()
        );

        boardRepository.save(board);

    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}

