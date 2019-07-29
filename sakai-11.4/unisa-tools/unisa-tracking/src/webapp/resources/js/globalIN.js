var countMainAssign = 0;
var countConListAssign = 0;
		
$(document).ready(function(){
	
	//Allow move of Focus for Barcode scanners where no enter or tab is pressed
	$(function() {
		$("input[name='enteredConsignmentNumber']").bind('paste', function(event) {
			$("input[name='addConsignment']").focus();
  		});
		$("input[name='docketNumber']").bind('paste', function(event) {
			$("input[name='addDocket']").focus();
  		});
		$("input[name='studNo']").bind('paste', function(event) {
			$("input[name='uniqueAssignmentNr']").focus();
  		});
		$("input[name='uniqueAssignmentNr']").bind('paste', function(event) {
			$("input[name='addAssignment']").focus();
  		});
	});			
	
	//Expand and Collapse major groups
	$('#titleTextImgCon').click(function(){
	    $(this).closest('table').find('#contentDivImgCon').toggle();
	    if ($('#imageConPlus').css('display') == 'none') {
	    	$("#imageConPlus").show();
	    	$("#imageConMinus").hide();
	    	$.cookie('imageConPlus', "show");
	   	}else{
	   		$("#imageConPlus").hide();
	   		$("#imageConMinus").show();
	   		$.cookie('imageConPlus', "hide");
	   	}
	});
	
	$('#titleTextImgDct').click(function(){
	    $(this).closest('table').find('#contentDivImgDct').toggle();
	    if ($('#imageDctPlus').css('display') == 'none') {
	    	$("#imageDctPlus").show();
	    	$("#imageDctMinus").hide();
	    	$.cookie('imageDctPlus', "show");
	   	}else{
	   		$("#imageDctPlus").hide();
	   		$("#imageDctMinus").show();
	   		$.cookie('imageDctPlus', "hide");
	   	}
	});
	
	$('#titleTextImgAss').click(function(){
	    $(this).closest('table').find('#contentDivImgAss').toggle();
	    if ($('#imageAssPlus').css('display') == 'none') {
	    	$("#imageAssPlus").show();
	    	$("#imageAssMinus").hide();
	    	$.cookie('imageAssPlus', "show");
	   	}else{
	   		$("#imageAssPlus").hide();
	   		$("#imageAssMinus").show();
	   		$.cookie('imageAssPlus', "hide");
	   	}
	});
}); //OnReady

//Expand and Collapse minor groups
function expandConsignments(conList){
   	$(".consignmentsList"+conList).toggle();
    if ($("#imageConsignmentsPlus"+conList).css('display') == 'none') {
    	$("#imageConsignmentsPlus"+conList).show();
    	$("#imageConsignmentsMinus"+conList).hide();
   	}else{
   		$("#imageConsignmentsPlus"+conList).hide();
   		$("#imageConsignmentsMinus"+conList).show();
   	}
}

function expandConDockets(dct){
   	//$('.dctAssList'+key).toggle();
    if ($("#imageConUniqueDctPlus"+dct).css('display') == 'none') {
    	$("#imageConUniqueDctPlus"+dct).show();
    	$("#imageConUniqueDctMinus"+dct).hide();
    	$(".conDctAssList"+dct).hide();
   	}else{
   		$("#imageConUniqueDctPlus"+dct).hide();
   		$("#imageConUniqueDctMinus"+dct).show();
    	$(".conDctAssList"+dct).show();
   	}
}

function expandDockets(dct){
   	//$('.dctAssList'+key).toggle();
    if ($("#imageUniqueDctPlus"+dct).css('display') == 'none') {
    	$("#imageUniqueDctPlus"+dct).show();
    	$("#imageUniqueDctMinus"+dct).hide();
    	$(".dctAssList"+dct).hide();
   	}else{
   		$("#imageUniqueDctPlus"+dct).hide();
   		$("#imageUniqueDctMinus"+dct).show();
    	$(".dctAssList"+dct).show();
   	}
}

function expandAssignments(count){
   	$('.uniqueAssignmentList').toggle();
    if ($('#imageUniqueAssignmentPlus').css('display') == 'none') {
    	$("#imageUniqueAssignmentPlus").show();
    	$("#imageUniqueAssignmentMinus").hide();
   	}else{
   		$("#imageUniqueAssignmentPlus").hide();
   		$("#imageUniqueAssignmentMinus").show();
   	}
}

