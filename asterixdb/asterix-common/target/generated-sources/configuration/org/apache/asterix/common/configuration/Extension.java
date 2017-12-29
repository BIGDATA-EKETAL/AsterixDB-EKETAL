//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:23:57 AM COT 
//


package org.apache.asterix.common.configuration;

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
 *         &lt;element ref="{asterixconf}extensionClassName"/>
 *         &lt;element ref="{asterixconf}property" maxOccurs="unbounded" minOccurs="0"/>
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
    "extensionClassName",
    "property"
})
@XmlRootElement(name = "extension")
public class Extension implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String extensionClassName;
    protected List<Property> property;

    /**
     * Default no-arg constructor
     * 
     */
    public Extension() {
        super();
    }

    /**
     * Fully-initialising value constructor
     * 
     */
    public Extension(final String extensionClassName, final List<Property> property) {
        this.extensionClassName = extensionClassName;
        this.property = property;
    }

    /**
     * Gets the value of the extensionClassName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtensionClassName() {
        return extensionClassName;
    }

    /**
     * Sets the value of the extensionClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtensionClassName(String value) {
        this.extensionClassName = value;
    }

    /**
     * Gets the value of the property property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Property }
     * 
     * 
     */
    public List<Property> getProperty() {
        if (property == null) {
            property = new ArrayList<Property>();
        }
        return this.property;
    }

    public void setProperty(List<Property> value) {
        this.property = null;
        List<Property> draftl = this.getProperty();
        draftl.addAll(value);
    }

}
