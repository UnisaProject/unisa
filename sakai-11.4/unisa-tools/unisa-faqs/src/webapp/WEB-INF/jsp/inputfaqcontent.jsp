
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.faqs.ApplicationResources"/>

<sakai:html>
  <html:form action="/faqcontent">
 <sakai:messages/>

  <sakai:heading><fmt:message key="faq.add"/></sakai:heading>
  <sakai:instruction><fmt:message key="faq.instruction"/><br><fmt:message key="faq.required"/>&nbsp;<sakai:required/></sakai:instruction>
  <!--<sakai:group_heading><fmt:message key="faq.add.heading"/></sakai:group_heading>-->
  <sakai:group_table>
		<tr>
			<td>
				<label><fmt:message key="faq.field.category"/>&nbsp;<sakai:required/></label>

			</td>
			<td>
				<html:select property="content.categoryId">
					<html:option value="-1">&nbsp;</html:option>
					<html:options collection="categories" property="categoryId" labelProperty="description"/>
				</html:select>
			</td>


		</tr>
		<tr>
			<td>
				<label><fmt:message key="faq.field.category1"/></label>

			</td>
			<td>
				<html:text property="category.description" size="50" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td>
				<label><fmt:message key="faq.questiontitle"/>&nbsp;<sakai:required/></label>
			</td>
			<td>
				<html:text property="content.question" size="50" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td>
				<label><fmt:message key="faq.answer"/>&nbsp;<sakai:required/></label>
			</td>
			<td>
				<sakai:html_area property="content.answer"/>
			</td>
		</tr>
	</sakai:group_table>
	<sakai:actions>
	  <html:hidden property="action" value="inputSave"/>
	  <html:submit styleClass="active" titleKey="button.save"/>
	  <html:cancel titleKey="button.cancel"/>
	</sakai:actions>
  </html:form>
</sakai:html>
