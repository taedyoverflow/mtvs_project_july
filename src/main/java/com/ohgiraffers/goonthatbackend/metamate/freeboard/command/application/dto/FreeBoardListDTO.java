package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto;

import com.ohgiraffers.goonthatbackend.metamate.common.CalcCreateDate;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FreeBoardListDTO {
    private Long boardNo;   // 글 번호
    private String createdAt; // 작성일시
    private String boardCategory;   // 글 카테고리
    private String boardTitle;  // 글 제목
    private String boardContent;    // 글 내용
    private Integer boardHits;      // 조회수
    private String boardWriter; //작성자

    public static FreeBoardListDTO fromEntity(FreeBoardPost boardPost) {
        CalcCreateDate cal = new CalcCreateDate();
        String boardWriter = boardPost.getMetaUser().getNickname();
        return new FreeBoardListDTO(
                boardPost.getBoardNo(),
                cal.calcCreateDate(boardPost.getCreatedAt()),
                boardPost.getBoardCategory(),
                boardPost.getBoardTitle(),
                boardPost.getBoardContent(),
                boardPost.getBoardHits(),
                boardWriter
        );
    }
}

