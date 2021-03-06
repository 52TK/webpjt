package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.config.Role;
import com.ydn.EaglesCM.dao.ArticleRepository;
import com.ydn.EaglesCM.dao.BoardRepository;
import com.ydn.EaglesCM.dao.MemberRepository;
import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.dto.adm.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdmService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public MemberStatDto getMemberStatDto(){

        return new MemberStatDto(
            memberRepository.count(),
            memberRepository.countTodayMember(),
            memberRepository.countByAuthorityLike(Role.ADMIN),
            memberRepository.countByAuthorityLike(Role.MEMBER)
        );

    }



    public BoardStatDto getBoardStatDto(){

        List<Board> findLatestBoards = boardRepository.find3LatestBoard();

        List<AdmBoardNameDto> latestBoardList = new ArrayList<>();
        List<AdmBoardCountDto> boardCountList = new ArrayList<>();

        for( Board findLatestBoard : findLatestBoards ){

            AdmBoardNameDto admBoardNameDto = new AdmBoardNameDto(findLatestBoard);
            latestBoardList.add(admBoardNameDto);

        }

        List<Board> findAll = boardRepository.findAll();

        for( Board board : findAll ){

            AdmBoardCountDto admBoardCountDto = new AdmBoardCountDto(board);
            boardCountList.add(admBoardCountDto);

        }

        return new BoardStatDto(
                boardRepository.count(),
                latestBoardList,
                boardCountList
        );

    }



    public ArticleStatDto getArticleStatDto(){

        return new ArticleStatDto(

                articleRepository.count()

        );

    }



}
