package Saaal40j;
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
//                    Source Code Generated by
//                            CA Gen r8
//
//           Copyright (c) 2012 CA. All rights reserved.
//
//    Name: SAAAL40S_IA                      Date: 2012/05/15
//    User: Tanderw                          Time: 13:49:47
//
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// Import Statements
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
import java.lang.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.sql.*;
import com.ca.gen80.vwrt.*;
import com.ca.gen80.vwrt.types.*;
import com.ca.gen80.vwrt.vdf.*;
import com.ca.gen80.csu.exception.*;

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// START OF IMPORT VIEW
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
/**
 * Internal data view storage for: SAAAL40S_IA
 **/
public class SAAAL40S_IA extends ViewBase implements IImportView, Serializable
{
  static VDF localVdf = null;
  
  // Entity View: IN
  //        Type: WS_STUDENT_ANNUAL_RECORD
  /**
   * Attribute missing flag for: InWsStudentAnnualRecordMkStudentNr
   **/
  public char InWsStudentAnnualRecordMkStudentNr_AS;
  /**
   * Attribute for: InWsStudentAnnualRecordMkStudentNr
   **/
  public int InWsStudentAnnualRecordMkStudentNr;
  // Entity View: IN
  //        Type: WS_STUDENT_STUDY_UNIT
  /**
   * Attribute missing flag for: InWsStudentStudyUnitMkStudyUnitCode
   **/
  public char InWsStudentStudyUnitMkStudyUnitCode_AS;
  /**
   * Attribute for: InWsStudentStudyUnitMkStudyUnitCode
   **/
  public String InWsStudentStudyUnitMkStudyUnitCode;
  /**
   * Default Constructor
   **/
  
  public SAAAL40S_IA()
  {
    reset();
  }
  /**
   * Copy Constructor
   **/
  
  public SAAAL40S_IA(SAAAL40S_IA orig)
  {
    copyFrom(orig);
  }
  /**
   * Static instance creator function
   **/
  
  public static SAAAL40S_IA getInstance()
  {
    return new SAAAL40S_IA();
  }
  /**
   * Static free instance method
   **/
  
  public void freeInstance()
  {
  }
  /**
   * clone constructor
   **/
  
  @Override public Object clone()
  	throws CloneNotSupportedException
  {
    return(new SAAAL40S_IA(this));
  }
  /**
   * Resets all properties to the defaults.
   **/
  
  public void reset()
  {
    InWsStudentAnnualRecordMkStudentNr_AS = ' ';
    InWsStudentAnnualRecordMkStudentNr = 0;
    InWsStudentStudyUnitMkStudyUnitCode_AS = ' ';
    InWsStudentStudyUnitMkStudyUnitCode = "       ";
  }
  /**
   * Gets the VDF array for the instance, initialized.
   **/
  
  public static VDF getViewDefinition()
  {
    if ( localVdf == null )
    {
      VDFEntry [] vdfEntries = {
        new VDFEntry((int)1, "", "InWsStudentAnnualRecord", 
          "WsStudentAnnualRecord", "MkStudentNr", VDFEntry.TYPE_INT, (short)1, (
          short)0, 8, (short)0, null), 
new VDFEntry((int)2, "", "InWsStudentStudyUnit", "WsStudentStudyUnit", 
          "MkStudyUnitCode", VDFEntry.TYPE_STRING, (short)1, (short)0, 7, (
          short)0, null), 
      };
      localVdf = new VDF(vdfEntries);
    }
    try {
      VDF result = (VDF)localVdf.clone();
      result.initViewData();
      return result;
    } catch( CloneNotSupportedException e ) {
      return null;
    }
  }
  
  /**
   * Gets the VDF version of the current state of the instance.
   **/
  public VDF getVDF()
  {
    VDF vdf = getViewDefinition();
    // predicate view item
    vdf.getEntries()[0].getDataValue().setObject(new Integer(
      InWsStudentAnnualRecordMkStudentNr));
    // predicate view item
    vdf.getEntries()[1].getDataValue().setObject(
      InWsStudentStudyUnitMkStudyUnitCode);
    return(vdf);
  }
  
  /**
   * Sets the current state of the instance to the VDF version.
   **/
  public void setFromVDF(VDF vdf)
  {
    // predicate view item
    if ( vdf.getEntries()[0].getDataValue().getObject() != null )
    {
      InWsStudentAnnualRecordMkStudentNr = ((Integer)vdf.getEntries()[0]
        .getDataValue().getObject()).intValue();
    }
    else 
    {
      InWsStudentAnnualRecordMkStudentNr = 0;
    }
    // predicate view item
    if ( vdf.getEntries()[1].getDataValue().getObject() != null )
    {
      InWsStudentStudyUnitMkStudyUnitCode = ((String)vdf.getEntries()[1]
        .getDataValue().getObject());
    }
    else 
    {
      InWsStudentStudyUnitMkStudyUnitCode = "       ";
    }
  }
  
  /**
   * Sets the current instance based on the passed view.
   **/
  public void copyFrom(IImportView orig)
  {
    this.copyFrom((SAAAL40S_IA) orig);
  }
  
  /**
   * Sets the current instance based on the passed view.
   **/
  public void copyFrom(SAAAL40S_IA orig)
  {
    InWsStudentAnnualRecordMkStudentNr_AS = 
      orig.InWsStudentAnnualRecordMkStudentNr_AS;
    InWsStudentAnnualRecordMkStudentNr = 
      orig.InWsStudentAnnualRecordMkStudentNr;
    InWsStudentStudyUnitMkStudyUnitCode_AS = 
      orig.InWsStudentStudyUnitMkStudyUnitCode_AS;
    InWsStudentStudyUnitMkStudyUnitCode = 
      orig.InWsStudentStudyUnitMkStudyUnitCode;
  }
}
