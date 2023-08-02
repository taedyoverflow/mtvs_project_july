package com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.service;

import com.ohgiraffers.goonthatbackend.metamate.common.MD5Generator;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto.MultiFilesReadDTO;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.dto.MultiFilesWriteDTO;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.domain.aggregate.entity.MultiFiles;
import com.ohgiraffers.goonthatbackend.metamate.multifile.command.infra.repository.MultiFilesRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MultiFilesService {

    private final MultiFilesRepository multiFilesRepository;

    @Transactional
    public List<MultiFilesWriteDTO> saveFile(List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {

        //DTO 반환객체
        List<MultiFilesWriteDTO> multiFilesWriteDTOList = new ArrayList<>();
        //저장위치변수
        String savePath = "C:\\metamate";
        //디렉토리생성
        File directory = new File(savePath);
        //디렉토리 없을경우 오류처리
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("디렉토리가 성공적으로 생성되었습니다.");
            } else {
                System.out.println("디렉토리 생성에 실패했습니다.");
                throw new IllegalArgumentException("파일 디렉토리 생성에 실패했습니다.");
            }
        }
        for (MultipartFile file : files) { // 반복문
            if (!file.isEmpty()) {
                MultiFilesWriteDTO multiFilesWriteDTO = new MultiFilesWriteDTO(); // 파일마다 새로운 DTO 객체 생성

                String originFileName = file.getOriginalFilename();
                if (originFileName == null) {
                    // 파일 이름이 null인 경우에 대한 예외 처리
                    throw new IllegalArgumentException("파일 이름이 null입니다.");
                }

                String fileName = new MD5Generator(originFileName).toString();
                String filePath = savePath + File.separator + fileName;

                try {
                    file.transferTo(new File(filePath));
                } catch (IOException e) {
                    // 파일 전송 과정에서 발생한 예외 처리
                    throw new IllegalArgumentException("파일 전송 중 오류가 발생했습니다.");
                }
                multiFilesWriteDTO.setOriginFileName(originFileName);
                multiFilesWriteDTO.setFileName(fileName);
                multiFilesWriteDTO.setFilePath(filePath);
                multiFilesWriteDTOList.add(multiFilesWriteDTO);
            }
        }
        return multiFilesWriteDTOList;
    }


//    @Transactional(readOnly = true)
//    public List<MultiFilesReadDTO> getFiles(Long boardNo) {
//        List<MultiFiles> multiFilesList = multiFilesRepository.findByFreeBoardPost_BoardNo(boardNo);
//        List<MultiFilesReadDTO> multiFilesReadDTOList = new ArrayList<>();
//
//        for (MultiFiles multiFiles : multiFilesList) {
//            MultiFilesReadDTO multiFilesReadDTO = new MultiFilesReadDTO();
//            multiFilesReadDTO.setFileNo(multiFiles.getFileNo());
//            multiFilesReadDTO.setOriginFileName(multiFiles.getOriginFileName());
//            multiFilesReadDTO.setFileName(multiFiles.getFileName());
//            multiFilesReadDTO.setFilePath(multiFiles.getFilePath());
//
//            multiFilesReadDTOList.add(multiFilesReadDTO);
//        }
//
//        return multiFilesReadDTOList;
//    }
}




