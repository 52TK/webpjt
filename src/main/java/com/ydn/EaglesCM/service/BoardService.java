package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.dao.BoardRepository;
import com.ydn.EaglesCM.domain.Article;
import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.article.ArticleListDTO;
import com.ydn.EaglesCM.dto.board.BoardDTO;
import com.ydn.EaglesCM.dto.board.BoardModifyForm;
import com.ydn.EaglesCM.dto.board.BoardSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService{

    private final BoardRepository boardRepository;

    @Transactional
    public void save(BoardSaveForm boardSaveForm, Member member){

        Board board = Board.createBoard(
                boardSaveForm.getName(),
                boardSaveForm.getDetail()
                member
        );

        boardRepository.save(board);

    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Board> findById(Long id){
        return boardRepository.findById(id);
    }

    public Board getBoard(Long id){

        Optional<Board> boardOptional = boardRepository.findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        return boardOptional.get();

    }

    public BoardDTO getBoardDetail(Long id){

        Optional<Board> boardOptional = findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        Board findBoard = boardOptional.get();

        List<ArticleListDTO> articleList = new ArrayList<>();
        List<Article> articles = findBoard.getArticles();

        for( Article article : articles ){
            ArticleListDTO articleListDTO = new ArticleListDTO(article);
            articleList.add(articleListDTO);
        }

        return new BoardDTO(findBoard, articleList);

    }

    @Transactional
    public Long modify(Long id, BoardModifyForm boardModifyForm) throws NoSuchElementException{

        Optional<Board> boardOptional = boardRepository.findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시판은 존재하지 않습니다.")
        );

        Board board = boardOptional.get();

        board.modifyBoard(
                boardModifyForm.getName(),
                boardModifyForm.getDetail()

        );

        return board.getId();


    }


    // 게시판 삭제
    @Transactional
    public void delete(Long id){

        Optional<Board> boardOptional = findById(id);

        boardOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시물은 존재하지 않습니다.")
        );

        Board findBoard = boardOptional.get();

        boardRepository.delete(findBoard);

    }


}

