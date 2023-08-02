package com.ohgiraffers.goonthatbackend.metamate.comment.command.application.service;

import com.ohgiraffers.goonthatbackend.metamate.comment.command.application.dto.FreeBoardCommentReadDTO;
import com.ohgiraffers.goonthatbackend.metamate.comment.command.application.dto.FreeBoardCommentWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.aggregate.entity.FreeBoardComment;
import com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.repository.FreeBoardCommentRepository;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.service.AccessService;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeBoardCommentService {

    private final FreeBoardCommentRepository freeBoardCommentRepository;
    private final MetaUserRepository metaUserRepository;
    private final AccessService accessService;

    @Transactional
    public List<FreeBoardCommentReadDTO> addComment(FreeBoardPost freeBoardPost, FreeBoardCommentWriteDTO freeBoardCommentWriteDTO, SessionMetaUser user) {

        MetaUser metaUser = metaUserRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(freeBoardCommentWriteDTO.getCommentContent().isEmpty()) {
            throw new CustomException(ErrorCode.USER_BAD_REQUEST);
        }

        FreeBoardComment freeBoardComment = freeBoardCommentWriteDTO.toEntity(metaUser, freeBoardPost);

        freeBoardCommentRepository.save(freeBoardComment);

        List<FreeBoardComment> commentList = freeBoardCommentRepository.findByFreeBoardPost(freeBoardPost);

        List<FreeBoardCommentReadDTO> commentReadList = new ArrayList<>();


        for (FreeBoardComment comment : commentList) {
            FreeBoardCommentReadDTO commentRead = new FreeBoardCommentReadDTO().fromEntity(comment);
            commentReadList.add(commentRead);
        }

        return commentReadList;
    }

    @Transactional
    public void removeComment(Long commentNo, SessionMetaUser user) {
        FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!accessService.commentValidateUserAccess(freeBoardComment, user)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }

        freeBoardComment.delete();
    }

    @Transactional
    public void modifyComment(Long commentNo, SessionMetaUser user, FreeBoardCommentWriteDTO freeBoardCommentWriteDTO) {
        FreeBoardComment freeBoardComment = freeBoardCommentRepository.findById(commentNo)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!accessService.commentValidateUserAccess(freeBoardComment, user)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }

        freeBoardComment.update(
                freeBoardCommentWriteDTO.getCommentContent()
        );
        freeBoardCommentRepository.save(freeBoardComment);
    }
}