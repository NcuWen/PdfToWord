package com._21cn.bigdata.pdf_to_word.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomXWPFDocument extends XWPFDocument {
    public CustomXWPFDocument(InputStream in) throws IOException {
        super(in);
    }

    public CustomXWPFDocument() {
        super();
    }

    public CustomXWPFDocument(OPCPackage pkg) throws IOException {
        super(pkg);
    }

    /**
     * @param r 图片
     * @param width 宽
     * @param height 高
     * @param imgFile 图片路径
     * @param format 图片格式
     */
    public void createPicture(XWPFRun r, int width, int height, String imgFile, int format) throws IOException, InvalidFormatException {
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
        r.setText(imgFile);
        r.addBreak();
        r.addPicture(new FileInputStream(imgFile), format, imgFile, width, height); // 200x200 pixels
        r.addBreak(BreakType.PAGE);
    }
}
