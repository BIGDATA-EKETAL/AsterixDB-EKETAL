//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:24:14 AM COT 
//


package org.apache.asterix.installer.schema.conf;

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
 *         &lt;element ref="{installer}hdfs" minOccurs="0"/>
 *         &lt;element ref="{installer}backupDir"/>
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
    "hdfs",
    "backupDir"
})
@XmlRootElement(name = "backup")
public class Backup implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Hdfs hdfs;
    @XmlElement(required = true)
    protected String backupDir;

    /**
     * Default no-arg constructor
     * 
     */
    public Backup() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public Backup(final Hdfs hdfs, final String backupDir) {
        this.hdfs = hdfs;
        this.backupDir = backupDir;
    }

    /**
     * Gets the value of the hdfs property.
     * 
     * @return
     *     possible object is
     *     {@link Hdfs }
     *     
     */
    public Hdfs getHdfs() {
        return hdfs;
    }

    /**
     * Sets the value of the hdfs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Hdfs }
     *     
     */
    public void setHdfs(Hdfs value) {
        this.hdfs = value;
    }

    /**
     * Gets the value of the backupDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackupDir() {
        return backupDir;
    }

    /**
     * Sets the value of the backupDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackupDir(String value) {
        this.backupDir = value;
    }

}