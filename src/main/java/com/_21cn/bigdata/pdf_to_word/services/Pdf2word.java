package com._21cn.bigdata.pdf_to_word.services;

import java.io.*;
import java.awt.image.BufferedImage;

import com._21cn.bigdata.pdf_to_word.entities.FileRowShow;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;

public class Pdf2word {

    /**
     * 获取PDF页数
     * @param pdfFileName PDF文件路径
     * @return 页数
     * @throws IOException 抛出IO异常
     */
    public int getPageNumber(String pdfFileName) throws IOException {
        PDDocument pdf = PDDocument.load(new File(pdfFileName));
        int pageNumber = pdf.getNumberOfPages();
        return pageNumber;
    }

    public byte pdf2Word(FileRowShow fileRowShow) {
        try {
            String pdfFileName = fileRowShow.getPath();
            PDDocument pdf = PDDocument.load(new File(pdfFileName));
            int pageNumber = pdf.getNumberOfPages();

            String docFileName = pdfFileName.substring(0, pdfFileName.lastIndexOf(".")) + ".docx";

            File file = new File(docFileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            CustomXWPFDocument document = new CustomXWPFDocument();
            FileOutputStream fos = new FileOutputStream(docFileName);

            //提取每一页的图片和文字，添加到 word 中
            for (int i = 0; i < pageNumber; i++) {

                PDPage page = pdf.getPage(i);
                PDResources resources = page.getResources();

                Iterable<COSName> names = resources.getXObjectNames();
                for (COSName cosName : names) {
                    if (resources.isImageXObject(cosName)) {
                        PDImageXObject imageXObject = (PDImageXObject) resources.getXObject(cosName);
                        String fileName = "D:\\img\\" + System.currentTimeMillis() + ".jpg";
                        File outImgFile = new File(fileName);
                        Thumbnails.of(imageXObject.getImage()).scale(0.9).rotate(0).toFile(outImgFile);
                        BufferedImage bufferedImage = ImageIO.read(outImgFile);
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        if (width > 600) {
                            double ratio = Math.round((double) width / 550.0);
                            System.out.println("缩放比ratio：" + ratio);
                            width = (int) (width / ratio);
                            height = (int) (height / ratio);
                        }
                        System.out.println("width: " + width + ",  height: " + height);
                        XWPFParagraph picture = document.createParagraph();
                        XWPFRun r = picture.createRun();
                        //图片大小、位置
                        document.createPicture(r, width, height, fileName, CustomXWPFDocument.PICTURE_TYPE_JPEG);
                    }
                }


                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                //当前页中的文字
                String text = stripper.getText(pdf);


                XWPFParagraph textParagraph = document.createParagraph();
                XWPFRun textRun = textParagraph.createRun();
                textRun.setText(text);
                textRun.setFontFamily("仿宋");
                textRun.setFontSize(11);
                //换行
                textParagraph.setWordWrapped(true);
            }
            document.write(fos);
            fos.close();
            pdf.close();
            System.out.println("pdf转换解析结束！！----");
            return 1;
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            return 2;
        }
    }

    public static void main(String[] args) {
        String pdfFileName = "D:\\1.pdf";
        Pdf2word pdf2word = new Pdf2word();
//        System.out.println(pdf2word.pdf2Word(pdfFileName));
    }
}
