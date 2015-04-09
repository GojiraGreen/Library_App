<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/style/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${param.title }</title>
</head>
<body>
	<div class="headerWrapper">
		<div class="header">
			<img src="${pageContext.request.contextPath }/images/logo.png" />
			<span id="title"></span>
		</div>
	</div>
	    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath }/library?action=home">Library</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="${pageContext.request.contextPath }/library?action=browse">Search for books</a>
                    </li>
                    <c:choose>
                    	<c:when test="${USER != null }">
                    	    <li>
                       			 <a href="${pageContext.request.contextPath }/library?action=userpage">My account</a>
                    		</li>	
                    	</c:when>
                    	<c:when test="${ADMIN != null }">
                    		<li>
                       			 <a style="color:red;" href="${pageContext.request.contextPath }/library?action=admin">Administration</a>
                    		</li>
                    	</c:when>
                    	<c:otherwise>
                    	    <li>
                       			 <a href="${pageContext.request.contextPath }/library?action=login">Login</a>
                    		</li>
                    	</c:otherwise>
                    </c:choose>
                    <li>
                        <a href="${pageContext.request.contextPath }/library?action=contact">Contact</a>
                    </li>
                    <c:choose>
	                    <c:when test="${USER != null }">
		                     <li>
		                        <a style="color:red;" href="${pageContext.request.contextPath }/library?action=logout">Logout</a>
		                    </li>
	                    </c:when>
	                    <c:when test="${ADMIN != null }">
		                     <li>
		                        <a style="color:red;" href="${pageContext.request.contextPath }/library?action=logout">Logout</a>
		                    </li>
	                    </c:when>
                    </c:choose>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>