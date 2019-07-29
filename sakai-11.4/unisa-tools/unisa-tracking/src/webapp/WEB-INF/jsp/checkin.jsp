<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
<fmt:setBundle basename="za.ac.unisa.lms.tools.tracking.ApplicationResources"/>
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
    <meta content='maximum-scale=1.0, initial-scale=1.0, width=device-width' name='viewport'>
    
	<title>Assignment Tracking</title>

	<!-- Bootstrap core CSS -->
	<link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value='/resources/css/bootstrap-theme.css' />" rel="stylesheet"  type="text/css" />

	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
    
    <!-- Unisa Custom styles --> 
    <link href="<c:url value="/resources/css/global.css" />" rel="stylesheet"  type="text/css" />
	
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.cookie.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	
	 <!-- Browser Version -->
 	<script type="text/javascript" src="<c:url value='/resources/js/ua-parser.js' />"></script>
 	
 	<!-- Unisa CheckIN -->
 	<script type="text/javascript" src="<c:url value='/resources/js/globalIN.js' />"></script> 
	
</head>
<body>

<html:form action="/tracking">
		<input type='hidden' id='netID' name='netID' value='<bean:write name="trackingForm" property="novelUserId"/>' />
		<input type='hidden' id='macCode' name='macCode' value='<bean:write name="trackingForm" property="macCode"/>' />
		<input type='hidden' id='isLSSClient' name='isLSSClient' value='<bean:write name="trackingForm" property="lssClient"/>' />
	
<!-- showValues Debug -->
<div id="results"></div>
	
<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
</p>
	
<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
	
