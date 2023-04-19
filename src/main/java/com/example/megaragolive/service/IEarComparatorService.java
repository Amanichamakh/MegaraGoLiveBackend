package com.example.megaragolive.service;

import com.example.megaragolive.entity.Folder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Set;

public interface IEarComparatorService {

    public ArrayList<Folder> getComparisonRESULT(MultipartFile file1,MultipartFile file2);
    public ArrayList<Folder> getModifiedFiles(MultipartFile file1,MultipartFile file2);
    public ArrayList<Folder> getRemovedAddedFiles(MultipartFile file1,MultipartFile file2);
    public ArrayList<Folder> getSameFiles(MultipartFile file1,MultipartFile file2);

}
