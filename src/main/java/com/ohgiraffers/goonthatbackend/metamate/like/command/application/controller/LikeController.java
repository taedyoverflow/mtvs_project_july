package com.ohgiraffers.goonthatbackend.metamate.like.command.application.controller;

import com.ohgiraffers.goonthatbackend.metamate.auth.LoginUser;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.repository.FreeBoardPostRepository;
import com.ohgiraffers.goonthatbackend.metamate.like.command.infra.service.LikeService;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LikeController {

    private final FreeBoardPostRepository freeBoardPostRepository;
    private final LikeService likeService;

    @ResponseBody
    @PostMapping("/like/{boardNo}")
    public void addLike(@PathVariable("boardNo") Long boardNo,
                                      @LoginUser SessionMetaUser user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        FreeBoardPost freeBoardPost = freeBoardPostRepository.findById(boardNo)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));


        // 좋아요 상태
        if (likeService.isLikedByUser(freeBoardPost, user.getId())) {
            likeService.deleteLike(freeBoardPost, user);
        } else {
             likeService.addLike(freeBoardPost, user);
        }
    }

}
