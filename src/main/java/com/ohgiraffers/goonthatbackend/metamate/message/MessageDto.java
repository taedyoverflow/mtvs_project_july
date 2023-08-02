package com.ohgiraffers.goonthatbackend.metamate.message;

import com.ohgiraffers.goonthatbackend.metamate.common.CalcCreateDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    private String senderNickname;
    @NotBlank(message = "받는 사람 닉네임을 입력해주세요.")
    private String receiverNickname;
    private String createdAt;

    public static MessageDto toDto(Message message) {
        CalcCreateDate calcCreateDate = new CalcCreateDate();
        return new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getContent(),
                message.getSender().getNickname(),
                message.getReceiver().getNickname(),
                calcCreateDate.calcCreateDate(message.getCreatedAt())
        );
    }
}
