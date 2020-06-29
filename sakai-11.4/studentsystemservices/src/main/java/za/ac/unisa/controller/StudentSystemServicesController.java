package za.ac.unisa.controller;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import za.ac.unisa.model.QPDownloadEventsBean;
import za.ac.unisa.model.StudentEventsBean;
import za.ac.unisa.model.Usrsun;
import za.ac.unisa.service.QPDownloadEventsService;
import za.ac.unisa.service.StudentEventsService;
import za.ac.unisa.service.UsrsunService;

@RestController
public class StudentSystemServicesController {

	UsrsunService usrsunService;
	QPDownloadEventsService qpDownloadEventsService;
	StudentEventsService studentEventsService;

	private static Log logger = LogFactory.getLog(StudentSystemServicesController.class);

	@Autowired
	public void setUserBO(QPDownloadEventsService qpDownloadEventsService) {
		this.qpDownloadEventsService = qpDownloadEventsService;
	}

	@Autowired
	public void setUserBO(UsrsunService usrsunService) {
		this.usrsunService = usrsunService;
	}

	@Autowired
	public void setUserBO(StudentEventsService studentEventsService) {
		this.studentEventsService = studentEventsService;
	}

	@RequestMapping(value = "/usrsunuserlist", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Usrsun> getUserDetailsPerModuleYearSem(@RequestParam(name = "module") String module,
			@RequestParam(name = "year") int year) {
		logger.info(this + " getUserDetailsPerModuleYearSem for module " + module + " and for year " + year);

		return usrsunService.getUserDetailsPerModuleYearSem(module.trim().toUpperCase(), year);
	}

	@RequestMapping(value = "/usrsunmodulelist", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<Usrsun> getModuleListPerUser(@RequestParam(name = "userId") String userId,
			@RequestParam(name = "year") int year) {
		logger.info(this + " usrsunmodulelist for userId " + userId + " and for year " + year);
		
		return usrsunService.getModuleListPerUser(userId.trim().toUpperCase(), year);
	}

	@RequestMapping(value = "/qpdownloadallevents", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<QPDownloadEventsBean> questionPaperDownload() {
		logger.info(this + " qpdownloadallevents");
		
		return qpDownloadEventsService.getQuestionPaperDownload();
	}

	@RequestMapping(value = "/qpdownloadeventspermodule", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<QPDownloadEventsBean> questionPaperDownloadPerModule(@RequestParam(name = "module") String module) {
		logger.info(this + " qpdownloadeventspermodule for module " + module);
		
		return qpDownloadEventsService.getQuestionPaperDownloadEventsPerModule(module.trim().toUpperCase());
	}

	@RequestMapping(value = "/studenteventspermodule", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<StudentEventsBean> studentevents(@RequestParam(name = "module") String module,
			@RequestParam(name = "studentnr") int studentnr) {
		logger.info(this + " studenteventspermodule for module " + module + " and for studentnr " + studentnr);
		
		return studentEventsService.getStudentEvents(module.trim().toUpperCase(), studentnr);
	}
}
