package com.example.megaragolive.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipInputStream;

public interface IEarFileService {
    public void initWorkingFolders(String sessionID);
    public void clearWorkingFolder(String sessionID) throws IOException;
    public File convertMultipartFileToFile(MultipartFile multipartFile);

    String getExtension(File file);

    FileOutputStream extractFile(ZipInputStream zipIn, String filePath) throws IOException;
   // public void extractEarFile(File earFile, String destination);
    //ZipInputStream unzipFile(MultipartFile file, String destDir) throws IOException;
    void extractEarFile(MultipartFile earFile, String destination) throws IOException;

    ZipInputStream unzipFile(InputStream is, String destDir) throws IOException;

    public void decompileOneJar(File jarFile, String targetFilePath ,String parent) throws IOException;


    public void decompileManyJars(String sourceFolder, String targetFolder) throws IOException;
    public void decompileOneClass(File classFile,String targetFolder) throws IOException;
}
