package com._21cn.bigdata.pdf_to_word.entities;

public class FileRowShow {
    private String path;
    private String name;
    private int  pageCount;
    private int outStart = 1;
    private int outEnd;
    private byte transformMode = 0;
    private boolean recovery = true;
    private byte status = 0;
    private String  pageRanges;
    private String  transMode = "自动选择";
    private String  recoveryStr = "Y";
    private String  statusStr = "";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getOutStart() {
        return outStart;
    }

    public void setOutStart(int outStart) {
        this.outStart = outStart;
        this.pageRanges = this.outStart + "-" + this.outEnd;
    }

    public int getOutEnd() {
        return outEnd;
    }

    public void setOutEnd(int outEnd) {
        this.outEnd = outEnd;
        this.pageRanges = this.outStart + "-" + this.outEnd;
    }

    public byte getTransformMode() {
        return transformMode;
    }

    public void setTransformMode(byte transformMode) {
        this.transformMode = transformMode;
        switch (transformMode) {
            case 1 : this.transMode = "布局优先"; break;
            case 2 : this.transMode = "内容优先"; break;
            default : this.transMode = "自动选择"; break;
        }
    }

    public boolean isRecovery() {
        return recovery;
    }

    public void setRecovery(boolean recovery) {
        this.recovery = recovery;
        if (recovery) {
            this.recoveryStr = "Y";
        } else {
            this.recoveryStr = "N";
        }
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
        switch (status) {
            case 1 : this.statusStr = "完成"; break;
            case 2 : this.transMode = "失败"; break;
            default : this.transMode = ""; break;
        }
    }

    public String getPageRanges() {
        return pageRanges;
    }

    public String getTransMode() {
        return transMode;
    }

    public String getRecoveryStr() {
        return recoveryStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    @Override
    public String toString() {
        return "FileRowShow{" +
                "name=" + name +
                ", pageCount=" + pageCount +
                ", outStart=" + outStart +
                ", outEnd=" + outEnd +
                ", transformMode=" + transformMode +
                ", recovery=" + recovery +
                ", status=" + status +
                ", pageRanges=" + pageRanges +
                ", transMode=" + transMode +
                ", recoveryStr=" + recoveryStr +
                ", statusStr=" + statusStr +
                '}';
    }
}
