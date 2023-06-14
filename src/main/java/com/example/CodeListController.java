package com.example;

import com.example.model.CodeList;
import com.example.service.CodeListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/codelist")
@Slf4j
public class CodeListController {

    private CodeListService codeListService;

    public CodeListController(CodeListService codeListService) {
        this.codeListService = codeListService;
    }

    @GetMapping("/all")
    public List<CodeList> getAllCode(){
        log.info("---- get all codes ----");
        return codeListService.getAllCodes();
    }

}
