<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
 <head>  
      <meta charset="utf-8">  
      <link href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">  
      <script src="https://code.jquery.com/jquery-1.10.2.js"></script>  
      <script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>  
  
      <!-- Javascript -->  
      <script>  
         $(function() {
        	 $( "#startDate" ).datepicker({
                 showOn:"button",
                 buttonImage:"https://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                 buttonImageOnly: true,
                 altField: '#startDate',
                 dateFormat: 'yy/mm/dd',
                 beforeShowDay: $.datepicker.noWeekends
              });
        	 $( "#endDate" ).datepicker({
                showOn:"button",
                buttonImage: "https://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                buttonImageOnly: true,
                altField: '#endDate',
                dateFormat: 'yy/mm/dd',
                beforeShowDay: $.datepicker.noWeekends
             });
        	 });
         
      </script> 
      <script type="text/javascript">
      </script>
  </head>  	
	<html:form action="/practicalPeriodMaintenance">
		<!--<html:hidden property="currentPage" value="displayDistrictList"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.practicalperiodmaintenance"/>
			<sakai:group_heading>
			<fmt:message key="heading.addpracticalperiod"/> 
		</sakai:group_heading>		
		
		</sakai:heading>	
			<sakai:group_table>				
			<tr>
			<td  ><fmt:message key="prompt.academicyear"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.academicYear"  onchange="submit()" >
						<html:optionsCollection name="studentPlacementForm"   property="listPracticalYear"  value="year" label="yearDescription"/>
					</html:select>                                           
				</td>                        
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.provCode">
						<html:optionsCollection name="studentPlacementForm" property="listRSAProvinces"  value="code" label="description"/>
					</html:select>                                           
				</td>
				<td><fmt:message key="prompt.level"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.level">
						<html:optionsCollection name="studentPlacementForm" property="listLevels"  value="level" label="levelDescription"/>
					</html:select>                                           
				</td>
			</tr>		
			<tr>
				<td ><fmt:message key="prompt.practicalperiod"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.practicalPeriod"  onchange="submit()" >
						<html:optionsCollection name="studentPlacementForm" property="listPracticalPeriods"  value="period" label="periodDescription"/>
					</html:select>                                           
				</td>
				<td ><fmt:message key="prompt.practicaldays"/>&nbsp;</td>
				<td>
				        <html:text name="studentPlacementForm" property="practiceBatchDate.practicalDays" size="4" maxlength="4"/>
			   </td>
			</tr>		
				<tr>
				            <td><fmt:message key="prompt.fromdate"/>&nbsp;&nbsp;</td>
					        <td><input type="text"  name="startDate" size="14" maxlength="10"  id="startDate"   value='<%=request.getAttribute("startDate")%>'/></td>
						    <td><fmt:message key="prompt.todate"/>&nbsp; </td>
						    <td><input type="text"   name="endDate" size="14" maxlength="10"  id="endDate"  value='<%=request.getAttribute("endDate")%>'/>
			    </tr>		
			</sakai:group_table>
			<sakai:actions>
				<html:submit property="action">
					<fmt:message key="button.save"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>
		<html:hidden property="action" value="acadYearChangeAction"/>		
	</html:form>
</sakai:html>