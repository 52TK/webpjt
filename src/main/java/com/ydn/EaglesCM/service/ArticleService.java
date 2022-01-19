package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.dao.ArticleRepository;
import com.ydn.EaglesCM.domain.Article;
import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.article.ArticleSaveForm;
import com.ydn.EaglesCM.dto.member.MemberModifyForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public void save(ArticleSaveForm articleSaveForm, Member member){

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );

        article.setMember(member);

        articleRepository.save(article);

    }


}
