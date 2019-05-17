package za.ac.unisa.lms.tools.brochures.dao;
import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Calendar;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.brochures.constants.SLPQuery;
import za.ac.unisa.lms.tools.brochures.forms.BrochuresForm;

public class MyShortLearningProgrammesDAO extends StudentSystemDAO{

	/*
	 * mySLP
	 */
	public Document mySLPXML(String collCode, String schCode, String dptCode, int year) throws Exception {

		SLPQuery slpQuery = new SLPQuery();
		String query;

		query = slpQuery.mySLPQuery(collCode, schCode, dptCode, year);
	
		System.out.println("My SLP sql+++++++++++++--"+query);

			try {

				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				Document document = documentBuilder.newDocument();

				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());

				List slpInfo = jdt.queryForList(query);

				String collegeName = "";
				String collegeCode;
				String dptName = "";
				String deptCode;
				String qualCatName = "";
				String qualName;
				String qualCode;
				String qualYear;
				String targetGroup;
				String admissionRequirement;
				String duration;
				String languageMedium;
				String registrationPeriods;
				String tuitionMethod;
				String kindOfAssessment;
				String leaderDetails;
				String programmeAdmin;
				String purposeStatement;
				
				Element colleges = null;
				Element college = null;
				Element department = null;
				Element qualificationCategory = null;
				Element qualification = null;
				
				colleges = document.createElement("colleges");
				document.appendChild(colleges);

				Iterator i = slpInfo.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();


					//college
					if(! collegeName.equals(data.get("collegeDesc").toString())){
						college = document.createElement("college");
						colleges.appendChild(college);
						
						collegeName = data.get("collegeDesc").toString();
						Element collName = document.createElement("name");
						collName.appendChild(document
								.createTextNode(collegeName));
						college.appendChild(collName);
						
						collegeCode = data.get("collegeCode").toString();
						Element eCollCode = document.createElement("code");
						eCollCode.appendChild(document
								.createTextNode(collegeCode));
						college.appendChild(eCollCode);
					}

					
					//department
					if(! dptName.equals(data.get("departmentDesc").toString())){
						department = document.createElement("department");
						college.appendChild(department);
																					
						dptName = data.get("departmentDesc").toString();
						Element departmentName = document.createElement("name");
						departmentName.appendChild(document
								.createTextNode(dptName));							
						department.appendChild(departmentName);		
						
						deptCode = data.get("departmentCode").toString();
						Element departmentCode = document.createElement("code");
						departmentCode.appendChild(document
								.createTextNode(deptCode));							
						department.appendChild(departmentCode);							
					}
	
					
					//category
					if(! qualCatName.equals(data.get("qualCategory").toString())){
						qualificationCategory = document.createElement("qualificationcategory");
						department.appendChild(qualificationCategory);
						
						qualCatName = data.get("qualCategory").toString();
						Element qualCategory = document.createElement("name");
						qualCategory.appendChild(document
								.createTextNode(qualCatName));							
						qualificationCategory.appendChild(qualCategory);						
					}
	
					
					//qualification details
					qualification = document.createElement("qualification");
					qualificationCategory.appendChild(qualification);
					
					qualName = data.get("qualDescription").toString();
					Element qualificationName = document.createElement("name");
					qualificationName.appendChild(document
							.createTextNode(qualName));							
					qualification.appendChild(qualificationName);	
					
					qualCode = data.get("code").toString();
					Element qualificationCode = document.createElement("code");
					qualificationCode.appendChild(document
							.createTextNode(qualCode));							
					qualification.appendChild(qualificationCode);	
					
					qualYear = data.get("year").toString();
					Element qualificationYear = document.createElement("year");
					qualificationYear.appendChild(document
							.createTextNode(qualYear));							
					qualification.appendChild(qualificationYear);	
					
					targetGroup = data.get("TARGET_GROUP").toString();
					Element qualificationTarget = document.createElement("target_group");
					qualificationTarget.appendChild(document
							.createTextNode(targetGroup));							
					qualification.appendChild(qualificationTarget);	
										
					admissionRequirement = data.get("adminReq").toString();
					Element qualificationRequirements = document.createElement("admission_requirements");
					qualificationRequirements.appendChild(document
							.createTextNode(admissionRequirement));							
					qualification.appendChild(qualificationRequirements);
					
					duration = data.get("duration").toString();
					Element qualificationDuration = document.createElement("duration");
					qualificationDuration.appendChild(document
							.createTextNode(duration));							
					qualification.appendChild(qualificationDuration);
					
					languageMedium = data.get("languageMedium").toString();
					Element qualificationLanguage = document.createElement("language_medium");
					qualificationLanguage.appendChild(document
							.createTextNode(languageMedium));							
					qualification.appendChild(qualificationLanguage);
					
					registrationPeriods = data.get("registrationPeriods").toString();
					Element qualificationRegPeriods = document.createElement("registration_periods");
					qualificationRegPeriods.appendChild(document
							.createTextNode(registrationPeriods));							
					qualification.appendChild(qualificationRegPeriods);

					tuitionMethod = data.get("tuitionMethod").toString();
					Element qualTuitionMethod = document.createElement("tuition_method");
					qualTuitionMethod.appendChild(document
							.createTextNode(tuitionMethod));							
					qualification.appendChild(qualTuitionMethod);
					
					kindOfAssessment = data.get("kindOfAssessment").toString();
					Element qualAssessment = document.createElement("kind_of_assessment");
					qualAssessment.appendChild(document
							.createTextNode(kindOfAssessment));							
					qualification.appendChild(qualAssessment);
					
					leaderDetails = data.get("leaderDetails").toString();
					Element qualLeader = document.createElement("course_leader_details");
					qualLeader.appendChild(document
							.createTextNode(leaderDetails));							
					qualification.appendChild(qualLeader);
					
					programmeAdmin = data.get("programmeAdmin").toString();
					Element qualAdmin = document.createElement("programme_administrator");
					qualAdmin.appendChild(document
							.createTextNode(programmeAdmin));							
					qualification.appendChild(qualAdmin);
					
					purposeStatement = data.get("purposeStatement").toString();
					Element qualPurpose = document.createElement("purpose_statement");
					qualPurpose.appendChild(document
							.createTextNode(purposeStatement));							
					qualification.appendChild(qualPurpose);
					

				}
				return document;

			} catch (Exception ex) {

				throw new Exception(
						"za.ac.unisa.lms.tools.brochures.dao.MyShortLearningProgrammesDAO: mySLPXML: Error occurred while trying to retrieve mySLP Brochure::: "
								+ ex, ex);

			}
		}
}