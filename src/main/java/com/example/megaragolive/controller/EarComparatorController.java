package com.example.megaragolive.controller;

import com.example.megaragolive.entity.Folder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
public class EarComparatorController {

    @PostMapping()
    public ArrayList<Folder> getComparisonRESULT(MultipartFile file1, MultipartFile file2) {
        return null;
    }

    
    public ArrayList<Folder> getModifiedFiles(MultipartFile file1, MultipartFile file2) {
        return null;
    }

    
    public ArrayList<Folder> getRemovedAddedFiles(MultipartFile file1, MultipartFile file2) {
        return null;
    }

    
    public ArrayList<Folder> getSameFiles(MultipartFile file1, MultipartFile file2) {
        return null;
    }
}
