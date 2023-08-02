package com.ohgiraffers.goonthatbackend.metamate.message;

import com.ohgiraffers.goonthatbackend.metamate.auth.LoginUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class MessageApiController {

    private final MessageService messageService;

    @ResponseBody
    @DeleteMapping("/messages/received/{id}")
    public ResponseEntity<Long> deleteReceivedMessage(@LoginUser SessionMetaUser user,
                                                      @PathVariable("id") Long id) {
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return ResponseEntity.ok(messageService.deleteMessageByReceiver(id, user));

    }


    @ResponseBody
    @DeleteMapping("/messages/sent/{id}")
    public ResponseEntity<Long> deleteSentMessage(@LoginUser SessionMetaUser user,
                                  @PathVariable("id") Long id) {

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return ResponseEntity.ok(messageService.deleteMessageBySender(id, user));
    }
}
