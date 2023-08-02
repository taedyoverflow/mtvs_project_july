package com.ohgiraffers.goonthatbackend.metamate.web.dto.user;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JoinRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "올바르지 않은 이메일 형식입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,15}$",
            message = "비밀번호는 대소문자, 숫자, 특수문자 포함 8~15자리 입니다.")
    private String password;

    private String confirmPassword;

    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z\\d-_]{2,15}$", message = "이름은 대소문자, 한글, 숫자 포함 2~15자리 입니다.")
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z\\d-_]{2,15}$", message = "닉네임은 대소문자, 한글, 숫자 포함 2~15자리 입니다.")
    private String nickname;

    @NotBlank(message = "기수를 입력해주세요.")
    private String number;

    @NotBlank(message = "전공을 선택해주세요.")
    private String major;

    public MetaUser toEntity() {
        return MetaUser.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .nickname(this.nickname)
                .number(this.number)
                .major(this.major)
                .role(Role.ASSOCIATE)
                .build();
    } // 가입에 성공한 모든 유저는 "ASSOCIATE" 권한 부여, 개발단계에서는 "USER" 부여

    public void encodePassword(String password) {
        this.password = password;
    }
}