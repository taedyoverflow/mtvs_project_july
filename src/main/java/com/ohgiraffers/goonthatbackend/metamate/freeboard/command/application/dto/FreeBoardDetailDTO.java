package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto;

import com.ohgiraffers.goonthatbackend.metamate.comment.command.application.dto.FreeBoardCommentReadDTO;
import com.ohgiraffers.goonthatbackend.metamate.common.CalcCreateDate;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.like.command.domain.aggregate.entity.Like;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto.MultiFilesReadDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FreeBoardDetailDTO {

    private Long boardNo; //글 번호
    private String createdAt; // 생성일시
    private String boardCategory;//카테고리
    private String boardTitle;  //제목
    private String boardContent; //내용
    private int boardHits; //조회수
    private boolean boardIsDeleted;// 삭제여부
    private List<FreeBoardCommentReadDTO> commentList;
    private String boardWriter;
    private MetaUser metaUser;
    private List<MultiFilesReadDTO> multiFilesList;
    private Long likeNo; // 좋아요 번호
    private List<Like> likeList; // 좋아요 목록
    private boolean isLiked; // 해당 사용자가 좋아요를 눌렀는지 여부
    private int likeCount; // 전체 좋아요 수


    public FreeBoardDetailDTO fromEntity(FreeBoardPost boardPost, List<FreeBoardCommentReadDTO> commentList, List<MultiFilesReadDTO> multiFilesList, boolean isLiked, int likeCount, Long likeNo) {
        CalcCreateDate cal = new CalcCreateDate();
        String boardWriter = boardPost.getMetaUser().getNickname();
        return new FreeBoardDetailDTO(
                boardPost.getBoardNo()
                , cal.calcCreateDate(boardPost.getCreatedAt())
                , boardPost.getBoardCategory()
                , boardPost.getBoardTitle()
                , boardPost.getBoardContent()
                , boardPost.getBoardHits()
                , boardPost.isBoardIsDeleted()
                , commentList
                , boardWriter
                , boardPost.getMetaUser()
                , multiFilesList
                , likeNo
                , likeList
                , isLiked
                , likeCount
        );
    }


}