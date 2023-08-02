package com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.controller;

import com.ohgiraffers.goonthatbackend.metamate.multifile.command.application.service.MultiFilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class MultiFilesController {

    private final MultiFilesService fileService;


}
