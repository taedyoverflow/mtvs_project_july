package com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.repository;


import com.ohgiraffers.goonthatbackend.metamate.comment.command.domain.aggregate.entity.FreeBoardComment;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment,Long> {

    List<FreeBoardComment> findByFreeBoardPost(FreeBoardPost freeBoardPost);

    List<FreeBoardComment> findByFreeBoardPost_BoardNoAndCommentIsDeletedFalse(Long boardNo); //글번호로 댓글조회

}
