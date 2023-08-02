package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.service;

import com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.aggregate.entity.FreeBoardComment;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.service.AccessService;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import org.springframework.stereotype.Service;

@Service
public class AccessImplService implements AccessService {

    @Override
    public boolean postValidateUserAccess(FreeBoardPost boardPost, SessionMetaUser user) {

        return boardPost.getMetaUser().getId().equals(user.getId());
    }

    @Override
    public boolean commentValidateUserAccess(FreeBoardComment freeBoardComment, SessionMetaUser user) {

        return freeBoardComment.getMetauser().getId().equals(user.getId());
    }

}
