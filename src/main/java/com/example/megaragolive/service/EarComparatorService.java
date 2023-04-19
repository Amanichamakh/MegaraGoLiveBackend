package com.example.megaragolive.service;

import com.example.megaragolive.entity.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Service
public class EarComparatorService implements IEarComparatorService{

    @Autowired
    EarFileService earFileService;
    @Override
    public ArrayList<Folder> getComparisonRESULT(MultipartFile file1, MultipartFile file2) {
        return null;
    }

    @Override
    public ArrayList<Folder> getModifiedFiles(MultipartFile file1, MultipartFile file2) {
        return null;
    }

    @Override
    public ArrayList<Folder> getRemovedAddedFiles(MultipartFile file1, MultipartFile file2) {
        return null;
    }

    @Override
    public ArrayList<Folder> getSameFiles(MultipartFile file1, MultipartFile file2) {
        return null;
    }
}
