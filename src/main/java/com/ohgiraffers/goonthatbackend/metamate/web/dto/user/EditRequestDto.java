package com.ohgiraffers.goonthatbackend.metamate.web.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EditRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,15}$",
            message = "비밀번호는 대소문자, 숫자, 특수문자 포함 8~15자리 입니다.")
    private String password;

    private String confirmPassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z\\d-_]{2,15}$", message = "닉네임은 대소문자, 한글, 숫자 포함 2~15자리 입니다.")
    private String nickname;
}