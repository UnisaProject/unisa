<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="za.ac.unisa.lms.tools.studentregistration.forms.StudentRegistrationForm"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Enumeration"%>
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
		    border-collapse: separate;
		}
		td { 
		    padding: 2px;
		}
	</style>
	
	<script type="text/javascript">
	
	function disableBackButton(){window.history.forward()} 
	disableBackButton(); 
	window.onload=disableBackButton(); 
	window.onpageshow=function(evt) { if(evt.persisted) disableBackButton() } 
	window.onunload=function() { void(0) }  
		
		/**Call when document is ready**/
		$(document).ready(function() {			
			
			$('form,input,select,textarea').attr("autocomplete", "off");
			
			var docMsg = $('#docMsg').val();
			if(docMsg != "" && docMsg != "null" && docMsg != null && docMsg != NaN){
				docMsg = docMsg.replace(/newLine /g,"\n");
				showError("Info", docMsg);
			}
			
			//Lock First Choice for SLP Students
			var isSTUSLP = $("#isSTUSLP").val();
			var isSTUAPQ = $("#isSTUAPQ").val();
			//alert("selectStudy - SLP - isSTUSLP=" + isSTUSLP+", isSTUAPQ=" + isSTUAPQ);
			if (isSTUSLP == "true" && isSTUAPQ == "true"){
				//alert("selectStudy - SLP - YES STUAPQ - isSTUSLP=" + isSTUSLP+", isSTUAPQ=" + isSTUAPQ);

				//Locked Qualification Dropdown 1 for SLP
				$("select[name='selCollegeCode1'] option:not(:selected)").prop("disabled", true);
				$("select[name='selCategoryCode1'] option:not(:selected)").prop("disabled", true);
				$("select[name='selQualCode1'] option:not(:selected)").prop("disabled", true);
				$("select[name='selSpecCode1'] option:not(:selected)").prop("disabled", true);

				//Change locked Dropdown Background colour
				$("select[name='selCollegeCode1']").css("background-color","#FFFEEE");
				$("select[name='selCategoryCode1']").css("background-color","#FFFEEE");
				$("select[name='selQualCode1']").css("background-color","#FFFEEE");
				$("select[name='selSpecCode1']").css("background-color","#FFFEEE");

				$("#second").show();
				$("#secondCol").hide();
			    $("#secondCat").show();
			    $("#secondQual").show();
			    $("#secondSpec").show();
			    
			    /**Retrieve saved colleges**/
				populateSelectedCollege1();				
				populateSelectedCollege2();
				
			    //alert("isSTUSLP="+isSTUSLP+", isSTUAPQ="+isSTUAPQ+" - Call populateSelectedCategory1");
				//populateSelectedCategory1();
				//alert("isSTUSLP="+isSTUSLP+", isSTUAPQ="+isSTUAPQ+" - Call populateSelectedCategory2");
				//populateSelectedCategory2();
			}else if (isSTUSLP == "true" && isSTUAPQ != "true"){
				//alert("selectStudy - SLP - NO STUAPQ - isSTUSLP=" + isSTUSLP+", isSTUAPQ=" + isSTUAPQ);
				$("#second").hide();
				$("#secondCol").hide();
			    $("#secondCat").hide();
			    $("#secondQual").hide();
			    $("#secondSpec").hide();
				
			    populateSelectedCollege1();	
			}
			
			//Johanet 2018July BRD hide second choice for RPL
			var aspGRD = $("#selectHEMain").val();
				if (aspGRD === "RPL"){
					$("#second").hide();
					$("#secondCol").hide();
				    $("#secondCat").hide();
				    $("#secondQual").hide();
				    $("#secondSpec").hide();
				}
			//end RPL
			
			var catSelect = $("#catSelect").val();
			if(catSelect == "MD"){
				//alert("selectStudy - catSelect - MD - catSelect=" + catSelect);
				$("#second").hide();
				$("#secondCol").hide();
				$("#secondCat").hide();
				$("#secondQual").hide();
				$("#secondSpec").hide();
				
				populateSelectedCollege1();	
			}else if (catSelect != "MD" && catSelect != "SLP"){  //LoginSelectMain
				//alert("selectStudyNew - catSelect - NOT MD or SLP - catSelect=" + catSelect);
				
				/**Retrieve saved colleges**/
				populateSelectedCollege1();				
				populateSelectedCollege2();
				
				/**Retrieve saved categories**/				
				//populateSelectedCategory1();
				//populateSelectedCategory2();
			}	
			
			//Change College1
			$("select[name='selCollegeCode1']").change(function(){
				$("select[name='selCategoryCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selCategoryCode1']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
				var stuNr = $("#stuNr").val();
				var url = 'applyForStudentNumber.do?act=populateCategories&id='+stuNr;				
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByCode);
					$("select[name='selCategoryCode1']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select category</option>');
					var count = 0;
					$.each(data, function(i, item) {						
					    	items.push('<option value="' + data[i].code + '">' + data[i].desc + '</option>');
					    	count++;
					});
					if(count==0){
					     //Show msg
						 showError("Note","The application period for the category in this College is closed");
						 $("select[name='selQualCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
						 $("select[name='selQualCode1']").html('<option value="0">Select qualification</option>'); 
						 $("select[name='selQualCode1']").empty();
						 $("select[name='selQualCode1']").html('<option value="0">Select qualification</option>'); 
						 $("select[name='selSpecCode1']").empty();
						 $("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>');
					}else{
						$("select[name='selCategoryCode1']").html(items);
						$("select[name='selQualCode1']").empty();
						$("select[name='selQualCode1']").html('<option value="0">Select qualification</option>'); 
						$("select[name='selSpecCode1']").empty();
						$("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>');
					}
					
				});	
			});
			
			//Change College2
			$("select[name='selCollegeCode2']").change(function(){
				$("select[name='selCategoryCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selCategoryCode2']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
				var stuNr = $("#stuNr").val();
				var url = 'applyForStudentNumber.do?act=populateCategories&id='+stuNr;				
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByCode);
					$("select[name='selCategoryCode2']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select category</option>');
					var count = 0;
					$.each(data, function(i, item) {						
					    	items.push('<option value="' + data[i].code + '">' + data[i].desc + '</option>');
					    	count++;
					});
					if(count==0){
					     //Show msg
						 showError("Note","The application period for this category is closed");
						 $("select[name='selQualCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
						 $("select[name='selQualCode2']").html('<option value="0">Select qualification</option>'); 
						 $("select[name='selQualCode2']").empty();
						 $("select[name='selQualCode2']").html('<option value="0">Select qualification</option>'); 
						 $("select[name='selSpecCode2']").empty();
						 $("select[name='selSpecCode2']").html('<option value="0">Select specialization</option>');
					}else{
						$("select[name='selCategoryCode2']").html(items);
						$("select[name='selQualCode2']").empty();
						$("select[name='selQualCode2']").html('<option value="0">Select qualification</option>'); 
						$("select[name='selSpecCode2']").empty();
						$("select[name='selSpecCode2']").html('<option value="0">Select specialization</option>');
					}
					
				});	
			});
			
		    //Change Category
			$("select[name='selCategoryCode1']").change(function(){
				
				var category = $(this).val();
				//Test if Currently in Grade 12
				var aspGRD = $("#selectHEMain").val();
				if (aspGRD === "G12"){
					if (category === "03" || category === "05" || category === "06" || category === "09" || category === "10" || category === "99"){
						showError("Note", "You are currently in Grade 12 and you do not comply with the admission requirements for postgraduate studies on this level.");			
					}					
				}
				var categoryText = $("select[name='selCategoryCode1']").find("option:selected").text();			
				
				//Hide 2nd set of dropbox if category contains 'Master' or 'Doctor'
				if($("#catSelect").val() == "MD" || categoryText.toLowerCase().indexOf("master") >= 0 || categoryText.toLowerCase().indexOf("doctor")>=0 || categoryText.toLowerCase().indexOf("magister")>=0){
					$("#second").hide();
					$("#secondCat").hide();
					$("#secondQual").hide();
					$("#secondSpec").hide();
				}
				
				var college = $("select[name='selCollegeCode1']").find("option:selected").val();
				$("select[name='selQualCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selQualCode1']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
				
				//Johanet - 20180823 add student nr to prevent caching if students do not close browser
				var stuNr = $("#stuNr").val();
				var url = 'applyForStudentNumber.do?act=populateQualifications&selCategoryCode='+category+'&selCollegeCode='+college+'&id='+stuNr;
				var aspGRD = $("#selectHEMain").val();  //Johanet 20190723	
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByDesc);
					$("select[name='selQualCode1']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select qualification</option>');
					var count = 0;
					$.each(data, function(i, item) {
						//20190723 Johanet BRS 2020 requirement 5 - do not load 0216X for grade 12 student
						if (aspGRD === "G12" && (data[i].code === "0216X" || data[i].code === "90148")){
							 //do nothing grade 12 student do not qualify for qualification 0216X
						}else{
				    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
				    	count++;
						}
						//alert("Count: " + count);
					});
					if(count==0){
					     //Show msg
						 showError("Note","The application period for the category in this College is closed");
						 $("select[name='selQualCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
						 $("select[name='selQualCode1']").html('<option value="0">Select qualification</option>'); 
						 $("select[name='selSpecCode1']").empty();
						 $("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>');
					}else{
						$("select[name='selQualCode1']").html(items);
						$("select[name='selSpecCode1']").empty();
						$("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>');
					}
				});
			});
			
		
		    
			$("select[name='selCategoryCode2']").change(function(){
				
				
				var category = $(this).val();
				//Test if Currently in Grade 12
				var aspGRD = $("#selectHEMain").val();
				if (aspGRD === "G12"){
					if (category === "03" || category === "05" || category === "06" || category === "09" || category === "10" || category === "99"){		
						showError("Note", "You are currently in Grade 12 and you do not comply with the admission requirements for postgraduate studies on this level.");			
					}
				}
				var categoryText = $("select[name='selCategoryCode2']").find("option:selected").text();			
				
				var college = $("select[name='selCollegeCode2']").find("option:selected").val();
				$("select[name='selQualCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selQualCode2']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
				
				//Johanet - 20180823 add student nr to prevent caching if students do not close browser
				var stuNr = $("#stuNr").val();
				var url = 'applyForStudentNumber.do?act=populateQualifications&selCategoryCode='+category+'&selCollegeCode='+college+'&id='+stuNr;				
				var aspGRD = $("#selectHEMain").val();  //Johanet 20190723	
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByDesc);
					$("select[name='selQualCode2']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select qualification</option>');
					var count = 0;
					$.each(data, function(i, item) {
						//20190723 Johanet BRS 2020 requirement 5 - do not load 0216X for grade 12 student
						if (aspGRD === "G12" && (data[i].code === "0216X" || data[i].code === "90148")){
							 //do nothing grade 12 student do not qualify for qualification 0216X
						}else{
					    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
					    	count++;
						}	
						//alert("Count: " + count);
					});
					if(count==0){
						//Show msg
						showError("Note","The application period for the category in this College is closed");
						$("select[name='selQualCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
						$("select[name='selQualCode2']").html('<option value="0">Select qualification</option>'); 
						$("select[name='selSpecCode2']").empty();
						$("select[name='selSpecCode2']").html('<option value="0">Select specialization</option>');
					}else{
						$("select[name='selQualCode2']").html(items);
						$("select[name='selSpecCode2']").empty();
						$("select[name='selSpecCode2']").html('<option value="0">Select specialization</option>');
					}
				});
				
			});
		
			//Change Qualification
			$("select[name='selQualCode1']").change(function(){
				
				var pattern = /oversubscribed/;
				var qualDesc = $("select[name='selQualCode1']").find("option:selected").text();
				
				if(pattern.test(qualDesc)){
						showError("Note", "This qualification has reached the number of applicaitons allowed and no additional applications can be submitted.");	
						 $("select[name='selSpecCode1']").empty();
						 $("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>');
				}else{
					$("select[name='selSpecCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
					$("select[name='selSpecCode1']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
					
					var qual = $(this).val();				
					var category1 = $("select[name='selCategoryCode1']").find("option:selected").val();
					var url = 'applyForStudentNumber.do?act=populateSpecializations&selCategoryCode='+category1+'&selQualCode='+qual;
					$.ajaxSetup( { "async": false } );
					$.getJSON(url, function(data) {
						data.sort(SortByCode);
						$("select[name='selSpecCode1']").empty(); //Remove all previous options again (Remove temp option above)
						var items = [];
						items.push('<option value="0">Select specialization</option>');
						var count = 0;
						$.each(data, function(i, item) {
					    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
					    	count++;
						});
						if(count==0){
						    $("select[name='selSpecCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
							$("select[name='selSpecCode1']").html('<option value="0">(N/A) - Not Applicable</option>'); 
						}else{
							$("select[name='selSpecCode1']").html(items);
						}
					});		
				}	
				
			});
			
			$("select[name='selQualCode2']").change(function(){
				
				var pattern = /oversubscribed/;
				var qualDesc = $("select[name='selQualCode1']").find("option:selected").text();
				
				if(pattern.test(qualDesc)){
						showError("Note", "This qualification has reached the number of applicaitons allowed and no additional applications can be submitted.");	
						 $("select[name='selSpecCode2']").empty();
						 $("select[name='selSpecCode2']").html('<option value="0">Select specialization</option>');
				}else{
					$("select[name='selSpecCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
					$("select[name='selSpecCode2']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
					
					var qual = $(this).val();					
					var category2 = $("select[name='selCategoryCode2']").find("option:selected").val();
					var url = 'applyForStudentNumber.do?act=populateSpecializations&selCategoryCode='+category2+'&selQualCode='+qual;
					$.ajaxSetup( { "async": false } );
					$.getJSON(url, function(data) {
						data.sort(SortByCode);
						$("select[name='selSpecCode2']").empty(); //Remove all previous options again (Remove temp option above)
						var items = [];
						items.push('<option value="0">Select specialization</option>');
						var count = 0;
						$.each(data, function(i, item) {
					    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
					    	count++;
						});
						if(count==0){
						    $("select[name='selSpecCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
							$("select[name='selSpecCode2']").html('<option value="0">(N/A) - Not Applicable</option>'); 
						}else{
							$("select[name='selSpecCode2']").html(items);
						}
					});
				}
			});
			
			$("select[name='selSpecCode1']").change(function(){
				var pattern = /oversubscribed/;
				var specDesc = $("select[name='selSpecCode1']").find("option:selected").text();
				
				if(pattern.test(specDesc)){
						showError("Note", "This specialisation has reached the number of applicaitons allowed and no additional applications can be submitted.");				  			
				}
				
			});
			
			$("select[name='selSpecCode2']").change(function(){
				var pattern = /oversubscribed/;
				var specDesc = $("select[name='selSpecCode2']").find("option:selected").text();
				
				if(pattern.test(specDesc)){
						showError("Note", "This specialisation has reached the number of applicaitons allowed and no additional applications can be submitted.");				  			
				}
				
			});
				
		});
		 
		//Click Continue button
		//Change Category
		function validate(){
			
			var checkError = 0;	
			
			var college1 = $("select[name='selCollegeCode1']").find("option:selected").val();
			var category1 = $("select[name='selCategoryCode1']").find("option:selected").val();
			var qual1 =     $("select[name='selQualCode1']").find("option:selected").val();
			var qual1OptionDesc = $("select[name='selQualCode1']").find("option:selected").text();  //JohanetTest
			$('input[id=qual1OptionDesc]').val(qual1OptionDesc);
			//document.getElementById("qual1Desc").value=qual1Desc;
			
		    var spec1 =     $("select[name='selSpecCode1']").find("option:selected").val();		   
		    
		    var college2 = $("select[name='selCollegeCode2']").find("option:selected").val();
		    var category2 = $("select[name='selCategoryCode2']").find("option:selected").val();
			var qual2 =     $("select[name='selQualCode2']").find("option:selected").val();
			var qual2OptionDesc = $("select[name='selQualCode2']").find("option:selected").text();  //JohanetTest
			$('input[id=qual2OptionDesc]').val(qual2OptionDesc);
		    var spec2 =     $("select[name='selSpecCode2']").find("option:selected").val();
		    
		    if(jQuery.trim(college1) == "0"){
		    	showError("Note","Please select a college from the drop list provided.");
		    	checkError = 1;
		    	return false;
		    }		    
		    if(jQuery.trim(category1) == "0"){
		    	showError("Note","Please select a category from the drop list provided.");
		    	checkError = 1;
		    	return false;
		    }
		    if(jQuery.trim(qual1) == "0"){
		    	showError("Note","Please select a qualification from the drop list provided.");
		    	checkError = 1;
		    	return false;
			}else if (typeof(jQuery.trim(qual1)) == "undefined" || jQuery.trim(qual1) == "undefined"){
				showError("Note","The qualification is invalid. Please try again or contact the application office at applications@unisa.ac.za");
				checkError = 1;
				return false;
		    }
		    
		    if(jQuery.trim(spec1) == "0"  && $("select[name='selSpecCode1'] option").length >1){
		    	showError("Note","Please select a specialization from the drop list provided.");
		    	checkError = 1;
		    	return false;
		    }else if (typeof(jQuery.trim(spec1)) == "undefined" || jQuery.trim(spec1) == "undefined"){
		    	showError("Note","The specialization is invalid. Please try again or contact the application office at applications@unisa.ac.za");
		    	checkError = 1;
		    	return false;
		    }
		    
		    if(jQuery.trim(college2) != "0"){
		    	if(jQuery.trim(category2) == "0"){
		    		showError("Note","Please select a category from the drop list provided.");
			    	checkError = 1;
			    	return false;
		    	}		    	
		    }	
		    if (jQuery.trim(category2) != "0"  ){
			    if(jQuery.trim(qual2) == "0"  ){
			    	showError("Note","Please select a qualification from the drop list provided.");
			    	checkError = 1;
			    	return false;
				}else if (typeof(jQuery.trim(qual2)) == "undefined" || jQuery.trim(qual2) == "undefined"){
					showError("Note","The qualification is invalid. Please try again or contact the application office at applications@unisa.ac.za");
					checkError = 1;
					return false;
			    }
			    
			    if(jQuery.trim(spec2) == "0"  && $("select[name='selSpecCode2'] option").length >1 ){
			    	showError("Note","Please select a specialization from the drop list provided.");
			    	checkError = 1;
			    	return false;
			    }else if (typeof(jQuery.trim(spec2)) == "undefined" || jQuery.trim(spec2) == "undefined"){
			    	showError("Note","The specialization is invalid. Please try again or contact the application office at applications@unisa.ac.za");
			    	checkError = 1;
			    	return false;
			    }
		    }
		    
// 		   if((jQuery.trim(qual1) == "02623" && jQuery.trim(spec1) == "NEW") ||
// 		    		(jQuery.trim(qual1) == "02631" && jQuery.trim(spec1) == "FDP") ||
// 		    		(jQuery.trim(qual1) == "03980" && jQuery.trim(spec1) == "NEW") ||
// 		    		(jQuery.trim(qual2) == "02623" && jQuery.trim(spec2) == "NEW") ||
// 		    		(jQuery.trim(qual2) == "02631" && jQuery.trim(spec2) == "FDP") ||
// 		    		(jQuery.trim(qual2) == "03980" && jQuery.trim(spec2) == "NEW")){
// 		    	var newLine = "\r\n";
// 		    	var msg = "Please note that you should have completed an undergraduate degree or National diploma to comply with admission requirements of the qualification you have selected.";
// 		    	msg += newLine;
// 		    	msg += newLine;
// 		    	msg += "If you selected either of the PGCEs 02623 NEW or 02631 FDP as qualifications, please note that you must have passed 2 official languages (English 1 and Afrikaans 1 or African languages 1) in the degree or diploma that you completed.";
// 		    	msg += newLine;
// 		    	msg += newLine;
// 		    	msg += "NB: If you do not comply with any of the admission requirements for the qualification you selected, you will not be admitted to the PGCE.";
// 		        alert(msg);
// 		    }
			//Johanet 20200528 display text for all postgraduate quals
		    if(jQuery.trim(category1) == "03" || jQuery.trim(category1) == "05" || jQuery.trim(category1) == "06" || jQuery.trim(category1) == "09" || jQuery.trim(category1) == "10" ||
		    		jQuery.trim(category2) == "03" || jQuery.trim(category2) == "05" || jQuery.trim(category2) == "06" || jQuery.trim(category2) == "09" || jQuery.trim(category2) == "10"){
		    	var newLine = "\r\n";
		    	var msg = "Please note that you should have completed an undergraduate degree or National diploma to comply with admission requirements of the qualification you have selected.";
		    	msg += newLine;
		    	msg += newLine;		    	
		    	msg += "NB: If you do not comply with any of the admission requirements for the qualification you selected, you will not be admitted.";
		        alert(msg);
			}		   
		    //Johanet 20190808 - add additional test for grade 12
		    var aspGRD = $("#selectHEMain").val();
			if (aspGRD === "G12"){
				if(jQuery.trim(category1) == "03" || jQuery.trim(category1) == "05" || jQuery.trim(category1) == "06" || jQuery.trim(category1) == "09" || jQuery.trim(category1) == "10" || jQuery.trim(category1) == "99"){
		  			showError("Note", "You are currently in Grade 12 and you do not comply with the admission requirements for postgraduate studies on this level.");	
		  			checkError = 1;
			    	return false;
				}
		    	if(jQuery.trim(category2) == "03" || jQuery.trim(category2) == "05" || jQuery.trim(category2) == "06" || jQuery.trim(category2) == "09" || jQuery.trim(category2) == "10" || jQuery.trim(category2) == "99"){
		  			showError("Note", "You are currently in Grade 12 and you do not comply with the admission requirements for postgraduate studies on this level.");	
		  			checkError = 1;
			    	return false;
				}
			}
			
			
			if (qual1OptionDesc.indexOf('You may not qualify') !== -1 || qual2OptionDesc.indexOf('You may not qualify') !== -1){
				var url = 'applyForStudentNumber.do?act=verifyStuQualify&qual1OptionDesc='+qual1OptionDesc+'&qual2OptionDesc='+qual2OptionDesc;
				var verifyError ="";
				
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					$.each(data, function(i, item) {
						if(data[i].error !== ""){
							verifyError = data[i].error;						
						}
					});
					if (verifyError !== ""){
						showError("Note", verifyError);
				    	checkError = 1;
				    	return false;
					}
					doSubmit('Continue');
				});		
			}else{				
				doSubmit('Continue');
			}	
		    
		}
		
		//This will sort your array
		function SortByCode(a, b){
		  	var aCode = a.code.toLowerCase();
		  	var bCode = b.code.toLowerCase(); 
		  	return ((aCode < bCode) ? -1 : ((aCode > bCode) ? 1 : 0));
		}
		
		function SortByDesc(a, b){
			var aName = a.desc.toLowerCase();
			var bName = b.desc.toLowerCase(); 
			return ((aName < bName) ? -1 : ((aName > bName) ? 1 : 0));
		}		
		
		function populateSelectedCollege1(){			
			var savedCollege1 = $("#savedCollege1").val();			
			$("select[name='selCollegeCode1']").val(savedCollege1).change();		
			populateSelectedCategory1();
		}
		 
		function populateSelectedCollege2(){	
			var savedCollege2 = $("#savedCollege2").val();			
			$("select[name='selCollegeCode2']").val(savedCollege2).change();		
			populateSelectedCategory2();
		}
		
		function populateSelectedCategory1(){			
			$("select[name='selCategoryCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selCategoryCode1']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
			var stuNr = $("#stuNr").val();
			var url = 'applyForStudentNumber.do?act=populateCategories&id='+stuNr;
			var savedCategory1 = $("#savedCategory1").val();
			//alert("populateSelectedCategory1="+savedCategory1);
			$.ajaxSetup( { "async": false } );
			$.getJSON(url, function(data) {
				data.sort(SortByCode);
				$("select[name='selCategoryCode1']").empty(); //Remove all previous options again (Remove temp option above)
				var items = [];
				items.push('<option value="0">Select category</option>');
				$.each(data, function(i, item) {
					if(data[i].code == savedCategory1) {
						//alert("savedCategory1 - sortedKeys: " + data[i].code + ", savedCategory1: " + savedCategory1);
						items.push('<option value="' + data[i].code + '" selected=selected>' + data[i].desc + '</option>');
				    }else{
				    	items.push('<option value="' + data[i].code + '">' + data[i].desc + '</option>');
				    }
				});
				$("select[name='selCategoryCode1']").html(items);
				populateSelectedQual1();
			});
		}
		
		function populateSelectedCategory2(){
			$("select[name='selCategoryCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selCategoryCode2']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
			var stuNr = $("#stuNr").val();
			var url = 'applyForStudentNumber.do?act=populateCategories&id='+stuNr;
			var savedCategory2 = $("#savedCategory2").val();
			$.ajaxSetup( { "async": false } );
			$.getJSON(url, function(data) {
				data.sort(SortByCode);
				$("select[name='selCategoryCode2']").empty(); //Remove all previous options again (Remove temp option above)
				var items = [];
				items.push('<option value="0">Select category</option>');
				$.each(data, function(i, item) {
					if(data[i].code == savedCategory2) {
						//alert("savedCategory2 - sortedKeys: " + data[i].code + ", savedCategory2: " + savedCategory2);
						items.push('<option value="' + data[i].code + '" selected=selected>' + data[i].desc + '</option>');
				    }else{
				    	items.push('<option value="' + data[i].code + '">' + data[i].desc + '</option>');
				    }
				});
				$("select[name='selCategoryCode2']").html(items);
				populateSelectedQual2();
			});
		}
		
		function populateSelectedQual1(){
			$("select[name='selQualCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selQualCode1']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
			var savedCollege1 = $("#savedCollege1").val();
			var savedCategory1 = $("#savedCategory1").val();
			var savedQual1 = $("#savedQual1").val();
			//Johanet - 20180823 add student nr to prevent caching if students do not close browser
			var stuNr = $("#stuNr").val();
			var url = 'applyForStudentNumber.do?act=populateQualifications&selCategoryCode='+savedCategory1+'&selCollegeCode='+savedCollege1+'&id='+stuNr;
			//alert("populateSelectedQual1 - savedCategory1="+savedCategory1+", savedQual1="+savedQual1);
			if ((savedQual1 == null || savedQual1 == "0" || savedQual1 == "" || savedQual1 == "undefined") && 
					(savedCategory1 == null || savedCategory1 == "0" || savedCategory1 == "" || savedCategory1 == "undefined")){
				$("select[name='selQualCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selQualCode1']").html('<option value="0">Select qualification</option>'); 
			}else{
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByDesc);
					$("select[name='selQualCode1']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select qualification</option>');
					var count = 0;
					$.each(data, function(i, item) {
						if(data[i].code == savedQual1) {
							//alert("populateSelectedQual1 - sortedKeys: " + data[i].code + ", savedQual1: " + savedQual1);
							items.push('<option value="' + data[i].code + '" selected=selected>' + data[i].code + " - " + data[i].desc + '</option>');
					    }else{
					    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
					    }
						count++;
					});
					if(count==0){
						 $("select[name='selQualCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
						 $("select[name='selQualCode1']").html('<option value="0">Select qualification</option>'); 
						 $("select[name='selSpecCode1']").empty();
						 $("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>');
					}else{
						$("select[name='selQualCode1']").html(items);
						$("select[name='selSpecCode1']").empty();
						$("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>');
						populateSelectedSpec1();
					}
				});
			}
		}
		
		function populateSelectedQual2(){
			$("select[name='selQualCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selQualCode2']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
			var savedCollege2 = $("#savedCollege2").val();
			var savedCategory2 = $("#savedCategory2").val();
			var savedQual2 = $("#savedQual2").val();
			//Johanet - 20180823 add student nr to prevent caching if students do not close browser
			var stuNr = $("#stuNr").val();			
			var url = 'applyForStudentNumber.do?act=populateQualifications&selCategoryCode='+savedCategory2+'&selCollegeCode='+savedCollege2+'&id='+stuNr;
			
			if ((savedQual2 == null || savedQual2 == "0" || savedQual2 == "" || savedQual2 == "undefined") && (savedCategory2 == null || savedCategory2 == "0" || savedCategory2 == "" || savedCategory2 == "undefined")){
				$("select[name='selQualCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selQualCode2']").html('<option value="0">Select qualification</option>'); 
			}else{
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByDesc);
					$("select[name='selQualCode2']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select qualification</option>');
					var count = 0;
					$.each(data, function(i, item) {
						if(data[i].code == savedQual2) {
							//alert("populateSelectedQual2 - sortedKeys: " + data[i].code + ", savedQual2: " + savedQual2);
							items.push('<option value="' + data[i].code + '" selected=selected>' + data[i].code + " - " + data[i].desc + '</option>');
					    }else{
					    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
					    }
						count++;
					});
					if(count==0){
						 $("select[name='selQualCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
						 $("select[name='selQualCode2']").html('<option value="0"> </option>'); 
						 $("select[name='selSpecCode2']").html('<option value="0"> </option>');
					}else{
						$("select[name='selQualCode2']").html(items);
						$("select[name='selSpecCode2']").html('<option value="0"> </option>');
						populateSelectedSpec2();
					}
				});
			}
		}
		
		function populateSelectedSpec1(){
			$("select[name='selSpecCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selSpecCode1']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
			var savedCategory1 = $("#savedCategory1").val();
			var savedQual1 = $("#savedQual1").val();
			if ((savedQual1 == null || savedQual1 == "0" || savedQual1 == "" || savedQual1 == "undefined") && (savedCategory1 == null || savedCategory1 == "0" || savedCategory1 == "" || savedCategory1 == "undefined")){
				$("select[name='selSpecCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selSpecCode1']").html('<option value="0">Select specialization</option>'); 
			}else{
				var url = 'applyForStudentNumber.do?act=populateSpecializations&selCategoryCode='+savedCategory1+'&selQualCode='+savedQual1;
				var savedSpec1 = $("#savedSpec1").val();
				var isNVT = false;
				if (savedSpec1 == "0" || savedSpec1 == " "){
					isNVT = true;
				}
				//alert("populateSelectedSpec1 - savedSpec1="+savedSpec1+", isNVT1= "+isNVT);
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByCode);
					$("select[name='selSpecCode1']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select specialization</option>');
					var count = 0;
					$.each(data, function(i, item) {
						if(data[i].code == savedSpec1 || (isNVT && data[i].code == "NVT")) {
							//alert("populateSelectedSpec1 - sortedKeys: " + data[i].code + ", savedSpec1: " + savedSpec1);
							items.push('<option value="' + data[i].code + '" selected=selected>' + data[i].code + " - " + data[i].desc + '</option>');
					    }else{
					    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
					    }
						count++;
					});
					if(count==0){
						$("select[name='selSpecCode1']").empty(); //Remove all previous options (Index cleanup for various browsers)
						$("select[name='selSpecCode1']").html('<option value="0">(N/A) - Not Applicable</option>');
					}else{
						$("select[name='selSpecCode1']").html(items);
					}
				});
			}
		}
		
		function populateSelectedSpec2(){
			$("select[name='selSpecCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
			$("select[name='selSpecCode2']").append('<option value="0">Loading....</option>'); //Temp option to show if database retrieval is slow
			var savedCategory2 = $("#savedCategory2").val();
			var savedQual2 = $("#savedQual2").val();
			if ((savedQual2 == null || savedQual2 == "0" || savedQual2 == "" || savedQual2 == "undefined") && (savedCategory2 == null || savedCategory2 == "0" || savedCategory2 == "" || savedCategory2 == "undefined")){
				$("select[name='selSpecCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
				$("select[name='selSpecCode2']").html('<option value="0">Select specialization</option>'); 
			}else{
				var url = 'applyForStudentNumber.do?act=populateSpecializations&selCategoryCode='+savedCategory2+'&selQualCode='+savedQual2;
				var savedSpec2 = $("#savedSpec2").val();
				var isNVT = false;
				if (savedSpec2 == "0" || savedSpec2 == " "){
					isNVT = true;
				}
				//alert("populateSelectedSpec2 - savedSpec2="+savedSpec1+", isNVT2= "+isNVT);
				$.ajaxSetup( { "async": false } );
				$.getJSON(url, function(data) {
					data.sort(SortByCode);
					$("select[name='selSpecCode2']").empty(); //Remove all previous options again (Remove temp option above)
					var items = [];
					items.push('<option value="0">Select specialization</option>');
					var count = 0;
					$.each(data, function(i, item) {
						if(data[i].code == savedSpec2 || (isNVT && data[i].code == "NVT")) {
							//alert("populateSelectedSpec2 - sortedKeys: " + data[i].code + ", savedSpec2: " + savedSpec2);
							items.push('<option value="' + data[i].code + '" selected=selected>' + data[i].code + " - " + data[i].desc + '</option>');
					    }else{
					    	items.push('<option value="' + data[i].code + '">' + data[i].code + " - " + data[i].desc + '</option>');
					    }
						count++;
					});
					if(count==0){
						$("select[name='selSpecCode2']").empty(); //Remove all previous options (Index cleanup for various browsers)
						$("select[name='selSpecCode2']").html('<option value="0">(N/A) - Not Applicable</option>');
					}else{
						$("select[name='selSpecCode2']").html(items);
					}
				});
			}
		}
		
		function doSubmit(button){
			if (button === "Continue"){
				document.studentRegistrationForm.action='applyForStudentNumber.do?act=nextStep';
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

	<html:hidden property="page" value="applyQualification"/>
		
	<input type="hidden" name="numYesNo" id="numYesNo" value="<bean:write name='studentRegistrationForm' property='loginSelectYesNo'/>"/>
	<input type="hidden" name="webADM" id="webADM" value="<bean:write name='studentRegistrationForm' property='adminStaff.admin' />"/>
	<input type="hidden" id="docMsg" name="docMsg" value="<bean:write name='studentRegistrationForm' property='webUploadMsg' />" />
	<input type="hidden" id="catSelect" name="catSelect" value="<bean:write name='studentRegistrationForm' property='loginSelectMain' />" />
	<input type="hidden" id="savedCollege1" name="savedCollege1" value="<bean:write name='studentRegistrationForm' property='savedCollege1' />" />
	<input type="hidden" id="savedCollege2" name="savedCollege2" value="<bean:write name='studentRegistrationForm' property='savedCollege2' />" />
	<input type="hidden" id="savedCategory1" name="savedCategory1" value="<bean:write name='studentRegistrationForm' property='savedCategory1' />" />
	<input type="hidden" id="savedCategory2" name="savedCategory2" value="<bean:write name='studentRegistrationForm' property='savedCategory2' />" />
	<input type="hidden" id="savedQual1" name="savedQual1" value="<bean:write name='studentRegistrationForm' property='savedQual1' />" />
	<input type="hidden" id="savedQual2" name="savedQual2" value="<bean:write name='studentRegistrationForm' property='savedQual2' />" />
	<input type="hidden" id="savedSpec1" name="savedSpec1" value="<bean:write name='studentRegistrationForm' property='savedSpec1' />" />
	<input type="hidden" id="savedSpec2" name="savedSpec2" value="<bean:write name='studentRegistrationForm' property='savedSpec2' />" />
	<input type="hidden" id="selectHEMain" name="selectHEMain" value="<bean:write name='studentRegistrationForm' property='selectHEMain' />" />
	<input type="hidden" id="isSTUSLP" name="isSTUSLP" value="<bean:write name='studentRegistrationForm' property='student.stuSLP' />" />
	<input type="hidden" id="isSTUAPQ" name="isSTUAPQ" value="<bean:write name='studentRegistrationForm' property='student.stuapq' />" />
	<input type="hidden" id="stuNr" name="stuNr" value="<bean:write name='studentRegistrationForm' property='student.number' />" />	
	<input type="hidden" id="qual1OptionDesc" name="qual1OptionDesc" value="" />
	<input type="hidden" id="qual2OptionDesc" name="qual2OptionDesc" value="" />
		
	<div style="display: none;" id="dialogHolder"><p id="dialogContent"></p></div>

	<br/>
	<div class="container">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<sakai:messages/>
			<sakai:messages message="true"/>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title text-center"><fmt:message key="page.studentnr.apply.heading"/></h3>
				</div>
				<div class="panel-body">
					<logic:equal name="studentRegistrationForm" property="student.stuSLP" value="true">
						<logic:notEqual name="studentRegistrationForm" property="student.stuapq" value="true">
							<strong><fmt:message key="page.studentnr.apply.qualbefore.SLP1"/></strong><br/>
						</logic:notEqual>
						<logic:equal name="studentRegistrationForm" property="student.stuapq" value="true">
							<strong><fmt:message key="page.studentnr.apply.qualbefore.SLP1"/></strong><br/>
						</logic:equal>
					</logic:equal>
					<logic:notEqual name="studentRegistrationForm" property="student.stuSLP" value="true">
						<logic:notEqual name="studentRegistrationForm" property="loginSelectMain" value="MD">
							<strong><fmt:message key="page.studentnr.apply.qualbefore"/></strong><br/>
						</logic:notEqual>
						<logic:equal name="studentRegistrationForm" property="loginSelectMain" value="MD">
							<strong><fmt:message key="page.studentnr.apply.qualbefore.md"/></strong><br/>
						</logic:equal>
						<logic:equal name="studentRegistrationForm" property="loginSelectMain" value="UD">
							<i><fmt:message key="page.studentnr.apply.qualbefore2"/></i><br/><br/>
						</logic:equal>
					</logic:notEqual>
					<logic:notEqual name="studentRegistrationForm" property="student.stuSLP" value="true">
						<logic:notEmpty name="studentRegistrationForm" property="existQual">
							<strong>Current Qualification</strong>
						 	<sakai:group_table>
								<tr>
									<td><fmt:message key="label.selectstudy.exist.qualification"/>:&nbsp;</td>
									<td colspan="2"><bean:write name="studentRegistrationForm" property="student.retQualPrevFinal"/></td>
								</tr><tr>
									<td><fmt:message key="label.selectstudy.exist.specialization"/>:&nbsp;</td>
									<td colspan="2"><bean:write name="studentRegistrationForm" property="student.retSpecPrevFinal"/></td>
								</tr>
							</sakai:group_table>
						</logic:notEmpty>
					</logic:notEqual>
					<div id="first">
						<strong>New Qualification</strong><br/>
						<sakai:group_table>
							<tr style="width:150">
								<td colspan="2"><fmt:message key="page.studentnr.apply.proqual" /></td>
							</tr>
							<tr style="width:500">
								<td>
									<fmt:message key="page.studentnr.apply.college"/>
								</td>
								<td>									
									<div id="firstCol"> 
										<html:select name="studentRegistrationForm" property="selCollegeCode1"
											errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
											<html:optionsCollection name="studentRegistrationForm" property="collegeList" value="code" label="description"/>
										</html:select>
									</div>
								</td>
							</tr>
							<tr style="width:500">
								<td>
									<fmt:message key="page.studentnr.apply.category" />
								</td>
								<td>
									<div id="firstCat"> 
										<html:select  name="studentRegistrationForm" property="selCategoryCode1"
											errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
											<html:option value="0">Select category</html:option>
										</html:select>
									</div>
								</td>
							</tr>
							<tr style="width:500">
								<td>
									<fmt:message key="page.studentnr.apply.qualification" />
								</td>
								<td>
									<div id="firstQual"> 
										<html:select name="studentRegistrationForm" property="selQualCode1"
											errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
									  		<option value="0">Select qualification</option>
										</html:select>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="page.studentnr.apply.stream" />
								</td>
								<td>
									<div id="firstSpec"> 
										<html:select name="studentRegistrationForm" property="selSpecCode1" style="width:100%">
									  		<option value="0">Select specialization</option>
										</html:select>
									</div>
								</td>
							</tr>
						</sakai:group_table>
					</div>
					<div id="second">
						<sakai:group_table>
							<tr style="width:150">
								<td colspan="2"><fmt:message key="page.studentnr.apply.optqual" /></td>
							</tr>
							<tr style="width:500">
								<td>
									<fmt:message key="page.studentnr.apply.college"/>
								</td>
								<td>									
									<div id="secondCol"> 
										<html:select name="studentRegistrationForm" property="selCollegeCode2"
											errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
											<html:optionsCollection name="studentRegistrationForm" property="collegeList" value="code" label="description"/>
										</html:select>
									</div>
								</td>
							</tr>
							<tr style="width:500">
								<td>
									<fmt:message key="page.studentnr.apply.category" />
								</td>
								<td>
									<div id="secondCat"> 
										<html:select  name="studentRegistrationForm" property="selCategoryCode2"
											errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
									  		<html:option value="0">Select category</html:option>
										</html:select>
									</div>
								</td>
							</tr>
							<tr style="width:500">
								<td>
									<fmt:message key="page.studentnr.apply.qualification" />
								</td>
								<td>
									<div id="secondQual"> 
										<html:select name="studentRegistrationForm" property="selQualCode2"
											errorStyleClass="error" errorKey="org.apache.struts.action.ERROR" style="width:100%">
									  		<option value="0">Select qualification</option>
										</html:select>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<fmt:message key="page.studentnr.apply.stream" />
								</td>
								<td>
									<div id="secondSpec"> 
										<html:select name="studentRegistrationForm" property="selSpecCode2" style="width:100%">
									 		<option value="0">Select specialization</option>
										</html:select>
									</div>
								</td>
							</tr>
						</sakai:group_table>
					</div>
					<br/>
				</div>	
				<div class="panel-footer clearfix">
					<button class="btn btn-default" type="button" onclick="validate();">Save and Continue</button>
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