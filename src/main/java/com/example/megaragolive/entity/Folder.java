package com.example.megaragolive.entity;

import java.io.File;
import java.util.ArrayList;

public class Folder {
    public static enum Status {
        MODIFIED,
        ADDED,
        EQUAL
    }

    private Status status=Status.EQUAL;
    private String name;
    private ArrayList<Folder> children;
    private File fileContent;

    private boolean hasChild;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isHasChild() {
        return hasChild;
    }
    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
    public Folder(String name, ArrayList<Folder> children, boolean hasChild,Status status) {
        this.name = name;
        this.children = children;
        this.hasChild = hasChild;
        this.status=status;
        initFileContent();
    }
    private void initFileContent(){
        File file=new File(name);
        if(!file.isDirectory()){
            this.fileContent=file;
        }
    }
    public File getFileContent() {
        return fileContent;
    }

    public void setFileContent(File fileContent) {
        this.fileContent = fileContent;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Folder> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Folder> children) {
        this.children = children;
    }
}
