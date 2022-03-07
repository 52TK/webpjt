package com.ydn.EaglesCM.dto.member;

import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.article.ArticleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MyPageDTO {

    private String loginId;
    private String nickname;

    private List<ArticleDTO> articles;

    public MyPageDTO(Member member, List<ArticleDTO> articles){

        this.loginId = member.getLoginId();
        this.nickname = member.getNickname();

        this.articles = articles;

    }

}
