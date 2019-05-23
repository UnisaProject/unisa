<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.brochures.ApplicationResources"/>

<sakai:html>
	<html:form action="/brochures">
	<html:hidden property="atStep" value="4"/>	
	<html:hidden property="type" value="${brochuresForm.type}"/>

   <sakai:group_heading>Brochure Output <bean:write name="brochuresForm"  property="type"/></sakai:group_heading>
   	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
<sakai:group_table>
 <tr> 
 	 <td>Year</td>
     <td><html:select property="year" style="width: 350px">
     <html:options collection="yearsList" property="value" labelProperty="label"/>
           </html:select> 
	 	</td>
  </tr>   
 <tr>
	 <td><fmt:message key="college.disc"/></td>
			
			<td>
			<html:select name="brochuresForm" property="collegeCode" onchange="submit();"  style="width: 350px; overflow:auto;"> 
        		    <html:option value="-1">All</html:option>
					<html:optionsCollection name="brochuresForm"  property="colleges"/>
    		</html:select> 
			</td>					
	    </tr>
  
		<tr>
	 <td><fmt:message key="schools.disc"/></td>
			
			<td>
			<html:select name="brochuresForm" property="schCode" onchange="submit();"  style="width: 350px; overflow:auto;">
					<html:option value="-1">All</html:option>	 
        		    <html:optionsCollection  name="brochuresForm" property="schList"/>
    		</html:select> 
			</td>					
	    </tr> <tr>
	 <td><fmt:message key="dpt.disc"/></td>
			
			<td>
			<html:select name="brochuresForm" property="dptCode" onchange="submit();"  style="width: 350px; overflow:auto;">
					<html:option value="-1">All</html:option>	 
        		    <html:optionsCollection  name="brochuresForm" property="dptList"/>
    		</html:select> 
			</td>					
	    </tr>



  

    <tr> 
 	 <td>Output Format</td>
 <td> <html:select property="format" style="width: 350px">
 	    <!--<html:option value="1">Word Format</html:option>-->
 	    <html:option value="2">XML Format</html:option>		         	
    	</html:select> </td>
    </tr>
</sakai:group_table>
<sakai:group_table>
   <sakai:actions>
     <tr><td>

			<html:submit styleClass="button" property="act">
				<fmt:message key="button.export"/>
		    </html:submit>

		     </td>
		     <td>

			<html:submit styleClass="button" property="act">
			    <fmt:message key="button.back"/>
			</html:submit>

		     </td>
		 </tr>

		<html:hidden property="act" value="mySLP"/>
	</sakai:actions>
  </sakai:group_table>

	</html:form>
</sakai:html> 