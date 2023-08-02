package com.ohgiraffers.goonthatbackend.metamate.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalcCreateDate {

    public String calcCreateDate(LocalDateTime createdDate) {
        LocalDate date = createdDate.toLocalDate();

        // 만약 게시글이 오늘 날짜라면 시간포맷만 return
        if (date.isEqual(LocalDate.now())) {
            return createdDate.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return createdDate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    }
}
