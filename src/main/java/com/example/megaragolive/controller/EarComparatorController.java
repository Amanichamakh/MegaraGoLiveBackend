package com.example.megaragolive.controller;

import com.example.megaragolive.entity.Folder;
import com.example.megaragolive.service.EarComparatorService;
import com.example.megaragolive.service.EarFileService;
import com.example.megaragolive.util.PathWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/Comparator")
public class EarComparatorController {
    @Autowired
    EarComparatorService ecs;
    @Autowired
    EarFileService es;

    @PostMapping("/result")
    public ResponseEntity<Folder> getComparisonRESULT(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2){
        Long token= null;
        try {
        token = es.uploadAndExtract2Files(file1,file2);
        ecs.createResultFiles(es.getUNZip1Path(token),es.getUNZip2Path(token),es.getUNZip1Path(token),es.getUNZip2Path(token),es.getResultPath(token));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<Folder>(PathWalker.getFolderTree(es.getResultPath(token)), headers, HttpStatus.OK);
        } catch (IOException e) {
            return null;
        }
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
