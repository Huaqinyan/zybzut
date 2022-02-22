package com.zyb.zut.req;

import com.sun.istack.internal.NotNull;

public class PageReq {
//    @NotNull(message="页码不能为空")
    private int page;
//    @NotNull(message="每页条数不能为空")
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}