<div class="container">

	<!--  Consignment Lists -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="table-responsive">
			<table style="width:100%">
			<tbody id="titleTextImgCon">
				<tr>
					<td bgcolor="lightgray">&nbsp;<fmt:message key="tracking.checkinorout.Consignment.title"/></td>
					<td bgcolor="lightgray" align="right"><img id="imageConPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageConMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png' />"></td>
				</tr>
			</tbody>
			<tbody id="contentDivImgCon">
				<tr>
					<td colspan="2" style="height:5px;"></td>
				</tr><tr>
					<td><fmt:message key="consignment.list"/>&nbsp;&nbsp;</td>
					<td align="right"> 
						<html:text property="enteredConsignmentNumber" size="10" maxlength="10" ></html:text>
						<html:button property="addConsignment" style="width: 50px; height: 80px" onclick="validateConsignmentList();"><fmt:message key="button.addCon" /></html:button>
					</td>		
				</tr>
				
				<!--  Shipping Lists Entered by User -->
				<tr>
					<td colspan="2" style="height:5px;"></td>
				</tr><tr>
					<td colspan="2">
						<table style="width:100%">
							<tr>
								<td style="width:35%"></td>
								<td style="width:65%">
										<div id="div_consignments"></div>
								</td>
							</tr>
			       		</table>
			       	</td>
			    </tr>
			</tbody>
			</table>
			</div>
		</div>
	</div>
	

	<!-- Dockets -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="table-responsive">
			<table style="width:100%">	
				<tbody id="titleTextImgDct">
					<tr>
						<td bgcolor="lightgray">&nbsp;<fmt:message key="tracking.checkinorout.Dockets.title"/></td>
						<td bgcolor="lightgray" align="right"><img id="imageDctPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageDctMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png' />"></td>
					</tr>
				</tbody>
				<tbody id="contentDivImgDct">
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td><fmt:message key="docket.number"/></td>
						<td align="right"><html:text property="docketNumber" size="10" maxlength="10" ></html:text> 
							<html:button property="addDocket" style="width: 50px; height: 80px" onclick="validateDocketNumber();"><fmt:message key="button.addDct"  /></html:button> 
						</td>
					</tr>
	
					<!-- List Unique Dockets Added -->		
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td colspan="2">
							<table style="width:100%">
								<tr>
									<td style="width:50%"></td>
									<td style="width:50%">
										<div id="div_dockets"></div>
									</td>
								</tr>
			           		</table>
			           	</td>
			        </tr>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	
	<!-- Unique Assignments -->
	<div class="panel panel-default">
	  	<div class="panel-body" style="padding-top:5px !important; padding-bottom:5px !important">
		  	<div class="table-responsive">
			<table style="width:100%">
				<tbody id="titleTextImgAss">
					<tr>
						<td bgcolor="lightgray">&nbsp;<fmt:message key="tracking.checkinorout.Single.title"/></td>
						<td bgcolor="lightgray" align="right"><img id="imageAssPlus" style="display:none" alt="Expand" src="<c:url value='/resources/images/plus-new_small.png' />"><img id="imageAssMinus" alt="Collapse" src="<c:url value='/resources/images/minus-new_small.png' />"></td>
					</tr>
				</tbody>
				<tbody id="contentDivImgAss">
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td valign="top">
							<table style="width:100%">
								<tr><td>&nbsp;</td></tr>
								<tr><td><fmt:message key="add.singleassignment"/>&nbsp;</td></tr>
							</table>
						</td>
						<td valign="top">
							<table style="width:100%">
								<tr>
									<td style="width:50%" align="center"><fmt:message key="pers.studno"/></td>
									<td style="width:50%" align="center"><fmt:message key="pers.uniqueassignmentnr"/></td>
								</tr><tr>	
									<td align="center"><html:text property="studNo" size="20" maxlength="10" ></html:text></td>
									<td align="right"><html:text property="uniqueAssignmentNr" size="20" maxlength="10" ></html:text>
										<html:button property="addAssignment" style="width: 50px; height: 80px" onclick="validateStudentUniqueNumber();"><fmt:message key="button.addAss" /></html:button>
									</td>		
								</tr>			        
							</table>
						</td>
					</tr>
					<!-- List Unique Assignments Added -->	
					<tr>
						<td colspan="2" style="height:5px;"></td>
					</tr><tr>
						<td colspan="2">
							<table style="width:100%">
								<tr>
									<td style="width:34%"></td>
									<td style="width:66%">
										<div id="div_assignments"></div>
									</td>
								</tr>
			           		</table>
						</td>
					</tr>
				</tbody>
			</table>
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
							<html:button property="bookIN" style="width: 100px; height: 80px" onclick="frmSubmitBookIN();"><fmt:message key="button.in" /></html:button>&nbsp;
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
</body>
<script>

		//Parent&Sibling Check/unCheck for Lists
	    $(function() {
	        // Click is better than change because of IE.
	        $('body').on('change', 'input[type="checkbox"]', function() {
	        	//alert("Checked");
	            var checked = $(this).prop("checked"),
	                container = $(this).parent(),
	                siblings = container.siblings();

	            container.find('input[type="checkbox"]').prop({
	                indeterminate: false,
	                checked: checked
	            });

	            function checkSiblings(el) {
	                var parent = el.parent().parent(),
	                    all = true;

	                el.siblings().each(function() {
	                    return all = ($(this).children('input[type="checkbox"]').prop("checked") === checked);
	                });

	                if (all && checked) {
	                    parent.children('input[type="checkbox"]').prop({
	                        indeterminate: false,
	                        checked: checked
	                    });
	                    checkSiblings(parent);
	                } else if (all && !checked) {
	                    parent.children('input[type="checkbox"]').prop("checked", checked);
	                    parent.children('input[type="checkbox"]').prop("indeterminate", (parent.find('input[type="checkbox"]:checked').length > 0));
	                    checkSiblings(parent);
	                } else {
	                    el.parents("li").children('input[type="checkbox"]').prop({
	                        indeterminate: true,
	                        checked: false
	                    });
	                }
	            }

	            checkSiblings(container);
	        });
	    });
		
</script>
</sakai:html>