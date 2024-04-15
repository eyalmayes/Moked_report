package com.example.moked_report;

public class Emplee {

    String name;
    String job;



    public Emplee() {
    }

    public Emplee(String job,String name) {
        this.job = job;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
