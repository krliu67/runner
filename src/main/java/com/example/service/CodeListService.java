package com.example.service;

import com.example.model.CodeList;
import com.example.repo.CodeListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CodeListService {
    private final CodeListRepo codeListRepo;

    @Autowired
    public CodeListService(CodeListRepo codeListRepo){
        this.codeListRepo = codeListRepo;
    }

    public List<CodeList> getAllCodes(){
        return codeListRepo.findAll();
    }
}
