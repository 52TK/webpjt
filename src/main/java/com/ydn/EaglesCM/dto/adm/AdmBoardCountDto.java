package com.ydn.EaglesCM.dto.adm;

import com.ydn.EaglesCM.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmBoardCountDto {

    private String name;
    private int articleCount;

    public AdmBoardCountDto(Board board){

        this.name = board.getName();
        this.articleCount = board.getArticles().size();

    }

}
