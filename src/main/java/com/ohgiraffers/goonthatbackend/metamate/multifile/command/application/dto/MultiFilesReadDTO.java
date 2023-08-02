package com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto;


import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.domain.aggregate.entity.MultiFiles;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MultiFilesReadDTO {

    private Long fileNo;

    private String originFileName;

    private String fileName;

    private String filePath;

    private MetaUser metaUser;

    private FreeBoardPost freeBoardPost;

    public static MultiFilesReadDTO fromEntity(MultiFiles multiFiles) {
        MultiFilesReadDTO fileDTO = new MultiFilesReadDTO();
        fileDTO.setFileNo(multiFiles.getFileNo());
        fileDTO.setOriginFileName(multiFiles.getOriginFileName());
        fileDTO.setFileName(multiFiles.getFileName());
        fileDTO.setFilePath(multiFiles.getFilePath());
        return fileDTO;
    }

    @Builder
    public MultiFilesReadDTO(Long fileNo, String originFileName, String fileName, String filePath, MetaUser metaUser, FreeBoardPost freeBoardPost) {
        this.fileNo = fileNo;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.metaUser = metaUser;
        this.freeBoardPost = freeBoardPost;
    }

}
