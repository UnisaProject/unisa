
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.welcome.ApplicationResources"/>

<sakai:html>
	<html:form action="welcomedisplay">
		<sakai:heading><fmt:message key="function.headingEdit"/></sakai:heading>
		<sakai:messages/>
		<sakai:instruction><fmt:message key="function.editinstruction"/></sakai:instruction>
		<br>
		<sakai:heading><fmt:message key="function.subheadingedit"/></sakai:heading>
		<%-- Unisa Changes:2018/09/27:Rename content variable to eliminate conflict with ckeditor v4.5.7 --%>
		<%-- <sakai:new_html_area property="content"></sakai:new_html_area> --%>
		<sakai:new_html_area property="welcomeContent"></sakai:new_html_area>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.submit"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
			<html:submit property="action"><fmt:message key="button.revert"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>