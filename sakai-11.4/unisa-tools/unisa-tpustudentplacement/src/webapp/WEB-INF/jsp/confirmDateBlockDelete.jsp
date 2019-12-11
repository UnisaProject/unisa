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
			<table  border="0">		
			                 <tr>
				                       <td><p style="color:white;">an invisible table was put here to avoid displaying a heading for this page  </p>            
				                                 </td>
				                </tr>
		   </table>
		  	<table  border="1">	
		  	               <tr>
				                       <th>Confirmation</th>
				          </tr>
				          <logic:equal name="studentPlacementForm" property="currentPage"   value="confirmDateBlockDelete">
			             <logic:equal name="studentPlacementForm" property="dateBlockAssigned"   value="Y">
			                   <tr>
				                       <td><p style="color:red;">A  date block  you selected to remove has already been assigned to a  placement,<br>
				                       Do you want to proceed ?  </p>            
				                        <sakai:actions>
				                                                      <html:submit property="action">
					                                                              <fmt:message key="button.delete"/>
			                                                          </html:submit>
			                                                          <html:submit property="action">
					                                                                    <fmt:message key="button.cancel"/>
			                                                          </html:submit>
		                                                   </sakai:actions>                          
				                       </td>
				                </tr>
				        </logic:equal>
				        <logic:notEqual name="studentPlacementForm" property="dateBlockAssigned"  value="Y">
			                         <tr>
				                                  <td><p style="color:red;">Are you sure,you want to delete the date block(s)   ?</p>  
				                                   <sakai:actions>
				                                                      <html:submit property="action">
					                                                              <fmt:message key="button.delete"/>
			                                                          </html:submit>
			                                                          <html:submit property="action">
					                                                                    <fmt:message key="button.cancel"/>
			                                                          </html:submit>
		                                                   </sakai:actions>                                    
				                                  </td>
			                        </tr>		
			                        	
			          </logic:notEqual>
			           </logic:equal>
			           <logic:equal name="studentPlacementForm" property="currentPage"   value="confirmDateBlockDeleteForCopyDates">
			                    <tr>
				                       <td><p style="color:red;">Copying date blocks of the selected province will  overwrite existing data for the other provinces,<br>
				                       Do you want to proceed ?  </p>            
				                        <sakai:actions>
				                                                      <html:submit property="action">
					                                                              <fmt:message key="button.delete"/>
			                                                          </html:submit>
			                                                          <html:submit property="action">
					                                                                    <fmt:message key="button.cancel"/>
			                                                          </html:submit>
		                                                   </sakai:actions>                          
				                       </td>
				                </tr>
				        </logic:equal>
			         </table>
				</html:form>
</sakai:html>