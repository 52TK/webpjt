package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.dao.BoardRepository;
import com.ydn.EaglesCM.domain.Article;
import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.dto.article.ArticleListDTO;
import com.ydn.EaglesCM.dto.board.BoardDTO;
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

    public Optional<Board> findById(Long id){
        return boardRepository.findById(id);
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




}

