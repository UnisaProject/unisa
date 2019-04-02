package Srraw01h.Abean;
 
import java.applet.*;
import java.awt.event.*;
import java.io.*;
import java.beans.*;
import java.util.*;
import java.net.URL;
import java.math.*;
import java.text.*;
import com.ca.gen80.jprt.*;
import com.ca.gen80.odc.coopflow.*;
import com.ca.gen80.odc.msgobj.*;
import com.ca.gen80.csu.trace.*;
import com.ca.gen80.vwrt.types.*;
 
/**
 * <strong>API bean documentation.</strong><p>
 *
 * This html file contains the public methods available in this bean.<br>
 * NOTE:  the links at the top of this page do not work, as they are not connected to anything. 
 * To get the images in the file to work, copy the images directory 
 * from 'jdk1.1.x/docs/api/images' to the directory where this file is located.
 */
public class Srraw01sStudReadmissWorklist  implements ActionListener, java.io.Serializable  {
 
   //  Use final String for the bean name
   public static final String BEANNAME = new String("Srraw01sStudReadmissWorklist");
 
   //  Constants for Asynchronous status
   public static final int PENDING = CoopFlow.DATA_NOT_READY;
   public static final int AVAILABLE = CoopFlow.DATA_READY;
   public static final int INVALID_ID = CoopFlow.INVALID_ID;
 
