//
//This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
//See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
//Any modifications to this file will be lost upon recompilation of the source schema. 
//Generated on: 2010.02.23 at 11:10:13 AM MST 
//


package gov.utah.dts.det.ccl.logging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
* <p>Java class for anonymous complex type.
* 
* <p>The following schema fragment specifies the expected content contained within this class.
* 
* <pre>
* &lt;complexType>
*   &lt;complexContent>
*     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       &lt;sequence>
*         &lt;element name="Application" type="{http://dts.utah.gov/alm/webservice/model}Application"/>
*         &lt;element name="LogData" type="{http://dts.utah.gov/alm/webservice/model}LogData"/>
*       &lt;/sequence>
*     &lt;/restriction>
*   &lt;/complexContent>
* &lt;/complexType>
* </pre>
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "application",
 "logData"
})
@XmlRootElement(name = "WriteLogRequest", namespace = "http://dts.utah.gov/alm/webservice/model")
public class WriteLogRequest {

 @XmlElement(name = "Application", required = true, namespace = "http://dts.utah.gov/alm/webservice/model")
 protected Application application;
 @XmlElement(name = "LogData", required = true, namespace = "http://dts.utah.gov/alm/webservice/model")
 protected LogData logData;

 /**
  * Gets the value of the application property.
  * 
  * @return
  *     possible object is
  *     {@link Application }
  *     
  */
 public Application getApplication() {
     return application;
 }

 /**
  * Sets the value of the application property.
  * 
  * @param value
  *     allowed object is
  *     {@link Application }
  *     
  */
 public void setApplication(Application value) {
     this.application = value;
 }

 /**
  * Gets the value of the logData property.
  * 
  * @return
  *     possible object is
  *     {@link LogData }
  *     
  */
 public LogData getLogData() {
     return logData;
 }

 /**
  * Sets the value of the logData property.
  * 
  * @param value
  *     allowed object is
  *     {@link LogData }
  *     
  */
 public void setLogData(LogData value) {
     this.logData = value;
 }

}