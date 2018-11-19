/**********************************************************************************
 *
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/java/org/etudes/tool/melete/SortModuleSectionPage.java $
 * $Id: SortModuleSectionPage.java 78261 2012-01-24 21:27:38Z rashmi@etudes.org $
 ***********************************************************************************
 *
 * Copyright (c) 2009, 2010, 2011 Etudes, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 **********************************************************************************/

package org.etudes.tool.melete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etudes.api.app.melete.ModuleObjService;
import org.etudes.api.app.melete.ModuleService;
import org.etudes.api.app.melete.SectionBeanService;
import org.etudes.api.app.melete.SectionService;
import org.etudes.api.app.melete.exception.MeleteException;
import org.sakaiproject.util.ResourceLoader;

public class SortModuleSectionPage implements Serializable{

	private String formName;
	private int showSize;
	private String newSelectedModule = "1";
	private List currModulesList;
	private List currList;
	private List newModulesList;
	private List newList;

	// sort sections
	private List allModulesList;
	private List allModules;
	private String currModule;
	private List currSectionsList;
	private List currSecList;
	private List newSectionsList;
	private List newSecList;
	private String newSelectedSection;

	/** Dependency:  The logging service. */
	protected Log logger = LogFactory.getLog(SortModuleSectionPage.class);
	protected ModuleService moduleService;
	protected SectionService sectionService;


	
	/** Reset values on list auth page and redirect
	 * @return list_auth_modules
	 */
	public String cancel()
	{
		FacesContext context = FacesContext.getCurrentInstance();
		ValueBinding binding = Util.getBinding("#{listAuthModulesPage}");
		ListAuthModulesPage lPage = (ListAuthModulesPage) binding.getValue(context);
		lPage.resetValues();
		return "list_auth_modules";
	}

	/**Reset all values
	 * 
	 */
	public void resetValues()
	{
		currModulesList = null;
		currList= null;
		showSize = 6;
		newModulesList= null;
		newList= null;
		newSelectedModule="1";
		currSectionsList = null;
		currSecList= null;
		newSectionsList= null;
		newSecList= null;
		allModules = null;
		allModulesList = null;
		currModule = null;
	}

	/**Get the current sequence of modules
	 * @return list of select items with current sequence of modules
	 */
	public List getCurrList()
	{
		try{
			if(currModulesList == null)
			{
				FacesContext context = FacesContext.getCurrentInstance();
				ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
		    	MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
		    	
				currModulesList = new ArrayList();
				currModulesList = getModuleService().getModules(mPage.getCurrentSiteId());
				currList  = forSelectItemsList(currModulesList);
			}
		} catch(Exception e)
		{
			logger.debug("error in getting currList "+e.toString());
		}
		return currList;
	}

	/**
	 * @return Returns the newList.
	 */
	public List getNewList() {
		try{
			if(newModulesList == null)
			{
				if(currModulesList == null)getCurrList();
				newModulesList = currModulesList;
				newList  = forSelectItemsList(newModulesList);
			}

		} catch(Exception e)
		{
			logger.debug("error in getting currList "+e.toString());
		}
		return newList;
	}
	/**
	 * @param newList The newList to set.
	 */
	public void setNewList(List newList) {
		this.newList = newList;
	}

	
	/*Converts the coursemodule list to selectItems for displaying at
	 * the list boxes in the JSF page
	 * @param list list of coursemodule objects
	 * @return list of selectItems
	 */
	private List forSelectItemsList(List list)
	{
		List selectList = new ArrayList();
		// Adding available list to select box
		if(list == null || list.size()==0)
		{
			selectList.add(new SelectItem("0", "No Items"));
			return selectList;
		}

		Iterator itr = list.iterator();
		int count = 1;
	
		while (itr.hasNext()) {
			ModuleObjService  mod = (ModuleObjService) itr.next();
			int seq = mod.getCoursemodule().getSeqNo();
			String value = new Integer(count++).toString();
			String label = seq + ". " + mod.getTitle();
			selectList.add(new SelectItem(value, label));
		}

		return selectList;
	}

	/**Converts the section list to selectItems for displaying at
	 * the list boxes in the JSF page
	 * @param list list of section objects
	 * @return list of select items
	 */
	private List forSelectSectionsItemsList(List list)
	{
		List selectList = new ArrayList();
		// Adding available list to select box
		if(list == null || list.size()==0)
		{
			selectList.add(new SelectItem("0", "No Items"));
			return selectList;
		}

		Iterator itr = list.iterator();
		int countidx = 0;
		while (itr.hasNext()) {
			SectionBeanService secBean= (SectionBeanService) itr.next();
			if (secBean != null)
			{
				String value = new Integer(countidx++).toString();
				String label = secBean.getDisplaySequence() +" "+ secBean.getSection().getTitle();
				selectList.add(new SelectItem(value, label));
			}
		}

		return selectList;
	}
	/**
	 * @return Returns the newSelectedModule.
	 */
	public String getNewSelectedModule() {
		return newSelectedModule;
	}
	/**
	 * @param newSelectedModule The newSelectedModule to set.
	 */
	public void setNewSelectedModule(String newSelectedModule) {
		this.newSelectedModule = newSelectedModule;
	}


