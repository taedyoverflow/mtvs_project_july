package com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.controller;

import com.ohgiraffers.goonthatbackend.metamate.auth.LoginUser;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto.MultiFilesReadDTO;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto.MultiFilesWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.service.MultiFilesService;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardDetailDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardEditDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardListDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.dto.FreeBoardWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.freeboard.command.application.service.FreeBoardPostService;
import com.ohgiraffers.goonthatbackend.metamate.web.dto.user.SessionMetaUser;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class FreeBoardController {

    private final FreeBoardPostService freeBoardService;
    private final MultiFilesService multiFilesService;


    /* 게시판 전체 목록 조회, 검색목록 조회 분리 */
    @GetMapping("/list")
    public String list(@RequestParam(required = false) String searchKeyword, //검색어
                       @RequestParam(required = false) String key, //검색선택
                       @RequestParam(required =false) String category,//카테고리 선택
                       @LoginUser SessionMetaUser user,
                       @PageableDefault(page = 0, size = 12, sort = "boardNo",
                               direction = Sort.Direction.DESC) Pageable pageable, //page정렬
                       Model model) {

        Page<FreeBoardListDTO> boardList;

        if (user != null) {
            model.addAttribute("user", user);
        }

        if (searchKeyword != null && key != null) {
            boardList = freeBoardService.getSearchPosts(key, searchKeyword, pageable);
        } else if (category !=null) {
            boardList = freeBoardService.getCategoryPosts(category,pageable);
        }else{
            boardList = freeBoardService.getAllPosts(pageable);
        }

        int nowPage = boardList.getPageable().getPageNumber() + 1; // 현재 페이지 번호를 1부터 시작하도록 수정
        int totalPages = boardList.getTotalPages(); //총 페이지 수
        int startPage = Math.max(nowPage - 4, 1); // 현재 페이지
        int endPage = Math.min(nowPage + 5, totalPages); // 마지막 페이지
        endPage = Math.min(endPage, startPage + 9); // 최대 10개 페이지 링크를 표시하도록 수정

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boardList", boardList);
        model.addAttribute("totalPages", totalPages);

        return "board/list";
    }

    /* 글쓰기 페이지 조회 */
    @GetMapping("/write")
    public String write(@ModelAttribute("freeBoardWriteDTO") FreeBoardWriteDTO freeBoardWriteDTO,
                        @LoginUser SessionMetaUser user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "board/write";
    }

    /* 글쓰기 페이지 작성 */
    @PostMapping("/write")
    public String writeSave(@Valid FreeBoardWriteDTO freeBoardWriteDTO, BindingResult bindingResult,
                            @RequestParam("file") List<MultipartFile> files,
                            @LoginUser SessionMetaUser user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            model.addAttribute("freeBoardWriteDTO", freeBoardWriteDTO);
            return "board/write"; // 에러가 발생한 폼 페이지로 다시 이동
        }

        try {
            if (!files.isEmpty()) { // 파일이 선택되었는지 확인
                List<MultiFilesWriteDTO> multiFilesWriteDTOList = multiFilesService.saveFile(files); // 파일 업로드 및 정보 저장
                freeBoardService.savePost(freeBoardWriteDTO, multiFilesWriteDTOList, user); // 게시글 정보 저장
            } else {
                // 파일이 선택되지 않았을 경우, 파일 업로드 로직을 건너뜀
                freeBoardService.savePost(freeBoardWriteDTO, null, user); // 게시글 정보 저장 (파일 정보를 null로 전달)
            }
        } catch (IOException e) {
            model.addAttribute("errorMessage", "파일 전송 중 오류가 발생했습니다.");
        } catch (NoSuchAlgorithmException e) {
            model.addAttribute("errorMessage", "파일 처리 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/board/list";
    }

    /* 게시판 글 번호 별 세부 조회 */
    @GetMapping("/detail/{boardNo}")
    public String detail(@PathVariable Long boardNo,HttpServletResponse response,
                         @CookieValue(value = "viewedPosts", defaultValue = "") String viewedPosts,
                         @LoginUser SessionMetaUser user, Model model) {

        if (user != null) {
            model.addAttribute("user", user);
        }
        //글 조회
        FreeBoardDetailDTO boardDetail = freeBoardService.getDetailPosts(boardNo, user);

        // 해당 게시글을 조회한 적이 없는 경우 조회수 증가
        if (!viewedPosts.contains("post" + boardNo)) {
            freeBoardService.hitsUp(boardNo, boardDetail);

            // 쿠키에 조회한 게시글 번호 추가
            viewedPosts += "post" + boardNo + "|";
            Cookie cookie = new Cookie("viewedPosts", viewedPosts);
            cookie.setMaxAge(60 * 60 * 24); // 24시간 동안 유지
            response.addCookie(cookie);
        }

        //url 강제접근 방어
        if (boardDetail.isBoardIsDeleted()) {
            return "redirect:board/list";
        }

        //게시글, 파일 model 적재
        model.addAttribute("boardDetail", boardDetail);
        return "board/detail";
    }

    /* 게시글 수정 페이지 조회 */
    @GetMapping("/edit/{boardNo}")
    public String edit(@PathVariable Long boardNo,
                       @ModelAttribute("freeBoardEditDTO") FreeBoardEditDTO freeBoardEditDTO,
                       @LoginUser SessionMetaUser user, Model model) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        FreeBoardDetailDTO boardDetail = freeBoardService.getDetailPosts(boardNo, user);
        if (boardDetail.getMetaUser().getId().equals(user.getId())) {
            model.addAttribute("boardDetail", boardDetail);
            freeBoardEditDTO.setBoardTitle(boardDetail.getBoardTitle());
            freeBoardEditDTO.setBoardContent(boardDetail.getBoardContent());
            freeBoardEditDTO.setBoardCategory(boardDetail.getBoardCategory());
            return "board/edit";
        } else {
            model.addAttribute("Message", "게시글을 수정할 권한이 없습니다.");
            return "redirect:/board/detail/" + boardNo;
        }
    }

    /* 게시글 수정 */
    @PostMapping("/edit/{boardNo}")
    public String editSave(@PathVariable Long boardNo,
                           @ModelAttribute("freeBoardEditDTO")
                           @Valid FreeBoardEditDTO freeBoardEditDTO, BindingResult bindingResult,
                           @LoginUser SessionMetaUser user, Model model) {

        if (user != null) {
            model.addAttribute("user", user);
        }
        FreeBoardDetailDTO boardDetail = freeBoardService.getDetailPosts(boardNo, user);
        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            model.addAttribute("boardDetail", boardDetail);
            model.addAttribute("freeBoardEditDTO", freeBoardEditDTO);
            return "board/edit";
        }
        try {
            freeBoardService.updatePost(boardNo, freeBoardEditDTO, user);
            return "redirect:/board/detail/" + boardNo;
        } catch (IllegalStateException e) {
            return "redirect:/board/detail/" + boardNo;
        }
    }

    /* 게시글 삭제 */
    @PostMapping(value = "/detail/{boardNo}")
    public String delete(@PathVariable Long boardNo, @LoginUser SessionMetaUser user, Model model) {

        if (user != null) {
            model.addAttribute("user", user);
        }

        try {
            freeBoardService.deletePost(boardNo, user);
            return "redirect:/board/list";
        } catch (IllegalStateException e) {
            return "redirect:/board/detail/" + boardNo;
        }
    }

    /* 첨부파일 다운로드 */

//    @GetMapping(path = "/download/{fileNo}")
//    public ResponseEntity<Resource> fileDownload(@PathVariable("fileNo") Long fileNo) {
//        // 파일 정보 조회
//        MultiFilesReadDTO fileInfo = multiFilesService.getFileInfo(fileId);
//
//        // 파일이 존재하지 않을 경우 예외 처리
//        if (fileInfo == null) {
//            throw new RuntimeException("파일이 존재하지 않습니다.");
//        }
//
//        String originFileName = fileInfo.getOriginFileName();
//        String filePath = fileInfo.getFilePath();
//        String contentType = "application/octet-stream"; // 바이너리 파일로 다운로드하도록 content type 설정
//
//        File file = new File(filePath);
//        long fileLength = file.length();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(contentType));
//        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(originFileName).build());
//
//        try {
//            // 파일을 Resource로 변환하여 ResponseEntity에 담아서 반환
//            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
//
//            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
//        } catch (IOException e) {
//            throw new RuntimeException("파일 로드 오류");
//        }
//    }

}


