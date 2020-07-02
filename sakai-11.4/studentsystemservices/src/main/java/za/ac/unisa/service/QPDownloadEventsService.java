package za.ac.unisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import za.ac.unisa.dao.QPDownloadEventsDAO;
import za.ac.unisa.model.QPDownloadEventsBean;

@Service
@Transactional
public class QPDownloadEventsService {

	private QPDownloadEventsDAO qpDownloadEventsDAO;

	@Autowired
	public void setUserDAO(QPDownloadEventsDAO usrsunDAO) {
		this.qpDownloadEventsDAO = usrsunDAO;
	}

	public List<QPDownloadEventsBean> getQuestionPaperDownload() {
		return this.qpDownloadEventsDAO.getQuestionPaperDownloadEvents();
	}

	public List<QPDownloadEventsBean> getQuestionPaperDownloadEventsPerModule(String module) {

		return this.qpDownloadEventsDAO.getQuestionPaperDownloadEventsPerModule(module);
	}
}
