package com.devchw.gukmo.utils;

public class PageBarUtil {
    /** 페이지 바 생성 */
    public static String createPageBar(int page, int totalPage, String url, String queryString) {
        // 1개 블럭(토막)당 보여지는 페이지번호의 개수
        int blockSize = 5;
        int loop = 1;
        int pageNo = ((page)/blockSize) * blockSize + 1;

        String pageBar = "<ul class='my pagination pagination-md justify-content-center mt-5'>";
        // === [<<][<] 만들기 === //
        if(pageNo != 1) {
            //[<<]
            pageBar += "<li class='page-item'>" +
                        "  <a class='page-link' href='"+url+"?page=0&"+queryString+"'>" +
                        "    <i class='fa-solid fa-angles-left'></i>" +
                        "  </a>" +
                        "</li>";
            //[<]
            pageBar += "<li class='page-item'>" +
                        "  <a class='page-link' href='"+url+"?page="+(pageNo-2)+"&"+queryString+"'>" +
                        "    <i class='fa-solid fa-angle-left'></i>" +
                        "  </a>" +
                        "</li>";
        }

        while( !(loop > blockSize || pageNo > totalPage) ) {
            if(pageNo == page+1) {	//페이지번호가 현재페이지번호와 같다면 .active
                pageBar += "<li class='page-item active' aria-current='page'>" +
                            "  <a class='page-link' href='#'>"+pageNo+"</a>" +
                            "</li>";
            } else {	//페이지번호가 현재페이지번호랑 다르다면 .active 뺌
                pageBar += "<li class='page-item'>" +
                            "  <a class='page-link' href='"+url+"?page="+(pageNo-1)+"&"+queryString+"'>"+pageNo+"</a>" +
                            "</li>";
            }
            loop++;
            pageNo++;
        }// end of while--

        // === [>][>>] 만들기 === //
        if (pageNo <= totalPage) {
            //[>]
            pageBar += "<li class='page-item'>" +
                        "  <a class='page-link' href='"+url+"?page="+(pageNo-1)+"&"+queryString+"'>"+
                        "    <i class='fa-solid fa-angle-right'></i>"+
                        "  </a>" +
                        "</li>";

            //[>>]
            pageBar += "<li class='page-item'>" +
                        "  <a class='page-link' href='"+url+"?page="+(totalPage-1)+"&"+queryString+"'>"+
                        "    <i class='fas fa-solid fa-angles-right'></i>"+
                        "  </a>" +
                        "</li>";
        }
        pageBar += "</ul>";
        return pageBar;
    }//end of getPageBar(){}---
}
