<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/subProvinceMaintenance">
		        <sakai:messages/>
		        <sakai:messages message="true"/>
			    <sakai:heading>
			<fmt:message key="heading.subprovinces"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="inputPlacementList.editsubprovincesforprov"/> 
		</sakai:group_heading>
			<sakai:group_table>	
					<tr>
				            <td><fmt:message key="prompt.column.supprovince"/></td>
					        <td><html:text name="studentPlacementForm" property="subProvince.description" size="12" /></td>
				</tr>
				<tr>
				         <td><fmt:message key="prompt.column.province"/></td>
				        <td><bean:write name="studentPlacementForm" property="subProvince.provinceDescription"/></td>	
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