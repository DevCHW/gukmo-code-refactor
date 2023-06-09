<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath();
%> 
    

<%-- 직접 만든 CSS --%>
<link rel="stylesheet" href="<%=ctxPath %>/resources/css/admin/admin.css">
    
<%-- Main Content --%>
<div id="content">

    <%-- Begin Page Content --%>
    <div class="container-fluid">

        <%-- 404 Error Text --%>
        <div class="text-center mt-5 pt-5">
            <div class="error mx-auto" data-text="ERROR">ERROR</div>
            <p class="lead text-gray-800 mb-5">서버 문제로 연결이 불가능합니다.</p>
            <p class="text-gray-500 mb-0">It looks like you found a glitch in the matrix...</p>
            <a href="/">&larr; Back to GUKMO</a>
        </div>

    </div>
    <%-- /.container-fluid --%>

</div>
<%-- End of Main Content --%>