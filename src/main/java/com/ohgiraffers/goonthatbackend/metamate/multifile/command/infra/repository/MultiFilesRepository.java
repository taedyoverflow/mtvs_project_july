package com.ohgiraffers.goonthatbackend.metamate.multifile.command.infra.repository;

import com.ohgiraffers.goonthatbackend.metamate.multifile.command.domain.aggregate.entity.MultiFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiFilesRepository extends JpaRepository<MultiFiles, Long> {
    //파일조회
    List<MultiFiles> findByFreeBoardPostBoardNo(Long boardNo);

    //게시글번호 기준 파일조회
    List<MultiFiles> findByFreeBoardPost_BoardNo(Long boardNo);


}
