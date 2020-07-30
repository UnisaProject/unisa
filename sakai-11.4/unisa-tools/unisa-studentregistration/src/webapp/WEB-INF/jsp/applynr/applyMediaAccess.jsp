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
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	<style type="text/css">
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
	        /*font-size: 12px !important;*/
			font-weight: normal !important;
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
		input[type='radio'] {
			float: left;
		}
		label:hover {
		    cursor:pointer;
		}
		label.inline {
			font-weight: normal;
		    display: table-cell;
		}
		table { 
		    border-spacing: 2px;
		    <!-- border-collapse: separate; -->
		    /*font-size: 12px !important;
			font-weight: bold !important;*/
		}
		table.itemSummary td, td.itemSummaryLite {
			padding: .1em;
			/*font-size: 12px !important;*/
			font-weight: normal !important;
		}
		td { 
		    padding: 2px;
		}
		.nav-tabs { 
			border-bottom: none;
		}
		.nav-tabs > li {
		    position:relative;
		    color : white;
		    background-color:#ededed;
		    padding : 2px 2px;
		    border-radius: 5px 5px 0px 0px ;
		    border:0;
		    display: inline-block;
		}
		.nav-tabs > li .close {
		    margin: -3px 0 0 10px;
		    font-size: 18px;
		    padding: 5px 0;
		    float: right;
		}
		.nav-tabs > li > a	{
		    /* adjust padding for height*/
		    padding-top: 4px; 
		    padding-bottom: 4px;
		}
		.nav-tabs > li a[data-toggle=tab] {
		    float: left!important;
		}
		li span{
			display:block;
			white-space: nowrap;
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
	</style>
	
	<script type="text/javascript">  
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) }  

		$(document).ready(function() {
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			//Hide doFinAid for SLP Students
			var isStuSLP = $("#isStuSLP").val();
			if (isStuSLP == "true"){
				$("#doFinAid").hide();	
				$("input:radio[name='studentApplication.finaidNsfas'][value='N']").prop('checked', true);
				$("input:radio[name='studentApplication.completeQual'][value='N']").prop('checked', true);
				$('#completeText').val(' ');
			}
			
			var isStuRegistered= $("#isStuRegistered").val();
			if (isStuRegistered == "true"){
					$("#doExamCenter").hide();	
					$("select[name='selectedExamCentre']").empty(); 
					$('#completeText').val(' ');
			}					
			
			//Hide completeText
			$(".doCompleteText").css('visibility', 'hidden');
			
		});
				
		function cleanError(){
			/**Clean error**/
			$("#forExam").text('');
		}
		
		//On button click
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=stepMediaAccess';
			}else if (button === "Back"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Back';
			}else if (button === "Cancel"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=Cancel';
			}
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
	<html:hidden property="page" value="applyMediaAccess"/>
	
	<input type="hidden" name="textLine" id="textLine" value="<bean:write name='studentRegistrationForm' property='studentApplication.completeText'/>"/>
	<input type="hidden" name="prevExam" id="prevExam" value="<bean:write name='studentRegistrationForm' property='selectedExamCentre'/>"/>
	<input type="hidden" name="prevPrison" id="prevPrison" value="<bean:write name='studentRegistrationForm' property='studentApplication.prisoner'/>"/>	
	<input type="hidden" id="isStuSLP" name="isStuSLP" value="<bean:write name='studentRegistrationForm' property='student.stuSLP' />" />
	<input type="hidden" id="isStuRegistered" name="isStuRegistered" value="<bean:write name='studentRegistrationForm' property='student.stuRegistered' />" />

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
				<div class="panel-body">	
					<sakai:group_table>
						<tr>
							<td colspan="3"><strong><fmt:message key="page.apply.media.access1"/></strong></td>						
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.media.personal.computer"/>&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><html:checkbox property="student.mediaAccess1" value="Y"/></td>
							<!--<td><html:radio property="student.mediaAccess1" value="N"/><fmt:message key="page.no"/></td>-->
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.media.work.computer"/>&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><html:checkbox property="student.mediaAccess2" value="Y"/></td>
							<!--<td><html:radio property="student.mediaAccess2" value="N"/><fmt:message key="page.no"/></td>-->
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.media.other.computer"/>&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><html:checkbox property="student.mediaAccess3" value="Y"/></td>
							<!--<td><html:radio property="student.mediaAccess3" value="N"/><fmt:message key="page.no"/></td>-->
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.media.cell"/>&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><html:checkbox property="student.mediaAccess4" value="Y"/></td>
							<!--<td><html:radio property="student.mediaAccess4" value="N"/><fmt:message key="page.no"/></td>-->
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr>
					</sakai:group_table>

					<sakai:group_table>
						<tr>
							<td colspan="3"><strong><fmt:message key="page.apply.media.access3"/></strong></td>						
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.media.home"/>&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><html:checkbox property="student.mediaAccess5" value="Y"/></td>
							<!--<td><html:radio property="student.mediaAccess5" value="N"/><fmt:message key="page.no"/></td>-->
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.media.work"/>&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><html:checkbox property="student.mediaAccess6" value="Y"/></td>
							<!--<td><html:radio property="student.mediaAccess6" value="N"/><fmt:message key="page.no"/></td>-->
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr><tr height="20px">
							<td><fmt:message key="page.apply.media.data"/>&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><html:checkbox property="student.mediaAccess7" value="Y"/></td>
							<!--<td><html:radio property="student.mediaAccess7" value="N"/><fmt:message key="page.no"/></td>-->
						</tr><tr height="5px">
							<td colspan="3">&nbsp;</td>
						</tr>
					</sakai:group_table>
				</div>				
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="doSubmit('Continue');">Save and Continue</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Back');">Back</button>
					<button class="btn btn-default" type="button" onclick="doSubmit('Cancel');">Cancel</button>
				</div>
			</div>
		</div>
	</div>

	<div style="display: none;"><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /></div>
	<div style="display: none;" align="center"><bean:write name="studentRegistrationForm" property="version"/></div>
	
</html:form>
</body>
</sakai:html>		
