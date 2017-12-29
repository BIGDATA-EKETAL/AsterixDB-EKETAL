//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:24:14 AM COT 
//


package org.apache.asterix.installer.schema.conf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{installer}java_home"/>
 *         &lt;element ref="{installer}server" maxOccurs="unbounded"/>
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
    "javaHome",
    "server"
})
@XmlRootElement(name = "servers")
public class Servers implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "java_home", required = true)
    protected String javaHome;
    @XmlElement(required = true)
    protected List<String> server;

    /**
     * Default no-arg constructor
     * 
     */
    public Servers() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public Servers(final String javaHome, final List<String> server) {
        this.javaHome = javaHome;
        this.server = server;
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
     * Gets the value of the server property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the server property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getServer() {
        if (server == null) {
            server = new ArrayList<String>();
        }
        return this.server;
    }

    public void setServer(List<String> value) {
        this.server = null;
        List<String> draftl = this.getServer();
        draftl.addAll(value);
    }

}
