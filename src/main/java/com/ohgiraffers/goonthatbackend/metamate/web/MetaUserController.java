package com.ohgiraffers.goonthatbackend.metamate.web;

import com.ohgiraffers.goonthatbackend.metamate.auth.LoginErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.auth.LoginUser;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.EditRequestDto;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.JoinRequestDto;
import com.ohgiraffers.goonthatbackend.metamate.service.MetaUserService;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@Controller
public class MetaUserController {

    private final MetaUserService metaUserService;



    @GetMapping("/join")
    public String joinForm(@ModelAttribute("user") JoinRequestDto user) {
        return "auth/joinForm";
    }

    @PostMapping("/join")
    public String signup(@Valid @ModelAttribute("user") JoinRequestDto user, BindingResult bindingResult) {

        // 회원가입 실패 (valid error)
        if (bindingResult.hasErrors()) {
            return "auth/joinForm";
        }

        boolean isError = false;

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.reject("confirmPassword", "비밀번호가 일치하지 않습니다.");
            isError = true;
        }

        // 중복 이메일 (global error)
        if (metaUserService.hasEmail(user.getEmail())) {
            bindingResult.reject("duplicateEmail", "이미 존재하는 이메일 입니다.");
            isError = true;
        }

        // 중복 닉네임 (global error)
        if (metaUserService.hasNickname(user.getNickname())) {
            bindingResult.reject("duplicateNickname", "이미 존재하는 닉네임입니다.");
            isError = true;
        }

        if (isError) {
            return "auth/joinForm";
        }

        metaUserService.join(user);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String loginForm(
            @RequestParam(value = "error", required = false) Boolean isError,
            @RequestParam(value = "code", required = false) Integer code,
            HttpServletRequest request,
            Model model) {

        if (isError != null) {
            LoginErrorCode[] errors = LoginErrorCode.values();

            LoginErrorCode loginErrorCode = Arrays.stream(errors)
                    .filter(e -> e.getCode() == code)
                    .findFirst()
                    .orElseThrow(RuntimeException::new);

            model.addAttribute("isError", isError);
            model.addAttribute("errorMessage", loginErrorCode.getMessage());
        }

        /**
         * 이전 페이지로 되돌아가기 위한 Referer 헤더값을 세션의 prevPage attribute로 저장
         * uri.contains를 통해 중복 저장되지 않도록 처리
         */
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "auth/loginForm";
    }

    @GetMapping("/edit")
    public String editForm(
            @LoginUser SessionMetaUser user,
            @ModelAttribute("user") EditRequestDto editRequestDto,
            Model model
    ) {
        if (user == null) {
//            throw new CustomException(ErrorCode.USER_NOT_FOUND);
            return "redirect:/auth/login";
        }
        model.addAttribute("sessionUser", user);
        return "auth/editForm";
    }

    @PostMapping("/edit")
    public String edit(
            @LoginUser SessionMetaUser user,
            @Validated @ModelAttribute("user") EditRequestDto editRequestDto,
            BindingResult bindingResult,
            Model model
    ) {

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        model.addAttribute("sessionUser", user);

        // 회원정보 수정 실패 (validation error)
        if (bindingResult.hasErrors()) {
            return "auth/editForm";
        }

        if (!editRequestDto.getPassword().equals(editRequestDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "passwordMismatch", "비밀번호가 일치하지 않습니다.");
            bindingResult.reject("globalError", "비밀번호가 일치하지 않습니다."); // 전역 에러 추가
            return "auth/editForm";
        }

        // 중복 닉네임 (global error)
        if (metaUserService.hasNickname(editRequestDto.getNickname())) {
            bindingResult.reject("duplicateNickname", "이미 존재하는 닉네임입니다.");
            return "auth/editForm";
        }

        metaUserService.edit(editRequestDto, user);

        return "redirect:/";
    }
}