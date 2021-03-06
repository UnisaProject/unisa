<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.smsbatch.ApplicationResources"/>

<sakai:html>
<script type="text/JavaScript" language="JavaScript">
		<!--
			function textCounter(field, maxlimit) {
				if (field.value.length > maxlimit)
					field.value = field.value.substring(0, maxlimit);
			}
-->
		</script>

  <sakai:heading><fmt:message key="page.file.heading"/></sakai:heading>
  <html:form action="/smsFile">
  	<html:hidden property="page" value="fileStep3"/>
	<sakai:messages/>
	<sakai:messages message="true"/>
  	<table>
  		<tr>
			<td valign="top"><fmt:message key="page.control.cell"/>&nbsp;</td>
			<td>
				<logic:iterate name="smsBatchForm" property="controlCellNumberList" id="record">
					<logic:notEmpty name="record">
						<bean:write name="record"/><br/>
					</logic:notEmpty>
				</logic:iterate>
			</td>			
		</tr>
		<tr>
			<td><fmt:message key="page.request.reason"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="reasonGc27"/></td>
		</tr>
		<tr>
			<td><fmt:message key="page.message"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="message"/></td>
		</tr>
		<tr>
    		<td colspan="2">&nbsp;</td>
    	</tr>			
    	<tr>
    		<td colspan="2"><strong><fmt:message key="page.request"/>&nbsp;</strong></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.messagesnr"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="messageCount"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.rccode"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="rcCode"/>&nbsp;:&nbsp;<bean:write name="smsBatchForm" property="rcDescription"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.smscost"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="costPerSms" format="0.000"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.totalbudget"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="budgetAmount" format="0.00"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.availablebudget"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="availableBudgetAmount" format="0.00"/></td>
    	</tr>
    	<tr>
    		<td><fmt:message key="page.request.totalcost"/>&nbsp;</td>
			<td><bean:write name="smsBatchForm" property="totalCost"/></td>
    	</tr>
  	</table>
  	<br/>  	
	<html:submit property="act"><fmt:message key="button.cancel"/></html:submit>
  </html:form>
</sakai:html>