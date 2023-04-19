package com.example.megaragolive.controller;

import com.example.megaragolive.EntityManager;
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
    @PostMapping(value="/check2Ears")
    public void check2EarExtractions(@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2) throws IOException, InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        String path=em.getExtractionPath();
        String file1P=path+File.separator+"file1";
        String unzipFile1P=file1P+File.separator+"Unzipfile1";
        String file2P=path+File.separator+"fil2";
        String unzipFile2P=file2P+File.separator+"Unzipfil2";
        long debut= System.currentTimeMillis();
        es.extractEarFile(file1,file1P);
           // es.decompileManyJars(file1P,unzipFile1P);
        long duration=System.currentTimeMillis()-debut;
        System.out.println("duration file 1 "+duration);
        debut= System.currentTimeMillis();
        es.extractEarFile(file2,file2P);
            //es.decompileManyJars(file2P,unzipFile2P);
            duration=System.currentTimeMillis()-debut;
        System.out.println("duration file 1 "+2);

        //    String futures = executorService.invokeAny(callableTasks);
       System.out.println("finished");


    }
}
