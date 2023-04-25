package com.example.megaragolive.controller;

import com.example.megaragolive.EntityManager;
import com.example.megaragolive.entity.FilesToken;
import com.example.megaragolive.service.EarFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/FileCntrl")
public class FileController {
@Autowired
    EarFileService es;
@Autowired
    EntityManager em;
    @PostMapping(value="/check")
    public void checkOneEarExtraction(MultipartFile file) throws IOException {
         String path=em.getExtractionPath();
         es.extractEarFile(file,path);
         es.decompileManyJars(path,path+ File.separator +"1");
    }
    @PostMapping(value="/upload2Ears")
    public long uploadAndExtract2EarFiles(@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2) throws IOException, InterruptedException, ExecutionException {
       return es.uploadAndExtract2Files(file1,file2);
    }
}
