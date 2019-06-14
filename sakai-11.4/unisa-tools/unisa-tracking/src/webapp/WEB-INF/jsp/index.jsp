<!DOCTYPE html>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="za.ac.unisa.lms.tools.tracking.bo.Consignment"%>
<%@page import="za.ac.unisa.lms.tools.tracking.bo.Docket"%>
<%@page import="za.ac.unisa.lms.tools.tracking.bo.Assignment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page session="true" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tracking.ApplicationResources"/>
<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<sakai:html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Unisa Assignment Hardcopy Tracking</title>

    <!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"  type="text/css" />	

    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/small-business.css" />" rel="stylesheet"  type="text/css" />	

 	<script type="text/javascript">
	 	$(document).ready(function(){

	 	});
	 	
	    function setAction(action) {
	    	var check = action;
	    	if(check == "in"){
	    		//alert("Action: "+check);
	    		document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=in';
	    	}else if(check == "out"){
	    		//alert("Action: "+check);
				document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=out';
	    	}else if(check == "search"){
	    		//alert("Action: "+check);
				document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=search';
	    	}else if(check == "report"){
	    		//alert("Action: "+check);
				document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=report';
	    	}else if(check == "printpdf"){
	    		//alert("Action: "+check);
				document.trackingForm.action = 'tracking.do?act=retrieveCSDInformation&type=printpdf';
	    	}
			document.trackingForm.submit();
		}
	</script>
	
</head>

<body>

<html:form action="/tracking">

	<p>
		<sakai:messages/>
		<sakai:messages message="true"/>
	</p>
    <!-- Page Content -->
    <div class="container">

      <!-- Content Row -->
      <div class="row">
        <div class="col-6">
          <div class="card h-100">
            <div class="card-body">
              <h5 class="card-title text-center"><fmt:message key="bookin.header"/></h5>
              <p class="card-text"><fmt:message key="bookin.info"/></p>
            </div>
            <div class="card-footer">
              <button class="btn btn-default btn-block" type="button" onclick="setAction('in');"><fmt:message key="button.indexin" /></button>
            </div>
          </div>
        </div>
        <!-- /.col-6 -->
        <div class="col-6">
          <div class="card h-100">
            <div class="card-body">
              <h5 class="card-title text-center"><fmt:message key="bookout.header"/></h5>
              <p class="card-text"><fmt:message key="bookout.info"/></p>
            </div>
            <div class="card-footer">
              <button class="btn btn-default btn-block" type="button" onclick="setAction('out');"><fmt:message key="button.indexout" /></button>
            </div>
          </div>
        </div>
        <!-- /.col-6 -->

      </div>
      <!-- /.row -->
	  <br/>
		<!-- Content Row -->
	    <div class="row">
	    	<div class="col-4">
	          <div class="card h-100">
	            <div class="card-body">
	              <h5 class="card-title text-center"><fmt:message key="index.print.header"/></h5>
	              <p class="card-text"><fmt:message key="pdf.info"/></p>
	            </div>
	            <div class="card-footer">
	              <button id="btnPrint" class="btn btn-default btn-block" onclick="setAction('printpdf');"><fmt:message key="button.indexpdf" /></button>
	            </div>
	          </div>
	        </div>
	        <!-- /.col-4 -->
	        <div class="col-4">
	          <div class="card h-100">
	            <div class="card-body">
	              <h5 class="card-title text-center"><fmt:message key="index.search.header"/></h5>
	              <p class="card-text"><fmt:message key="search.info"/></p>
	            </div>
	            <div class="card-footer">
	              <button id="btnSearch" class="btn btn-default btn-block" onclick="setAction('search');"><fmt:message key="button.indexsearch" /></button>
	            </div>
	          </div>
	        </div>
	        <!-- /.col-4 -->
	        <div class="col-4">
	          <div class="card h-100">
	            <div class="card-body">
	              <h5 class="card-title text-center"><fmt:message key="index.history.header"/></h5>
	              <p class="card-text"><fmt:message key="report.info"/></p>
	            </div>
	            <div class="card-footer">
	              <button id="btnReport" class="btn btn-default btn-block" onclick="setAction('report');"><fmt:message key="button.indexreport" /></button>
	            </div>
	          </div>
	        </div>
	        <!-- /.col-4 -->
		</div>
		<!-- /.row -->
    </div>
    <!-- /.container -->

    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap.bundle.min.js" />"></script>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="trackingForm" property="version"/></div>
	
</html:form>
</body>
  
</sakai:html>
