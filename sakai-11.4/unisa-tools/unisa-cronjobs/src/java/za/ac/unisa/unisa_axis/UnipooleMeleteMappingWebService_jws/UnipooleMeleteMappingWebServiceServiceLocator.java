/**
 * UnipooleMeleteMappingWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws;

public class UnipooleMeleteMappingWebServiceServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebServiceService {

    public UnipooleMeleteMappingWebServiceServiceLocator() {
    }


    public UnipooleMeleteMappingWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UnipooleMeleteMappingWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UnipooleMeleteMappingWebService
    private java.lang.String UnipooleMeleteMappingWebService_address = "https://mydev.unisa.ac.za/unisa-axis/UnipooleMeleteMappingWebService.jws";

    public java.lang.String getUnipooleMeleteMappingWebServiceAddress() {
        return UnipooleMeleteMappingWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UnipooleMeleteMappingWebServiceWSDDServiceName = "UnipooleMeleteMappingWebService";

    public java.lang.String getUnipooleMeleteMappingWebServiceWSDDServiceName() {
        return UnipooleMeleteMappingWebServiceWSDDServiceName;
    }

    public void setUnipooleMeleteMappingWebServiceWSDDServiceName(java.lang.String name) {
        UnipooleMeleteMappingWebServiceWSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebService_PortType getUnipooleMeleteMappingWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UnipooleMeleteMappingWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUnipooleMeleteMappingWebService(endpoint);
    }

    public za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebService_PortType getUnipooleMeleteMappingWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getUnipooleMeleteMappingWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUnipooleMeleteMappingWebServiceEndpointAddress(java.lang.String address) {
        UnipooleMeleteMappingWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.UnipooleMeleteMappingWebService_jws.UnipooleMeleteMappingWebServiceSoapBindingStub(new java.net.URL(UnipooleMeleteMappingWebService_address), this);
                _stub.setPortName(getUnipooleMeleteMappingWebServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("UnipooleMeleteMappingWebService".equals(inputPortName)) {
            return getUnipooleMeleteMappingWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.unisa.ac.za/unisa-axis/UnipooleMeleteMappingWebService.jws", "UnipooleMeleteMappingWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.unisa.ac.za/unisa-axis/UnipooleMeleteMappingWebService.jws", "UnipooleMeleteMappingWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("UnipooleMeleteMappingWebService".equals(portName)) {
            setUnipooleMeleteMappingWebServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
