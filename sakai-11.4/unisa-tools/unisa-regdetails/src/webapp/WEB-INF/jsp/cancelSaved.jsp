<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<sakai:messages/>
<sakai:messages message="true"/>		
<h3><fmt:message key="page.heading.cancel"/></h3>
	
<!-- Form -->
<html:form action="/cancel" >
	<html:hidden property="goto" value="home"/>
	
	
 <p><fmt:message key="page.cancel.saved1"/><fmt:message key="page.cancel.saved2"/><br/><br/>
	<logic:present name="stamp">
		<fmt:message key="page.step4.date"/>&nbsp;<bean:write name="stamp"/><br/><br/>
	</logic:present>
 </p>
 
 <sakai:actions>		
		<html:submit property="action"><fmt:message key="button.regdetails"/></html:submit>
 </sakai:actions>
			
</html:form>

</sakai:html>