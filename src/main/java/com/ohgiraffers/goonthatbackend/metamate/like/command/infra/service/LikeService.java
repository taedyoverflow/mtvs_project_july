package com.ohgiraffers.goonthatbackend.metamate.like.command.infra.service;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUser;
import com.ohgiraffers.goonthatbackend.metamate.domain.user.MetaUserRepository;
import com.ohgiraffers.goonthatbackend.metamate.exception.CustomException;
import com.ohgiraffers.goonthatbackend.metamate.exception.ErrorCode;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.domain.aggregate.entity.FreeBoardPost;
import com.ohgiraffers.goonthatbackend.metamate.like.command.application.dto.LikeBoardDTO;
import com.ohgiraffers.goonthatbackend.metamate.like.command.domain.aggregate.entity.Like;
import com.ohgiraffers.goonthatbackend.metamate.like.command.infra.repository.LikeRepository;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final MetaUserRepository metaUserRepository;
    private final LikeRepository likeRepository;

    public boolean isLikedByUser(FreeBoardPost freeBoardPost, Long userId) {
        MetaUser metaUser = metaUserRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return likeRepository.findByFreeBoardPostAndMetaUser(freeBoardPost, metaUser).isPresent();
    }
    @Transactional
    public List<LikeBoardDTO> addLike(FreeBoardPost freeBoardPost, SessionMetaUser user) {

        MetaUser metaUser = metaUserRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 이미 해당 사용자가 좋아요를 눌렀는지 확인
        if (likeRepository.findByFreeBoardPostAndMetaUser(freeBoardPost, metaUser).isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Like like = Like.builder()
                .freeBoardPost(freeBoardPost)
                .metaUser(metaUser)
                .build();

        likeRepository.save(like);

        return getLikeCount(freeBoardPost);
    }

    @Transactional
    public List<LikeBoardDTO> deleteLike(FreeBoardPost freeBoardPost, SessionMetaUser user) {
        MetaUser metaUser = metaUserRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        if (!likeRepository.findByFreeBoardPostAndMetaUser(freeBoardPost, metaUser).isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        likeRepository.deleteByFreeBoardPostAndMetaUser(freeBoardPost, metaUser);

        // 좋아요 수를 반환
        return getLikeCount(freeBoardPost);
    }

    public List<LikeBoardDTO> getLikeCount(FreeBoardPost freeBoardPost) {
        List<Like> likes = likeRepository.findByFreeBoardPost(freeBoardPost);

        // 좋아요 수를 계산하여 LikeBoardDTO에 설정 후, 해당 DTO를 리스트로 담는 작업
        List<LikeBoardDTO> likeList = new ArrayList<>();
        for (Like like : likes) {
            LikeBoardDTO likeDTO = LikeBoardDTO.fromEntity(like);
            likeDTO.setLikeCount(likes.size());
            likeList.add(likeDTO);
        }

        return likeList;
    }

}