function expandConUniqueAssignments(){
   	$('.uniqueConAssignmentList').toggle();
    if ($('#imageConUniqueAssignmentPlus').css('display') == 'none') {
    	$("#imageConUniqueAssignmentPlus").show();
    	$("#imageConUniqueAssignmentMinus").hide();
   	}else{
   		$("#imageConUniqueAssignmentPlus").hide();
   		$("#imageConUniqueAssignmentMinus").show();
   	}
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
		
function validateDocketNumber(){
	//alert("validateDocketNumber");
	var contentDct="";
	var contentAss="";
	var contentDctSub="";
	var contentAssSub="";
	var DctLiCol;
	var AssLiCol;
	var countDct=1;
	var liCount=1;
	var url = "tracking.do?act=validateDocketNumber&docketNumber="+$("input[name='docketNumber']").val();
		
	$.blockUI({ message: "<b><img src='/unisa-tracking/resources/images/ajax-loader.gif' /> <br>Validating Cover Docket: "+$("input[name='docketNumber']").val()+"</b>" });
	$.getJSON(url, function(data) {
		cache : false,
		$.each(data, function(key, data2) {
			//alert("DocketID="+key);
			if (key === "Error"){
				$.unblockUI();
				showError("Error", data2);
				$("input[name='docketNumber']").focus();
				return false;
			}else if (key === "Empty"){
				$.unblockUI();
				showError("Empty", data2);
				$("input[name='docketNumber']").focus();
				return false;
			}else if (key === "RemoveAssignments"){	
				//alert("Processing Removal of Unique Assignments as they are already in Cover Docket list");
				$.each(data2, function(unqKey, unqVal) {
					var splitString = unqVal.split('~');
					//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
					$.blockUI({ message: "Removing StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]+" as it already exists in Consignment list" });
					$("#uniqueAssignmentList"+splitString[0]+splitString[1]).remove();
					countMainAssign = countMainAssign -1;
					//alert("countMainAssign="+countMainAssign);
					if (countMainAssign === 0){
						//alert("Removing uniqueAssignmentDiv");
						$("#uniqueAssignmentDiv").remove();
					}
				});
			}else if (key === "RemoveConAssignments"){	
				//alert("Processing Removal of Consignment Unique Assignments as they are already in Cover Docket list");
				$.each(data2, function(unqKey, unqVal) {
					var splitString2 = unqVal.split('~');
					//alert("StudentNumber="+splitString2[0]+", AssignmentNumber="+splitString2[1]);
					$.blockUI({ message: "Removing StudentNumber="+splitString2[0]+", AssignmentNumber="+splitString2[1]+" from Consignment list as it already exists in Cover Docket list" });
					$("#conUniqueAssignmentList"+splitString2[0]+splitString2[1]).remove();
					countConListAssign = countConListAssign -1;
					//alert("countConListAssign="+countConListAssign);
					if (countConListAssign === 0){
						//alert("Removing uniqueConAssignmentDiv");
						$("#conUniqueAssignmentList").remove();
					}
				});
			}else{
				//alert("key="+key);
				if (countDct % 2 === 0) {
					DctLiCol = "#DDDDDD";
	        	}else{
	        		DctLiCol = "#EEEEEE";
	        	}
							
				$.each(data2, function(key2, val2) {
					var splitString = val2.split('~');
					//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
			       	if (liCount % 2 === 0) {
			       		AssLiCol = "#BEEFDE"; 
			       	}else{
			       		AssLiCol = "#EEFAF6";
			       	}
			        contentAssSub += "<li class='dctAssList"+key+"' id='dctAssList"+key+"' style='background:"+AssLiCol+"'><input type='checkbox' class='childCheckBox' name='chkDctAssignment' value='chkDctAssignment~"+key+"~"+splitString[0]+"~"+splitString[1]+"' id='chkDctAssignment~"+key+"~"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span><label for='chkDctAssignment~"+key+"~"+splitString[0]+"~"+splitString[1]+"'>&nbsp;&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</label></span></li>";
			        liCount++;
				});
				contentDctSub += "<div id='docketList"+key+"'><ul class='docketList' style='background:"+DctLiCol+"'><li id='liChkDocket~"+key+"'><input type='checkbox' class='parentCheckBox' name='chkDocket' value='chkDocket~"+key+"' id='chkDocket~"+key+"' checked='checked'/><span><label for='chkDocket~"+key+"'>&nbsp;"+key+"&nbsp;</label></span><img id='imageUniqueDctPlus"+key+"'' style='display:none' src='/unisa-tracking/resources/images/bullet-toggle-plus-icon32.png' onclick='expandDockets("+key+");' /><img id='imageUniqueDctMinus"+key+"'' src='/unisa-tracking/resources/images/bullet-toggle-minus-icon32.png' onclick='expandDockets("+key+");' /><ul class='dctAssignmentList' id='dctAssignmentList~"+key+"'>"+contentAssSub+"</ul></li></ul></div>";
				countDct++;
			}
						
		});
		
		contentDct = contentDctSub;
				
		$("#div_dockets").append(contentDct);
		$("input[name='docketNumber']").val("");
		$.unblockUI(); 
		$("input[name='docketNumber']").focus();
	});
}

function validateStudentUniqueNumber(){
	var content = "";
	var contentAss = "";
	var contentErr = false;
   	var liCol;
   	var liCount=1;
	var url = "tracking.do?act=validateStudentUniqueNumber&studNo="+$("input[name='studNo']").val()+"&uniqueAssignmentNr="+$("input[name='uniqueAssignmentNr']").val();
	$.blockUI({ message: "<b><img src='/unisa-tracking/resources/images/ajax-loader.gif' /> <br>Validating Unique Assignment: "+$("input[name='studNo']").val()+" / "+$("input[name='uniqueAssignmentNr']").val()+"</b>" });
	$.getJSON(url, function(data) {
		cache : false,
		$.each(data, function(key, val) {
			//alert("StudentNumber="+key+", AssignmentNumber="+val);
			if (key === "Error"){
				$.unblockUI();
				showError("Error", val);
				contentErr = true;
				$("input[name='studNo']").val("");
				$("input[name='uniqueAssignmentNr']").val("");
				$("input[name='studNo']").focus();
				return false;
			}
			var splitString = val.split('~');
			//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
			countMainAssign = liCount;
			countConListAssign = liCount;
			//alert(countMainAssign);
		   	if (liCount % 2 === 0) { /* we are even */ 
		   		liCol = "#BEEFDE"; 
		   	}else{
		   		liCol = "#EEFAF6";
		   	}
		   	content += "<li style='background:"+liCol+"' id='uniqueAssignmentList"+splitString[0]+splitString[1]+"'><input type='checkbox' class='childCheckBox' name='chkUniqueAssignment' value='"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span>&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</span></li>";
		   	liCount++;
		});
		if (contentErr === false){
			contentAss = "<div id='uniqueAssignmentDiv'><ul name='parentAssignment' id='parentAssignment' class='parent'><li><input type='checkbox' class='parentCheckBox' name='chkAssignmentList' value='chkAssignmentList' checked='checked' /><span>&nbsp;Student Assignments:&nbsp;</span><img id='imageUniqueAssignmentPlus' style='display:none' src='/unisa-tracking/resources/images/bullet-toggle-plus-icon32.png' onclick='expandAssignments();' /><img id='imageUniqueAssignmentMinus' src='/unisa-tracking/resources/images/bullet-toggle-minus-icon32.png' onclick='expandAssignments();' /><ul class='uniqueAssignmentList' id='uniqueAssignmentList"+liCount+"'>"+content+"</ul></li></ul>";
			$("#div_assignments").empty();
			$("#div_assignments").append(contentAss);
			$("input[name='studNo']").val("");
			$("input[name='uniqueAssignmentNr']").val("");
			$.unblockUI();
			$("input[name='studNo']").focus();
		}
	});
}

function validateConsignmentList(){
	//alert("validateConsignmentList="+$("input[name='enteredConsignmentNumber']").val());
	$.blockUI({ message: "<b><img src='/unisa-tracking/resources/images/ajax-loader.gif' /> <br>Validating Consignment List: "+$("input[name='enteredConsignmentNumber']").val()+"</b>" });
	var consignmentList = "";
	var consignmentDate = "";
	var content = "";
	var contentCon = "";
	var contentDct = "";
	var contentDctSub = "";
	var contentDctAssSub = "";
	var contentUnqAss = "";
	var contentUnqAssSub = "";
	var contentErr = false;
	var dctLiCol;
	var assLiCol;
	var unqAssLiCol;
	var countDct=1;
	var countDctAss=1;
	var countUnqAss=1;
	var url = "tracking.do?act=validateConsignmentList&enteredConsignmentNumber="+$("input[name='enteredConsignmentNumber']").val();
	
	$.getJSON(url, function(data) {
		cache : false,
		$.each(data, function(conKey, conVal) {
			if (conKey === "Error"){
				$.unblockUI();
				showError("Error", conVal);
				contentErr = true;
				$("input[name='enteredConsignmentNumber']").val("");
				$("input[name='enteredConsignmentNumber']").focus();
				return false;
			}else{
				if (conKey === $.trim($("input[name='enteredConsignmentNumber']").val())){
					//alert("Consignment="+conKey);
					//contentCon += "<br>Consignment="+conKey+", Date="+conVal+"<br>";
					consignmentList = conKey;
					consignmentDate = conVal;
				}
				if (conKey === "Dockets"){	
					//alert("Processing Dockets");
					var countDct = 1;
					contentDct = "";
					contentDctAssSub = "";
					$.each(conVal, function(dctKey, dctVal) {
						if (countDct % 2 === 0) {
							dctLiCol = "#DDDDDD";
			        	}else{
			        		dctLiCol = "#EEEEEE";
			        	}
						contentDctAssSub = "";
						$.each(dctVal, function(assKey, assVal) {
							var splitString = assVal.split('~');
							//alert("Dct StudentNumber="+splitString[0]+", Dct AssignmentNumber="+splitString[1]);
					       	if (countDctAss % 2 === 0) {
					       		assLiCol = "#BEEFDE"; 
					       	}else{
					       		assLiCol = "#EEFAF6";
					       	}
					        contentDctAssSub += "<li class='conDctAssList"+dctKey+"' style='background:"+assLiCol+"'><input type='checkbox' class='childCheckBox' name='chkConDctAssignment' value='chkConDctAssignment~"+dctKey+"~"+splitString[0]+"~"+splitString[1]+"' id='chkConDctAssignment~"+dctKey+"~"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span><label for='chkConDctAssignment~"+dctKey+"~"+splitString[0]+"~"+splitString[1]+"'>&nbsp;&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</label></span></li>";
					        countDctAss++;
						});
							
						contentDctSub += "<ul class='conDocketList' id='conDocketList~"+countDct+"' style='background:"+dctLiCol+"'><li><input type='checkbox' class='childCheckBox' name='chkConDocket' value='chkConDocket~"+dctKey+"' id='chkConDocket"+dctKey+"' checked='checked'/><span><label for='chkConDocket"+dctKey+"'>&nbsp;"+dctKey+"&nbsp;</label></span><img id='imageConUniqueDctPlus"+dctKey+"'' style='display:none' src='/unisa-tracking/resources/images/bullet-toggle-plus-icon32.png' /> onclick='expandConDockets("+dctKey+");'><img id='imageConUniqueDctMinus"+dctKey+"'' src='/unisa-tracking/resources/images/bullet-toggle-minus-icon32.png' onclick='expandConDockets("+dctKey+");' /><ul class='conDctAssignmentList' id='conDctAssignmentList~"+dctKey+"'>"+contentDctAssSub+"</ul></li></ul>";
						countDct++;
					});
					contentDct += contentDctSub;
				}
				if (conKey === "Assignments"){	
					//alert("Processing Assignments");
					$.each(conVal, function(unqKey, unqVal) {
						var splitString = unqVal.split('~');
						//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
			        	if (countUnqAss % 2 === 0) {
			        		unqAssLiCol = "#BEEFDE"; 
			        	}else{
			        		unqAssLiCol = "#EEFAF6";
			        	}
			        	contentUnqAssSub += "<li style='background:"+unqAssLiCol+"' class='uniqueConAssignmentList' id='conUniqueAssignmentList"+splitString[0]+splitString[1]+"'><input type='checkbox' class='childCheckBox' name='chkConUniqueAssignment' value='chkConUniqueAssignment~"+splitString[0]+"~"+splitString[1]+"' checked='checked' /><span>&nbsp;&nbsp;"+splitString[0]+"&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;"+splitString[1]+"&nbsp;</span></li>";
					    countUnqAss++;
					});
					contentUnqAss = "<li><input type='checkbox' class='childCheckBox' name='chkConAssignmentList' value='chkConAssignmentList' checked='checked' /><span>&nbsp;Student Assignments:&nbsp;</span><img id='imageConUniqueAssignmentPlus' style='display:none' src='/unisa-tracking/resources/images/bullet-toggle-plus-icon32.png' onclick='expandConUniqueAssignments();' /><img id='imageConUniqueAssignmentMinus' src='/unisa-tracking/resources/images/bullet-toggle-minus-icon32.png' onclick='expandConUniqueAssignments();' /><ul>"+contentUnqAssSub+"</ul></li>";
				}
				if (conKey === "RemoveDockets"){	
					//alert("Processing Removal of Unique Dockets as they are already in Consignment list");
					$.each(conVal, function(unqKey, unqVal) {
						$.blockUI({ message: "Removing Unique Cover Docket="+unqVal+" As it already exists in Consignment list" });
						$("#docketList"+unqVal).remove();
					});
				}
				if (conKey === "RemoveAssignments"){	
					//alert("Processing Removal of Unique Assignments as they are already in Consignment list");
					$.each(conVal, function(unqKey, unqVal) {
						var splitString = unqVal.split('~');
						//alert("StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]);
						$.blockUI({ message: "Removing StudentNumber="+splitString[0]+", AssignmentNumber="+splitString[1]+" As it already exists in Consignment list" });
						$("#uniqueAssignmentList"+splitString[0]+splitString[1]).remove();
						countMainAssign = countMainAssign -1;
						//alert("countMainAssign="+countMainAssign);
						if (countMainAssign === 0){
							//alert("Removing uniqueAssignmentDiv");
							$("#uniqueAssignmentDiv").remove();
						}
					});
				}
			}
		});
	    content = "<ul name='parentConsignment' id='parentConsignment' class='parent'><li><input type='checkbox' class='parentCheckBox' id='chkConsignmentList"+consignmentList+"' value='chkConsignmentList"+consignmentList+"' checked='checked' /><span>&nbsp;Consignment: "+consignmentList+", Date: "+consignmentDate+"&nbsp;</span><img id='imageConsignmentsPlus"+consignmentList+"' style='display:none' src='/unisa-tracking/resources/images/bullet-toggle-plus-icon32.png' onclick='expandConsignments("+consignmentList+");' /><img id='imageConsignmentsMinus"+consignmentList+"' src='/unisa-tracking/resources/images/bullet-toggle-minus-icon32.png' onclick='expandConsignments("+consignmentList+");' /><ul class='consignmentsList"+consignmentList+"' id='consignmentsList"+consignmentList+"'>"+ contentDct + contentUnqAss+"</ul></li></ul>";
		if (contentErr === false){
			//alert("No Error");
			$("#div_consignments").append(content);
			$("input[name='enteredConsignmentNumber']").val("");
			$("input[name='enteredConsignmentNumber']").prop('disabled', true);
			$("input[name='addConsignment']").prop('disabled', true);
			$.unblockUI();
			$("input[name='enteredConsignmentNumber']").focus();
		}
	});
}
		
function frmSubmitBookIN(){
	//showValues(); /*Debug*/
    $.blockUI({ message: "<b><img src='/unisa-tracking/resources/images/ajax-loader.gif' /> <br>Processing Book IN information</b>" });
	var url = "tracking.do?act=processInput&process=BookIN";
    var data = $("Form").serialize();
    $.getJSON(url, data, function(result){
    	cache : false,
    	$.each(result, function(key, val) {
    		//alert("Key="+key+", Value="+val);
    		if (key === "Error"){
				$.unblockUI();
				showError("Error", val);
				return false;
			}else if (key === "Success"){
				$.unblockUI();
				//showError("Success", val);
				var url2 = "tracking.do?act=result";
				window.location.href = url2;
			}
    	});
    });		    
}
		
function frmSubmitSameWin(){
	var buttonCheck = $("input[name='act']").val();
	if (buttonCheck.toUpperCase() === "CLEAR"){
        $('#contentDivImgAdr').show();
        $("#imageAdrPlus").hide();
        $("#imageAdrMinus").show();
        $('#contentDivImgCon').show();
        $("#imageConPlus").hide();
        $("#imageConMinus").show();
        $('#contentDivImgDct').show();
        $("#imageDctPlus").hide();
        $("#imageDctMinus").show();
        $('#contentDivImgAss').show();
        $("#imageAssPlus").hide();
        $("#imageAssMinus").show();
	}
	document.trackingForm.target = '';
}

function showValues() {
    var str = $("form").serialize();
    $( "#results" ).text( str );
}

