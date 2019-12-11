package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.ArrayList;
import java.util.List;

import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;

public class PracticalYear {
	public  PracticalYear( int year,String yearDescription ) {
	                              this.year=year;
	                              this.yearDescription=yearDescription;
	 }
	public  PracticalYear() {
    }
        private int year;
           private String yearDescription;

		public  int getYear() {
			return year;
		}

		public void setYear (int  year) {
			this.year = year;
		}

		public String getYearDescription() {
			return yearDescription;
		}
        public void setYearDescription(String yearDescription) {
			this.yearDescription = yearDescription;
		}
		public List getPracticalYearList() {
			                              DateUtil dateUtil=new  DateUtil();
			                              int currYear=dateUtil.getYearInt();
                                          List  listPracticalYear=new ArrayList();
                                          listPracticalYear.add(new PracticalYear((currYear-1),(""+(currYear-1))));
                                          listPracticalYear.add(new PracticalYear(currYear,""+currYear));
                                          listPracticalYear.add(new PracticalYear((currYear+1),(""+(currYear+1))));
                                         return    listPracticalYear;
        }
        public List  getPracticalYearListforview()  {
                                          DateUtil dateUtil=new  DateUtil();
			                              int currYear=dateUtil.getYearInt();
                                          List  listPracticalYear=new ArrayList();
                                          listPracticalYear.add(new PracticalYear((currYear-1),(""+(currYear-1))));
                                          listPracticalYear.add(new PracticalYear(currYear,""+currYear));
                                          listPracticalYear.add(new PracticalYear((currYear+1),(""+(currYear+1))));
                                          listPracticalYear.add(0,new  PracticalYear(-1,"All"));
                                   return  listPracticalYear;
         }
          public void setListPracticalYear(StudentPlacementForm studentPlacementForm){
       	                                 studentPlacementForm.setListPracticalYear( getPracticalYearList());
       	                                 studentPlacementForm.setListPracticalYearforview(getPracticalYearListforview());
         }
	}
