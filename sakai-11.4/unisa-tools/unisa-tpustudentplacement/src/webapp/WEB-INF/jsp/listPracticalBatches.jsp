<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/practicalPeriodMaintenance">
		<!--<html:hidden property="currentPage" value="displayDistrictList"/>-->
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.practicalperiodmaintenance"/>
			<sakai:group_heading>
			<fmt:message key="heading.practicalperiodmaintenanceInfo"/> 
		</sakai:group_heading>		
	
		</sakai:heading>	
			<sakai:group_table>				
			<tr>
				<td><fmt:message key="prompt.province"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.provCode">
						<html:optionsCollection name="studentPlacementForm" property="listProvincesforview"  value="code" label="description"/>
					</html:select>                                           
				</td>
				<td><fmt:message key="prompt.level"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.level">
						<html:optionsCollection name="studentPlacementForm" property="listLevelsforview"  value="level" label="levelDescription"/>
					</html:select>                                           
				</td>
			</tr>		
			<tr>
				<td ><fmt:message key="prompt.practicalperiod"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.practicalPeriod">
						<html:optionsCollection name="studentPlacementForm"   property="listPracticalPeriodsforview"  value="period" label="periodDescription"/>
					</html:select>                                           
				</td>
				<td  ><fmt:message key="prompt.academicyear"/>&nbsp;</td>
				<td><html:select name="studentPlacementForm" property="practiceBatchDate.academicYear" >
						<html:optionsCollection name="studentPlacementForm"   property="listPracticalYearforview"  value="year" label="yearDescription"/>
					</html:select>                                           
				</td>                        
			</tr>		
			</sakai:group_table>
			<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.add"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>
		<hr/>
		<logic:notEmpty name="studentPlacementForm" property="practiceBatchDateList">
			<sakai:flat_list>
		  	<tr >
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.fromdate"/></th>		
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.todate"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.level"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.practicalperiod"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.numOfDays"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.province"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.academicyear"/></th>
				</tr>
			   <logic:iterate name="studentPlacementForm" property="practiceBatchDateList"  id="rec" indexId="index">
				<tr>
					<td style="white-space:nowrap;align:left">	
						<html:multibox property="indexNrSelected"><bean:write name="index"/></html:multibox>				
						<bean:write name="rec" property="fromDate"/>
					</td>
					<td><bean:write name="rec" property="toDate"/></td>
					<td><bean:write name="rec" property="level"/></td>
					<td><bean:write name="rec" property="practicalPeriod"/></td>
					<td><bean:write name="rec" property="practicalDays"/></td>
					<td><bean:write name="rec" property="provDescr"/></td>
					<td><bean:write name="rec" property="academicYear"/></td>
						</tr>
			  </logic:iterate>
			</sakai:flat_list>
			<sakai:actions>
				<html:submit property="action">
					<fmt:message key="button.edit"/>
			   </html:submit>	
				<html:submit property="action">
					<fmt:message key="button.delete"/>
			 </html:submit>		
				<html:submit property="action">
					<fmt:message key="button.copy"/>
			</html:submit>			
		</sakai:actions>		
				</logic:notEmpty>
				
	</html:form>
</sakai:html>