package com.ohgiraffers.goonthatbackend.metamate.like.command.application.dto;

import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.like.command.domain.aggregate.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LikeBoardDTO {

    private Long likeNo;
    private FreeBoardPost freeBoardPost;
    private String likeMan;
    private int likeCount;

    public static LikeBoardDTO fromEntity(Like like) {

        return new LikeBoardDTO(
                like.getLikeNo(),
                like.getFreeBoardPost(),
                like.getMetaUser().getNickname(),
                1
        );
    }
}
