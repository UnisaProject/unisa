<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
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
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content='maximum-scale=1.0, initial-scale=1.0, width=device-width' name='viewport'>
    <meta name="description" content="">
    <meta name="author" content="">

	<title>Assignment Tracking</title>

	<!-- Bootstrap core CSS -->
	<link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"  type="text/css" />	
	<!-- Bootstrap theme -->
    <link href="<c:url value='/resources/css/bootstrap-theme.css' />" rel="stylesheet"  type="text/css" />
	<!-- jQuery modal styles -->
    <link href="<c:url value='/resources/css/jquery-ui.css' />" rel="stylesheet"  type="text/css" />
    <!-- Unisa Custom styles --> 
    <link href="<c:url value='/resources/css/global.css' />" rel="stylesheet"  type="text/css" />
    <!--  Date picker styles -->
    <link href="<c:url value='/resources/css/daterangepicker.css' />" rel="stylesheet"  type="text/css" />

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
    	input.wide {
    		display:block; 
    		width:200px;
    		padding: 5px 10px;
    		border-width:1;
    	}
		

	</style>
	
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.12.4.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.blockUI.js' />"></script> 
	<script type="text/javascript" src="<c:url value='/resources/js/moment.js' />"></script> 
	
	 <!-- Browser Version -->
 	<script type="text/javascript" src="<c:url value='/resources/js/ua-parser.js' />"></script>   

	<!-- Include Date Range Picker -->
	<script type="text/javascript" src="<c:url value='/resources/js/daterangepicker.js' />"></script> 
	
	<script type="text/javascript">

	 	$(document).ready(function(){
	 		var onToday = $("#onToday").val();
	 		$('#dateStart').val(onToday);
	        $('#dateEnd').val(onToday);
	 	});

	    function setAction() {
			document.trackingForm.action = 'tracking.do?act=search';
			document.trackingForm.submit();
		}		
	       
		function frmSubmitSearch(){
			//alert("frmSubmitSearch");
			$("#div_pdfFiles").empty();
			var content = "";
			var contentPDFSub = "";
			//Get Date range
			var dateStart = $('#dateStart').val();
			var dateEnd = $('#dateEnd').val() 
			//alert("Dates="+dateStart+" - "+dateEnd);
			
			var url = "tracking.do?act=findReportPDF&dateStart="+dateStart+"&dateEnd="+dateEnd;
			var contentErr = false;
			/* Get input values from form */
		    var formValues = jQuery(trackingForm).serializeArray();

		    /* Because serializeArray() ignores unset checkboxes and radio buttons: */
		    formValues = formValues.concat(
		            jQuery('trackingForm input[type=checkbox]:not(:checked)').map(
		                    function() {
		                        return {"name": this.name, "value": this.value}
		                    }).get()
		    );
						
			$.blockUI({ message: "<b><img src='<c:url value='/resources/images/ajax-loader.gif' />' alt=' * ' /> <br>Retrieving PDF Files</b>" });
			$.getJSON(url,  formValues, function(data) {
				cache : false,
				$.each(data, function(baseKey, baseVal) {
					//alert("baseKey="+baseKey);
					if (baseKey === "Error"){
						$.unblockUI();
						showError("Error", baseVal);
						contentErr = true;
						return false;
					}else if (baseKey === "Info"){
						$.unblockUI();
						showError("Info", baseVal);
						contentErr = true;
						return false;
					}else{
						if (baseKey === "PFDFiles"){	
							//alert("PFDFiles="+baseVal);
							var countPDF = 1;
							$.each(baseVal, function(pdfKey, pdfVal) {
								//alert("PDF ID="+pdfKey);
								if (countPDF % 2 === 0) {
									pdfLiCol = "#DDDDDD";
					        	}else{
					        		pdfLiCol = "#EEEEEE";
					        	}
								contentPDFSub += "<li style='background:"+pdfLiCol+"'><span><a href='tracking.do?act=getPDFtoPrint&pdfFileName="+pdfVal+"' style='text-decoration:none; color:#000;'>"+pdfVal+"</a></span></li>";
								countPDF++;
							});
							content = "<ul style='list-style-type:none'>"+contentPDFSub+"</ul>";
						}
					}
				});
				if (contentErr === false){
					//alert("PDF Files Retrieved - No Error");
					$("#div_pdfFiles").empty();
					$("#div_pdfFiles").append(content);
					$.unblockUI();
				}
			});
		}
	    
    	function showError(errorTitle, errorText) {

    	    // show the actual error modal
    	    $.unblockUI();		    
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
    	
		function frmSubmitSameWin(){
			document.trackingForm.target = '';
		}
	</script>
</head>
	
	<html:form action="/tracking" >
	<INPUT type='HIDDEN' id="onToday" name="onToday" value="<%=(new java.util.Date()).getTime()%>" />
	<input type="hidden" name="dateStart" id="dateStart" value="">
	<input type="hidden" name="dateEnd" id="dateEnd" value="">
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>
   
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
<div class="container">
   <br style="height:5px;">
   <div class="row row-eq-height ">
		<div class='col-md-12 col-sm-12 col-xs-12'>
	    	<div class="panel panel-default">
	        	<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="print.header"/></h3>
				</div>
			  	<div class="panel-body">
			  		<div class="row">
						<div class="col-xs-4">
							<input type="radio" name="printRadio" value="IN"/>&nbsp;<fmt:message key="print.info.in"/><br/>
							<input type="radio" name="printRadio" value="OUT"/>&nbsp;<fmt:message key="print.info.out"/>
						</div>
						<div class="col-xs-8">
							<div class="row">
								<div class="col-xs-4">
									<input type="checkbox" id="printShipChk" name="printShipChk" value="OK"/>&nbsp;<fmt:message key="print.info.shiplist"/>
								</div>
								<div class="col-xs-8">
									<input class="wide" type="text" id="printStringShip" name="printStringShip" />
								</div>
							</div>
							<div class="row">
								<div class="col-xs-4">
									<input type="checkbox" id="printUserChk" name="printUserChk" value="OK"/>&nbsp;<fmt:message key="print.info.user" />
								</div>
								<div class="col-xs-8">
									<input class="wide" type="text" id="printStringUser" name="printStringUser" />
								</div>
							</div>
						</div>
					</div>
					<br style="height:5px;">
					<div class="row">
						<div id="reportrange" class="col-xs-12" style="background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 100%">
						    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>&nbsp;
						    <span></span> <b class="caret"></b>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
		
	<!--  PDF File List -->
	<div class="row row-eq-height ">
		<div class='col-md-12 col-sm-12 col-xs-12'>
	    	<div class="panel panel-default">
			  	<div class="panel-body">
					<div id="div_pdfFiles"></div>
				</div>
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
								<html:button property="search" style="width: 100px; height: 80px" onclick="frmSubmitSearch();"><fmt:message key="button.searchDetail" /></html:button>&nbsp;
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
<div style="display: none;"><!-- Default PlaceHolder -->
	<input type="radio" name="printRadio" value="Unisa" checked />
</div>
			
</html:form>

<script type="text/javascript">
	$(function() {
	
	    var start = moment().subtract(29, 'days');
	    var end = moment();
	
	    function cb(start, end) {
	        $('#reportrange span').html(start.format('D MMMM, YYYY') + ' - ' + end.format('D MMMM, YYYY'));
	 		$('#dateStart').val(start.format('YYYY/MM/D'));
	        $('#dateEnd').val(end.format('YYYY/MM/D'));
	    }
	
	    $('#reportrange').daterangepicker({
	        startDate: start,
	        endDate: end,
	        ranges: {
	           'Today': [moment(), moment()],
	           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	           'This Month': [moment().startOf('month'), moment().endOf('month')],
	           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	        }
	    }, cb);
	
	    cb(start, end);
	    
	});
</script>

</sakai:html>