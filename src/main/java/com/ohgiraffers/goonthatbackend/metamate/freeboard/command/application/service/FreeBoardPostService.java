package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.service;


import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto.MultiFilesWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardDetailDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardEditDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardListDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FreeBoardPostService {


    void savePost(FreeBoardWriteDTO boardDTO, List<MultiFilesWriteDTO> multiFilesWriteDTOList, SessionMetaUser user);

    void updatePost(Long boardNo, FreeBoardEditDTO freeBoardEditDTO, SessionMetaUser user);

    void deletePost(Long boardNo, SessionMetaUser user);

    Page<FreeBoardListDTO> getAllPosts(Pageable pageable);

    Page<FreeBoardListDTO> getCategoryPosts(String boardCategory, Pageable pageable);

    FreeBoardDetailDTO getDetailPosts(Long boardNo, SessionMetaUser user);

    void hitsUp(Long boardNo, FreeBoardDetailDTO freeBoardDetailDTO);

    Page<FreeBoardListDTO> getSearchPosts(String key, String searchKeyword, Pageable pageable);
}
