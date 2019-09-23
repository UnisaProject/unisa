package za.ac.unisa.lms.tools.tpustudentplacement.forms;

import java.util.ArrayList;
import java.util.List;
public class Level {
	public   Level(int level,String  levelDescription) {
		this. level= level;
		this. levelDescription= levelDescription;
	}
	public   Level() {
	}
	   private int level;
	   private String levelDescription;
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getLevelDescription() {
		return levelDescription;
	}
	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
	}
	public List getLevelsList(int totalLevels) {
                                    List  listLevels=new ArrayList();
                                    for(int x=1;x<=totalLevels;x++){
                                    	listLevels.add(new Level(x,""+x));
                                     }
                                     return   listLevels;
  }
  public List getLevelsList(int totalLevels,String defaultString,int  defaultValue)  {
                                       List  listLevels=new ArrayList();
                                       for(int x=1;x<=totalLevels;x++){
                                    	    	listLevels.add(new Level(x,""+x));
                                       }
                                        listLevels.add(0,new Level(defaultValue,defaultString));
                                   return  listLevels;
 }
  public void setPracticeLevels(StudentPlacementForm studentPlacementForm){
                               	  studentPlacementForm.setListLevels(getLevelsList(4));
                               	  studentPlacementForm.setListLevelsforview(getLevelsList(4,"All",-1));
   }

}