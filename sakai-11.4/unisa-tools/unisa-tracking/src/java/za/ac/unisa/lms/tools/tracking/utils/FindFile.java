package za.ac.unisa.lms.tools.tracking.utils;
 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
 
/*
 * Search files in Folder
 */
public class FindFile {
 
	public void searchFile(String folderPath, String fileName, String novelUserID) throws IOException {
		//log.debug("TrackingAction - "+novelUserID+" - searchFile - pdfFile="+folderPath+"/"+fileName);
 
		File folder = new File(folderPath);
		JSONObject listFilesObj = new JSONObject();
		
		try{
	       	Map<String, String> mapListFiles = new LinkedHashMap<String, String>();
	 
			if (folder.isDirectory()) {
				File[] listOfFiles = folder.listFiles();
				if (listOfFiles.length < 1){
					//log.debug("TrackingAction - "+novelUserID+" - searchFile - There is no File inside Folder");
				}else{
					//log.debug("TrackingAction - "+novelUserID+" - searchFile - List of Files & Folder");
					
					int fileCount = 0;
					try{
						for (File file : listOfFiles) {
							if(!file.isDirectory() && file.getCanonicalPath().toString().contains("UNISA_HardCopy_Tracking")){
								//log.debug("TrackingAction - "+novelUserID+" - searchFile - File All=" +file.getCanonicalPath().toString());
								//By User
								if (file.getCanonicalPath().toString().contains("TWILSCE")){
									//log.debug("TrackingAction - "+novelUserID+" - searchFile - File User=" +file.getCanonicalPath().toString());
									mapListFiles.put(Integer.toString(fileCount), file.getCanonicalPath().toString());
								}
								//By IN
								if (file.getCanonicalPath().toString().contains("IN")){
									//log.debug("TrackingAction - "+novelUserID+" - searchFile - File IN=" +file.getCanonicalPath().toString());
									mapListFiles.put(Integer.toString(fileCount), file.getCanonicalPath().toString());
								}
								//By OUT
								if (file.getCanonicalPath().toString().contains("OUT")){
									//log.debug("TrackingAction - "+novelUserID+" - searchFile - File OUT=" +file.getCanonicalPath().toString());
									mapListFiles.put(Integer.toString(fileCount), file.getCanonicalPath().toString());
								}
								//By Shipping/Consignment List
								if (file.getCanonicalPath().toString().contains("91970")){
									//log.debug("TrackingAction - "+novelUserID+" - searchFile - File SHIP=" +file.getCanonicalPath().toString());
									mapListFiles.put(Integer.toString(fileCount), file.getCanonicalPath().toString());
								}
								//By Date
								if (file.getCanonicalPath().toString().contains("20170307")){
									//log.debug("TrackingAction - "+novelUserID+" - searchFile - File DATE=" +file.getCanonicalPath().toString());
									mapListFiles.put(Integer.toString(fileCount), file.getCanonicalPath().toString());
								}
							}
							fileCount++;
						}
					 }catch(Exception ex){
						 listFilesObj.put("Error","The PDF File Listing Failed! Please try again / <br/>"+ex);
				     }
					listFilesObj.put("PFDFiles",mapListFiles);
				}
			}else{
				listFilesObj.put("Error","There is no Folder at given path! Please try again / <br/>");
				//log.debug("There is no Folder @ given path :" + folderPath);
			}
		}catch (Exception e){
			listFilesObj.put("Error","searchFile Crashed! Please try again / <br/>"+e);
			//log.debug("TrackingAction - "+novelUserID+" - searchFile Crashed / "+e);
		}
		
		@SuppressWarnings("rawtypes")
		JSONObject jsonObject = JSONObject.fromObject(listFilesObj);

		//log.debug("TrackingAction - "+novelUserID+" - searchFile - Final - **************************************************************");
		//log.debug("TrackingAction - "+novelUserID+" - searchFile - Final - jsonObject="+jsonObject.toString());
		//log.debug("TrackingAction - "+novelUserID+" - searchFile - Final - **************************************************************");
		
		/*
		// Convert the map to json
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = JSONObject.fromObject(dsaaRegionObj);
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchFile - Final - **************************************************************");
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchFile - Final - jsonObject="+jsonObject.toString());
		//log.debug("TrackingAction - "+pTrackingForm.getNovelUserId()+" - searchFile - Final - **************************************************************");
		out.write(jsonObject.toString());
		out.flush();

		return null; //Returning null to prevent struts from interfering with ajax/json
		*/
				
	}
}