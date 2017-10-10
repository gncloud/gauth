<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="<%=pageContext.getServletContext().getContextPath()%>" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>gauth admin</title>

    <link rel="stylesheet" href="${contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/admin/contents.css">

    <script src='${contextPath}/js/jquery-3.2.1.js'></script>
    <script src="${contextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${contextPath}/admin/contents.js"></script>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="javascript:void(0);">Gauth</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${contextPath}/admin/client">클라이언트</a></li>
            <li><a href="${contextPath}/admin/user">회원(인증)</a></li>
            <li><a href="${contextPath}/admin/pendusers">회원(미인증)</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li class="right"><a href="javascript:void(0);" id="logout">로그아웃</a></li>
        </ul>


    </div>
</nav>

<script>
    $(function(){
        var hostIndex = location.protocol.length + 2 + location.host.length;
        var paramIndex = location.href.indexOf('?') == -1 ?  location.href.length : location.href.indexOf('?');
        var path = location.href.substring(hostIndex, paramIndex);
        $('ul.nav li').each(function(i,o){
            var aTag = $(o).find('a');
            if(aTag.attr('href') == path){
                $(aTag).attr('href', 'javascript:void(0);');
                $(aTag).parent().addClass('active');
                return false;
            }
        });
    });
</script>