//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:23:57 AM COT 
//


package org.apache.asterix.event.schema.cluster;

import java.io.Serializable;
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
 *         &lt;element ref="{cluster}enabled" minOccurs="0"/>
 *         &lt;element ref="{cluster}data_replication" minOccurs="0"/>
 *         &lt;element ref="{cluster}fault_tolerance" minOccurs="0"/>
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
    "enabled",
    "dataReplication",
    "faultTolerance"
})
@XmlRootElement(name = "high_availability")
public class HighAvailability implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String enabled;
    @XmlElement(name = "data_replication")
    protected DataReplication dataReplication;
    @XmlElement(name = "fault_tolerance")
    protected FaultTolerance faultTolerance;

    /**
     * Default no-arg constructor
     * 
     */
    public HighAvailability() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public HighAvailability(final String enabled, final DataReplication dataReplication, final FaultTolerance faultTolerance) {
        this.enabled = enabled;
        this.dataReplication = dataReplication;
        this.faultTolerance = faultTolerance;
    }

    /**
     * Gets the value of the enabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnabled(String value) {
        this.enabled = value;
    }

    /**
     * Gets the value of the dataReplication property.
     * 
     * @return
     *     possible object is
     *     {@link DataReplication }
     *     
     */
    public DataReplication getDataReplication() {
        return dataReplication;
    }

    /**
     * Sets the value of the dataReplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataReplication }
     *     
     */
    public void setDataReplication(DataReplication value) {
        this.dataReplication = value;
    }

    /**
     * Gets the value of the faultTolerance property.
     * 
     * @return
     *     possible object is
     *     {@link FaultTolerance }
     *     
     */
    public FaultTolerance getFaultTolerance() {
        return faultTolerance;
    }

    /**
     * Sets the value of the faultTolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultTolerance }
     *     
     */
    public void setFaultTolerance(FaultTolerance value) {
        this.faultTolerance = value;
    }

}
