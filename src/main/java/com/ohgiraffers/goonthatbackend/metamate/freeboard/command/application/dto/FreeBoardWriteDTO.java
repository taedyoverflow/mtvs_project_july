package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto;

import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto.MultiFilesWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.domain.aggregate.entity.MultiFiles;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FreeBoardWriteDTO {

    private String boardCategory;   //카테고리

    @NotBlank(message = "제목을 입력하세요")
    @Size(max = 20, message = "제목은 20자 이하만 가능합니다.")
    private String boardTitle;  //제목

    @NotBlank(message = "내용을 입력하세요.")
    private String boardContent; //내용

    private List<MultiFiles> multiFiles;


    public FreeBoardPost toEntity(MetaUser metaUser) {
        return FreeBoardPost.builder()
                .boardCategory(this.boardCategory)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .metaUser(metaUser)
                .boardHits(0)
                .boardIsDeleted(false)
                .multiFiles(multiFiles)
                .build();

    }
}