package com.ohgiraffers.goonthatbackend.metamate.multifile.command.domain.aggregate.entity;

import com.ohgiraffers.goonthatbackend.metamate.domain.AuditingFields;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "multifile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MultiFiles extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long fileNo;

    @Column
    private String originFileName;

    @Column
    private String fileName;

    @Column
    private String filePath;


    //프로필 사진용 아직 미사용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private MetaUser metaUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private FreeBoardPost freeBoardPost;

    public void setFreeBoardPost(FreeBoardPost freeBoardPost) {
        this.freeBoardPost = freeBoardPost;
    }

    @Builder
    public MultiFiles(String originFileName, String fileName, String filePath,
                      MetaUser metaUser, FreeBoardPost freeBoardPost) {
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.metaUser = metaUser;
        this.freeBoardPost = freeBoardPost;
    }

    public void fileUpdate(String originFileName, String fileName, String filePath){
        this.originFileName=originFileName;
        this.fileName=fileName;
        this.filePath=filePath;
    }


}