	/** Determines number of sections or modules shown in the select list
	 * @return list size to show
	 */
	public int getShowSize()
	{
		showSize = 6;
		if(formName != null && formName.equals("SortSectionForm"))
		{
			if (currSecList !=null && currSecList.size() >6)
				showSize = currSecList.size();
		}
		else
		{
			if(currList !=null && currList.size() > 6)
				showSize = currList.size();
		}
		return showSize;
	}

	/** Move selected item to the top of the select list
	 * @return modules_sort
	 */
	public String MoveItemAllUpAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
    	MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
    	String courseId = mPage.getCurrentSiteId();
		// if module is selected then throw message
		logger.debug("values" + newSelectedModule );

		try{
			if(newSelectedModule != null)
			{
				int selIndex = (new Integer(newSelectedModule).intValue()) -1;

				if(newModulesList.size() < 2 || selIndex == 0)
				{
					logger.debug("first module selected to move up");
					return "modules_sort";
				}
				ModuleObjService  mod  = (ModuleObjService) newModulesList.get(selIndex);
				logger.debug("calling sort for " + mod.getTitle());
				moduleService.sortModule(mod,courseId,"allUp");
				newModulesList = getModuleService().getModules(courseId);
				newList  = forSelectItemsList(newModulesList);
				newSelectedModule = new Integer(selIndex).toString();
			}
		}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			//	me.printStackTrace();
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return "modules_sort";
	}


	/** Move item up by one in the select list
	 * @return modules_sort
	 */
	public String MoveItemUpAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
    	MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
    	String courseId = mPage.getCurrentSiteId();
		// if module is selected then throw message
	  	logger.debug("values" + newSelectedModule );

		  try{
		   	if(newSelectedModule != null)
		   		{
		   		int selIndex = (new Integer(newSelectedModule).intValue()) -1;

		   		if(newModulesList.size() < 2 || selIndex == 0)
		   		{
		   			logger.debug("first module selected to move up");
					return "modules_sort";
		   		}
		   		ModuleObjService  mod  = (ModuleObjService) newModulesList.get(selIndex);
	  	  		logger.debug("calling sort for " + mod.getTitle());
		   		moduleService.sortModule(mod,courseId,"up");
		   		newModulesList = getModuleService().getModules(courseId);
				newList  = forSelectItemsList(newModulesList);
				newSelectedModule = new Integer(selIndex).toString();
		   		}
		  	}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			//me.printStackTrace();
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		return "modules_sort";
	}

	/** Move item down by one in the select list
	 * @return modules_sort
	 */
	public String MoveItemDownAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
    	MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
    	String courseId = mPage.getCurrentSiteId();

		try{
			if(newSelectedModule != null)
			{
				int selIndex = (new Integer(newSelectedModule).intValue()) -1;

				if(newModulesList.size() < 2 || (selIndex == newModulesList.size()-1))
				{
					logger.debug("last module selected to move down");
					return "modules_sort";
				}
				ModuleObjService  mod  = (ModuleObjService) newModulesList.get(selIndex);
				logger.debug("calling sort for " + mod.getTitle());
				moduleService.sortModule(mod,courseId,"down");
				newModulesList = getModuleService().getModules(courseId);
				newList  = forSelectItemsList(newModulesList);
				newSelectedModule = new Integer(selIndex+2).toString();
			}
		}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		return "modules_sort";
	}

	/** Move item to bottom of select list
	 * @return modules_sort
	 */
	public String MoveItemAllDownAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
		ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
    	MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(ctx);
    	String courseId = mPage.getCurrentSiteId();

		try{
			if(newSelectedModule != null)
			{
				int selIndex = (new Integer(newSelectedModule).intValue()) -1;

				if(newModulesList.size() < 2 || (selIndex == newModulesList.size()-1))
				{
					logger.debug("last module selected to move down");
					return "modules_sort";
				}
				ModuleObjService  mod  = (ModuleObjService) newModulesList.get(selIndex);
				logger.debug("calling sort for " + mod.getTitle());
				moduleService.sortModule(mod,courseId,"allDown");
				newModulesList = getModuleService().getModules(courseId);
				newList  = forSelectItemsList(newModulesList);
				newSelectedModule = new Integer(selIndex+1).toString();
			}
		}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		return "modules_sort";
	}

	/** Reset values and redirect to sort section page
	 * @return sections_sort
	 */
	public String gotoSortSections()
	{
		resetValues();
		//fetch the first module and its sections
		setFormName("SortSectionForm");
		return "sections_sort";
	}

	
	/** Reset values and redirect to sort modules page
	 * @return modules_sort
	 */
	public String goToSortModules(){
		resetValues();
		setFormName("");
		return "modules_sort";

	}
	/**
	 * @return Returns the formName.
	 */
	public String getFormName() {
		return formName;
	}
	/**
	 * @param formName The formName to set.
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/** Get list of modules in a list of select items
	 * @return list of select items with modules
	 */
	public List getAllModulesList()
	{
		if(allModules == null || allModulesList == null )
		{
			FacesContext context = FacesContext.getCurrentInstance();
			ValueBinding binding = Util.getBinding("#{meleteSiteAndUserInfo}");
	    	MeleteSiteAndUserInfo mPage = (MeleteSiteAndUserInfo) binding.getValue(context);
	    	String courseId = mPage.getCurrentSiteId();
	    	
			allModules = new ArrayList();
			allModules = moduleService.getModules(courseId);
			if (allModules.size() > 0)
			{
				if(currModule == null || currModule.length()==0)
				{
					currModule = "1";
				}
			}
		}
		allModulesList = forSelectItemsList(allModules);
		return allModulesList;
	}
	/**
	 * @return Returns the currModule.
	 */
	public String getCurrModule() {

		if(currModule == null)
		{
			getAllModulesList();
		}
		return currModule;
	}
	/**
	 * @param currModule The currModule to set.
	 */
	public void setCurrModule(String currModule) {
		this.currModule = currModule;
	}

	/** Get sections of next module as a list of select items
	 * @param event ValueChangeEvent
	 * @throws AbortProcessingException
	 */
	public void nextModuleSections(ValueChangeEvent event)throws AbortProcessingException
	{
		UIInput moduleSelect = (UIInput)event.getComponent();
		String selModId = (String)moduleSelect.getValue();

		currSecList = null;
		currSectionsList = null;
		int modId= new Integer(selModId).intValue()-1;
		if(modId > 0)
		{
			ModuleObjService  mod = (ModuleObjService) allModules.get(modId);
			currSectionsList = new ArrayList();
			currSectionsList = getSectionService().getSortSections(mod);
			currSecList  = forSelectSectionsItemsList(currSectionsList);
		}
		newSectionsList = null;
		newSecList = null;
	}

	/**
	 * Store the user picked module for sort from the list 
	 * 
	 * @param event
	 * @throws AbortProcessingException
	 */
	public void changeSelectedModule(ValueChangeEvent event) throws AbortProcessingException
	{
		UIInput moduleSelect = (UIInput) event.getComponent();
		newSelectedModule = (String) moduleSelect.getValue();
	}
	
	/**
	 * Store the user picked section for sort from the list
	 * @param event
	 * @throws AbortProcessingException
	 */
	public void changeSelectedSection(ValueChangeEvent event)throws AbortProcessingException
	{
		UIInput sectionSelect = (UIInput)event.getComponent();
		newSelectedSection = (String)sectionSelect.getValue();	
	}
	
	/** Get list of sections formatted with dashes in a list of select items
	 * @return list of select items
	 */
	public List getCurrSecList()
	{
		try{
			if(currSectionsList == null)
			{
				if(allModules != null && allModules.size() > 0 )
				{
					if(currModule == null)	currModule = "1";
					ModuleObjService  mod  = (ModuleObjService) allModules.get(new Integer(currModule).intValue() -1);
					currSectionsList = new ArrayList();
					currSectionsList = getSectionService().getSortSections(mod);
				}
			}
			currSecList  = forSelectSectionsItemsList(currSectionsList);
		} catch(Exception e)
		{
			logger.debug("error in getting currSecList "+e.toString());
		}
		return currSecList;
	}

	/**
	 * @return Returns the newList.
	 */
	public List getNewSecList() {
		try{
			if(newSectionsList == null)
			{
				if (currSectionsList == null) getCurrSecList();
				newSectionsList = currSectionsList;
				newSecList  = forSelectSectionsItemsList(newSectionsList);
			}

		} catch(Exception e)
		{
			if (logger.isDebugEnabled()) {
				logger.debug("error in getting newSecList "+e.toString());
				e.printStackTrace();
			}
		}
		return newSecList;
	}

	/** Move section to the top of the select item list
	 * @return sections_sort
	 */
	public String MoveSectionItemAllUpAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try{
			if(newSelectedSection != null)
			{
				int selIndex = new Integer(newSelectedSection).intValue();
				if((selIndex == 0) || newSectionsList == null || newSectionsList.size() < 2 )
				{
					logger.debug("one item in the list or first section is selected to move all up");
					return "sections_sort";
				}
				ModuleObjService  mod  = (ModuleObjService) allModules.get(new Integer(currModule).intValue() -1);
				String sort_sec_id = ((SectionBeanService)newSectionsList.get(selIndex)).getSection().getSectionId().toString();
				moduleService.sortSectionItem(mod,sort_sec_id, "allUp");
				newSectionsList = sectionService.getSortSections(mod);
				newSecList = forSelectSectionsItemsList(newSectionsList);
				newSelectedSection = new Integer(0).toString();
			}
		}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		catch (Exception me)
		{
		me.printStackTrace();
		}
		return "sections_sort";
	}
	
	/** Move section item up by one in the select list
	 * @return sections_sort
	 */
	public String MoveSectionItemUpAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try{
			logger.debug("new selected value is" + newSelectedSection);
			if(newSelectedSection != null)
			{
				int selIndex = new Integer(newSelectedSection).intValue();
				if((selIndex == 0) || newSectionsList == null || newSectionsList.size() < 2 )
				{
					logger.debug("one item in the list or first section is selected to move up");
					return "sections_sort";
				}
				ModuleObjService  mod  = (ModuleObjService) allModules.get(new Integer(currModule).intValue() -1);
				String sort_sec_id = ((SectionBeanService)newSectionsList.get(selIndex)).getSection().getSectionId().toString();
				moduleService.sortSectionItem(mod,sort_sec_id, "up");
				newSectionsList = sectionService.getSortSections(mod);
				newSecList = forSelectSectionsItemsList(newSectionsList);
				newSelectedSection = new Integer(selIndex - 1).toString();
			}
		}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		return "sections_sort";
	}

	/** Move section item down by one in the select list
	 * @return sections_sort
	 */
	public String MoveSectionItemDownAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try{

			if(newSelectedSection != null)
			{
				int selIndex = new Integer(newSelectedSection).intValue();
				if(newSectionsList == null || newSectionsList.size() < 2 || (selIndex == newSectionsList.size()-1))
				{
					logger.debug("one item in the list or last section is selected to move down");
					return "sections_sort";
				}
				ModuleObjService  mod  = (ModuleObjService) allModules.get(new Integer(currModule).intValue() -1);
				String sort_sec_id = ((SectionBeanService)newSectionsList.get(selIndex)).getSection().getSectionId().toString();
				moduleService.sortSectionItem(mod,sort_sec_id, "down");
				newSectionsList = sectionService.getSortSections(mod);
				newSecList = forSelectSectionsItemsList(newSectionsList);
				newSelectedSection = new Integer(selIndex + 1).toString();
			}
		}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		return "sections_sort";
	}

	/** Move section item to the bottom of the select list
	 * @return sections_sort
	 */
	public String MoveSectionItemAllDownAction()
	{
		FacesContext ctx = FacesContext.getCurrentInstance();
		ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");

		try{

			if(newSelectedSection != null)
			{
				int selIndex = new Integer(newSelectedSection).intValue();
				if(newSectionsList == null || newSectionsList.size() < 2 || (selIndex == newSectionsList.size()-1))
				{
					logger.debug("one item in the list or last section is selected to move down");
					return "sections_sort";
				}
				ModuleObjService  mod  = (ModuleObjService) allModules.get(new Integer(currModule).intValue() -1);
				String sort_sec_id = ((SectionBeanService)newSectionsList.get(selIndex)).getSection().getSectionId().toString();
				moduleService.sortSectionItem(mod,sort_sec_id, "allDown");
				newSectionsList = sectionService.getSortSections(mod);
				newSecList = forSelectSectionsItemsList(newSectionsList);
				newSelectedSection = new Integer(newSectionsList.size()-1).toString();
			}
		}
		catch (MeleteException me)
		{
			logger.debug(me.toString());
			String ErrMsg = bundle.getString("sort_fail");
			FacesMessage msg =new FacesMessage(ErrMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			ctx.addMessage (null, msg);
		}
		return "sections_sort";
	}

	/**
	 * @return Returns the ModuleService.
	 */
	public ModuleService getModuleService() {
		return moduleService;
	}
	/**
	 * @param ModuleService The ModuleService to set.
	 */
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	/**
	 * @return Returns the sectionService.
	 */
	public SectionService getSectionService() {
		return sectionService;
	}
	/**
	 * @param sectionService The sectionService to set.
	 */
	public void setSectionService(SectionService sectionService) {
		this.sectionService = sectionService;
	}
	/**
	 * @return Returns the newSelectedSection.
	 */
	public String getNewSelectedSection() {
		return newSelectedSection;
	}
	/**
	 * @param newSelectedSection The newSelectedSection to set.
	 */
	public void setNewSelectedSection(String newSelectedSection) {
		this.newSelectedSection = newSelectedSection;
	}

}
