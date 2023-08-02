package com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto;


import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.domain.aggregate.entity.MultiFiles;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MultiFilesWriteDTO {

    private String originFileName;

    private String fileName;

    private String filePath;

    private MetaUser metaUser;

    private FreeBoardPost freeBoardPost;

    @Builder
    public MultiFilesWriteDTO(String originFileName, String fileName, String filePath, MetaUser metaUser, FreeBoardPost freeBoardPost) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.metaUser = metaUser;
        this.freeBoardPost = freeBoardPost;
    }

    public MultiFiles toEntity() {
        return MultiFiles.builder()
                .originFileName(this.originFileName)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .build();
    }
}
