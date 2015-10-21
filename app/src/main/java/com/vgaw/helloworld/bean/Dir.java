package com.vgaw.helloworld.bean;

/**
 * Created by caojin on 15-10-14.
 */
public class Dir {
    private int id;
    private String name;
    private int count;

    public Dir(){}

    public Dir(Dir dir){
        this.id = dir.id;
        this.name = dir.name;
        this.count = dir.count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount(){return count;}

    public void setCount(int count){
        this.count = count;
    }

    @Override
    public String toString() {
        return "Dir{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
