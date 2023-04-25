package com.example.megaragolive.entity;

import jakarta.persistence.*;


@Entity
@Table(name="FilesToken")
public class FilesToken {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long token;

    public FilesToken() {

    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long t){
        this.token=t;
    }

}
