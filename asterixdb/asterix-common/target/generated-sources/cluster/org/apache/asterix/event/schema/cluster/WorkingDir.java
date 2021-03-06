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
 *         &lt;element ref="{cluster}dir"/>
 *         &lt;element ref="{cluster}NFS"/>
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
    "dir",
    "nfs"
})
@XmlRootElement(name = "working_dir")
public class WorkingDir implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String dir;
    @XmlElement(name = "NFS")
    protected boolean nfs;

    /**
     * Default no-arg constructor
     * 
     */
    public WorkingDir() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public WorkingDir(final String dir, final boolean nfs) {
        this.dir = dir;
        this.nfs = nfs;
    }

    /**
     * Gets the value of the dir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDir() {
        return dir;
    }

    /**
     * Sets the value of the dir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDir(String value) {
        this.dir = value;
    }

    /**
     * Gets the value of the nfs property.
     * 
     */
    public boolean isNFS() {
        return nfs;
    }

    /**
     * Sets the value of the nfs property.
     * 
     */
    public void setNFS(boolean value) {
        this.nfs = value;
    }

}
