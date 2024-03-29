package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.List;
import za.ac.unisa.lms.tools.tpustudentplacement.model.modelImpl.SupervisorImpl;
import za.ac.unisa.lms.tools.tpustudentplacement.uiLayer.ProvinceUI;

import org.apache.struts.util.LabelValueBean;

public class Supervisor  extends SupervisorImpl {
	private Integer code;
	private String title;
	private String initials;
	private String surname;
	private String surpervisorName;//access the name from database
	private String occupation;
	private String countryCode;
	private Short provinceCode;
	private List listArea;
	private String emailAddress;
	private String cellNr;
	private String landLineNr;
	private String faxNr;	
	private String contract;
	private String contractStart;
	private String contractEnd;
	private String physicalAddress1;
	private String physicalAddress2;
	private String physicalAddress3;
	private String physicalAddress4;
	private String physicalAddress5;
	private String physicalAddress6;
	private String physicalPostalCode;
	private String postalAddress1;
	private String postalAddress2;
	private String postalAddress3;
	private String postalAddress4;
	private String postalAddress5;
	private String postalAddress6;
	private String postalCode;
	private String studentsAllocated;
	private String studentsAllowed;
	private String name;
	private String provinceDescr;
	private List provCodesList;
	
	
	public List getProvCodesList(){
		 return provCodesList;
	}
	public void setProvCodesList(List  provCodesList){
		 this.provCodesList= provCodesList;
	}
	  
	public String getName(){
		return name;
		
	}
	public void setName(String name){
		this.name=name;
	}
	
	public String getProvinceDescr(){
		     return provinceDescr;
	}
	public void setProviceDescr(String provinceDescr){
		  this.provinceDescr=provinceDescr;
	}
	
	private List studentPlacementList;
	
	
	    	  public Supervisor getSup(int code) throws Exception {
	           return getSup(code);
         }
		 public String supervisorProvListAsStr(List provincesCodeList)throws Exception {
			             ProvinceUI prov=new ProvinceUI();
			             return prov.provinceListAsString(provincesCodeList);
			             
		 }
		
		 public List getSupervisorList(String country,Short province,String assignable) throws Exception {
                         List supervList=getSupervList(country,province,assignable);
                         Supervisor superv=new Supervisor();
                         LabelValueBean labelValueBean=new LabelValueBean();
                         labelValueBean.setValue("283");
                         labelValueBean.setLabel("--select  supervisor--");
                         supervList.add(0,labelValueBean);
                   return supervList;
        }
		
	    public List  getStudentPlacementList(){
	   	       return studentPlacementList;
	    }
	    public void setStudentPlacementList(List studentPlacementList){
		      this.studentPlacementList=studentPlacementList;
	    }
	    
	
	
	public String getSupervisorFullName(){
		            String supervisorFullName=title+" "+initials+" "+surname; 
		     return supervisorFullName;
	}
	public String getStudentsAllocated(){
		return studentsAllocated;
	}
	public void  setStudentsAllocated(String studentsAllocated){
		     this.studentsAllocated=studentsAllocated;
	}
	public String getStudentsAllowed(){
		return studentsAllowed;
	}
	public void setStudentsAllowed(String studentsAllowed){
		    this.studentsAllowed=studentsAllowed;
	}
	public String getPhysicalPostalCode() {
		return physicalPostalCode;
	}
	public void setPhysicalPostalCode(String physicalPostalCode) {
		this.physicalPostalCode = physicalPostalCode;
	}
	public String getPhysicalAddress4() {
		return physicalAddress4;
	}
	public void setPhysicalAddress4(String physicalAddress4) {
		this.physicalAddress4 = physicalAddress4;
	}
	public String getPhysicalAddress5() {
		return physicalAddress5;
	}
	public void setPhysicalAddress5(String physicalAddress5) {
		this.physicalAddress5 = physicalAddress5;
	}
	public String getPhysicalAddress6() {
		return physicalAddress6;
	}
	public void setPhysicalAddress6(String physicalAddress6) {
		this.physicalAddress6 = physicalAddress6;
	}
	public String getPostalAddress4() {
		return postalAddress4;
	}
	public void setPostalAddress4(String postalAddress4) {
		this.postalAddress4 = postalAddress4;
	}
	public String getPostalAddress5() {
		return postalAddress5;
	}
	public void setPostalAddress5(String postalAddress5) {
		this.postalAddress5 = postalAddress5;
	}
	public String getPostalAddress6() {
		return postalAddress6;
	}
	public void setPostalAddress6(String postalAddress6) {
		this.postalAddress6 = postalAddress6;
	}
	public List getListArea() {
		return listArea;
	}
	public void setListArea(List listArea) {
		this.listArea = listArea;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Short getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(Short provinceCode) {
		this.provinceCode = provinceCode;
	}	
	public String getEmailAddress() {
		return emailAddress;
	}
        public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getCellNr() {
		return cellNr;
	}
	public void setCellNr(String cellNr) {
		this.cellNr = cellNr;
	}
	public String getLandLineNr() {
		return landLineNr;
	}
	public void setLandLineNr(String landLineNr) {
		this.landLineNr = landLineNr;
	}
	public String getFaxNr() {
		return faxNr;
	}
	public void setFaxNr(String faxNr) {
		this.faxNr = faxNr;
	}	
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getContractStart() {
		return contractStart;
	}
	public void setContractStart(String contractStart) {
		this.contractStart = contractStart;
	}
	public String getContractEnd() {
		return contractEnd;
	}
	public void setContractEnd(String contractEnd) {
		this.contractEnd = contractEnd;
	}
	public String getPhysicalAddress1() {
		return physicalAddress1;
	}
	public void setPhysicalAddress1(String physicalAddress1) {
		this.physicalAddress1 = physicalAddress1;
	}
	public String getPhysicalAddress2() {
		return physicalAddress2;
	}
	public void setPhysicalAddress2(String physicalAddress2) {
		this.physicalAddress2 = physicalAddress2;
	}
	public String getPhysicalAddress3() {
		return physicalAddress3;
	}
	public void setPhysicalAddress3(String physicalAddress3) {
		this.physicalAddress3 = physicalAddress3;
	}
	public String getPostalAddress1() {
		return postalAddress1;
	}
	public void setPostalAddress1(String postalAddress1) {
		this.postalAddress1 = postalAddress1;
	}
	public String getPostalAddress2() {
		return postalAddress2;
	}
	public void setPostalAddress2(String postalAddress2) {
		this.postalAddress2 = postalAddress2;
	}
	public String getPostalAddress3() {
		return postalAddress3;
	}
	public void setPostalAddress3(String postalAddress3) {
		this.postalAddress3 = postalAddress3;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
}
