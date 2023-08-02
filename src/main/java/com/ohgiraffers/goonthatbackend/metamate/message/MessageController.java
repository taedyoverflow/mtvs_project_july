package com.ohgiraffers.goonthatbackend.metamate.message;

import com.ohgiraffers.goonthatbackend.metamate.auth.LoginUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.service.MetaUserService;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/messages")
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final MessageService messageService;
    private final MetaUserRepository metaUserRepository;
    private final MetaUserService metaUserService;

    @GetMapping("/received")
    public String getReceivedMessage(
            @LoginUser SessionMetaUser loginUser,
            Model model) {
        MetaUser metaUser = metaUserRepository.findById(loginUser.getId()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        if (loginUser != null) {
            model.addAttribute("user", loginUser);
        }

        model.addAttribute("receivedMessage", messageService.receivedMessage(metaUser));

        return "messages/received";
    }

    @GetMapping("/sendForm")
    public String sendMessageForm(
            @LoginUser SessionMetaUser loginUser,
            @ModelAttribute("messageDto") MessageDto messageDto,
            Model model) {

        if (loginUser == null) {
            return "redirect:/messages/sendForm";
        }
        if (loginUser != null) {
            model.addAttribute("user", loginUser);
        }
        model.addAttribute("sessionUser", loginUser);

        return "messages/sendForm";
    }

    @PostMapping("/sendForm")
    public String sendMessage(
            @LoginUser SessionMetaUser loginUser,
            @Validated @ModelAttribute("messageDto") MessageDto messageDto,
            BindingResult bindingResult, Model model) {
        MetaUser user = metaUserRepository.findById(loginUser.getId()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        if (loginUser != null) {
            model.addAttribute("user", loginUser);
        }

        // 쪽지 보내기 실패 (valid error)
        if (bindingResult.hasErrors()) {
            return "messages/sendForm";
        }

        // 받는 사람 닉네임 없음 (global error)
        if (!metaUserService.hasNickname(messageDto.getReceiverNickname())) {
            bindingResult.reject("nullNickname", "존재하지 않는 닉네임입니다.");
            return "messages/sendForm";
        }

        messageDto.setSenderNickname(user.getNickname());
        messageService.write(messageDto);

        return "redirect:/messages/sent";
    }


    @GetMapping("/sent")
    public String getSentMessage(@LoginUser SessionMetaUser loginUser,
                                 Model model) {
        MetaUser user = metaUserRepository.findById(loginUser.getId()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        });

        if (loginUser != null) {
            model.addAttribute("user", loginUser);
        }


        model.addAttribute("sentMessage", messageService.sentMessage(user));

        return "messages/sent";
    }

}