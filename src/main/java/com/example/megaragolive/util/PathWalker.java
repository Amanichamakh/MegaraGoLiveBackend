package com.example.megaragolive.util;

import com.example.megaragolive.entity.Folder;
import java.util.*;
import java.util.regex.Pattern;

public class PathWalker {
    private Folder.Status pathStatus;
    public PathWalker(Folder.Status status) {
        this.pathStatus=status;
    }

    public static class Node {
        private final Map<String, Node> children = new TreeMap<>();

        public Node getChild(String name) {
            if (children.containsKey(name))
                return children.get(name);
            Node result = new Node();
            children.put(name, result);
            return result;
        }

        public Map<String, Node> getChildren() {
            return Collections.unmodifiableMap(children);
        }
    }

    private final Node root = new Node();

    private static final Pattern PATH_SEPARATOR = Pattern.compile("/");
    public void setListOfPaths(Set<String> paths){
        for(String path :paths){
            addPath(path);
        }
    }
    public void addPath(String path) {
        String[] names = PATH_SEPARATOR.split(path);
        Node node = root;
        for (String name : names)
            node = node.getChild(name);
    }
    public Folder showTree(){
        Folder folder=new Folder("",new ArrayList<Folder>(),true,this.pathStatus);
        printTree(folder,root);
        return folder;
    }
    private  void printTree(Folder folder, Node node) {
        Map<String, Node> children = node.getChildren();
        if ( children==null || children.isEmpty() )
            return;
        List<Folder> childs=new ArrayList<>();
        for (Map.Entry<String, Node> child : children.entrySet()) {
            Folder subFolder  = new Folder(child.getKey(), new ArrayList<Folder>(),true,this.pathStatus);
            printTree(subFolder,child.getValue());
            childs.add(subFolder);
        }
        folder.setChildren((ArrayList<Folder>) childs);
    }

    }
