package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.repository;

import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FreeBoardPostRepository extends JpaRepository<FreeBoardPost, Long> {

    Page<FreeBoardPost> findByBoardIsDeletedFalse(Pageable pageable); //삭제여부 false만 조회

    Page<FreeBoardPost> findByBoardTitleContainingAndBoardIsDeletedFalse(String searchKeyword, Pageable pageable); // 제목검색

    Page<FreeBoardPost> findByBoardContentContainingAndBoardIsDeletedFalse(String searchKeyword, Pageable pageable); //내용검색

    Page<FreeBoardPost> findByMetaUserNicknameContainingAndBoardIsDeletedFalse(String searchKeyword, Pageable pageable); //닉네임검색

    Page<FreeBoardPost> findByBoardCategoryAndBoardIsDeletedFalse(String boardCategory, Pageable pageable); // 카테고리

}
