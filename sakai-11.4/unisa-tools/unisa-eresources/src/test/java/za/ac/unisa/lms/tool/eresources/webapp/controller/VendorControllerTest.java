package za.ac.unisa.lms.tool.eresources.webapp.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;


public class VendorControllerTest extends BaseControllerTestCase {
	
	@Autowired
	private VendorController controller;
	
	@Test    
	public void testHandleRequest() throws Exception {
		ModelAndView mav = controller.handleRequest();
		ModelMap m = mav.getModelMap();
		assertNotNull(m.get("vendorList"));
		assertTrue(((List) m.get("vendorList")).size() > 0);    
		}
	}