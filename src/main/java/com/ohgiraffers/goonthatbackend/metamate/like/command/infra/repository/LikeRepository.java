package com.ohgiraffers.goonthatbackend.metamate.like.command.infra.repository;


import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.like.command.domain.aggregate.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByFreeBoardPost(FreeBoardPost freeBoardPost);

    Optional<Like> findByFreeBoardPostAndMetaUser(FreeBoardPost freeBoardPost, MetaUser metaUser);

    void deleteByFreeBoardPostAndMetaUser(FreeBoardPost freeBoardPost, MetaUser metaUser);
}

