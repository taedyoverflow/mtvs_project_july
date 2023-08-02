package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.service;

import com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.aggregate.entity.FreeBoardComment;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardDetailDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;

public interface AccessService {

    public boolean postValidateUserAccess(FreeBoardPost boardPost, SessionMetaUser user);

    boolean commentValidateUserAccess(FreeBoardComment freeBoardComment, SessionMetaUser user);
}
