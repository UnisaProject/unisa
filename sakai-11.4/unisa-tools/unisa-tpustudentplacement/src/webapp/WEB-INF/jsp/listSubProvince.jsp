<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/subProvince">
		<!--<html:hidden property="currentPage" value="displayDistrictList"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.subprovincemaintenance"/>
		</sakai:heading>	
		<sakai:group_heading>
			<fmt:message key="subprovinceinruction.plschooseprovtodisplaysubprovinces"/> 
		</sakai:group_heading>
		<sakai:group_table>				
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="districtFilterProvince">
						<html:optionsCollection name="studentPlacementForm" property="listRSAProvinces"  value="code" label="description"/>
					</html:select>                                           
				</td>
			</tr>		
			</sakai:group_table>
			<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>
		<hr/>
		<logic:notEmpty name="studentPlacementForm" property="listSubProvincesOfProvince">
			<sakai:flat_list>
		  	<tr >
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.subprovince"/></th>	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.province"/></th>
				
				</tr>
			   <logic:iterate name="studentPlacementForm" property="listSubProvincesOfProvince"  id="rec" indexId="index">
				<tr>
					<td style="white-space:nowrap;align:left">	
						<html:multibox property="indexNrSelected"><bean:write name="index"/></html:multibox>				
						<bean:write name="rec" property="description"/>
					</td>
					<td><bean:write name="rec" property="provinceDescription"/></td>
					</tr>
			  </logic:iterate>
			</sakai:flat_list>
			<sakai:actions>
				<html:submit property="action">
					<fmt:message key="button.add"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.edit"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.delete"/>
			</html:submit>	
		</sakai:actions>		
		</logic:notEmpty>
	</html:form>
</sakai:html>