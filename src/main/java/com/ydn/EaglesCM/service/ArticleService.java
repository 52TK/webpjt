package com.ydn.EaglesCM.service;

import com.ydn.EaglesCM.dao.ArticleRepository;
import com.ydn.EaglesCM.domain.Article;
import com.ydn.EaglesCM.domain.Board;
import com.ydn.EaglesCM.domain.Member;
import com.ydn.EaglesCM.dto.article.ArticleDTO;
import com.ydn.EaglesCM.dto.article.ArticleModifyForm;
import com.ydn.EaglesCM.dto.article.ArticleSaveForm;
import com.ydn.EaglesCM.dto.member.MemberModifyForm;
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
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void save(ArticleSaveForm articleSaveForm, Member member, Board board){

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );

        article.setMember(member);
        article.setBoard(board);

        articleRepository.save(article);

    }

    public Optional<Article> findById(Long id){
        return articleRepository.findById(id);
    }

    public Article getById(Long id) throws NoSuchElementException{

        Optional<Article> articleOptional = findById(id);

        articleOptional.orElseThrow(
                () -> new NoSuchElementException("해당 게시물은 존재하지 않습니다.")
        );

        return articleOptional.get();
    }

    public ArticleDTO getArticle(Long id){
        Article findArticle = getById(id);

        ArticleDTO articleDTO = new ArticleDTO(findArticle);

        return articleDTO;

    }

    @Transactional
    public void modifyArticle(ArticleModifyForm articleModifyForm, Long id){

        Article findArticle = getById(id);

        findArticle.modifyArticle(
                articleModifyForm.getTitle(),
                articleModifyForm.getBody()
        );

    }

    public List<ArticleDTO> getArticleList(){

        List<Article> articleList = articleRepository.findAll();

        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for (Article article: articleList) {

            ArticleDTO articleDTO = new ArticleDTO(article);
            articleDTOList.add(articleDTO);

        }

        return articleDTOList;

    }

    @Transactional
    public void delete(Long id){
        Article findArticle = getById(id);

        articleRepository.delete(findArticle);


    }






}
