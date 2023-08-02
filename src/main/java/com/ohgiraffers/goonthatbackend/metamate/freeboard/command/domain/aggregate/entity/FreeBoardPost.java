package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity;

import com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.aggregate.entity.FreeBoardComment;
import com.ohgiraffers.goonthatbackend.metamate.domain.AuditingFields;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.domain.aggregate.entity.MultiFiles;
import lombok.*;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "freeboard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FreeBoardPost extends AuditingFields {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long boardNo;

    private String boardCategory;

    private String boardTitle;

    @OneToMany(mappedBy = "freeBoardPost", fetch = FetchType.LAZY)
    @OrderBy("fileNo asc")
    private List<MultiFiles> multiFiles;

    @Column(columnDefinition = "TEXT")
    private String boardContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private MetaUser metaUser;

    private int boardHits;

    @OneToMany(mappedBy = "freeBoardPost", fetch = FetchType.EAGER)
    @OrderBy("commentNo asc")
    private List<FreeBoardComment> commentList;

    private boolean boardIsDeleted;

    @Builder
    public FreeBoardPost(String boardCategory, String boardTitle, String boardContent,
                         MetaUser metaUser, int boardHits, List<FreeBoardComment> commentList,
                         boolean boardIsDeleted, List<MultiFiles> multiFiles) {
        this.boardCategory = boardCategory;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.metaUser = metaUser;
        this.boardHits = boardHits;
        this.commentList = commentList;
        this.boardIsDeleted = boardIsDeleted;
        this.multiFiles = multiFiles;
    }

    public void update(String newCategory, String newTitle, String newContent) {
        this.boardCategory = newCategory;
        this.boardTitle = newTitle;
        this.boardContent = newContent;
    }

    public void delete() {
        this.boardIsDeleted = true;
    }

    public void hitsUp(int boardHits) {
        this.boardHits = boardHits + 1;
    }
}

