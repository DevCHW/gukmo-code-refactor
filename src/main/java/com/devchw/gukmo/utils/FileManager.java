package com.devchw.gukmo.utils;

import org.springframework.stereotype.Component;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.*;
import java.util.Calendar;

@Component
public class FileManager {

    /**
     * 파일 업로드
     */
    public String doFileUpload(byte[] bytes, String originalFilename, String path) throws IOException {

        String newFileName = null;

        if(bytes == null) {
            return null;
        }

        if("".equals(originalFilename) || originalFilename == null ) {
            return null;
        }

        String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(fileExt == null || "".equals(fileExt) || ".".equals(fileExt)) {
            return null;
        }

        newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
        newFileName += System.nanoTime();
        newFileName += fileExt;

        File dir = new File(path);

        if(!dir.exists()) {
            dir.mkdirs();
        }

        String pathname = path + File.separator + newFileName;

        FileOutputStream fos = new FileOutputStream(pathname);

        fos.write(bytes);

        fos.close();

        return newFileName;
    }

    /**
     * 파일 다운로드
     */
    public boolean doFileDownload(String fileName, String orgFilename, String path, HttpServletResponse response) throws IOException {

        String pathname = path + File.separator + fileName;

        if("".equals(orgFilename) || orgFilename == null) {
            orgFilename = fileName;
        }

        try {
            orgFilename = new String(orgFilename.getBytes("UTF-8"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File file = new File(pathname);

        if(file.exists()) {  //파일이 존재한다면
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename="+orgFilename);

            byte[] readByte = new byte[4096];

            BufferedInputStream bfin = new BufferedInputStream(new FileInputStream(file));

            ServletOutputStream souts = response.getOutputStream();
            int length = 0;

            while( (length = bfin.read(readByte, 0, 4096)) != -1  ) {

                souts.write(readByte, 0, length);
            }// end of while()--

            souts.flush(); // ServletOutputStream(souts) 에 기록(저장)해둔 내용을 클라이언트로 내보내기.

            souts.close(); // ServletOutputStream(souts) 객체를 소멸
            bfin.close();  // BufferedInputStream(bfin) 객체를 소멸

            return true; // 다운로드 해줄 파일이 존재하고 Exception 이 발생하지 않으면 true 를 리턴시킨다.
        } // end of if--
        return false;
    }


    // === 파일 삭제하기 ===
    public void doFileDelete(String fileName, String path) {

        String pathname = path + File.separator + fileName;

        File file = new File(pathname);

        if(file.exists()) {
            file.delete();
        }
    }

    /**
     * 파일 업로드 2(네이버 스마트 에디어 사용 사진첨부)
     */
    public String doFileUpload(InputStream is, String originalFilename, String path) throws IOException {

        String newFilename = null;

        // 클라이언트가 업로드한 파일의 이름
        if(originalFilename==null || originalFilename.equals(""))
            return null;

        // 확장자
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(fileExt == null || fileExt.equals(""))
            return null;

        // 서버에 저장할 새로운 파일명을 만든다.
        newFilename = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
        newFilename += System.nanoTime();
        newFilename += fileExt;

        // 업로드할 경로가 존재하지 않는 경우 폴더를 생성 한다.
        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();

        String pathname = path + File.separator + newFilename;

        byte[] byteArr = new byte[1024];
        int size = 0;
        FileOutputStream fos = new FileOutputStream(pathname);

        while((size = is.read(byteArr)) != -1) {
            fos.write(byteArr, 0, size);
        }
        fos.flush();

        fos.close();
        is.close();

        return newFilename;
    }


    // 이미지 폭
    public int getImageWidth(String pathname) {
        int width=-1;

        File file = new File(pathname);
        if (! file.exists())
            return width;

        ParameterBlock pb=new ParameterBlock();
        pb.add(pathname);
        RenderedOp rOp= JAI.create("fileload",pb);

        BufferedImage bi=rOp.getAsBufferedImage();

        width = bi.getWidth();

        return width;
    }

    // 이미지 높이
    public int getImageHeight(String pathname) {
        int height=-1;

        File file = new File(pathname);
        if (! file.exists())
            return height;

        ParameterBlock pb=new ParameterBlock();
        pb.add(pathname);
        RenderedOp rOp=JAI.create("fileload",pb);

        BufferedImage bi=rOp.getAsBufferedImage();

        height = bi.getHeight();

        return height;
    }
}
