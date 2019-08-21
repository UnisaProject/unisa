<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/district">
		        <sakai:messages/>
		        <sakai:messages message="true"/>
				<sakai:group_heading>
		                  	<fmt:message key="heading.linksubprovtoditrict"/>
		        </sakai:group_heading>
				<sakai:group_table>	
					<tr>
				         <td><fmt:message key="prompt.column.district"/></td>
				        <td><bean:write name="studentPlacementForm" property="district.description"/></td>	
				</tr>
				<tr>
				         <td><fmt:message key="prompt.column.province"/></td>
				        <td><bean:write name="studentPlacementForm" property="district.provinceName"/></td>	
				</tr>
				<tr>
							<td style="white-space:nowrap;align:top"><fmt:message key="prompt.column.subprovince"/></td>
						    <td><html:select name="studentPlacementForm" property="district.subProvCode"  >
							              <html:optionsCollection name="studentPlacementForm" property="listSubProvincesOfProvince" value="code" label="description"/>
					               	</html:select>
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