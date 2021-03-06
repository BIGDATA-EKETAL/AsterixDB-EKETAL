//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:23:57 AM COT 
//


package org.apache.asterix.common.configuration;

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
 *         &lt;element ref="{asterixconf}ncId"/>
 *         &lt;element ref="{asterixconf}txnLogDirPath"/>
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
    "ncId",
    "txnLogDirPath"
})
@XmlRootElement(name = "transactionLogDir")
public class TransactionLogDir implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String ncId;
    @XmlElement(required = true)
    protected String txnLogDirPath;

    /**
     * Default no-arg constructor
     * 
     */
    public TransactionLogDir() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public TransactionLogDir(final String ncId, final String txnLogDirPath) {
        this.ncId = ncId;
        this.txnLogDirPath = txnLogDirPath;
    }

    /**
     * Gets the value of the ncId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNcId() {
        return ncId;
    }

    /**
     * Sets the value of the ncId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNcId(String value) {
        this.ncId = value;
    }

    /**
     * Gets the value of the txnLogDirPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnLogDirPath() {
        return txnLogDirPath;
    }

    /**
     * Sets the value of the txnLogDirPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnLogDirPath(String value) {
        this.txnLogDirPath = value;
    }

}
