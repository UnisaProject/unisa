package za.ac.unisa.lms.tools.telecentre.dao;

public class TelecentreDetails {
	
		private String centre;
		private String todate;
		private String timeIn;
		private String timeOut;
		private String duration;		//Sifiso Changes:Added:2016/06/30-Variable to display duration in seconds, minutes, hour
		private String durationReport;	//Sifiso Changes:Added:2016/08/15-Duration as defined in business rules for usage report
		private String province;
		private String studentNr;
		private String teleId;
		private String email;		//Sifiso Changes:Added:2016/07/28-set/get the telecentre email address from create page
		private String status;		//Sifiso Changes:Added:2016/07/28-set/get 'YES/NO' values for 'Active' status
		private String code;		//Sifiso Changes:Added:2016/07/28-set/get the telecentre code auto-generated by the system
		
		//Sifiso Changes: Added: 2016/12/07: Telecentre Admin Info
		private String adminADusername;
		private String adminEmail;
		private String adminName;
		private String adminRegionFlag;
		
		public void setTeleId(String teleId){
		     this.teleId=teleId;	 
		}
		public String getTeleId(){
			  return teleId;
		}
		public String getStudentNr(){
			   return  studentNr;
		}
		public void setStudentNr(String studentNr){
			   this.studentNr=studentNr;
		}
		public String getProvince(){
			return province;
		}
		public void setProvince(String province){
			this.province=province;
		}
		public String getCentre() {
			return centre;
		}
		public void setCentre(String centre) {
			this.centre = centre;
		}
		public String getTodate() {
			return todate;
		}
		public void setTodate(String todate) {
			this.todate = todate;
		}
		public String getTimeIn() {
			return timeIn;
		}
		public void setTimeIn(String timeIn) {
			this.timeIn = timeIn;
		}
		public String getTimeOut() {
			return timeOut;
		}
		public void setTimeOut(String timeOut) {
			this.timeOut = timeOut;
		}
		//Sifiso Changes:Added:2016/06/30-Getter method for duration in minutes, seconds, hours 
		public String getDuration() {
			return duration;
		}
		//Sifiso Changes:Added:2016/06/30-Setter method for duration in minutes, seconds, hours
		public void setDuration(String duration) {
			this.duration = duration;
		}
		//Sifiso Changes:Added:2016/08/05-Getter method for telecentre email
		public String getEmail() {
			return email;
		}
		//Sifiso Changes:Added:2016/08/05-Setter method for telecentre email
		public void setEmail(String email) {
			this.email = email;
		}
		//Sifiso Changes:Added:2016/08/05-Getter method for telecentre status
		public String getStatus() {
			return status;
		}
		//Sifiso Changes:Added:2016/08/05-Setter method for telecentre status
		public void setStatus(String status) {
			this.status = status;
		}
		//Sifiso Changes:Added:2016/08/05-Getter method for telecentre code
		public String getCode() {
			return code;
		}
		//Sifiso Changes:Added:2016/08/05-Setter method for telecentre code
		public void setCode(String code) {
			this.code = code;
		}
		//Sifiso Changes:Added:2016/08/14-Getter method for duration defined in business rules for usage report
		public String getDurationReport() {
			return durationReport;
		}
		//Sifiso Changes:Added:2016/08/14-Setter method for duration defined in business rules for usage report
		public void setDurationReport(String durationReport) {
			this.durationReport = durationReport;
		}
		
		//Sifiso Changes: Added: 2016/12/07: Telecentre Admin Info
		public void setAdminADusername(String adminADusername){
		     this.adminADusername=adminADusername;	 
		}
		public String getAdminADusername(){
			  return adminADusername;
		}
		
		public void setAdminEmail(String adminEmail){
		     this.adminEmail=adminEmail;	 
		}
		public String getAdminEmail(){
			  return adminEmail;
		}
		
		public void setAdminName(String adminName){
		     this.adminName=adminName;	 
		}
		public String getAdminName(){
			  return adminName;
		}
		
		public void setAdminRegionFlag(String adminRegionFlag){
		     this.adminRegionFlag=adminRegionFlag;	 
		}
		public String getAdminRegionFlag(){
			  return adminRegionFlag;
		}
}