   private final SimpleDateFormat nativeTimestampFormatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
   private final SimpleDateFormat nativeTimeFormatter = new SimpleDateFormat("HHmmss");
   private final SimpleDateFormat nativeDateFormatter = new SimpleDateFormat("yyyyMMdd");
   public Srraw01sStudReadmissWorklist() {
      super();
      nativeDateFormatter.setLenient(false);
      nativeTimeFormatter.setLenient(false);
      nativeTimestampFormatter.setLenient(false);
      Trace.initialize(null);
   }
   /**
    * Sets the traceflag to tell the bean to output diagnostic messages on stdout.
    *
    * @param traceFlag 1 to turn tracing on, 0 to turn tracing off.
    */
   public void setTracing(int traceFlag) {
      if (traceFlag == 0)
         Trace.setMask(Trace.MASK_NONE);
      else
         Trace.setMask(Trace.MASK_ALL);
   }
   /**
    * Gets the current state of tracing.
    *
    * @return traceFlag value
    */
   public int  getTracing() {
      if (Trace.getMask() == Trace.MASK_NONE)
         return 0;
      else
         return 1;
   }
   protected void traceOut(String x)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, x);
      }
   }
 
 
   private Srraw01sStudReadmissWorklistOperation oper = null;
 
   /**
    * Calls the transaction/procedure step on the server.
    *
    * @exception java.beans.PropertyVetoException
    * Final property checks can throw this exception.
    */
   public void execute()  throws PropertyVetoException {
      try  {
         if (oper == null) {
            oper = new Srraw01sStudReadmissWorklistOperation(this);
            addCompletionListener(new operListener(this));
            addExceptionListener(new operListener(this));
         }
 

 
         oper.doSrraw01sStudReadmissWorklistOperation();
         notifyCompletionListeners();
      }
      catch (PropertyVetoException ePve)  {
         PropertyChangeEvent pce = ePve.getPropertyChangeEvent();
         String s = pce.getPropertyName();
         System.out.println("\nPropertyVetoException on " + s + ": " + ePve.toString());
         throw ePve;
      }
      catch (ProxyException e)  {
         notifyExceptionListeners(e.toString());
         return;
      }
   }
 
 
   private class operListener implements ActionListener, java.io.Serializable  {
      private Srraw01sStudReadmissWorklist rP;
      operListener(Srraw01sStudReadmissWorklist r)  {
         rP = r;
      }
      public void actionPerformed(ActionEvent aEvent)  {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, "Srraw01sStudReadmissWorklist", "Listener heard that Srraw01sStudReadmissWorklistOperation completed with " + 
               aEvent.getActionCommand());
         }
         String excp = "Exception";
         if (excp.equalsIgnoreCase(aEvent.getActionCommand().substring(0,9)))
            System.out.println("\nException on " + aEvent.getActionCommand().substring(10));
      }
   }
 
   private Vector completionListeners = new Vector();
   /**
    * Adds an object to the list of listeners that are called when execute has completed.
    *
    * @param l a class object that implements the ActionListener interface.  See the test UI 
    * for an example.
    */
   public synchronized void addCompletionListener(ActionListener l)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "addCompletionListener registered");
      }
      completionListeners.addElement(l);     //add listeners
   }
   /**
    * Removes the object from the list of listeners that are called after completion of execute.
    *
    * @param l the class object that was registered as a CompletionListener.  See the test UI 
    * for an example.
    */
   public synchronized void removeCompletionListener(ActionListener l)  {
      completionListeners.removeElement(l);  //remove listeners
   }
   private void notifyCompletionListeners()  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of completion of operation Srraw01sStudReadmissWorklist ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      synchronized (this)  {
         targets = (Vector) completionListeners.clone();
      }
      actionEvt = new ActionEvent(this, 0, "Completion.Srraw01sStudReadmissWorklist");
      for (int i = 0; targets.size() > i; i++)  {
         ActionListener target = (ActionListener)targets.elementAt(i);
         target.actionPerformed(actionEvt);
      }
   }
 
   private Vector exceptionListeners = new Vector();
   /**
    * Adds an object to the list of listeners that are called when an exception occurs.
    *
    * @param l a class object that implements the ActionListener interface.  See the test UI 
    * for an example.
    */
   public synchronized void addExceptionListener(ActionListener l)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "addExceptionListener registered");
      }
      exceptionListeners.addElement(l);     //add listeners
   }
   /**
    * Removes the object from the list of listeners that are called when an exception occurs.
    *
    * @param l the class object that was registered as an ExceptionListener.  See the test UI 
    * for an example.
    */
   public synchronized void removeExceptionListener(ActionListener l)  {
      exceptionListeners.removeElement(l);  //remove listeners
   }
   private void notifyExceptionListeners(String s)  {
      if (Trace.isTracing(Trace.MASK_APPLICATION))
      {
         Trace.record(Trace.MASK_APPLICATION, BEANNAME, "notifying listeners of exception of operation Srraw01sStudReadmissWorklist ()\n");
      }
      Vector targets;
      ActionEvent actionEvt = null;
      String failCommand = "Exception.Srraw01sStudReadmissWorklist";
      synchronized (this)  {
         targets = (Vector) exceptionListeners.clone();
      }
      if (s.length() > 0)
          failCommand = failCommand.concat(s);
      actionEvt = new ActionEvent(this, 0, failCommand);
      for (int i = 0; targets.size() > i; i++)  {
         ActionListener target = (ActionListener)targets.elementAt(i);
         target.actionPerformed(actionEvt);
      }
   }
 
   /**
    * Called by the sender of Listener events.
    */
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();
 
      if (command.equals("execute"))  {
         try {
            execute();
         } catch (PropertyVetoException pve) {}
      } else {
         if (Trace.isTracing(Trace.MASK_APPLICATION))
         {
            Trace.record(Trace.MASK_APPLICATION, BEANNAME, "ActionEvent " + e.toString());
         }
      }
   }
 
   //these are the standard properties that are passed into the Server Dialog 
   //all of these are checked when loaded by the operation into the srvdialog class
 
   private String commandSent = "";
   /**
    * Sets the command sent property to be sent to the server where
    * the procedure step's executable code is installed. This property should only be
    * set if the procedure step uses case of command construct.
    *
    * @param s a string representing the command name
    */
   public void setCommandSent(String s) {
      if (s == null) commandSent = "";
      else commandSent = s;
   }
   /**
    * Gets the command sent property to be sent to the server where
    * the procedure step's executable code is installed.
    *
    * @return a string representing the command name
    */
   public String getCommandSent() {
      return commandSent;
   }
 
   private String clientId = "";
   /**
    * Sets the client user id property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * user id is usually accompanied by a client user password, which can be set
    * with the clientPassword property.  Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @param s a string representing the client user id
    */
   public void setClientId(String s) {
      if (s == null) clientId = "";
      else clientId = s;
   }
   /**
    * Gets the client user id property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * user id is usually accompanied by a client user password, which can also be set
    * with the clientPassword property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @return a string representing the client user id
    */
   public String getClientId() {
      return clientId;
   }
 
   private String clientPassword = "";
   /**
    * Sets the client password property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * password usually accompanies a client user id, which can be set
    * with the clientId property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @param s a string representing the client password
    */
   public void setClientPassword(String s) {
      if (s == null) clientPassword = "";
      else  clientPassword = s;
   }
   /**
    * Gets the client password property which will be sent to
    * the server where the procedure step's executable code is installed. A client
    * password usually accompanies a client user id, which can be set
    * with the clientId property. Security is not enabled until the security
    * user exit is modified to enable it.
    *
    * @return a string representing the client password
    */
   public String getClientPassword() {
      return clientPassword;
   }
 
   private String nextLocation = "";
   /**
    * Sets the location name (NEXTLOC) property that may be
    * used by ODC user exit flow dlls.
    *
    * @param s a string representing the NEXTLOC value
    */
   public void setNextLocation(String s) {
      if (s == null) nextLocation = "";
      else nextLocation = s;
   }
   /**
    * Gets the location name (NEXTLOC) property that may be
    * used by ODC user exit flow dlls.
    *
    * @return a string representing the NEXTLOC value
    */
   public String getNextLocation() {
      return nextLocation;
   }
 
   private int exitStateSent = 0;
   /**
    * Sets the exit state property which will be sent to server procedure step.
    *
    * @param n an integer representing the exit state value
    */
   public void setExitStateSent(int n) {
      exitStateSent = n;
   }
   /**
    * Gets the exit state property which will be sent to server procedure step.
    *
    * @return an integer representing the exit state value
    */
   public int getExitStateSent() {
      return exitStateSent;
   }
 
   private String dialect = "DEFAULT";
   /**
    * Sets the dialect property.  It has the default value of "DEFAULT".
    *
    * @param s a string representing the dialect value
    */
   public void setDialect(String s) {
      if (s == null) dialect = "DEFAULT";
      else dialect = s;
   }
   /**
    * Gets the dialect property.  It has the default value of "DEFAULT".
    *
    * @return a string representing the dialect value
    */
   public String getDialect() {
      return dialect;
   }
 
   private String commandReturned;
   protected void setCommandReturned(String s) {
      commandReturned = s;
   }
   /**
    * Retrieves the command returned property, if any,
    * after the server procedure step has been executed.
    *
    * @return a string representing the command returned value
    */
   public String getCommandReturned() {
      return commandReturned;
   }
 
   private int exitStateReturned;
   protected void setExitStateReturned(int n) {
      exitStateReturned = n;
   }
   /**
    * Retrieves the exit state returned property, if any,
    * after the server procedure step has been executed.
    *
    * @return a string representing the exit state returned value
    */
   public int getExitStateReturned() {
      return exitStateReturned;
   }
 
   private int exitStateType;
   protected void setExitStateType(int n) {
      exitStateType = n;
   }
   /**
    * Gets the exit state type based upon the server procedure step exit state. 
    *
    * @return a string representing the exit state type value
    */
   public int getExitStateType() {
      return exitStateType;
   }
 
   private String exitStateMsg = "";
   protected void setExitStateMsg(String s) {
      exitStateMsg = s;
   }
   /**
    * Gets the current status text message, if
    * one exists. A status message is associated with a status code, and can
    * be returned by a Gen exit state.
    *
    * @return a string representing the exit state message value
    */
   public String getExitStateMsg() {
      return exitStateMsg;
   }
 
   private String comCfg = "";
   /**
    * Sets the value to be used for communications instead of the information in commcfg.properties.
    * For details on this information, refer to the commcfg.properties file provided.
    *
    * @param s a string containing the communications command value
    */
   public void setComCfg(String s) {
      if (s == null) comCfg = "";
      else  comCfg = s;
   }
   /**
    * Gets the value to be used for communications instead of the information in commcfg.properties.
    * For details on this information, refer to the commcfg.properties file provided.
    *
    * @return a string containing the communications command value
    */
   public String getComCfg() {
      return comCfg;
   }
 
   private URL serverLocation;
   /**
    * Sets the URL used to locate the servlet.  Set this property by calling
    * myObject.setServerLocation( getDocumentBase()) from your applet
    * or, force a server connection by using<br>
    * <code>try {new URL("http://localhost:80");} catch(MalformedURLException e) {}</code>
    * 
    * @param s a URL containing the base URL for the servlet
    */
   public void setServerLocation(URL newServerLoc) {
      serverLocation = newServerLoc;
   }
   /**
    * Gets the URL used to locate the servlet.
    * 
    * @return a URL containing the base URL for the servlet
    */
   public URL getServerLocation() {
      return serverLocation;
   }
 
   private String servletPath = "";
   /**
    * Sets the URL path to be used to locate the servlet.  Set this property by calling
    * myObject.setServletPath( ... ) from your applet.
    * If the servletPath is left blank, then a default path
    * of "servlet" will be used.
    * 
    * @param s a String containing the URL path for the servlet
    */
   public void setServletPath(String newServletPath) {
      if (newServletPath == null) servletPath = "";
      else servletPath = newServletPath;
   }
   /**
    * Gets the URL path that will be used to locate the servlet.
    * 
    * @return a String containing the URL path for the servlet
    */
   public String getServletPath() {
      return servletPath;
   }
 
   private String fileEncoding = "";
   /**
    * Sets the file encoding to be used for converting to/from UNICODE.
    *
    * @param s a string containing the file encoding value
    */
   public void setFileEncoding(String s) {
      if (s == null) fileEncoding = "";
      else  fileEncoding = s;
   }
   /**
    * Gets the file encoding that will be used for converting to/from UNICODE.
    *
    * @return a string the file encoding value
    */
   public String getFileEncoding() {
      return fileEncoding;
   }
 
   //  Property interfaces
   //     (names include full predicate viewname)
   //  get...  for each output predicate view
   //  set...  for each input predicate view
   //  both for input-output views
   //  export (set and fire pcs) for input-output and output views
 
   // support notifying bound properties when a attribute value changes
   // see pcs changes below
   protected transient PropertyChangeSupport pcs = new PropertyChangeSupport(this);
   /**
    * Adds an object to the list of listeners that are called when a property value changes.
    *
    * @param l a class object that implements the PropertyChangeListener interface.  See the test UI 
    * for an example.
    */
   public void addPropertyChangeListener (PropertyChangeListener l)
                           { pcs.addPropertyChangeListener (l);    }
   /**
    * Removes the object from the list of listeners that are called when a property value changes.
    *
    * @param l the class object that was registered as a PropertyChangeListener.  See the test UI 
    * for an example.
    */
   public void removePropertyChangeListener (PropertyChangeListener l)
                           { pcs.removePropertyChangeListener (l);    }
 
   /**
    * This method clears all import and export attribute properties. The
    * default value is used if one is specified in the model otherwise 0 is used
    * for numeric, date and time attributes, an empty string is used for string attributes
    * and "00000000000000000000" is used for timestamp attributes. For attributes in repeating
    * groups, the clear method sets the repeat count to 0. In addition to clearing
    * attribute properties, the <code>clear</code> method also clears the system properties
    * commandReturned, exitStateReturned, and exitStateMsg.
    *
    * @exception java.beans.PropertyVetoException
    * This is needed to cover the setXXXX calls used in the function.
    */
   public void clear()  throws PropertyVetoException  {
      setCommandReturned("");
      setExitStateReturned(0);
      setExitStateMsg("");
 
      importView.reset();
      exportView.reset();
      importView.InGroup_MA = IntAttr.valueOf(InGroupMax);
      exportView.OutCollegeGroup_MA = IntAttr.getDefaultValue();
      exportView.OutGroup_MA = IntAttr.getDefaultValue();
   }

   Srraw01h.SRRAW01S_IA importView = Srraw01h.SRRAW01S_IA.getInstance();
   Srraw01h.SRRAW01S_OA exportView = Srraw01h.SRRAW01S_OA.getInstance();
   public String getInEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(importView.InEmailOrFaxCsfStringsString1, 1);
   }
   public void setInEmailOrFaxCsfStringsString1(String s)
      throws PropertyVetoException {
      if (s.length() > 1) {
         throw new PropertyVetoException("InEmailOrFaxCsfStringsString1 must be <= 1 characters.",
               new PropertyChangeEvent (this, "InEmailOrFaxCsfStringsString1", null, null));
      }
      importView.InEmailOrFaxCsfStringsString1 = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailFromCsfStringsString132, 132);
   }
   public void setInEmailFromCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailFromCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailFromCsfStringsString132", null, null));
      }
      importView.InEmailFromCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InEmailToCsfStringsString132, 132);
   }
   public void setInEmailToCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InEmailToCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InEmailToCsfStringsString132", null, null));
      }
      importView.InEmailToCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNameCsfStringsString132, 132);
   }
   public void setInFaxNameCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNameCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNameCsfStringsString132", null, null));
      }
      importView.InFaxNameCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public String getInFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(importView.InFaxNoCsfStringsString132, 132);
   }
   public void setInFaxNoCsfStringsString132(String s)
      throws PropertyVetoException {
      if (s.length() > 132) {
         throw new PropertyVetoException("InFaxNoCsfStringsString132 must be <= 132 characters.",
               new PropertyChangeEvent (this, "InFaxNoCsfStringsString132", null, null));
      }
      importView.InFaxNoCsfStringsString132 = FixedStringAttr.valueOf(s, (short)132);
   }
 
   public final int InGroupMax = 200;
   public short getInGroupCount() {
      return (short)(importView.InGroup_MA);
   };
 
   public void setInGroupCount(short s) throws PropertyVetoException {
      if (s < 0 || s > InGroupMax) {
         throw new PropertyVetoException("InGroupCount value is not a valid value. (0 to 200)",
               new PropertyChangeEvent (this, "InGroupCount", null, null));
      } else {
         importView.InGroup_MA = IntAttr.valueOf((int)s);
      }
   }
 
   public String getInGrpCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InGrpCsfLineActionBarSelectionFlag[index], 1);
   }
   public void setInGrpCsfLineActionBarSelectionFlag(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InGrpCsfLineActionBarSelectionFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InGrpCsfLineActionBarSelectionFlag", null, null));
      }
      importView.InGrpCsfLineActionBarSelectionFlag[index] = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInGStudentApplicationReadmissionMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return importView.InGStudentApplicationReadmissionMkAcademicYear[index];
   }
   public void setInGStudentApplicationReadmissionMkAcademicYear(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionMkAcademicYear", null, null));
      }
      importView.InGStudentApplicationReadmissionMkAcademicYear[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGStudentApplicationReadmissionMkAcademicYear(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionMkAcademicYear", null, null));
      }
      try {
          setInGStudentApplicationReadmissionMkAcademicYear(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionMkAcademicYear", null, null));
      }
   }
 
   public short getInGStudentApplicationReadmissionSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return importView.InGStudentApplicationReadmissionSemesterPeriod[index];
   }
   public void setInGStudentApplicationReadmissionSemesterPeriod(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionSemesterPeriod", null, null));
      }
      importView.InGStudentApplicationReadmissionSemesterPeriod[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGStudentApplicationReadmissionSemesterPeriod(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionSemesterPeriod", null, null));
      }
      try {
          setInGStudentApplicationReadmissionSemesterPeriod(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionSemesterPeriod", null, null));
      }
   }
 
   public int getInGStudentApplicationReadmissionMkStudentNr(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return importView.InGStudentApplicationReadmissionMkStudentNr[index];
   }
   public void setInGStudentApplicationReadmissionMkStudentNr(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionMkStudentNr", null, null));
      }
      importView.InGStudentApplicationReadmissionMkStudentNr[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGStudentApplicationReadmissionMkStudentNr(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionMkStudentNr", null, null));
      }
      try {
          setInGStudentApplicationReadmissionMkStudentNr(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionMkStudentNr", null, null));
      }
   }
 
   public String getInGStudentApplicationReadmissionReadmissionTypeGc329(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(importView.InGStudentApplicationReadmissionReadmissionTypeGc329[index]);
   }
   public void setInGStudentApplicationReadmissionReadmissionTypeGc329(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionReadmissionTypeGc329 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionReadmissionTypeGc329", null, null));
      }
      importView.InGStudentApplicationReadmissionReadmissionTypeGc329[index] = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInGStudentApplicationReadmissionMkQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(importView.InGStudentApplicationReadmissionMkQualificationCode[index]);
   }
   public void setInGStudentApplicationReadmissionMkQualificationCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionMkQualificationCode", null, null));
      }
      importView.InGStudentApplicationReadmissionMkQualificationCode[index] = StringAttr.valueOf(s, (short)5);
   }
 
   public Calendar getInGStudentApplicationReadmissionAppealSubmitDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toCalendar(importView.InGStudentApplicationReadmissionAppealSubmitDate[index]);
   }
   public String getAsStringInGStudentApplicationReadmissionAppealSubmitDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toString(importView.InGStudentApplicationReadmissionAppealSubmitDate[index]);
   }
   public void setInGStudentApplicationReadmissionAppealSubmitDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      importView.InGStudentApplicationReadmissionAppealSubmitDate[index] = TimestampAttr.valueOf(s);
   }
   public void setAsStringInGStudentApplicationReadmissionAppealSubmitDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGStudentApplicationReadmissionAppealSubmitDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InGStudentApplicationReadmissionAppealSubmitDate[index] = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGStudentApplicationReadmissionAppealSubmitDate has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InGStudentApplicationReadmissionAppealSubmitDate", null, null));
         }
      }
   }
 
   public String getInGStudentApplicationReadmissionStatusCodeGc328(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(importView.InGStudentApplicationReadmissionStatusCodeGc328[index]);
   }
   public void setInGStudentApplicationReadmissionStatusCodeGc328(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionStatusCodeGc328 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionStatusCodeGc328", null, null));
      }
      importView.InGStudentApplicationReadmissionStatusCodeGc328[index] = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInGStudentApplicationReadmissionAppealOutcomeGc330(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(importView.InGStudentApplicationReadmissionAppealOutcomeGc330[index]);
   }
   public void setInGStudentApplicationReadmissionAppealOutcomeGc330(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionAppealOutcomeGc330 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionAppealOutcomeGc330", null, null));
      }
      importView.InGStudentApplicationReadmissionAppealOutcomeGc330[index] = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInGStudentApplicationReadmissionStudentMotivation(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(importView.InGStudentApplicationReadmissionStudentMotivation[index]);
   }
   public void setInGStudentApplicationReadmissionStudentMotivation(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 2000) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionStudentMotivation must be <= 2000 characters.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionStudentMotivation", null, null));
      }
      importView.InGStudentApplicationReadmissionStudentMotivation[index] = StringAttr.valueOf(s, (short)2000);
   }
 
   public int getInGStudentApplicationReadmissionUpdatedBy(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return importView.InGStudentApplicationReadmissionUpdatedBy[index];
   }
   public void setInGStudentApplicationReadmissionUpdatedBy(int index, int s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionUpdatedBy has more than 5 digits.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionUpdatedBy", null, null));
      }
      importView.InGStudentApplicationReadmissionUpdatedBy[index] = IntAttr.valueOf(s);
   }
   public void setAsStringInGStudentApplicationReadmissionUpdatedBy(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionUpdatedBy is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionUpdatedBy", null, null));
      }
      try {
          setInGStudentApplicationReadmissionUpdatedBy(index, Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGStudentApplicationReadmissionUpdatedBy is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGStudentApplicationReadmissionUpdatedBy", null, null));
      }
   }
 
   public Calendar getInGStudentApplicationReadmissionUpdatedDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toCalendar(importView.InGStudentApplicationReadmissionUpdatedDate[index]);
   }
   public String getAsStringInGStudentApplicationReadmissionUpdatedDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toString(importView.InGStudentApplicationReadmissionUpdatedDate[index]);
   }
   public void setInGStudentApplicationReadmissionUpdatedDate(int index, Calendar s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      importView.InGStudentApplicationReadmissionUpdatedDate[index] = TimestampAttr.valueOf(s);
   }
   public void setAsStringInGStudentApplicationReadmissionUpdatedDate(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
         setInGStudentApplicationReadmissionUpdatedDate(index, (Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InGStudentApplicationReadmissionUpdatedDate[index] = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InGStudentApplicationReadmissionUpdatedDate has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InGStudentApplicationReadmissionUpdatedDate", null, null));
         }
      }
   }
 
   public String getInGStudentApplicationReadmissionUserComments(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(importView.InGStudentApplicationReadmissionUserComments[index]);
   }
   public void setInGStudentApplicationReadmissionUserComments(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 2000) {
         throw new PropertyVetoException("InGStudentApplicationReadmissionUserComments must be <= 2000 characters.",
               new PropertyChangeEvent (this, "InGStudentApplicationReadmissionUserComments", null, null));
      }
      importView.InGStudentApplicationReadmissionUserComments[index] = StringAttr.valueOf(s, (short)2000);
   }
 
   public String getInAdminSectionGCsfStringsString3(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InAdminSectionGCsfStringsString3[index], 3);
   }
   public void setInAdminSectionGCsfStringsString3(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InAdminSectionGCsfStringsString3 must be <= 3 characters.",
               new PropertyChangeEvent (this, "InAdminSectionGCsfStringsString3", null, null));
      }
      importView.InAdminSectionGCsfStringsString3[index] = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public String getInCollegeGCsfStringsString5(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(importView.InCollegeGCsfStringsString5[index], 5);
   }
   public void setInCollegeGCsfStringsString5(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s.length() > 5) {
         throw new PropertyVetoException("InCollegeGCsfStringsString5 must be <= 5 characters.",
               new PropertyChangeEvent (this, "InCollegeGCsfStringsString5", null, null));
      }
      importView.InCollegeGCsfStringsString5[index] = FixedStringAttr.valueOf(s, (short)5);
   }
 
   public short getInGWsQualificationCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return importView.InGWsQualificationCollegeCode[index];
   }
   public void setInGWsQualificationCollegeCode(int index, short s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InGWsQualificationCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InGWsQualificationCollegeCode", null, null));
      }
      importView.InGWsQualificationCollegeCode[index] = ShortAttr.valueOf(s);
   }
   public void setAsStringInGWsQualificationCollegeCode(int index, String s)
      throws ArrayIndexOutOfBoundsException, PropertyVetoException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InGWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsQualificationCollegeCode", null, null));
      }
      try {
          setInGWsQualificationCollegeCode(index, Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InGWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InGWsQualificationCollegeCode", null, null));
      }
   }
 
   public String getInCsfClientServerCommunicationsFirstpassFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsFirstpassFlag, 1);
   }
   public void setInCsfClientServerCommunicationsFirstpassFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsFirstpassFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsFirstpassFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsFirstpassFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientDate() {
      return DateAttr.toCalendar(importView.InCsfClientServerCommunicationsClientDate);
   }
   public int getAsIntInCsfClientServerCommunicationsClientDate() {
      return DateAttr.toInt(importView.InCsfClientServerCommunicationsClientDate);
   }
   public void setInCsfClientServerCommunicationsClientDate(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientDate = DateAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInCsfClientServerCommunicationsClientDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientDate", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsClientDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsClientDate(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toCalendar(importView.InCsfClientServerCommunicationsClientTime);
   }
   public int getAsIntInCsfClientServerCommunicationsClientTime() {
      return TimeAttr.toInt(importView.InCsfClientServerCommunicationsClientTime);
   }
   public void setInCsfClientServerCommunicationsClientTime(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientTime = TimeAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientTime(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientTime((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimeFormatter.parse(s.length() > 6 ? s.substring(0, 6) : s));
            setInCsfClientServerCommunicationsClientTime(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientTime has an invalid format (HHmmss).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTime", null, null));
         }
      }
   }
   public void setAsIntInCsfClientServerCommunicationsClientTime(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 6)
      {
         temp = "000000".substring(temp.length()) + temp;
      }
      setAsStringInCsfClientServerCommunicationsClientTime(temp);
   }
 
   public Calendar getInCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toCalendar(importView.InCsfClientServerCommunicationsClientTimestamp);
   }
   public String getAsStringInCsfClientServerCommunicationsClientTimestamp() {
      return TimestampAttr.toString(importView.InCsfClientServerCommunicationsClientTimestamp);
   }
   public void setInCsfClientServerCommunicationsClientTimestamp(Calendar s)
      throws PropertyVetoException {
      importView.InCsfClientServerCommunicationsClientTimestamp = TimestampAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientTimestamp(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInCsfClientServerCommunicationsClientTimestamp((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InCsfClientServerCommunicationsClientTimestamp = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InCsfClientServerCommunicationsClientTimestamp has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTimestamp", null, null));
         }
      }
   }
 
   public String getInCsfClientServerCommunicationsClientTransactionCode() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientTransactionCode, 8);
   }
   public void setInCsfClientServerCommunicationsClientTransactionCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientTransactionCode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientTransactionCode", null, null));
      }
      importView.InCsfClientServerCommunicationsClientTransactionCode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInCsfClientServerCommunicationsClientUserId() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientUserId, 8);
   }
   public void setInCsfClientServerCommunicationsClientUserId(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientUserId must be <= 8 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientUserId", null, null));
      }
      importView.InCsfClientServerCommunicationsClientUserId = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public short getInCsfClientServerCommunicationsClientVersionNumber() {
      return importView.InCsfClientServerCommunicationsClientVersionNumber;
   }
   public void setInCsfClientServerCommunicationsClientVersionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsClientVersionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientVersionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsClientVersionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientVersionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientVersionNumber", null, null));
      }
   }
 
   public short getInCsfClientServerCommunicationsClientRevisionNumber() {
      return importView.InCsfClientServerCommunicationsClientRevisionNumber;
   }
   public void setInCsfClientServerCommunicationsClientRevisionNumber(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
      importView.InCsfClientServerCommunicationsClientRevisionNumber = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfClientServerCommunicationsClientRevisionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
      try {
          setInCsfClientServerCommunicationsClientRevisionNumber(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfClientServerCommunicationsClientRevisionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientRevisionNumber", null, null));
      }
   }
 
   public String getInCsfClientServerCommunicationsClientDevelopmentPhase() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientDevelopmentPhase, 1);
   }
   public void setInCsfClientServerCommunicationsClientDevelopmentPhase(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientDevelopmentPhase must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientDevelopmentPhase", null, null));
      }
      importView.InCsfClientServerCommunicationsClientDevelopmentPhase = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsClientIsGuiFlag, 1);
   }
   public void setInCsfClientServerCommunicationsClientIsGuiFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsClientIsGuiFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsClientIsGuiFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsClientIsGuiFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsAction, 2);
   }
   public void setInCsfClientServerCommunicationsAction(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsAction must be <= 2 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsAction", null, null));
      }
      importView.InCsfClientServerCommunicationsAction = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInCsfClientServerCommunicationsListLinkFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsListLinkFlag, 1);
   }
   public void setInCsfClientServerCommunicationsListLinkFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsListLinkFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsListLinkFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsListLinkFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsMaintLinkFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsMaintLinkFlag, 1);
   }
   public void setInCsfClientServerCommunicationsMaintLinkFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsMaintLinkFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsMaintLinkFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsMaintLinkFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsCancelFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsCancelFlag, 1);
   }
   public void setInCsfClientServerCommunicationsCancelFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsCancelFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsCancelFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsCancelFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsForceDatabaseRead() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsForceDatabaseRead, 1);
   }
   public void setInCsfClientServerCommunicationsForceDatabaseRead(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsForceDatabaseRead must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsForceDatabaseRead", null, null));
      }
      importView.InCsfClientServerCommunicationsForceDatabaseRead = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
   public void setInCsfClientServerCommunicationsObjectRetrievedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsObjectRetrievedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsObjectRetrievedFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsObjectRetrievedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
   public void setInCsfClientServerCommunicationsServerRollbackFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsServerRollbackFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsServerRollbackFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsServerRollbackFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
   public void setInCsfClientServerCommunicationsRgvScrollUpFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsRgvScrollUpFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsRgvScrollUpFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsRgvScrollUpFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(importView.InCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
   public void setInCsfClientServerCommunicationsRgvScrollDownFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfClientServerCommunicationsRgvScrollDownFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfClientServerCommunicationsRgvScrollDownFlag", null, null));
      }
      importView.InCsfClientServerCommunicationsRgvScrollDownFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public int getInSecurityWsUserNumber() {
      return importView.InSecurityWsUserNumber;
   }
   public void setInSecurityWsUserNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSecurityWsUserNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
      importView.InSecurityWsUserNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
      try {
          setInSecurityWsUserNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsUserNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumber", null, null));
      }
   }
 
   public short getInSecurityWsUserNumberOfLogonAttempts() {
      return importView.InSecurityWsUserNumberOfLogonAttempts;
   }
   public void setInSecurityWsUserNumberOfLogonAttempts(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts has more than 1 digits.",
               new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
      importView.InSecurityWsUserNumberOfLogonAttempts = ShortAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserNumberOfLogonAttempts(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
      try {
          setInSecurityWsUserNumberOfLogonAttempts(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsUserNumberOfLogonAttempts is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsUserNumberOfLogonAttempts", null, null));
      }
   }
 
   public String getInSecurityWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserLoggedOnFlag, 1);
   }
   public void setInSecurityWsUserLoggedOnFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSecurityWsUserLoggedOnFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserLoggedOnFlag", null, null));
      }
      importView.InSecurityWsUserLoggedOnFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInSecurityWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserInUsedFlag, 1);
   }
   public void setInSecurityWsUserInUsedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InSecurityWsUserInUsedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserInUsedFlag", null, null));
      }
      importView.InSecurityWsUserInUsedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public Calendar getInSecurityWsUserLastLogonDate() {
      return DateAttr.toCalendar(importView.InSecurityWsUserLastLogonDate);
   }
   public int getAsIntInSecurityWsUserLastLogonDate() {
      return DateAttr.toInt(importView.InSecurityWsUserLastLogonDate);
   }
   public void setInSecurityWsUserLastLogonDate(Calendar s)
      throws PropertyVetoException {
      importView.InSecurityWsUserLastLogonDate = DateAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsUserLastLogonDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSecurityWsUserLastLogonDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeDateFormatter.parse(s.length() > 8 ? s.substring(0, 8) : s));
            setInSecurityWsUserLastLogonDate(tempCalendar);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSecurityWsUserLastLogonDate has an invalid format (yyyyMMdd).",
                  new PropertyChangeEvent (this, "InSecurityWsUserLastLogonDate", null, null));
         }
      }
   }
   public void setAsIntInSecurityWsUserLastLogonDate(int s)
      throws PropertyVetoException {
      String temp = Integer.toString(s);
      if (temp.length() < 8)
      {
         temp = "00000000".substring(temp.length()) + temp;
      }
      setAsStringInSecurityWsUserLastLogonDate(temp);
   }
 
   public String getInSecurityWsUserName() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserName, 28);
   }
   public void setInSecurityWsUserName(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InSecurityWsUserName must be <= 28 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserName", null, null));
      }
      importView.InSecurityWsUserName = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInSecurityWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPersonnelNo, 10);
   }
   public void setInSecurityWsUserPersonnelNo(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsUserPersonnelNo must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPersonnelNo", null, null));
      }
      importView.InSecurityWsUserPersonnelNo = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInSecurityWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPhoneNumber, 20);
   }
   public void setInSecurityWsUserPhoneNumber(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 20) {
         throw new PropertyVetoException("InSecurityWsUserPhoneNumber must be <= 20 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPhoneNumber", null, null));
      }
      importView.InSecurityWsUserPhoneNumber = FixedStringAttr.valueOf(s, (short)20);
   }
 
   public String getInSecurityWsUserPassword() {
      return FixedStringAttr.valueOf(importView.InSecurityWsUserPassword, 12);
   }
   public void setInSecurityWsUserPassword(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 12) {
         throw new PropertyVetoException("InSecurityWsUserPassword must be <= 12 characters.",
               new PropertyChangeEvent (this, "InSecurityWsUserPassword", null, null));
      }
      importView.InSecurityWsUserPassword = FixedStringAttr.valueOf(s, (short)12);
   }
 
   public String getInSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsPrinterCode, 10);
   }
   public void setInSecurityWsPrinterCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsPrinterCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsPrinterCode", null, null));
      }
      importView.InSecurityWsPrinterCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInCsfEventCommunicationsQueuedAction() {
      return FixedStringAttr.valueOf(importView.InCsfEventCommunicationsQueuedAction, 2);
   }
   public void setInCsfEventCommunicationsQueuedAction(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InCsfEventCommunicationsQueuedAction must be <= 2 characters.",
               new PropertyChangeEvent (this, "InCsfEventCommunicationsQueuedAction", null, null));
      }
      importView.InCsfEventCommunicationsQueuedAction = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInCsfEventCommunicationsPasteActiveFlag() {
      return FixedStringAttr.valueOf(importView.InCsfEventCommunicationsPasteActiveFlag, 1);
   }
   public void setInCsfEventCommunicationsPasteActiveFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfEventCommunicationsPasteActiveFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfEventCommunicationsPasteActiveFlag", null, null));
      }
      importView.InCsfEventCommunicationsPasteActiveFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfEventCommunicationsRgvRowRetrievedFlag() {
      return FixedStringAttr.valueOf(importView.InCsfEventCommunicationsRgvRowRetrievedFlag, 1);
   }
   public void setInCsfEventCommunicationsRgvRowRetrievedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfEventCommunicationsRgvRowRetrievedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfEventCommunicationsRgvRowRetrievedFlag", null, null));
      }
      importView.InCsfEventCommunicationsRgvRowRetrievedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfEventCommunicationsWaitForDialogFlag() {
      return FixedStringAttr.valueOf(importView.InCsfEventCommunicationsWaitForDialogFlag, 1);
   }
   public void setInCsfEventCommunicationsWaitForDialogFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfEventCommunicationsWaitForDialogFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfEventCommunicationsWaitForDialogFlag", null, null));
      }
      importView.InCsfEventCommunicationsWaitForDialogFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfEventCommunicationsMultiEventErrorFlag() {
      return FixedStringAttr.valueOf(importView.InCsfEventCommunicationsMultiEventErrorFlag, 1);
   }
   public void setInCsfEventCommunicationsMultiEventErrorFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfEventCommunicationsMultiEventErrorFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfEventCommunicationsMultiEventErrorFlag", null, null));
      }
      importView.InCsfEventCommunicationsMultiEventErrorFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public String getInCsfEventCommunicationsObjectChangedFlag() {
      return FixedStringAttr.valueOf(importView.InCsfEventCommunicationsObjectChangedFlag, 1);
   }
   public void setInCsfEventCommunicationsObjectChangedFlag(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 1) {
         throw new PropertyVetoException("InCsfEventCommunicationsObjectChangedFlag must be <= 1 characters.",
               new PropertyChangeEvent (this, "InCsfEventCommunicationsObjectChangedFlag", null, null));
      }
      importView.InCsfEventCommunicationsObjectChangedFlag = FixedStringAttr.valueOf(s, (short)1);
   }
 
   public short getInCsfEventCommunicationsRgvErrorCount() {
      return importView.InCsfEventCommunicationsRgvErrorCount;
   }
   public void setInCsfEventCommunicationsRgvErrorCount(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InCsfEventCommunicationsRgvErrorCount has more than 4 digits.",
               new PropertyChangeEvent (this, "InCsfEventCommunicationsRgvErrorCount", null, null));
      }
      importView.InCsfEventCommunicationsRgvErrorCount = ShortAttr.valueOf(s);
   }
   public void setAsStringInCsfEventCommunicationsRgvErrorCount(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InCsfEventCommunicationsRgvErrorCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfEventCommunicationsRgvErrorCount", null, null));
      }
      try {
          setInCsfEventCommunicationsRgvErrorCount(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InCsfEventCommunicationsRgvErrorCount is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InCsfEventCommunicationsRgvErrorCount", null, null));
      }
   }
 
   public String getInSecurityWsActionCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsActionCode, 2);
   }
   public void setInSecurityWsActionCode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 2) {
         throw new PropertyVetoException("InSecurityWsActionCode must be <= 2 characters.",
               new PropertyChangeEvent (this, "InSecurityWsActionCode", null, null));
      }
      importView.InSecurityWsActionCode = FixedStringAttr.valueOf(s, (short)2);
   }
 
   public String getInSecurityWsActionDescription() {
      return FixedStringAttr.valueOf(importView.InSecurityWsActionDescription, 10);
   }
   public void setInSecurityWsActionDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsActionDescription must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsActionDescription", null, null));
      }
      importView.InSecurityWsActionDescription = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public int getInSecurityWsFunctionNumber() {
      return importView.InSecurityWsFunctionNumber;
   }
   public void setInSecurityWsFunctionNumber(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSecurityWsFunctionNumber has more than 5 digits.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
      importView.InSecurityWsFunctionNumber = IntAttr.valueOf(s);
   }
   public void setAsStringInSecurityWsFunctionNumber(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSecurityWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
      try {
          setInSecurityWsFunctionNumber(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSecurityWsFunctionNumber is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSecurityWsFunctionNumber", null, null));
      }
   }
 
   public String getInSecurityWsFunctionTrancode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsFunctionTrancode, 8);
   }
   public void setInSecurityWsFunctionTrancode(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 8) {
         throw new PropertyVetoException("InSecurityWsFunctionTrancode must be <= 8 characters.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionTrancode", null, null));
      }
      importView.InSecurityWsFunctionTrancode = FixedStringAttr.valueOf(s, (short)8);
   }
 
   public String getInSecurityWsFunctionDescription() {
      return FixedStringAttr.valueOf(importView.InSecurityWsFunctionDescription, 28);
   }
   public void setInSecurityWsFunctionDescription(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 28) {
         throw new PropertyVetoException("InSecurityWsFunctionDescription must be <= 28 characters.",
               new PropertyChangeEvent (this, "InSecurityWsFunctionDescription", null, null));
      }
      importView.InSecurityWsFunctionDescription = FixedStringAttr.valueOf(s, (short)28);
   }
 
   public String getInSecurityWsWorkstationCode() {
      return FixedStringAttr.valueOf(importView.InSecurityWsWorkstationCode, 10);
   }
   public void setInSecurityWsWorkstationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSecurityWsWorkstationCode must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSecurityWsWorkstationCode", null, null));
      }
      importView.InSecurityWsWorkstationCode = FixedStringAttr.valueOf(s, (short)10);
   }
 
   public String getInProddevCsfStringsString4() {
      return FixedStringAttr.valueOf(importView.InProddevCsfStringsString4, 4);
   }
   public void setInProddevCsfStringsString4(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 4) {
         throw new PropertyVetoException("InProddevCsfStringsString4 must be <= 4 characters.",
               new PropertyChangeEvent (this, "InProddevCsfStringsString4", null, null));
      }
      importView.InProddevCsfStringsString4 = FixedStringAttr.valueOf(s, (short)4);
   }
 
   public String getInCsfStringsString500() {
      return StringAttr.valueOf(importView.InCsfStringsString500);
   }
   public void setInCsfStringsString500(String s)
      throws PropertyVetoException {
      if (s.length() > 500) {
         throw new PropertyVetoException("InCsfStringsString500 must be <= 500 characters.",
               new PropertyChangeEvent (this, "InCsfStringsString500", null, null));
      }
      importView.InCsfStringsString500 = StringAttr.valueOf(s, (short)500);
   }
 
   public short getInFilterStudentApplicationReadmissionMkAcademicYear() {
      return importView.InFilterStudentApplicationReadmissionMkAcademicYear;
   }
   public void setInFilterStudentApplicationReadmissionMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InFilterStudentApplicationReadmissionMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionMkAcademicYear", null, null));
      }
      importView.InFilterStudentApplicationReadmissionMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInFilterStudentApplicationReadmissionMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFilterStudentApplicationReadmissionMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionMkAcademicYear", null, null));
      }
      try {
          setInFilterStudentApplicationReadmissionMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFilterStudentApplicationReadmissionMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionMkAcademicYear", null, null));
      }
   }
 
   public short getInFilterStudentApplicationReadmissionSemesterPeriod() {
      return importView.InFilterStudentApplicationReadmissionSemesterPeriod;
   }
   public void setInFilterStudentApplicationReadmissionSemesterPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InFilterStudentApplicationReadmissionSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionSemesterPeriod", null, null));
      }
      importView.InFilterStudentApplicationReadmissionSemesterPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInFilterStudentApplicationReadmissionSemesterPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFilterStudentApplicationReadmissionSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionSemesterPeriod", null, null));
      }
      try {
          setInFilterStudentApplicationReadmissionSemesterPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFilterStudentApplicationReadmissionSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionSemesterPeriod", null, null));
      }
   }
 
   public int getInFilterStudentApplicationReadmissionMkStudentNr() {
      return importView.InFilterStudentApplicationReadmissionMkStudentNr;
   }
   public void setInFilterStudentApplicationReadmissionMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InFilterStudentApplicationReadmissionMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionMkStudentNr", null, null));
      }
      importView.InFilterStudentApplicationReadmissionMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInFilterStudentApplicationReadmissionMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFilterStudentApplicationReadmissionMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionMkStudentNr", null, null));
      }
      try {
          setInFilterStudentApplicationReadmissionMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFilterStudentApplicationReadmissionMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionMkStudentNr", null, null));
      }
   }
 
   public String getInFilterStudentApplicationReadmissionReadmissionTypeGc329() {
      return StringAttr.valueOf(importView.InFilterStudentApplicationReadmissionReadmissionTypeGc329);
   }
   public void setInFilterStudentApplicationReadmissionReadmissionTypeGc329(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InFilterStudentApplicationReadmissionReadmissionTypeGc329 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionReadmissionTypeGc329", null, null));
      }
      importView.InFilterStudentApplicationReadmissionReadmissionTypeGc329 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInFilterStudentApplicationReadmissionMkQualificationCode() {
      return StringAttr.valueOf(importView.InFilterStudentApplicationReadmissionMkQualificationCode);
   }
   public void setInFilterStudentApplicationReadmissionMkQualificationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 5) {
         throw new PropertyVetoException("InFilterStudentApplicationReadmissionMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionMkQualificationCode", null, null));
      }
      importView.InFilterStudentApplicationReadmissionMkQualificationCode = StringAttr.valueOf(s, (short)5);
   }
 
   public String getInFilterStudentApplicationReadmissionStatusCodeGc328() {
      return StringAttr.valueOf(importView.InFilterStudentApplicationReadmissionStatusCodeGc328);
   }
   public void setInFilterStudentApplicationReadmissionStatusCodeGc328(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InFilterStudentApplicationReadmissionStatusCodeGc328 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionStatusCodeGc328", null, null));
      }
      importView.InFilterStudentApplicationReadmissionStatusCodeGc328 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInFilterStudentApplicationReadmissionAppealOutcomeGc330() {
      return StringAttr.valueOf(importView.InFilterStudentApplicationReadmissionAppealOutcomeGc330);
   }
   public void setInFilterStudentApplicationReadmissionAppealOutcomeGc330(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InFilterStudentApplicationReadmissionAppealOutcomeGc330 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InFilterStudentApplicationReadmissionAppealOutcomeGc330", null, null));
      }
      importView.InFilterStudentApplicationReadmissionAppealOutcomeGc330 = StringAttr.valueOf(s, (short)10);
   }
 
   public short getInSelectedStudentApplicationReadmissionMkAcademicYear() {
      return importView.InSelectedStudentApplicationReadmissionMkAcademicYear;
   }
   public void setInSelectedStudentApplicationReadmissionMkAcademicYear(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10000.0) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionMkAcademicYear has more than 4 digits.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionMkAcademicYear", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionMkAcademicYear = ShortAttr.valueOf(s);
   }
   public void setAsStringInSelectedStudentApplicationReadmissionMkAcademicYear(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionMkAcademicYear", null, null));
      }
      try {
          setInSelectedStudentApplicationReadmissionMkAcademicYear(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionMkAcademicYear is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionMkAcademicYear", null, null));
      }
   }
 
   public short getInSelectedStudentApplicationReadmissionSemesterPeriod() {
      return importView.InSelectedStudentApplicationReadmissionSemesterPeriod;
   }
   public void setInSelectedStudentApplicationReadmissionSemesterPeriod(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 10.0) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionSemesterPeriod has more than 1 digits.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionSemesterPeriod", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionSemesterPeriod = ShortAttr.valueOf(s);
   }
   public void setAsStringInSelectedStudentApplicationReadmissionSemesterPeriod(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionSemesterPeriod", null, null));
      }
      try {
          setInSelectedStudentApplicationReadmissionSemesterPeriod(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionSemesterPeriod is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionSemesterPeriod", null, null));
      }
   }
 
   public int getInSelectedStudentApplicationReadmissionMkStudentNr() {
      return importView.InSelectedStudentApplicationReadmissionMkStudentNr;
   }
   public void setInSelectedStudentApplicationReadmissionMkStudentNr(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000000.0) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionMkStudentNr has more than 8 digits.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionMkStudentNr", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionMkStudentNr = IntAttr.valueOf(s);
   }
   public void setAsStringInSelectedStudentApplicationReadmissionMkStudentNr(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionMkStudentNr", null, null));
      }
      try {
          setInSelectedStudentApplicationReadmissionMkStudentNr(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionMkStudentNr is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionMkStudentNr", null, null));
      }
   }
 
   public String getInSelectedStudentApplicationReadmissionReadmissionTypeGc329() {
      return StringAttr.valueOf(importView.InSelectedStudentApplicationReadmissionReadmissionTypeGc329);
   }
   public void setInSelectedStudentApplicationReadmissionReadmissionTypeGc329(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionReadmissionTypeGc329 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionReadmissionTypeGc329", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionReadmissionTypeGc329 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInSelectedStudentApplicationReadmissionMkQualificationCode() {
      return StringAttr.valueOf(importView.InSelectedStudentApplicationReadmissionMkQualificationCode);
   }
   public void setInSelectedStudentApplicationReadmissionMkQualificationCode(String s)
      throws PropertyVetoException {
      if (s.length() > 5) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionMkQualificationCode must be <= 5 characters.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionMkQualificationCode", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionMkQualificationCode = StringAttr.valueOf(s, (short)5);
   }
 
   public Calendar getInSelectedStudentApplicationReadmissionAppealSubmitDate() {
      return TimestampAttr.toCalendar(importView.InSelectedStudentApplicationReadmissionAppealSubmitDate);
   }
   public String getAsStringInSelectedStudentApplicationReadmissionAppealSubmitDate() {
      return TimestampAttr.toString(importView.InSelectedStudentApplicationReadmissionAppealSubmitDate);
   }
   public void setInSelectedStudentApplicationReadmissionAppealSubmitDate(Calendar s)
      throws PropertyVetoException {
      importView.InSelectedStudentApplicationReadmissionAppealSubmitDate = TimestampAttr.valueOf(s);
   }
   public void setAsStringInSelectedStudentApplicationReadmissionAppealSubmitDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSelectedStudentApplicationReadmissionAppealSubmitDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InSelectedStudentApplicationReadmissionAppealSubmitDate = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSelectedStudentApplicationReadmissionAppealSubmitDate has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionAppealSubmitDate", null, null));
         }
      }
   }
 
   public String getInSelectedStudentApplicationReadmissionStatusCodeGc328() {
      return StringAttr.valueOf(importView.InSelectedStudentApplicationReadmissionStatusCodeGc328);
   }
   public void setInSelectedStudentApplicationReadmissionStatusCodeGc328(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionStatusCodeGc328 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionStatusCodeGc328", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionStatusCodeGc328 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInSelectedStudentApplicationReadmissionAppealOutcomeGc330() {
      return StringAttr.valueOf(importView.InSelectedStudentApplicationReadmissionAppealOutcomeGc330);
   }
   public void setInSelectedStudentApplicationReadmissionAppealOutcomeGc330(String s)
      throws PropertyVetoException {
      if (s.length() > 10) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionAppealOutcomeGc330 must be <= 10 characters.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionAppealOutcomeGc330", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionAppealOutcomeGc330 = StringAttr.valueOf(s, (short)10);
   }
 
   public String getInSelectedStudentApplicationReadmissionStudentMotivation() {
      return StringAttr.valueOf(importView.InSelectedStudentApplicationReadmissionStudentMotivation);
   }
   public void setInSelectedStudentApplicationReadmissionStudentMotivation(String s)
      throws PropertyVetoException {
      if (s.length() > 2000) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionStudentMotivation must be <= 2000 characters.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionStudentMotivation", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionStudentMotivation = StringAttr.valueOf(s, (short)2000);
   }
 
   public int getInSelectedStudentApplicationReadmissionUpdatedBy() {
      return importView.InSelectedStudentApplicationReadmissionUpdatedBy;
   }
   public void setInSelectedStudentApplicationReadmissionUpdatedBy(int s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 100000.0) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionUpdatedBy has more than 5 digits.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionUpdatedBy", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionUpdatedBy = IntAttr.valueOf(s);
   }
   public void setAsStringInSelectedStudentApplicationReadmissionUpdatedBy(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionUpdatedBy is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionUpdatedBy", null, null));
      }
      try {
          setInSelectedStudentApplicationReadmissionUpdatedBy(Integer.parseInt(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSelectedStudentApplicationReadmissionUpdatedBy is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionUpdatedBy", null, null));
      }
   }
 
   public Calendar getInSelectedStudentApplicationReadmissionUpdatedDate() {
      return TimestampAttr.toCalendar(importView.InSelectedStudentApplicationReadmissionUpdatedDate);
   }
   public String getAsStringInSelectedStudentApplicationReadmissionUpdatedDate() {
      return TimestampAttr.toString(importView.InSelectedStudentApplicationReadmissionUpdatedDate);
   }
   public void setInSelectedStudentApplicationReadmissionUpdatedDate(Calendar s)
      throws PropertyVetoException {
      importView.InSelectedStudentApplicationReadmissionUpdatedDate = TimestampAttr.valueOf(s);
   }
   public void setAsStringInSelectedStudentApplicationReadmissionUpdatedDate(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
         setInSelectedStudentApplicationReadmissionUpdatedDate((Calendar)null);
      } else {
         Calendar tempCalendar = Calendar.getInstance();
         try {
            tempCalendar.setTime(nativeTimestampFormatter.parse(s.length() > 17 ? s.substring(0, 17) : s));
            importView.InSelectedStudentApplicationReadmissionUpdatedDate = TimestampAttr.valueOf(s);
         } catch (ParseException e) {
            throw new PropertyVetoException("InSelectedStudentApplicationReadmissionUpdatedDate has an invalid format (yyyyMMddHHmmssSSSSSS).",
                  new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionUpdatedDate", null, null));
         }
      }
   }
 
   public String getInSelectedStudentApplicationReadmissionUserComments() {
      return StringAttr.valueOf(importView.InSelectedStudentApplicationReadmissionUserComments);
   }
   public void setInSelectedStudentApplicationReadmissionUserComments(String s)
      throws PropertyVetoException {
      if (s.length() > 2000) {
         throw new PropertyVetoException("InSelectedStudentApplicationReadmissionUserComments must be <= 2000 characters.",
               new PropertyChangeEvent (this, "InSelectedStudentApplicationReadmissionUserComments", null, null));
      }
      importView.InSelectedStudentApplicationReadmissionUserComments = StringAttr.valueOf(s, (short)2000);
   }
 
   public String getInFilterWsQualificationAdminSection() {
      return FixedStringAttr.valueOf(importView.InFilterWsQualificationAdminSection, 3);
   }
   public void setInFilterWsQualificationAdminSection(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InFilterWsQualificationAdminSection must be <= 3 characters.",
               new PropertyChangeEvent (this, "InFilterWsQualificationAdminSection", null, null));
      }
      importView.InFilterWsQualificationAdminSection = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public short getInFilterWsQualificationCollegeCode() {
      return importView.InFilterWsQualificationCollegeCode;
   }
   public void setInFilterWsQualificationCollegeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InFilterWsQualificationCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InFilterWsQualificationCollegeCode", null, null));
      }
      importView.InFilterWsQualificationCollegeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInFilterWsQualificationCollegeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InFilterWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterWsQualificationCollegeCode", null, null));
      }
      try {
          setInFilterWsQualificationCollegeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InFilterWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InFilterWsQualificationCollegeCode", null, null));
      }
   }
 
   public String getInSelectedWsQualificationAdminSection() {
      return FixedStringAttr.valueOf(importView.InSelectedWsQualificationAdminSection, 3);
   }
   public void setInSelectedWsQualificationAdminSection(String s)
      throws PropertyVetoException {
      if (s != null) {
          s = s.toUpperCase();
      }
      if (s.length() > 3) {
         throw new PropertyVetoException("InSelectedWsQualificationAdminSection must be <= 3 characters.",
               new PropertyChangeEvent (this, "InSelectedWsQualificationAdminSection", null, null));
      }
      importView.InSelectedWsQualificationAdminSection = FixedStringAttr.valueOf(s, (short)3);
   }
 
   public short getInSelectedWsQualificationCollegeCode() {
      return importView.InSelectedWsQualificationCollegeCode;
   }
   public void setInSelectedWsQualificationCollegeCode(short s)
      throws PropertyVetoException {
      if (java.lang.Math.abs(s) >= 1000.0) {
         throw new PropertyVetoException("InSelectedWsQualificationCollegeCode has more than 3 digits.",
               new PropertyChangeEvent (this, "InSelectedWsQualificationCollegeCode", null, null));
      }
      importView.InSelectedWsQualificationCollegeCode = ShortAttr.valueOf(s);
   }
   public void setAsStringInSelectedWsQualificationCollegeCode(String s)
      throws PropertyVetoException {
      if (s == null || s.length() == 0) {
          throw new PropertyVetoException("InSelectedWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedWsQualificationCollegeCode", null, null));
      }
      try {
          setInSelectedWsQualificationCollegeCode(Short.parseShort(s) );
      } catch (NumberFormatException e) {
          throw new PropertyVetoException("InSelectedWsQualificationCollegeCode is not a valid numeric value: " + s,
                                          new PropertyChangeEvent (this, "InSelectedWsQualificationCollegeCode", null, null));
      }
   }
 
   public final int OutCollegeGroupMax = 20;
   public short getOutCollegeGroupCount() {
      return (short)(exportView.OutCollegeGroup_MA);
   };
 
   public String getOutCollegeGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutCollegeGCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public short getOutCollegeGWsUnisaCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return exportView.OutCollegeGWsUnisaCollegeCode[index];
   }
 
   public String getOutCollegeGWsUnisaCollegeEngDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutCollegeGWsUnisaCollegeEngDescription[index], 100);
   }
 
   public String getOutCollegeGWsUnisaCollegeAfrDescription(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutCollegeGWsUnisaCollegeAfrDescription[index], 100);
   }
 
   public String getOutCollegeGWsUnisaCollegeAbbreviation(int index) throws ArrayIndexOutOfBoundsException {
      if (19 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 19, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutCollegeGWsUnisaCollegeAbbreviation[index], 10);
   }
 
   public String getOutEmailOrFaxCsfStringsString1() {
      return FixedStringAttr.valueOf(exportView.OutEmailOrFaxCsfStringsString1, 1);
   }
 
   public String getOutEmailFromCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailFromCsfStringsString132, 132);
   }
 
   public String getOutEmailToCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutEmailToCsfStringsString132, 132);
   }
 
   public String getOutFaxNameCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNameCsfStringsString132, 132);
   }
 
   public String getOutFaxNoCsfStringsString132() {
      return FixedStringAttr.valueOf(exportView.OutFaxNoCsfStringsString132, 132);
   }
 
   public final int OutGroupMax = 200;
   public short getOutGroupCount() {
      return (short)(exportView.OutGroup_MA);
   };
 
   public String getOutGCsfLineActionBarSelectionFlag(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutGCsfLineActionBarSelectionFlag[index], 1);
   }
 
   public short getOutGStudentApplicationReadmissionMkAcademicYear(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return exportView.OutGStudentApplicationReadmissionMkAcademicYear[index];
   }
 
   public short getOutGStudentApplicationReadmissionSemesterPeriod(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return exportView.OutGStudentApplicationReadmissionSemesterPeriod[index];
   }
 
   public int getOutGStudentApplicationReadmissionMkStudentNr(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return exportView.OutGStudentApplicationReadmissionMkStudentNr[index];
   }
 
   public String getOutGStudentApplicationReadmissionReadmissionTypeGc329(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGStudentApplicationReadmissionReadmissionTypeGc329[index]);
   }
 
   public String getOutGStudentApplicationReadmissionMkQualificationCode(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGStudentApplicationReadmissionMkQualificationCode[index]);
   }
 
   public Calendar getOutGStudentApplicationReadmissionAppealSubmitDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutGStudentApplicationReadmissionAppealSubmitDate[index]);
   }
   public String getAsStringOutGStudentApplicationReadmissionAppealSubmitDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutGStudentApplicationReadmissionAppealSubmitDate[index]);
   }
 
   public String getOutGStudentApplicationReadmissionStatusCodeGc328(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGStudentApplicationReadmissionStatusCodeGc328[index]);
   }
 
   public String getOutGStudentApplicationReadmissionAppealOutcomeGc330(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGStudentApplicationReadmissionAppealOutcomeGc330[index]);
   }
 
   public String getOutGStudentApplicationReadmissionStudentMotivation(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGStudentApplicationReadmissionStudentMotivation[index]);
   }
 
   public int getOutGStudentApplicationReadmissionUpdatedBy(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return exportView.OutGStudentApplicationReadmissionUpdatedBy[index];
   }
 
   public Calendar getOutGStudentApplicationReadmissionUpdatedDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toCalendar(exportView.OutGStudentApplicationReadmissionUpdatedDate[index]);
   }
   public String getAsStringOutGStudentApplicationReadmissionUpdatedDate(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return TimestampAttr.toString(exportView.OutGStudentApplicationReadmissionUpdatedDate[index]);
   }
 
   public String getOutGStudentApplicationReadmissionUserComments(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return StringAttr.valueOf(exportView.OutGStudentApplicationReadmissionUserComments[index]);
   }
 
   public String getOutAdminSectionGCsfStringsString3(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutAdminSectionGCsfStringsString3[index], 3);
   }
 
   public String getOutCollegeGCsfStringsString5(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return FixedStringAttr.valueOf(exportView.OutCollegeGCsfStringsString5[index], 5);
   }
 
   public short getOutGWsQualificationCollegeCode(int index) throws ArrayIndexOutOfBoundsException {
      if (199 < index || index < 0) {
         throw new ArrayIndexOutOfBoundsException("index range must be from 0 to 199, not: " + index);
      }
      return exportView.OutGWsQualificationCollegeCode[index];
   }
 
   public short getOutCsfClientServerCommunicationsClientVersionNumber() {
      return exportView.OutCsfClientServerCommunicationsClientVersionNumber;
   }
 
   public short getOutCsfClientServerCommunicationsClientRevisionNumber() {
      return exportView.OutCsfClientServerCommunicationsClientRevisionNumber;
   }
 
   public String getOutCsfClientServerCommunicationsClientDevelopmentPhase() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientDevelopmentPhase, 1);
   }
 
   public String getOutCsfClientServerCommunicationsClientIsGuiFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsClientIsGuiFlag, 1);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerDate() {
      return DateAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerDate);
   }
   public int getAsIntOutCsfClientServerCommunicationsServerDate() {
      return DateAttr.toInt(exportView.OutCsfClientServerCommunicationsServerDate);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerTime);
   }
   public int getAsIntOutCsfClientServerCommunicationsServerTime() {
      return TimeAttr.toInt(exportView.OutCsfClientServerCommunicationsServerTime);
   }
 
   public Calendar getOutCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toCalendar(exportView.OutCsfClientServerCommunicationsServerTimestamp);
   }
   public String getAsStringOutCsfClientServerCommunicationsServerTimestamp() {
      return TimestampAttr.toString(exportView.OutCsfClientServerCommunicationsServerTimestamp);
   }
 
   public String getOutCsfClientServerCommunicationsServerTransactionCode() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerTransactionCode, 8);
   }
 
   public String getOutCsfClientServerCommunicationsServerUserId() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerUserId, 8);
   }
 
   public short getOutCsfClientServerCommunicationsServerVersionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerVersionNumber;
   }
 
   public short getOutCsfClientServerCommunicationsServerRevisionNumber() {
      return exportView.OutCsfClientServerCommunicationsServerRevisionNumber;
   }
 
   public String getOutCsfClientServerCommunicationsServerDevelopmentPhase() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerDevelopmentPhase, 1);
   }
 
   public short getOutCsfClientServerCommunicationsReturnCode() {
      return exportView.OutCsfClientServerCommunicationsReturnCode;
   }
 
   public String getOutCsfClientServerCommunicationsAction() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsAction, 2);
   }
 
   public String getOutCsfClientServerCommunicationsActionsPendingFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsActionsPendingFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsListLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsListLinkFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsMaintLinkFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsMaintLinkFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsCancelFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsCancelFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsForceDatabaseRead() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsForceDatabaseRead, 1);
   }
 
   public String getOutCsfClientServerCommunicationsObjectRetrievedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsObjectRetrievedFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsServerRollbackFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsServerRollbackFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollUpFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollUpFlag, 1);
   }
 
   public String getOutCsfClientServerCommunicationsRgvScrollDownFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfClientServerCommunicationsRgvScrollDownFlag, 1);
   }
 
   public int getOutSecurityWsUserNumber() {
      return exportView.OutSecurityWsUserNumber;
   }
 
   public short getOutSecurityWsUserNumberOfLogonAttempts() {
      return exportView.OutSecurityWsUserNumberOfLogonAttempts;
   }
 
   public String getOutSecurityWsUserLoggedOnFlag() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserLoggedOnFlag, 1);
   }
 
   public String getOutSecurityWsUserInUsedFlag() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserInUsedFlag, 1);
   }
 
   public Calendar getOutSecurityWsUserLastLogonDate() {
      return DateAttr.toCalendar(exportView.OutSecurityWsUserLastLogonDate);
   }
   public int getAsIntOutSecurityWsUserLastLogonDate() {
      return DateAttr.toInt(exportView.OutSecurityWsUserLastLogonDate);
   }
 
   public String getOutSecurityWsUserName() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserName, 28);
   }
 
   public String getOutSecurityWsUserPersonnelNo() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPersonnelNo, 10);
   }
 
   public String getOutSecurityWsUserPhoneNumber() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPhoneNumber, 20);
   }
 
   public String getOutSecurityWsUserPassword() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsUserPassword, 12);
   }
 
   public String getOutSecurityWsPrinterCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsPrinterCode, 10);
   }
 
   public String getOutCsfEventCommunicationsQueuedAction() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsQueuedAction, 2);
   }
 
   public String getOutCsfEventCommunicationsPasteActiveFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsPasteActiveFlag, 1);
   }
 
   public String getOutCsfEventCommunicationsRgvRowRetrievedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsRgvRowRetrievedFlag, 1);
   }
 
   public String getOutCsfEventCommunicationsWaitForDialogFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsWaitForDialogFlag, 1);
   }
 
   public String getOutCsfEventCommunicationsMultiEventErrorFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsMultiEventErrorFlag, 1);
   }
 
   public String getOutCsfEventCommunicationsObjectChangedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsObjectChangedFlag, 1);
   }
 
   public short getOutCsfEventCommunicationsRgvErrorCount() {
      return exportView.OutCsfEventCommunicationsRgvErrorCount;
   }
 
   public String getOutCsfEventCommunicationsChangedEventInitiator() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsChangedEventInitiator, 20);
   }
 
   public String getOutCsfEventCommunicationsRgvLineAddedFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsRgvLineAddedFlag, 1);
   }
 
   public String getOutCsfEventCommunicationsDialogFirstpassFlag() {
      return FixedStringAttr.valueOf(exportView.OutCsfEventCommunicationsDialogFirstpassFlag, 1);
   }
 
   public String getOutSecurityWsActionCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsActionCode, 2);
   }
 
   public String getOutSecurityWsActionDescription() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsActionDescription, 10);
   }
 
   public int getOutSecurityWsFunctionNumber() {
      return exportView.OutSecurityWsFunctionNumber;
   }
 
   public String getOutSecurityWsFunctionTrancode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsFunctionTrancode, 8);
   }
 
   public String getOutSecurityWsFunctionDescription() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsFunctionDescription, 28);
   }
 
   public String getOutSecurityWsWorkstationCode() {
      return FixedStringAttr.valueOf(exportView.OutSecurityWsWorkstationCode, 10);
   }
 
   public String getOutProddevCsfStringsString4() {
      return FixedStringAttr.valueOf(exportView.OutProddevCsfStringsString4, 4);
   }
 
   public String getOutCsfStringsString500() {
      return StringAttr.valueOf(exportView.OutCsfStringsString500);
   }
 
   public short getOutFilterStudentApplicationReadmissionMkAcademicYear() {
      return exportView.OutFilterStudentApplicationReadmissionMkAcademicYear;
   }
 
   public short getOutFilterStudentApplicationReadmissionSemesterPeriod() {
      return exportView.OutFilterStudentApplicationReadmissionSemesterPeriod;
   }
 
   public int getOutFilterStudentApplicationReadmissionMkStudentNr() {
      return exportView.OutFilterStudentApplicationReadmissionMkStudentNr;
   }
 
   public String getOutFilterStudentApplicationReadmissionReadmissionTypeGc329() {
      return StringAttr.valueOf(exportView.OutFilterStudentApplicationReadmissionReadmissionTypeGc329);
   }
 
   public String getOutFilterStudentApplicationReadmissionMkQualificationCode() {
      return StringAttr.valueOf(exportView.OutFilterStudentApplicationReadmissionMkQualificationCode);
   }
 
   public String getOutFilterStudentApplicationReadmissionStatusCodeGc328() {
      return StringAttr.valueOf(exportView.OutFilterStudentApplicationReadmissionStatusCodeGc328);
   }
 
   public String getOutFilterStudentApplicationReadmissionAppealOutcomeGc330() {
      return StringAttr.valueOf(exportView.OutFilterStudentApplicationReadmissionAppealOutcomeGc330);
   }
 
   public short getOutSelectedStudentApplicationReadmissionMkAcademicYear() {
      return exportView.OutSelectedStudentApplicationReadmissionMkAcademicYear;
   }
 
   public short getOutSelectedStudentApplicationReadmissionSemesterPeriod() {
      return exportView.OutSelectedStudentApplicationReadmissionSemesterPeriod;
   }
 
   public int getOutSelectedStudentApplicationReadmissionMkStudentNr() {
      return exportView.OutSelectedStudentApplicationReadmissionMkStudentNr;
   }
 
   public String getOutSelectedStudentApplicationReadmissionReadmissionTypeGc329() {
      return StringAttr.valueOf(exportView.OutSelectedStudentApplicationReadmissionReadmissionTypeGc329);
   }
 
   public String getOutSelectedStudentApplicationReadmissionMkQualificationCode() {
      return StringAttr.valueOf(exportView.OutSelectedStudentApplicationReadmissionMkQualificationCode);
   }
 
   public Calendar getOutSelectedStudentApplicationReadmissionAppealSubmitDate() {
      return TimestampAttr.toCalendar(exportView.OutSelectedStudentApplicationReadmissionAppealSubmitDate);
   }
   public String getAsStringOutSelectedStudentApplicationReadmissionAppealSubmitDate() {
      return TimestampAttr.toString(exportView.OutSelectedStudentApplicationReadmissionAppealSubmitDate);
   }
 
   public String getOutSelectedStudentApplicationReadmissionStatusCodeGc328() {
      return StringAttr.valueOf(exportView.OutSelectedStudentApplicationReadmissionStatusCodeGc328);
   }
 
   public String getOutSelectedStudentApplicationReadmissionAppealOutcomeGc330() {
      return StringAttr.valueOf(exportView.OutSelectedStudentApplicationReadmissionAppealOutcomeGc330);
   }
 
   public String getOutSelectedStudentApplicationReadmissionStudentMotivation() {
      return StringAttr.valueOf(exportView.OutSelectedStudentApplicationReadmissionStudentMotivation);
   }
 
   public int getOutSelectedStudentApplicationReadmissionUpdatedBy() {
      return exportView.OutSelectedStudentApplicationReadmissionUpdatedBy;
   }
 
   public Calendar getOutSelectedStudentApplicationReadmissionUpdatedDate() {
      return TimestampAttr.toCalendar(exportView.OutSelectedStudentApplicationReadmissionUpdatedDate);
   }
   public String getAsStringOutSelectedStudentApplicationReadmissionUpdatedDate() {
      return TimestampAttr.toString(exportView.OutSelectedStudentApplicationReadmissionUpdatedDate);
   }
 
   public String getOutSelectedStudentApplicationReadmissionUserComments() {
      return StringAttr.valueOf(exportView.OutSelectedStudentApplicationReadmissionUserComments);
   }
 
   public String getOutFilterWsQualificationAdminSection() {
      return FixedStringAttr.valueOf(exportView.OutFilterWsQualificationAdminSection, 3);
   }
 
   public short getOutFilterWsQualificationCollegeCode() {
      return exportView.OutFilterWsQualificationCollegeCode;
   }
 
   public String getOutSelectedWsQualificationAdminSection() {
      return FixedStringAttr.valueOf(exportView.OutSelectedWsQualificationAdminSection, 3);
   }
 
   public short getOutSelectedWsQualificationCollegeCode() {
      return exportView.OutSelectedWsQualificationCollegeCode;
   }
 
};
