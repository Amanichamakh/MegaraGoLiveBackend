package com.example.megaragolive.service;

import com.example.megaragolive.EntityManager;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.benf.cfr.reader.api.CfrDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class EarFileService implements IEarFileService{
    @Autowired
    EntityManager em;
    private MultipartFile file;
    private String destDir;
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void initWorkingFolders(String sessionID) {
      String path=em.getExtractionPath()+File.separator+sessionID;
      File file=new File(path);
      if(!file.exists()){
          file.mkdir();
      }
    }

    @Override
    public void clearWorkingFolder(String sessionID) throws IOException {
        String path=em.getExtractionPath()+File.separator+sessionID;
        FileUtils.deleteDirectory(new File(path));
    }

    @Override
    public File convertMultipartFileToFile(MultipartFile multipartFile) {
        return null;
    }
    @Override
    public String getExtension(File file) {
        String fileName = file.getName();
        Optional<String> oFileExtension1 = Optional.ofNullable(fileName).filter(f -> f.contains(".")).map(f -> f.substring(fileName.lastIndexOf(".")));
        if (oFileExtension1.isPresent()) {
            return oFileExtension1.get();
        }

        return ".txt";
    }
    @Override
    public FileOutputStream extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        byte[] buffer = new byte[4096];
        FileOutputStream fos = new FileOutputStream(filePath);//filepath = le dossier dest+ le nom du fichier
        int bytesRead;
        while ((bytesRead = zipIn.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        return fos;
    }

    @Override
    public void extractEarFile(MultipartFile earFile, String destination) throws IOException {
        InputStream inputStream1 = earFile.getInputStream();
        // Créez un fichier temporaire pour stocker le contenu du fichier
        unzipFile(inputStream1,destination);
    }
    private Boolean forceFolderCreation(File file){
        File child=file;
        File parent=file.getParentFile();
        while(!child.exists() && !parent.exists()){
            child=parent;
            parent=parent.getParentFile();
            forceFolderCreation(parent);
            parent.mkdirs();
            child.mkdirs();
        }
        return true;
    }
    public FileOutputStream writeFile(InputStream zipIn, String filePath) throws IOException {
        byte[] buffer = new byte[4096];
        File file=new File(filePath.substring(0,filePath.lastIndexOf(File.separator)));
        File newFile=new File(filePath);
        if(!file.exists()){
            file.mkdirs();

        }
        FileOutputStream fos = new FileOutputStream(newFile);//filepath = le dossier dest+ le nom du fichier
        int bytesRead;
        while ((bytesRead = zipIn.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        if(filePath.contains(".jar")){
            decompileOneJar(file,filePath.substring(0,filePath.lastIndexOf(File.separator)),filePath.substring(0,filePath.lastIndexOf(File.separator)));
        }
        else         if(filePath.contains(".class")) {
            decompileOneClass(file,filePath);
        }
            return fos;
    }

    @Override
    public ZipInputStream unzipFile(InputStream is, String destDir) throws IOException {
        File file=new File(destDir);
        if(!file.exists()) file.mkdir();
        ZipInputStream zipIn = new ZipInputStream(is);
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            String filepath = destDir + File.separator + entry.getName();//fil
            if (!entry.isDirectory() ){
                writeFile(zipIn, filepath.replace("/",File.separator).replace("\\",File.separator));
            } else {
                File dir = new File(filepath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        return zipIn;

    }

    @Override
    public void decompileOneJar(File jarFile, String targetFolder, String targetParent) throws IOException {
        if(getExtension(jarFile).equals(".jar")){
            unzipFile(new FileInputStream(jarFile),targetFolder);
        }
        File folder=new File(targetFolder);
        if(folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    if (getExtension(file).equals(".jar") || getExtension(file).equals(".war")) {
                        decompileOneJar(file, targetFolder +File.separator+ file.getName(),targetParent);
                    } else if (getExtension(file).equals(".class")) {
                        decompileOneClass(file,targetParent);
                        Files.delete(Path.of(file.toURI()));
                    } else {
                        Files.copy(Paths.get(file.getPath()), new File(targetFolder+File.separator+file.getName()).toPath());
                        Files.delete(Path.of(file.toURI()));
                    }
                } else {
                    decompileOneJar(file, targetFolder +File.separator+ file.getName(),targetParent);
                 //   Files.delete(Path.of(file.toURI()));
                }
            }
        }  else{
            if(getExtension(folder).equals(".jar")  || getExtension(folder).equals(".war")){
                decompileOneJar(folder,targetFolder+File.separator+folder.getName(),targetParent);
                Files.delete(Path.of(folder.toURI()));
            } else if(getExtension(folder).equals(".class") ){
                decompileOneClass(folder,targetParent);
                Files.delete(Path.of(folder.toURI()));
            }
            else{
                Files.copy(Paths.get(folder.getPath()), new File(targetFolder+File.separator+folder.getName()).toPath());
                Files.delete(Path.of(folder.toURI()));
            }
        }
    }

    @Override
    public void decompileManyJars(String sourceFolder, String targetFolder) throws IOException {
        File sourceFile=new File(sourceFolder);
       File[] files = sourceFile.listFiles();
       for(File file:files){
           if(getExtension(file).equals(".jar")){
            decompileOneJar(file,targetFolder+File.separator+file.getName(),targetFolder+File.separator+file.getName());
            Files.delete(Path.of(file.toURI()));
           }
       }
    }
    @Override
    public void decompileOneClass(File fileToDecompile,String targetFolder) throws IOException {
        // Vérifier si le fichier existe
        if (!fileToDecompile.exists()) {
            throw new IllegalArgumentException("Le fichier spécifié n'existe pas : " + fileToDecompile.getAbsolutePath());
        }
        //    FileOutputStream fos=new FileOutputStream(targetFile);
        Map<String, String> options = new HashMap<>();
        options.put("outputdir", targetFolder);
        CfrDriver driver = new CfrDriver.Builder()
                .withOptions(options)
                .build();
        // Décompiler le fichier
        driver.analyse(Arrays.asList(fileToDecompile.getAbsolutePath()));
    }

}
