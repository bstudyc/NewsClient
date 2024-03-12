package com.example.newsclient;

/**
 * 功能：
 * 作者：吴蓓
 * 日期：2023年12月16日
 */
public class CntoEn {
    private String title;
    private String py_title;

    public CntoEn(String title, String py_title) {
        this.title = title;
        this.py_title = py_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPy_title() {
        return py_title;
    }

    public void setPy_title(String py_title) {
        this.py_title = py_title;
    }
}
