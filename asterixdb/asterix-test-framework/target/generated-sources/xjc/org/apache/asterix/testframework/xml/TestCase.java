//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:24:42 AM COT 
//


package org.apache.asterix.testframework.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for test-case complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="test-case">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{urn:xml.testframework.asterix.apache.org}description" minOccurs="0"/>
 *         &lt;element name="compilation-unit" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="description" type="{urn:xml.testframework.asterix.apache.org}description" minOccurs="0"/>
 *                   &lt;element name="parameter" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="output-dir" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="compare" use="required" type="{urn:xml.testframework.asterix.apache.org}comparison-enum" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="expected-error" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="FilePath" use="required" type="{urn:xml.testframework.asterix.apache.org}SimplifiedRelativeFilePath" />
 *       &lt;attribute name="category" type="{urn:xml.testframework.asterix.apache.org}category-enum" />
 *       &lt;attribute name="repeat" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" default="1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "test-case", propOrder = {
    "description",
    "compilationUnit"
})
public class TestCase {

    protected Description description;
    @XmlElement(name = "compilation-unit", required = true)
    protected List<TestCase.CompilationUnit> compilationUnit;
    @XmlAttribute(name = "FilePath", required = true)
    protected String filePath;
    @XmlAttribute(name = "category")
    protected CategoryEnum category;
    @XmlAttribute(name = "repeat")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger repeat;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link Description }
     *     
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link Description }
     *     
     */
    public void setDescription(Description value) {
        this.description = value;
    }

    /**
     * Gets the value of the compilationUnit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compilationUnit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompilationUnit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TestCase.CompilationUnit }
     * 
     * 
     */
    public List<TestCase.CompilationUnit> getCompilationUnit() {
        if (compilationUnit == null) {
            compilationUnit = new ArrayList<TestCase.CompilationUnit>();
        }
        return this.compilationUnit;
    }

    /**
     * Gets the value of the filePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the value of the filePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilePath(String value) {
        this.filePath = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryEnum }
     *     
     */
    public CategoryEnum getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryEnum }
     *     
     */
    public void setCategory(CategoryEnum value) {
        this.category = value;
    }

    /**
     * Gets the value of the repeat property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRepeat() {
        if (repeat == null) {
            return new BigInteger("1");
        } else {
            return repeat;
        }
    }

    /**
     * Sets the value of the repeat property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRepeat(BigInteger value) {
        this.repeat = value;
    }


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
     *         &lt;element name="description" type="{urn:xml.testframework.asterix.apache.org}description" minOccurs="0"/>
     *         &lt;element name="parameter" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="output-dir" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="compare" use="required" type="{urn:xml.testframework.asterix.apache.org}comparison-enum" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="expected-error" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "description",
        "parameter",
        "outputDir",
        "expectedError"
    })
    public static class CompilationUnit {

        protected Description description;
        protected List<TestCase.CompilationUnit.Parameter> parameter;
        @XmlElement(name = "output-dir")
        protected TestCase.CompilationUnit.OutputDir outputDir;
        @XmlElement(name = "expected-error")
        protected List<String> expectedError;
        @XmlAttribute(name = "name", required = true)
        protected String name;

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link Description }
         *     
         */
        public Description getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link Description }
         *     
         */
        public void setDescription(Description value) {
            this.description = value;
        }

        /**
         * Gets the value of the parameter property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the parameter property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParameter().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TestCase.CompilationUnit.Parameter }
         * 
         * 
         */
        public List<TestCase.CompilationUnit.Parameter> getParameter() {
            if (parameter == null) {
                parameter = new ArrayList<TestCase.CompilationUnit.Parameter>();
            }
            return this.parameter;
        }

        /**
         * Gets the value of the outputDir property.
         * 
         * @return
         *     possible object is
         *     {@link TestCase.CompilationUnit.OutputDir }
         *     
         */
        public TestCase.CompilationUnit.OutputDir getOutputDir() {
            return outputDir;
        }

        /**
         * Sets the value of the outputDir property.
         * 
         * @param value
         *     allowed object is
         *     {@link TestCase.CompilationUnit.OutputDir }
         *     
         */
        public void setOutputDir(TestCase.CompilationUnit.OutputDir value) {
            this.outputDir = value;
        }

        /**
         * Gets the value of the expectedError property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the expectedError property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExpectedError().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getExpectedError() {
            if (expectedError == null) {
                expectedError = new ArrayList<String>();
            }
            return this.expectedError;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="compare" use="required" type="{urn:xml.testframework.asterix.apache.org}comparison-enum" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class OutputDir {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "compare", required = true)
            protected ComparisonEnum compare;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the compare property.
             * 
             * @return
             *     possible object is
             *     {@link ComparisonEnum }
             *     
             */
            public ComparisonEnum getCompare() {
                return compare;
            }

            /**
             * Sets the value of the compare property.
             * 
             * @param value
             *     allowed object is
             *     {@link ComparisonEnum }
             *     
             */
            public void setCompare(ComparisonEnum value) {
                this.compare = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Parameter {

            @XmlAttribute(name = "name")
            protected String name;
            @XmlAttribute(name = "value")
            protected String value;

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }

}
