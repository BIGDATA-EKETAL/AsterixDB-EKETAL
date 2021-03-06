//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:23:57 AM COT 
//


package org.apache.asterix.event.schema.cluster;

import java.io.Serializable;
import java.math.BigInteger;
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
 *         &lt;element ref="{cluster}id"/>
 *         &lt;element ref="{cluster}cluster_ip"/>
 *         &lt;element ref="{cluster}java_home" minOccurs="0"/>
 *         &lt;element ref="{cluster}log_dir" minOccurs="0"/>
 *         &lt;element ref="{cluster}txn_log_dir" minOccurs="0"/>
 *         &lt;element ref="{cluster}iodevices" minOccurs="0"/>
 *         &lt;element ref="{cluster}debug_port" minOccurs="0"/>
 *         &lt;element ref="{cluster}replication_port" minOccurs="0"/>
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
    "id",
    "clusterIp",
    "javaHome",
    "logDir",
    "txnLogDir",
    "iodevices",
    "debugPort",
    "replicationPort"
})
@XmlRootElement(name = "node")
public class Node implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String id;
    @XmlElement(name = "cluster_ip", required = true)
    protected String clusterIp;
    @XmlElement(name = "java_home")
    protected String javaHome;
    @XmlElement(name = "log_dir")
    protected String logDir;
    @XmlElement(name = "txn_log_dir")
    protected String txnLogDir;
    protected String iodevices;
    @XmlElement(name = "debug_port")
    protected BigInteger debugPort;
    @XmlElement(name = "replication_port")
    protected BigInteger replicationPort;

    /**
     * Default no-arg constructor
     * 
     */
    public Node() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public Node(final String id, final String clusterIp, final String javaHome, final String logDir, final String txnLogDir, final String iodevices, final BigInteger debugPort, final BigInteger replicationPort) {
        this.id = id;
        this.clusterIp = clusterIp;
        this.javaHome = javaHome;
        this.logDir = logDir;
        this.txnLogDir = txnLogDir;
        this.iodevices = iodevices;
        this.debugPort = debugPort;
        this.replicationPort = replicationPort;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the clusterIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClusterIp() {
        return clusterIp;
    }

    /**
     * Sets the value of the clusterIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClusterIp(String value) {
        this.clusterIp = value;
    }

    /**
     * Gets the value of the javaHome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJavaHome() {
        return javaHome;
    }

    /**
     * Sets the value of the javaHome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJavaHome(String value) {
        this.javaHome = value;
    }

    /**
     * Gets the value of the logDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogDir() {
        return logDir;
    }

    /**
     * Sets the value of the logDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogDir(String value) {
        this.logDir = value;
    }

    /**
     * Gets the value of the txnLogDir property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnLogDir() {
        return txnLogDir;
    }

    /**
     * Sets the value of the txnLogDir property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnLogDir(String value) {
        this.txnLogDir = value;
    }

    /**
     * Gets the value of the iodevices property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIodevices() {
        return iodevices;
    }

    /**
     * Sets the value of the iodevices property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIodevices(String value) {
        this.iodevices = value;
    }

    /**
     * Gets the value of the debugPort property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDebugPort() {
        return debugPort;
    }

    /**
     * Sets the value of the debugPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDebugPort(BigInteger value) {
        this.debugPort = value;
    }

    /**
     * Gets the value of the replicationPort property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getReplicationPort() {
        return replicationPort;
    }

    /**
     * Sets the value of the replicationPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setReplicationPort(BigInteger value) {
        this.replicationPort = value;
    }

}
