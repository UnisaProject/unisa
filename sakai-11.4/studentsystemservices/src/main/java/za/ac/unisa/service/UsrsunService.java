package za.ac.unisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import za.ac.unisa.dao.UsrsunDAO;
import za.ac.unisa.model.Usrsun;

@Service
@Transactional
public class UsrsunService {
	private UsrsunDAO usrsunDAO;

	@Autowired
	public void setUserDAO(UsrsunDAO usrsunDAO) {
		this.usrsunDAO = usrsunDAO;
	}

	public List<Usrsun> getUserList() {
		return this.usrsunDAO.getUserList();
	}

	public List<Usrsun> getUserDetailsPerModuleYearSem(String studyUnitCode, int academicYear) {
		return this.usrsunDAO.getUserDetailsPerModuleYearSem(studyUnitCode, academicYear);
	}

	public List<Usrsun> getModuleListPerUser(String studyUnitCode, int academicYear) {
		return this.usrsunDAO.getModuleListPerUser(studyUnitCode, academicYear);
	}

}
