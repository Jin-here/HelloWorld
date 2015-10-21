package com.vgaw.helloworld.bean;

/**
 * Created by Administrator on 2015/10/12.
 */
public class Memo {
    public static final int MAX_VALUE = 9999;
    private int id = MAX_VALUE;
    private String content;
    private String create_date;

    public Memo(){}

    public Memo(Memo memo){
        this.id = memo.getId();
        this.content = memo.getContent();
        this.create_date = memo.getCreate_date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", create_date='" + create_date + '\'' +
                '}';
    }
}
