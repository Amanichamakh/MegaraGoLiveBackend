package com.example.megaragolive.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
@JsonSerialize
public class Folder {
    public static enum Status {
        MODIFIED,
        ADDED,
        EQUAL
    }

    private Status status=Status.EQUAL;
    private String name;
    private String label;

    private ArrayList<Folder> children;
    private String data;
private String icon=  "pi pi-folder";
    private boolean hasChildren;
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }
    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
    public Folder(String name, ArrayList<Folder> children, boolean hasChildren, Status status) throws IOException {
        this.name = name;
        this.children = children;
        this.hasChildren = hasChildren;
        this.status=status;
        this.label=name;
    }
    public Folder(String name, ArrayList<Folder> children, boolean hasChildren, File data) throws IOException {
        this.name = name;
        this.children = children;
        this.hasChildren = hasChildren;
        this.label=name;
        initFileContent();
    }
    private void initFileContent() throws IOException {
        File file=new File(name);
        if(!file.isDirectory()){
            this.data = IOUtils.toString(new FileInputStream(file));
        }
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
