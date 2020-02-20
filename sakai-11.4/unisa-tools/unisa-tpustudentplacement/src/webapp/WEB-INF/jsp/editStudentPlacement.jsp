<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>
s
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
        	 $( "#startDateSecPrd" ).datepicker({
                 showOn:"button",
                 buttonImage:"https://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                 buttonImageOnly: true,
                 altField: '#startDateSecPrd',
                 dateFormat: 'yy/mm/dd',
                 beforeShowDay: $.datepicker.noWeekends
              });
        	 $( "#endDateSecPrd" ).datepicker({
                showOn:"button",
                buttonImage: "https://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
                buttonImageOnly: true,
                altField: '#endDateSecPrd',
                dateFormat: 'yy/mm/dd',
                beforeShowDay: $.datepicker.noWeekends
             });
        	 });
         
      </script> 
      <script type="text/javascript">
      </script>
  </head>  	
	<html:form action="/studentPlacement">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.studentPlacement.editPlacement"/> 
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>	
		<sakai:group_heading>
			<fmt:message key="heading.studentPlacement.studentInfo"/> 
		</sakai:group_heading>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>/<fmt:message key="prompt.semester"/></td>
				<td><bean:write name="studentPlacementForm" property="acadYear"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.studentNr"/></td>
				<td><bean:write name="studentPlacementForm" property="student.number"/></td>
			</tr>	
			<tr>
				<td><fmt:message key="prompt.studentName"/></td>
				<td><bean:write name="studentPlacementForm" property="student.name"/>
					<html:submit property="action">
						<fmt:message key="button.contactDetails"/>
					</html:submit>	
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.qualification"/></td>
				<td><bean:write name="studentPlacementForm" property="student.qualification.description"/></td>
			</tr>				
		</sakai:group_table>
		<hr/>	
		<sakai:group_heading>
			<fmt:message key="heading.studentPlacement.modulePlacement"/> 
		</sakai:group_heading>	
		<sakai:group_table>
			<tr>
				<td><fmt:message key="prompt.module"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="studentPlacement.module"  onchange="submit();">
						<html:options name="studentPlacementForm" property="student.listPracticalModules"/>
					</html:select>                                           
				</td>
			</tr>	
			<tr>
				 			<td><fmt:message key="prompt.school"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="studentPlacement.schoolDesc" size="40" maxlength="60" readonly="true"/>
				<html:submit property="action">                      
					<fmt:message key="button.searchSchool"/>
				</html:submit>				
			</tr> 
			<tr>
			  <td><fmt:message key="prompt.fulltimeStu"/></td>
				<td><html:select name="studentPlacementForm" property="studentPlacement.stuFullTime" >
							<html:optionsCollection name="studentPlacementForm" property="listMentorTrained" value="value" label="label"/>
					</html:select>
				</td>
			</tr> 
			<tr>
			  <tr>
				   <td><fmt:message key="prompt.mentor"/>
				    <td><html:text name="studentPlacementForm" property="studentPlacement.mentorName" size="30" maxlength="28" readonly="true"/>
				    <html:submit property="action">                      
					    <fmt:message key="button.searchMentor"/>
				    </html:submit>				
			     </tr>
			  <tr>
			   <td><fmt:message key="prompt.town"/></td>
				<td><bean:write name ="studentPlacementForm" property="studentPlacement.town" /></td>
			</tr>
			<logic:notEqual name="studentPlacementForm" property="currentPage"   value="editPrelimPlacement">
			    <tr>
				          <td><fmt:message key="prompt.supervisor"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				          <td>
				                       <html:text name="studentPlacementForm" property="studentPlacement.supervisorName" size="30" maxlength="28" readonly="true"/>
				                      <html:submit property="action">                      
					                       <fmt:message key="button.searchSupervisor"/>
				                        </html:submit>				
			     </tr>
			</logic:notEqual>
			<logic:equal name="studentPlacementForm" property="currentPage"   value="editPrelimPlacement">
			     <tr>
				     <td><fmt:message key="prompt.supervisor"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				     <td><html:text name="studentPlacementForm" property="studentPlacement.supervisorName" size="30" maxlength="28" readonly="true"/>
				         <html:select name="studentPlacementForm" property="studentPlacement.supervisorCode"  >
						      <html:optionsCollection name="studentPlacementForm" property="listProvSupervisor"    value="value"  label="label"/>
					     </html:select>    
				         <html:submit property="action">                      
					            <fmt:message key="button.searchSupervisor"/>
				         </html:submit>	
				    </td>			
			      </tr>
			</logic:equal>     
			<logic:equal name="studentPlacementForm" property="student.countryCode"   value="1015">
			                               	       <logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="editPrelimPlacement">
			                               	          <logic:equal name="studentPlacementForm" property="isPGCE"   value="Y">
			                               	                    <tr><td align="left"  colspan=3><fmt:message key="prompt.pracprds"/></td></tr> 
				                                                       <tr> <td ><fmt:message key="prompt.pracprd"/></td>
				                                                       <td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                <input type="text" name="startDate" size="14" maxlength="10" id="startDate"   value='<%=request.getAttribute("startDate")%>'/>
				                                                                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                 <input type="text"  name="endDate" size="14" maxlength="10"  id="endDate"  value='<%=request.getAttribute("endDate")%>'/>
				                                                                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                       <html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="2"/></td>
			                                                     </tr>	
			                                       </logic:equal>
			                               	         <logic:equal name="studentPlacementForm" property="isPGCE"   value="N">
			                                 	             <tr><td align="left"  colspan=2><fmt:message key="prompt.pracprds"/></td></tr> 
			                                 	              <tr>
			                                 	                <tr>
			                                                   <td  colspan="2"><fmt:message key="prompt.pracprd"/></td>
			                                                   </tr>
			                                                             <tr>
			                                                                          <td >  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.firstperiodselectedblock"/></td>
				                                                                       <td>
			                                 	                                                        <bean:write name="studentPlacementForm"  property="studentPlacement.startDateView"/>
			                                 	                                                          <logic:notEmpty  name="studentPlacementForm" property="studentPlacement.startDate" >
			                                 	                                                                  <fmt:message key="prompt.dash"/>
			                                 	                                                             </logic:notEmpty>
			                                 	                                                   <bean:write name="studentPlacementForm"  property="studentPlacement.endDateView"/>
			                                 	                                        </td>
			                                 	                         </tr> 
			                                 	                <tr>
				                                                         <td>&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
				                                                                    <fmt:message key="prompt.selectpracprd"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                         </td>
				                                                         <td>
				                                                                <html:select name="studentPlacementForm" property="practiceBatchDateListsIndex"  onchange="submit();">
						                                                                 <html:optionsCollection name="studentPlacementForm" property="practiceBatchDateList"    value="index"  label="practicalDateRange"/>
					                                                            </html:select>
					                                                     </td>
				                                                 </tr>
				                                                  <tr>
			                                                          <td>
				                                                       &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/></td>
				                                                             <td><html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="2"/>
				                                                        </td>
				                                                     </tr>
			                                               	<logic:notEqual name="studentPlacementForm" property="studyLevel"   value="1">
			                                                	<logic:notEmpty  name="studentPlacementForm" property="studentPlacement.startDateSecPracPeriodView" >
			                                                    <tr>
			                                                      <td  colspan="2"><fmt:message key="prompt.secpracprd"/></td>
			                                                    </tr>
			                                                            <tr><td > &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.secperiodselectedblock"/>  
			                                 	                              </td> 
			                                 	                              <td > 
			                                 	                                      <bean:write name="studentPlacementForm"  property="studentPlacement.startDateSecPracPeriodView"/>
			                                 	                                         <logic:notEmpty  name="studentPlacementForm" property="studentPlacement.startDateSecPracPeriodView" >
			                                 	                                                       <fmt:message key="prompt.dash"/>
			                                 	                                         </logic:notEmpty>
			                                 	                                     <bean:write name="studentPlacementForm"  property="studentPlacement.endDateSecPracPeriodView"/>
			                                 	                               </td> 
			                                 	                        </tr> 
			                                                              <tr>
			                                                                      <td> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
			                                                                              <fmt:message key="prompt.selectpracprd"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                   </td>
			                                                                       <td>
			                                                                            <html:select name="studentPlacementForm" property="practiceBatchDateSecPracPrdListsIndex"  onchange="submit();">
						                                                                      <html:optionsCollection name="studentPlacementForm" property="practiceBatchDateSecPracPrdList"    value="index"  label="practicalDateRange"/>
					                                                                   </html:select>    
				                                                                </td>
			                                                         </tr>	
			                                                          <tr>
			                                                          <td>
				                                                           &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/></td>
				                                                             <td><html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeksSecPracPrd" size="4" maxlength="2"/>
				                                                        </td>
				                                                     </tr>
				                                                     </logic:notEmpty>
			                                                   </logic:notEqual>
			                                      </logic:equal>
			                            </logic:equal>
			                           	<logic:notEqual name="studentPlacementForm" property="studentPlacementAction"   value="editPrelimPlacement">
			                           	<logic:equal name="studentPlacementForm" property="isPGCE"   value="Y">
			                                                          <tr><td align="left"  colspan=3><fmt:message key="prompt.pracprds"/></td></tr> 
				                                                       <tr> <td ><fmt:message key="prompt.pracprd"/></td>
				                                                       <td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                <input type="text" name="startDate" size="14" maxlength="10" id="startDate"   value='<%=request.getAttribute("startDate")%>'/>
				                                                                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                 <input type="text"  name="endDate" size="14" maxlength="10"  id="endDate"  value='<%=request.getAttribute("endDate")%>'/>
				                                                                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                       <html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="6"/></td>
			                                                    
			                                      </logic:equal>
			                                         <logic:equal name="studentPlacementForm" property="isPGCE"   value="N">
			                                                   <tr><td align="left"  colspan=2><fmt:message key="prompt.pracprds"/></td></tr> 
			                                 	              <tr>
			                                 	                <tr>
			                                                   <td  colspan="2"><fmt:message key="prompt.pracprd"/></td>
			                                                   </tr>
			                                                   <logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="edit">
			                                                             <tr>
			                                                                          <td >  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.firstperiodselectedblock"/></td>
				                                                                       <td>
			                                 	                                                        <bean:write name="studentPlacementForm"  property="studentPlacement.startDateView"/>
			                                 	                                                          <logic:notEmpty  name="studentPlacementForm" property="studentPlacement.startDate" >
			                                 	                                                                  <fmt:message key="prompt.dash"/>
			                                 	                                                             </logic:notEmpty>
			                                 	                                                   <bean:write name="studentPlacementForm"  property="studentPlacement.endDateView"/>
			                                 	                                        </td>
			                                 	                         </tr> 
			                                 	               </logic:equal>
			                                 	                <tr>
				                                                         <td>&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
				                                                                    <fmt:message key="prompt.selectpracprd"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                         </td>
				                                                         <td>
				                                                                <html:select name="studentPlacementForm" property="practiceBatchDateListsIndex"  onchange="submit();">
						                                                                 <html:optionsCollection name="studentPlacementForm" property="practiceBatchDateList"    value="index"  label="practicalDateRange"/>
					                                                            </html:select>
					                                                     </td>
				                                                 </tr>
				                                                  <tr>
			                                                          <td>
				                                                       &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/></td>
				                                                             <td><html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="2"/>
				                                                        </td>
				                                                     </tr>
				                                        <logic:notEqual name="studentPlacementForm" property="studentPlacementAction"   value="edit">
			                                               	<logic:notEqual name="studentPlacementForm" property="studyLevel"   value="1">
			                                                	  <tr>
			                                                      <td  colspan="2"><fmt:message key="prompt.secpracprd"/></td>
			                                                    </tr>
			                                                     <logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="edit">
			                                                            <tr><td > &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.secperiodselectedblock"/>  
			                                 	                              </td> 
			                                 	                              <td > 
			                                 	                                      <bean:write name="studentPlacementForm"  property="studentPlacement.startDateSecPracPeriodView"/>
			                                 	                                         <logic:notEmpty  name="studentPlacementForm" property="studentPlacement.startDateSecPracPeriodView" >
			                                 	                                                       <fmt:message key="prompt.dash"/>
			                                 	                                         </logic:notEmpty>
			                                 	                                     <bean:write name="studentPlacementForm"  property="studentPlacement.endDateSecPracPeriodView"/>
			                                 	                               </td> 
			                                 	                        </tr> 
			                                 	                        </logic:equal>
			                                                              <tr>
			                                                                      <td> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
			                                                                              <fmt:message key="prompt.selectpracprd"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                   </td>
			                                                                       <td>
			                                                                            <html:select name="studentPlacementForm" property="practiceBatchDateSecPracPrdListsIndex"  onchange="submit();">
						                                                                      <html:optionsCollection name="studentPlacementForm" property="practiceBatchDateSecPracPrdList"    value="index"  label="practicalDateRange"/>
					                                                                   </html:select>    
				                                                                </td>
			                                                         </tr>	
			                                                          <tr>
			                                                          <td>
				                                                           &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/></td>
				                                                                <td><html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeksSecPracPrd" size="4" maxlength="2"/>
				                                                        </td>
				                                                     </tr>
				                                                     </logic:notEqual>
				                                                  </logic:notEqual>
			                                         </logic:equal>
			                            </logic:notEqual>
				    </logic:equal>
					 <logic:notEqual name="studentPlacementForm"  property="student.countryCode"   value="1015">
					                            	<logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="editPrelimPlacement">
					                                                     <tr><td align="left"  colspan=3><fmt:message key="prompt.pracprds"/></td></tr> 
				                                                       <tr> <td ><fmt:message key="prompt.pracprd"/></td>
				                                                       <td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                <input type="text" name="startDate" size="14" maxlength="10" id="startDate"   value='<%=request.getAttribute("startDate")%>'/>
				                                                                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                 <input type="text"  name="endDate" size="14" maxlength="10"  id="endDate"  value='<%=request.getAttribute("endDate")%>'/>
				                                                                  &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                 <html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="2"/></td>
			                                                          </tr>	
			                                                          	<logic:notEqual name="studentPlacementForm" property="studyLevel"   value="1">
			                                                                     <tr>     
			                                                                          <td ><fmt:message key="prompt.secpracprd"/></td>
				                                                                     <td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                          <input type="text" name="startDateSecPrd" size="14" maxlength="10" id="startDateSecPrd"   value='<%=request.getAttribute("startDateSecPrd")%>'/>
				                                                                          &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  <fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
			                                                                              <input type="text"  name="endDateSecPrd" size="14" maxlength="10"  id="endDateSecPrd"  value='<%=request.getAttribute("endDateSecPrd")%>'/>
			                                                                              &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/>
			                                                                              <html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeksSecPracPrd" size="4" maxlength="2"/>
			                                                                         </td>
			                                                         </tr>	
			                                                         	</logic:notEqual>
			                                  </logic:equal>
			                              <logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="add">
			                                <tr><td align="left"  colspan=3><fmt:message key="prompt.pracprds"/></td></tr> 
				                                                       <tr> <td ><fmt:message key="prompt.pracprd"/></td>
				                                                       <td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                          <input type="text" name="startDate" size="14" maxlength="10" id="startDate"   value='<%=request.getAttribute("startDate")%>'/>
				                                                           &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                             <input type="text"  name="endDate" size="14" maxlength="10"  id="endDate"  value='<%=request.getAttribute("endDate")%>'/>
				                                                                 &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                            <html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="6"/></td>
			                                               </tr>
			                                               <logic:notEqual name="studentPlacementForm" property="studyLevel"   value="1">
			                                                                     <tr>     
			                                                                          <td ><fmt:message key="prompt.secpracprd"/></td>
				                                                                     <td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                                          <input type="text" name="startDateSecPrd" size="14" maxlength="10" id="startDateSecPrd"   value='<%=request.getAttribute("startDateSecPrd")%>'/>
				                                                                          &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;  <fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
			                                                                              <input type="text"  name="endDateSecPrd" size="14" maxlength="10"  id="endDateSecPrd"  value='<%=request.getAttribute("endDateSecPrd")%>'/>
			                                                                              &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/>
			                                                                              <html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeksSecPracPrd" size="4" maxlength="2"/>
			                                                                         </td>
			                                                         </tr>	
			                                                         	</logic:notEqual>
			                              </logic:equal>
			                                 <logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="edit">
			                                <tr><td align="left"  colspan=3><fmt:message key="prompt.pracprds"/></td></tr> 
				                                                       <tr> <td ><fmt:message key="prompt.pracprd"/></td>
				                                                       <td><fmt:message key="prompt.startDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                          <input type="text" name="startDate" size="14" maxlength="10" id="startDate"   value='<%=request.getAttribute("startDate")%>'/>
				                                                           &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<fmt:message key="prompt.endDate"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                             <input type="text"  name="endDate" size="14" maxlength="10"  id="endDate"  value='<%=request.getAttribute("endDate")%>'/>
				                                                                 &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; <fmt:message key="prompt.numberOfWeeks"/>&nbsp;<fmt:message key="prompt.mandatory"/>
				                                                            <html:text name="studentPlacementForm" property="studentPlacement.numberOfWeeks" size="4" maxlength="6"/></td>
			                                               </tr>
			                              
			                              </logic:equal>
			       </logic:notEqual>
			       <tr>
				                         <td><fmt:message key="prompt.evaluationMark"/>&nbsp;</td>
				                         <td><html:text name="studentPlacementForm" property="studentPlacement.evaluationMark" size="4" maxlength="3"/></td>
			       </tr>	
		</sakai:group_table>	
		<sakai:actions>
		    <logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="add">
			    <html:submit property="action">
					<fmt:message key="button.save"/>
			    </html:submit>
			 </logic:equal>
			 <logic:notEqual name="studentPlacementForm" property="studentPlacementAction"   value="add">  	
			      <html:submit property="action">
				   	   <fmt:message key="button.saveEditedData"/>
			        </html:submit>
			 </logic:notEqual>
			 <html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>
			<logic:equal name="studentPlacementForm" property="studentPlacementAction"   value="editPrelimPlacement">
			       <logic:equal name="studentPlacementForm" property="firstPlacementReached"    value="Y" >	
			            <html:submit property="action"  disabled="true">
				         	 <fmt:message key="button.previous"/>
			             </html:submit>
			        </logic:equal>
			        <logic:notEqual name="studentPlacementForm" property="firstPlacementReached"    value="Y">	
			           <html:submit property="action">
				        	 <fmt:message key="button.previous"/>
			           </html:submit>	
			        </logic:notEqual>	
			        <logic:equal name="studentPlacementForm" property="lastPlacementReached"   value="Y">	
			              <html:submit property="action"  disabled="true">
				     	     <fmt:message key="button.next"/>
			              </html:submit>
			        </logic:equal>
			        <logic:notEqual name="studentPlacementForm" property="lastPlacementReached"   value="Y">		
			              <html:submit property="action">
				     	     <fmt:message key="button.next"/>
			              </html:submit>
			       </logic:notEqual>
			        <html:submit property="action">
					    <fmt:message key="button.correspondence"/>
			        </html:submit>						
		   </logic:equal>
		</sakai:actions>
		<html:hidden property="action" value="supervForProvOnchangeAction"/>						
	</html:form>
</sakai:html>
