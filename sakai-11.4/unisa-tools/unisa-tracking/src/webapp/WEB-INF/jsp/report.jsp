<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%@ page import="java.util.*" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tracking.ApplicationResources"/>

<sakai:html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
    <meta content='maximum-scale=1.0, initial-scale=1.0, width=device-width' name='viewport'>
    <meta name="description" content="">
    <meta name="author" content="">

	<title>Assignment Tracking</title>

	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.css" />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
    <!-- Custom styles for this template -->
    <link href="<c:url value="/resources/css/theme.css" />" rel="stylesheet"  type="text/css" />

	<!-- CSS code from Bootply.com editor -->
    <style type="text/css">
    	body {
		    padding:30px;
		}
		@media print {
		    .panel-heading {
		        display:none
		    }
		}
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		
		.panel {
	        /*height : 300px;*/
	        margin-bottom:0;
	        position: relative;
	    }
	
	    .panel .panel-footer {
	        position : absolute;
	        bottom : 0;
	        margin-bottom:1px;
	        width : 100%;
	    }
	    .paddRight{
	    	padding-right: 60px; /*used to align list with checkboxes */
	    }
	    .paddRightSmall{
	    	padding-right: 5px; /*used to align list with checkboxes */
	    }
	    .topAlign{
	    	vertical-align:top;
	    	/*text-align:center;*/
	    }
	    
	    #activity {  
	        text-align:center;border:1px solid #ccc;  
    	}  
    	#activity td{  
        	text-align:center;border:1px solid #ccc;  
    	}  
     	#footerExport td{  
	       	cursor:pointer;  
    		text-align:center;border:1px solid #ccc;  
       		border:none;  
    	}  

	    /* This is the equivalent of cellspacing="0" */
		table {
			border-collapse:collapse;
		}
 
		/* This is the equivalent of cellpadding="0" */
		td, th {
  			padding:0;
		}
 
		/* This is the equivalent of border="1" */
		td, th {
  			border:0;
		}
	</style>
	
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.12.4.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.cookie.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery.blockUI.js" />"></script> 
	<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui.js" />"></script> 
	
	<script type="text/javascript">

		function frmSubmitSameWin(){
			document.trackingForm.target = '';
		}
	</script>
</head>
	
	<html:form action="/tracking" >
   
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
<div class="container">
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="row">
				<iframe class="col-lg-12 col-md-12 col-sm-12" src="http://stratusdev.unisa.ac.za/aol/asp/sql_exec_report5.asp?ID=853588&myid=CDTRACKING" width="560" height="400" frameborder="0" allowfullscreen>
				</iframe>
			</div>
		</div>
	</div>

	<!-- Actions -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:0px !important; padding-bottom:0px !important">
		  	<div class="table-responsive">
			<table style="width:100%">	
			    <tr>
			 		<td>
						<sakai:actions>
							<html:submit property="act" style="width: 70px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.clear" /></html:submit>&nbsp;
							<html:submit property="act" style="width: 50px; height: 80px" onclick="frmSubmitSameWin();"><fmt:message key="button.back" /></html:submit>
			  			</sakai:actions>
			 		</td>
					<td align="right"><font color="gray"><fmt:message key="user.name"></fmt:message><bean:write name="trackingForm" property="novelUserId"/></font></td>
				</tr>
			</table>
			</div>
		</div>
	</div>
</div>
<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
<div style="display: none;" align="center"><bean:write name="trackingForm" property="version"/></div>
</html:form>

</sakai:html>