package com.example.megaragolive.service;

import com.example.megaragolive.entity.Folder;
import com.example.megaragolive.util.PathWalker;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public ArrayList<Folder> getAddedFilesFolder(File file1, File file2) throws IOException {
        PathWalker pw = new PathWalker(Folder.Status.ADDED);
        pw.setListOfPaths(getAddedFiles(file1,file2));
        return pw.showTree().getChildren();
    }
    @Override
    public Set<String> getAddedFiles(File file1, File file2) throws IOException {
        Set<String> addedfiles = new HashSet<>();
        Set<String>  set1=earFileService.getAllFileNamesFromJarsEars(file1);
        Set<String>  set2=earFileService.getAllFileNamesFromJarsEars(file2);
        for (String s2 : set2) {
            if (!set1.contains(s2)) {
                addedfiles.add(s2);
            }
        }
        return addedfiles;
    }


    private String convertFileToString(File file) throws IOException {
        return IOUtils.toString(new FileInputStream(file));
    }

   @Override
    public  void  createResultFiles(String sourceFolder, String targetFolder, String sprefix, String tprefix, String outputD) throws IOException {
       File source = new File(sourceFolder);
       if (source.isDirectory()) {
           for (File f : source.listFiles()) {
               String secondPath = f.getAbsolutePath().replace(sprefix, tprefix);
               File f2 = new File(secondPath);
               if (!f.isDirectory() && f2.exists()) {
                   String fContent = convertFileToString(f);
                   String f2Content = convertFileToString(f2);
                   File result = new File(outputD + File.separator + ((!equals(fContent, f2Content)) ? "modified" : "") + f.getName());
                   FileOutputStream fos = new FileOutputStream(result);
                   fos.write(getComparisonResultInHTML(fContent, f2Content).getBytes());
                   fos.flush();
               } else if (f.isDirectory()) {
                   String newOD = outputD + File.separator + f.getName();
                   File dir = new File(newOD);
                   if (!dir.exists()) {
                       dir.mkdirs();
                   }
                   createResultFiles(f.getAbsolutePath(), secondPath, sprefix, tprefix, newOD);
               }
           }
       }
   }
    public String getComparisonResultInHTML(String text1, String text2) {
        List<String> differences = new ArrayList<>();
        StringBuilder html=new StringBuilder();
        String[] lines1 = text1.split("\\r?\\n");
        String[] lines2 = text2.split("\\r?\\n");

        int numLines = Math.max(lines1.length, lines2.length);
        for (int i = 0; i < numLines; i++) {
            String line1 = (i < lines1.length) ? lines1[i] : "";
            String line2 = (i < lines2.length) ? lines2[i] : "";
            if (!line1.equals(line2)) {
                html.append("<span class=\"Modified\">").append(String.format("%d :%n- %s%n+ %s", i + 1, line1, line2)).append("</span><br>");
            }
            else{
                html.append("<span>").append(String.format("%d %s %s", i + 1, line1, line2)).append("</span><br>");
            }
        }

        return html.toString();
    }
    public Boolean equals(String text1, String text2) {
        List<String> differences = new ArrayList<>();
        StringBuilder html=new StringBuilder();
        String[] lines1 = text1.split("\\r?\\n");
        String[] lines2 = text2.split("\\r?\\n");

        int numLines = Math.max(lines1.length, lines2.length);
        for (int i = 0; i < numLines; i++) {
            String line1 = (i < lines1.length) ? lines1[i] : "";
            String line2 = (i < lines2.length) ? lines2[i] : "";
            if (!line1.equals(line2)) {
                return false;
            }
        }

        return true;
    }
}
