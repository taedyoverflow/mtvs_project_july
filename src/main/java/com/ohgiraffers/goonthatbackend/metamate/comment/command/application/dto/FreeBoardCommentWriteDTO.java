package com.ohgiraffers.goonthatbackend.metamate.comment.command.application.dto;

import com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.aggregate.entity.FreeBoardComment;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FreeBoardCommentWriteDTO {

    @NotBlank
    private String commentContent;

    public FreeBoardComment toEntity(MetaUser user, FreeBoardPost freeBoardPost) {
        return FreeBoardComment.builder()
                .commentContent(this.commentContent)
                .metauser(user)
                .freeBoardPost(freeBoardPost)
                .build();

    }
}