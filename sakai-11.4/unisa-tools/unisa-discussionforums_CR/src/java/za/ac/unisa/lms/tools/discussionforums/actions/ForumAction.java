//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.discussionforums.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.event.api.UsageSession;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.exception.IdUnusedException;
//import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.site.api.SiteService;
import za.ac.unisa.lms.constants.EventTrackingTypes;
import za.ac.unisa.lms.tools.discussionforums.dao.Forum;
import za.ac.unisa.lms.tools.discussionforums.dao.IForumDao;
import za.ac.unisa.lms.tools.discussionforums.dao.impl.ForumDAOImpl;
import za.ac.unisa.lms.tools.discussionforums.forms.ForumDetailsForm;
import za.ac.unisa.lms.tools.discussionforums.service.ForumDaoService;
import za.ac.unisa.security.SecurityServices;


public class ForumAction extends LookupDispatchAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter("action") == null) return showForums(mapping, form, request, response);
		return super.execute(mapping, form, request, response);
	}

	private EventTrackingService eventTrackingService;
	private UsageSessionService usageSessionService;
    private SessionManager sessionManager;
    private ToolManager toolManager;
    private SiteService siteService;
	private Log log = LogFactory.getLog(this.getClass());
	protected Map getKeyMethodMap() {
		Map map = new HashMap();
		map.put("showForums", "showForums");
		map.put("createForum", "createForum");
		map.put("button.back", "showForums");
		map.put("editForum", "editForum");
		map.put("button.save", "saveForum");
		map.put("button.delete", "deleteForum");
		return map;
	}



	public ActionForward showForums(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response){

		ForumDetailsForm forumDetailsForm = (ForumDetailsForm)form;
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
		siteService = (SiteService) ComponentManager.get(SiteService.class);
		Session currentSession = sessionManager.getCurrentSession();
		String userID = currentSession.getUserEid();
		String siteId ="";
		String totalPages = "";
		String siteReference = "";
		int pages = 0;
		Integer pageNo;
		boolean generalForumCreated = false;	
		SecurityServices ss = new SecurityServices();
		siteReference = ss.getSiteReference();
		forumDetailsForm.setAddForum(ss.checkPermission(siteReference, "discuss.addforum"));
		forumDetailsForm.setUpdateAnyForum(ss.checkPermission(siteReference, "discuss.updateforum.any"));
		forumDetailsForm.setUpdateOwnForum(ss.checkPermission(siteReference, "discuss.updateforum.own"));
		siteId = toolManager.getCurrentPlacement().getContext();
		String siteTitle="";
        try {
            siteTitle = siteService.getSite(siteId).getTitle().toString();
        } catch(IdUnusedException e){
            System.out.println(e.getLocalizedMessage());
        }
        forumDetailsForm.setTitle(siteTitle);
		request.getSession().setAttribute("userID",userID);
		request.getSession().setAttribute("siteId",siteId);
		IForumDao forumDao = ForumDaoService.getForumDao();//Called Service Locator
		forumDetailsForm.getForum().setSiteId(siteId);
		forumDetailsForm.getForum().setUserId(userID);
		if (request.getParameter("pageNo") == null){
			pageNo = new Integer("1");
		} else {
			pageNo = new Integer(request.getParameter("pageNo"));
		}
		forumDetailsForm.setPageNo(pageNo);
		List forums = null;
		if (forumDetailsForm.getSortBy() == null && forumDetailsForm.getSortOrder() == null){
			forumDetailsForm.setSortBy("Creation_Date");
			forumDetailsForm.setSortOrder("Asc");
		}
		if (forumDetailsForm.getSortIcon() == null){
			forumDetailsForm.setSortIcon("0");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("1")){
			forumDetailsForm.setSortOrder("Asc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("2")){
			forumDetailsForm.setSortOrder("Desc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("3")){
			forumDetailsForm.setSortOrder("Asc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("4")){
			forumDetailsForm.setSortOrder("Desc");
		}else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("5")){
			forumDetailsForm.setSortOrder("Asc");
		} else if (forumDetailsForm.getSortIcon().equalsIgnoreCase("6")){
			forumDetailsForm.setSortOrder("Desc");
		}
		forums = forumDao.getForumList(forumDetailsForm.getForum().getSiteId(),forumDetailsForm.getSortBy(),forumDetailsForm.getSortOrder());
		//List listAllForums = forumdao.getAllForumsList(forumDetailsForm.getForum().getSiteId());
		Iterator i = forums.iterator();
		if(forums.isEmpty()) {
			Forum forum = new Forum();
			forum.setForumName("General Subject Related Discussions");
			forum.setForumDescription("A forum on general discussion about the subject content");
			forum.setSiteId(siteId);
			forum.setUserId("admin");
			forum.setHideStatus("N");
			forumDao.insertForum(forum);
			generalForumCreated = true;
			forums = forumDao.getForumList(forumDetailsForm.getForum().getSiteId(),forumDetailsForm.getSortBy(),forumDetailsForm.getSortOrder());
			if(forums.isEmpty()) {
				log.debug("Forum List is STILL empty after trying to insert default forum");
			}
		} else {
			while(i.hasNext()){
				Forum fm = (Forum) i.next();
				if (fm.getForumName().equalsIgnoreCase("General Subject Related Discussions")){
					generalForumCreated = true;
				}
			}
			Forum forum = new Forum();
			if (!generalForumCreated){
				forum.setForumName("General Subject Related Discussions");
				forum.setForumDescription("A forum on general discussion about the subject content TESTSING LDJDLDLF");
				forum.setSiteId(siteId);
				forum.setUserId("admin");
				forum.setHideStatus("N");
				forumDao.insertForum(forum);
				forums = forumDao.getForumList(forumDetailsForm.getForum().getSiteId(),forumDetailsForm.getSortBy(),forumDetailsForm.getSortOrder());
			}
		}
		try{
			totalPages = forumDao.getForumsPerSiteCounter(forumDetailsForm.getForum().getSiteId());
		}  catch (Exception ex) {
			log.error("ERROR occured in  showTopics method "+ex);
		}
	try{
		pages =  Integer.parseInt(totalPages)/10;
		if ((Integer.parseInt(totalPages)% 10) != 0){
			pages = pages + 1;
		}
	}
	catch(NumberFormatException ne){
	}
		forumDetailsForm.setTotalPages(new Integer(pages));
		forumDetailsForm.setSiteForums(forums);
		return mapping.findForward("forumlistforward");
	}

	public ActionForward createForum(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			ForumDetailsForm forumDetailsForm = (ForumDetailsForm)form;
			forumDetailsForm.getForum().setForumDescription("");
			forumDetailsForm.getForum().setForumName("");
			return mapping.findForward("createforumforward");
	}

	public ActionForward editForum(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			ForumDetailsForm forumDetailsForm = (ForumDetailsForm)form;
			ActionMessages messages = new ActionMessages();
			Forum forum = new Forum();
			ForumDAOImpl forumdao = new ForumDAOImpl();
			forum  = forumdao.getForumContent(forumDetailsForm.getForumId());
			forumDetailsForm.getForum().setForumId(forum.getForumId());
			forumDetailsForm.getForum().setForumName(forum.getForumName());
			forumDetailsForm.getForum().setForumDescription(forum.getForumDescription());
			forumDetailsForm.setTmpForumName(forum.getForumName());
			if (forumDetailsForm.isEditForum()){
				return mapping.findForward("createforumforward");
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Are you sure you want to delete the following forum and all its topics and messages?"));
				addErrors(request, messages);
				return mapping.findForward("deleteforumconfirmforward");
			}
	}

	public ActionForward saveForum(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			ActionMessages messages = new ActionMessages();
			//ForumDAOImpl forumdao = new ForumDAOImpl();  old code
			IForumDao forumdao = ForumDaoService.getForumDao();
			ForumDetailsForm forumDetailsForm = (ForumDetailsForm) form;
			Forum forum = new Forum();
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager= (ToolManager) ComponentManager.get(ToolManager.class);
			UsageSession usageSession = usageSessionService.getSession();
			String tmpName = "";
			String tmpDbName = "";
			String forumNameCheck = "";
			if(forumDetailsForm.getForum().getForumName().length() < 1){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Forum Name field cannot be empty. Please enter a Title or click Cancel to return to the Discussion Forums View."));
				addErrors(request,messages);
				return mapping.findForward("createforumforward");
			} else if(forumDetailsForm.getForum().getForumName().length() > 100){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Forum Name field should not be more than 100 charecters. Please enter a shoter Forum Name or click Cancel to return to the Discussion Forums View."));
				addErrors(request,messages);
				return mapping.findForward("createforumforward");
			} else if (forumDetailsForm.getForum().getForumDescription().length() > 250){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Forum Description field should not be more than 250 charecters. Please enter a  shorter Forum Description."));
				addErrors(request,messages);
				return mapping.findForward("createforumforward");
			}
			
			System.out.print("  passess here ");
		
			//forumNameCheck = forumDetailsForm.getForum().getForumName().replaceAll("'", "''");
			forumNameCheck = forumdao.makeJDBCCompatible(forumDetailsForm.getForum().getForumName());
			forumDetailsForm.getForum().setForumName(forumNameCheck);
			System.out.println("Forum Name "+forumNameCheck);
			tmpDbName = forumdao.getForumByName(forumDetailsForm.getForum().getForumName(),forumDetailsForm.getForum().getSiteId());
			tmpName = forumDetailsForm.getForum().getForumName();
			if (!forumDetailsForm.isEditForum()){
				if(!tmpName.equalsIgnoreCase(tmpDbName)){
								
					forumdao.insertForum(forumDetailsForm.getForum());
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_ADD, toolManager.getCurrentPlacement().getContext()+"  Forum id: "+forumDetailsForm.getForum().getForumId()+" was created", false),usageSession);
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Forum Name already exist, enter a new Forum Name."));
					addErrors(request,messages);
					return mapping.findForward("createforumforward");
				}
			} else {
				if (tmpName.equalsIgnoreCase(tmpDbName) && !tmpName.equalsIgnoreCase(forumDetailsForm.getTmpForumName())){
					messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","The Forum Name already exist, try a new Forum Name."));
					addErrors(request,messages);
					return mapping.findForward("createforumforward");
				} else {
					forumdao.updateForum(forumDetailsForm.getForum());
					forumDetailsForm.setEditForum(false);
					eventTrackingService.post(
							eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_EDIT, toolManager.getCurrentPlacement().getContext()+" Forum id: "+forumDetailsForm.getForum().getForumId()+" was edited", false),usageSession);
				}
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You have successfully created a forum"));
			saveMessages(request, messages);
			return showForums(mapping,form,request,response);
	}

	public ActionForward deleteForum(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response){
			ActionMessages messages = new ActionMessages();
			ForumDetailsForm forumDetailsForm = (ForumDetailsForm) form;
			eventTrackingService = (EventTrackingService) ComponentManager.get(EventTrackingService.class);
			usageSessionService = (UsageSessionService) ComponentManager.get(UsageSessionService.class);
			toolManager= (ToolManager) ComponentManager.get(ToolManager.class);
			UsageSession usageSession = usageSessionService.getSession();
			sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
			Session currentSession = sessionManager.getCurrentSession();
			String userID = currentSession.getUserEid();
			String ipAddress =  request.getRemoteAddr();
			boolean status=false;
			ForumDAOImpl forumdao = new ForumDAOImpl();
			forumDetailsForm.getForum().setHideStatus("Y");
			status = forumdao.deleteForum(forumDetailsForm.getForum(), userID, ipAddress );
			if(status==true){
				messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","Forum was not deleted, please tryagain."));
				addErrors(request, messages);
				return showForums(mapping,form,request,response);
			}
			messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("errors.message","You have successfully deleted a forum"));
			addErrors(request, messages);
			eventTrackingService.post(
					eventTrackingService.newEvent(EventTrackingTypes.EVENT_DISCUSSIONFORUMS_DELETE, toolManager.getCurrentPlacement().getContext()+" Forum id: "+forumDetailsForm.getForum().getForumId()+" deleted", false),usageSession);
			return showForums(mapping,form,request,response);
	}

}


