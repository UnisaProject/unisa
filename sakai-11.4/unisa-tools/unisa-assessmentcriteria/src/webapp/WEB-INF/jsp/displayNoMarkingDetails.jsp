<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assessmentcriteria.ApplicationResources"/>
<sakai:html>
<script language="javascript">
	function doAction() {
		document.assessmentCriteriaForm.action = 'assessmentCriteria.do?act=saveData';  
		document.assessmentCriteriaForm.submit();
	}
</script>

	<html:form action="/assessmentCriteria">
		<!--<html:hidden property="currentPage" value="noMarking"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<logic:notEqual  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.mcq.markingDetails"/>
			</logic:notEqual>
			<logic:equal  name="assessmentCriteriaForm"  property="assignmentAction" value="view">
				<fmt:message key="heading.view.mcq.markingDetails"/>
			</logic:equal>				
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.note1"/>&nbsp;<fmt:message key="page.mandatory"/>
			
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.code"/></td>
				<td><bean:write name="assessmentCriteriaForm" property="studyUnit.description"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="academicYear"/>/<bean:write name="assessmentCriteriaForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.assignment"/>&nbsp;</td>	
				<td colspan="2"><bean:write name="assessmentCriteriaForm" property="assignment.number"/>&nbsp;&nbsp;(<fmt:message key="page.assignment.dueDate"/>:&nbsp;<bean:write name="assessmentCriteriaForm" property="assignment.dueDate"/>)</td>	
			</tr>
			<tr>
				<td><fmt:message key="page.status"/>&nbsp;</td>			
				<td><bean:write name="assessmentCriteriaForm" property="statusDesc"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="page.onlineMethod"/>&nbsp;</td>	
				<td colspan="2"><bean:write name="assessmentCriteriaForm" property="onlineMethod"/></td>
			</tr>
		</sakai:group_table>	
		<hr/>
		<BR/>
		<sakai:instruction>
			<fmt:message key="page.note17"/>
		</sakai:instruction>		
		<sakai:actions>
			<logic:notEqual name="assessmentCriteriaForm" property="assignmentAction" value="view">
				<html:submit property="act" onclick="javascript:disabled=true;doAction();">
						<fmt:message key="button.save"/>
				</html:submit>
			</logic:notEqual>
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>
			<html:submit property="act">
					<fmt:message key="button.cancel"/>
			</html:submit>				
		</sakai:actions>
	</html:form>
</sakai:html>