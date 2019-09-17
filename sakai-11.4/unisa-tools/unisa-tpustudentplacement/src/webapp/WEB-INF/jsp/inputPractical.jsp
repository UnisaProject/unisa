<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/practicalMaintenance">
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
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.provCode">
						<html:optionsCollection name="studentPlacementForm" property="listRSAProvinces"  value="code" label="description"/>
					</html:select>                                           
				</td>
				<td><fmt:message key="prompt.level"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.level">
						<html:optionsCollection name="studentPlacementForm" property="listLevels"  value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>		
			<tr>
				<td ><fmt:message key="prompt.practicalperiod"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.practicalPeriod">
						<html:optionsCollection name="studentPlacementForm" property="listPracticalPeriods"  value="code" label="description"/>
					</html:select>                                           
				</td>
				<td ><fmt:message key="prompt.practicaldays"/>&nbsp;</td>
				<td>
				        <html:text name="studentPlacementForm" property="practiceBatchDate.numOfDays" size="4" maxlength="4"/>
			   </td>
			</tr>		
				<tr>
				<td ><fmt:message key="prompt.practicaldatebatch"/>&nbsp;</td>
				<td colspan="3"> <fmt:message key="prompt.fromdate"/>&nbsp;
				                      <html:text name="studentPlacementForm" property="practiceBatchDate.fromDate"  size="4" maxlength="4"/>
						             <fmt:message key="prompt.todate"/>&nbsp; 
						              <html:text name="studentPlacementForm" property="practiceBatchDate.toDate" size="4" maxlength="4"/>
			   </td>
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
	</html:form>
</sakai:html>