package com.ohgiraffers.goonthatbackend.metamate.comment.command.application.controller;

import com.ohgiraffers.goonthatbackend.metamate.auth.LoginUser;
import com.ohgiraffers.goonthatbackend.metamate.comment.command.application.dto.FreeBoardCommentReadDTO;
import com.ohgiraffers.goonthatbackend.metamate.comment.command.application.dto.FreeBoardCommentWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.comment.command.application.service.FreeBoardCommentService;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.repository.FreeBoardPostRepository;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FreeBoardCommentController {

    private final FreeBoardCommentService freeBoardCommentService;
    private final FreeBoardPostRepository freeBoardPostRepository;

    /* 댓글 조회 및 추가 */
    @ResponseBody
    @PostMapping("/comment/{refBoardNo}")
    public ResponseEntity<List<FreeBoardCommentReadDTO>> addComment(@PathVariable("refBoardNo") Long refBoardNo,
                                                    @LoginUser SessionMetaUser user, Model model,
                                                    FreeBoardCommentWriteDTO freeBoardCommentWriteDTO) {
        if (user != null) {
            model.addAttribute("user", user);
        }

        if (freeBoardCommentWriteDTO.getCommentContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        FreeBoardPost freeBoardPost = freeBoardPostRepository.findById(refBoardNo)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<FreeBoardCommentReadDTO> commentList = freeBoardCommentService.addComment(freeBoardPost, freeBoardCommentWriteDTO, user);

        return ResponseEntity.ok(commentList);
    }

    /* 댓글 수정 */
    @PutMapping("/edit/{commentNo}")
    public ResponseEntity<String> modifyComment(@PathVariable Long commentNo, @LoginUser SessionMetaUser user, FreeBoardCommentWriteDTO freeBoardCommentWriteDTO) {

        try {
             freeBoardCommentService.modifyComment(commentNo, user, freeBoardCommentWriteDTO);
            return ResponseEntity.ok("댓글이 수정되었습니다.");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 수정에 실패하였습니다.");
        }
    }

    /* 댓글 삭제 */
    @DeleteMapping(value = "/comment/{commentNo}")
    public ResponseEntity<String> removeComment(@PathVariable Long commentNo, @LoginUser SessionMetaUser user) {
        try {
            freeBoardCommentService.removeComment(commentNo, user);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제에 실패하였습니다.");
        }
    }
}