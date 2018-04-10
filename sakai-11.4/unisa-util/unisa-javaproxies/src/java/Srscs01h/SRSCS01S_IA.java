package Srscs01h;
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
//
//                    Source Code Generated by
//                            CA Gen r8
//
//           Copyright (c) 2012 CA. All rights reserved.
//
//    Name: SRSCS01S_IA                      Date: 2012/05/15
//    User: Tanderw                          Time: 14:24:19
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
 * Internal data view storage for: SRSCS01S_IA
 **/
public class SRSCS01S_IA extends ViewBase implements IImportView, Serializable
{
  static VDF localVdf = null;
  
  // Entity View: IN
  //        Type: WS_STUDENT
  /**
   * Attribute missing flag for: InWsStudentNr
   **/
  public char InWsStudentNr_AS;
  /**
   * Attribute for: InWsStudentNr
   **/
  public int InWsStudentNr;
  /**
   * Default Constructor
   **/
  
  public SRSCS01S_IA()
  {
    reset();
  }
  /**
   * Copy Constructor
   **/
  
  public SRSCS01S_IA(SRSCS01S_IA orig)
  {
    copyFrom(orig);
  }
  /**
   * Static instance creator function
   **/
  
  public static SRSCS01S_IA getInstance()
  {
    return new SRSCS01S_IA();
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
    return(new SRSCS01S_IA(this));
  }
  /**
   * Resets all properties to the defaults.
   **/
  
  public void reset()
  {
    InWsStudentNr_AS = ' ';
    InWsStudentNr = 0;
  }
  /**
   * Gets the VDF array for the instance, initialized.
   **/
  
  public static VDF getViewDefinition()
  {
    if ( localVdf == null )
    {
      VDFEntry [] vdfEntries = {
        new VDFEntry((int)1, "", "InWsStudent", "WsStudent", "Nr", 
          VDFEntry.TYPE_INT, (short)1, (short)0, 8, (short)0, null), 
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
    vdf.getEntries()[0].getDataValue().setObject(new Integer(InWsStudentNr));
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
      InWsStudentNr = ((Integer)vdf.getEntries()[0].getDataValue().getObject())
        .intValue();
    }
    else 
    {
      InWsStudentNr = 0;
    }
  }
  
  /**
   * Sets the current instance based on the passed view.
   **/
  public void copyFrom(IImportView orig)
  {
    this.copyFrom((SRSCS01S_IA) orig);
  }
  
  /**
   * Sets the current instance based on the passed view.
   **/
  public void copyFrom(SRSCS01S_IA orig)
  {
    InWsStudentNr_AS = orig.InWsStudentNr_AS;
    InWsStudentNr = orig.InWsStudentNr;
  }
}
