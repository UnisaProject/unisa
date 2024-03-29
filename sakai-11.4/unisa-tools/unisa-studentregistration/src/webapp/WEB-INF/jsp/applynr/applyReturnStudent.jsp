<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentregistration.ApplicationResources"/>

<%
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<sakai:html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<!-- Sakai Themes -->
	<link href="<c:url value='/resources/css/tool_base.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/tool.css' />" rel="stylesheet"  type="text/css" />	
	<link href="<c:url value='/resources/css/portal.css' />" rel="stylesheet"  type="text/css" />

	<!-- Bootstrap core CSS -->
	<link href="<c:url value="/resources/css/bootstrap.css" />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value="/resources/css/bootstrap-theme.css" />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
	
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>  
	<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style>
	   <!-- CSS code from Bootply.com editor -->
    	.equal, .equal > div[class*='col-'] {  
		    display: -webkit-flex;
		    display: flex;
		    flex:1 1 auto;
		}
		.panel {
	        /*height : 300px;*/
	        margin-bottom:1px;
	        position: relative;
	    }
	    .panel .panel-footer {
	        position : relative;
	        /*bottom : 0;*/
	        margin-bottom:1px;
	        width : 100%;
	    }
	    <!-- Bootply end -->
	    
		.light {
			opacity : 0.5;
		    filter: alpha(opacity=50);   /* For IE8 and earlier */
		}
		.stretch {
		    width: 100%;
		
		    padding: 0 5px;
		    /*border: 0;    */
		    /*margin: 0 -5px;  compensate horizontal padding and border-width with negative horizontal marfin */
		
		    /* small styling 
		    border-radius: 5px; 
		    box-shadow: 0 0 5px #555 inset;*/
		}
		#stretch {
		    width: 100%;
		    padding: 0 5px;
		}
		input[type="text"] {
			width: 100%;
			padding: 0 5px;
			height: 2em;
			line-height: 2; 
		}
	</style>
		
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) } 

	$(document).ready(function() {
		
		$('form,input,select,textarea').attr("autocomplete", "off");
		
		var isReturn = $("#numYesNo").val();
		if (isReturn === "YES"){
			$("input[name='student.number']").focus();
		}else{
			$("input[name='student.surname']").focus();
		}
		
	});

	//Onlick()
	function validate(){
		
		var nextStep = "applyReturnStudent";
		var recentlyRegistered = $("#recentlyRegistered").val();
		if (recentlyRegistered === "false"){
			var number = $('input[name="student.number"]').val();
			if(number == null || number.trim() == "" || number == "undefinded"){
				showError("Note", "Please enter your Student number");
				return false;
			}
		}else if(recentlyRegistered === "true"){
			var password = $('input[name="student.password"]').val();
			if(password == null || password.trim() == "" || password == "undefinded"){
				showError("Note", "Please enter your password");
				return false;
			}
		}

		doSubmit("Continue", nextStep);
	}
		
	function doSubmit(button, nextStep){
		if (button === "Continue"){
			document.studentRegistrationForm.action='applyForStudentNumber.do?act='+nextStep;
		}else if (button === "Back"){
			document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
		}else if (button === "Cancel"){
			document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
		}
		$.blockUI({ message: "<strong><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Submitting...</strong>" });
		document.studentRegistrationForm.submit();
	}

	function showError(errorTitle, errorText) {
		// show the actual error modal
		$('#dialogContent').html(errorText);
		$('#dialogHolder').dialog({
			autoOpen: true,
		  	title: errorTitle,
		  	modal: true,
		  	buttons: {
		  		"Ok": function() {
		  			$(this).dialog("close");
		  		}
		  	}
		});
	}
	
	</script>

</head>

<body onload="disableBackButton();" onpageshow="if (event.persisted) disableBackButton" onunload="">
<!-- Form -->
<html:form action="/applyForStudentNumber">
	<html:hidden property="page" value="applyReturnStudent"/>
	
	<input type="hidden" name="webOpenDate" id="webOpenDate" value="<bean:write name='studentRegistrationForm' property='student.webOpenDate' />"/>
	<input type="hidden" name="webLogCheck" id="webLogCheck" value="<bean:write name='studentRegistrationForm' property='loginSelectMain'/>"/>
	<input type="hidden" name="numYesNo" id="numYesNo" value="<bean:write name='studentRegistrationForm' property='loginSelectYesNo'/>"/>
	<input type="hidden" name="allowLogin" id="allowLogin" value="<bean:write name='studentRegistrationForm' property='allowLogin'/>"/>
	<input type="hidden" name="recentlyRegistered" id="recentlyRegistered" value="<bean:write name='studentRegistrationForm' property='recentlyRegistered'/>"/>

	<sakai:messages/>
	<sakai:messages message="true"/>
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
		
	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body clearfix">
					<div class="col-md-3 col-sm-3 col-xs-3">
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6 clearfix">
						<table style="width:100%;">
			            	<tr>
			              		<td colspan="2" valign="top" style="height:50px;color:#A90E13;font-size:14px; margin-bottom:10px;" align="center">
			                  		<strong><bean:write name="studentRegistrationForm" property="webLoginMsg"/></strong><br/>
			                  		<strong><bean:write name="studentRegistrationForm" property="webLoginMsg2"/></strong>
			               		</td>
			               	</tr>
			               	<logic:equal name="studentRegistrationForm" property="allowLogin" value="true">
				               	<logic:equal name="studentRegistrationForm" property="loginSelectYesNo" value="YES">
									<logic:notEqual name="studentRegistrationForm" property="recentlyRegistered" value="true">
										<tr>
											<td style="width:180px;height:30px" valign="top"><fmt:message key="page.login.studnumber"/></td>
											<td style="height:30px"><html:text name="studentRegistrationForm" property="student.number" maxlength="8" size="24" onfocus="this.select();" />&nbsp;</td>
										</tr>
									</logic:notEqual>
									<logic:equal name="studentRegistrationForm" property="recentlyRegistered" value="true">
										<tr>
											<td style="width:180px;height:30px" valign="top"><fmt:message key="page.apply.login.password"/></td>
											<td style="height:30px"><html:password name="studentRegistrationForm" property="student.password" size="24" onfocus="this.select();" />&nbsp;</td>
										</tr>
									</logic:equal>
								</logic:equal>

							</logic:equal>
							<logic:equal name="studentRegistrationForm" property="allowLogin" value="false">
								<tr>
									<td colspan="2"><strong><font color="red" size="large"><fmt:message key="page.login.newClosed"/></font></strong></td>
						        </tr>
							</logic:equal>
			        	</table>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-3">
					</div>
				</div>
				<div class="panel-footer clearfix">
					<logic:equal name="studentRegistrationForm" property="allowLogin" value="true">
						<button class="btn btn-default" type="button" onclick="validate();">Continue</button>
						<button class="btn btn-default" type="button" onclick="doSubmit('Back','Back');">Back</button>
					</logic:equal>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel','Back');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>

</html:form>
</body>
</sakai:html>
