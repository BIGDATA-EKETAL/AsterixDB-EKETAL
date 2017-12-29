//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.12.29 at 09:24:14 AM COT 
//


package org.apache.asterix.installer.schema.conf;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.apache.asterix.installer.schema.conf package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BackupDir_QNAME = new QName("installer", "backupDir");
    private final static QName _Server_QNAME = new QName("installer", "server");
    private final static QName _Configured_QNAME = new QName("installer", "configured");
    private final static QName _Hdfsurl_QNAME = new QName("installer", "hdfsurl");
    private final static QName _JavaHome_QNAME = new QName("installer", "java_home");
    private final static QName _ClientPort_QNAME = new QName("installer", "clientPort");
    private final static QName _AsterixHome_QNAME = new QName("installer", "asterix_home");
    private final static QName _Url_QNAME = new QName("installer", "url");
    private final static QName _HomeDir_QNAME = new QName("installer", "homeDir");
    private final static QName _Version_QNAME = new QName("installer", "version");
    private final static QName _HyracksHome_QNAME = new QName("installer", "hyracks_home");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.apache.asterix.installer.schema.conf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Backup }
     * 
     */
    public Backup createBackup() {
        return new Backup();
    }

    /**
     * Create an instance of {@link Hdfs }
     * 
     */
    public Hdfs createHdfs() {
        return new Hdfs();
    }

    /**
     * Create an instance of {@link Zookeeper }
     * 
     */
    public Zookeeper createZookeeper() {
        return new Zookeeper();
    }

    /**
     * Create an instance of {@link Servers }
     * 
     */
    public Servers createServers() {
        return new Servers();
    }

    /**
     * Create an instance of {@link Configuration }
     * 
     */
    public Configuration createConfiguration() {
        return new Configuration();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "backupDir")
    public JAXBElement<String> createBackupDir(String value) {
        return new JAXBElement<String>(_BackupDir_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "server")
    public JAXBElement<String> createServer(String value) {
        return new JAXBElement<String>(_Server_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "configured")
    public JAXBElement<Boolean> createConfigured(Boolean value) {
        return new JAXBElement<Boolean>(_Configured_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "hdfsurl")
    public JAXBElement<String> createHdfsurl(String value) {
        return new JAXBElement<String>(_Hdfsurl_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "java_home")
    public JAXBElement<String> createJavaHome(String value) {
        return new JAXBElement<String>(_JavaHome_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "clientPort")
    public JAXBElement<BigInteger> createClientPort(BigInteger value) {
        return new JAXBElement<BigInteger>(_ClientPort_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "asterix_home")
    public JAXBElement<String> createAsterixHome(String value) {
        return new JAXBElement<String>(_AsterixHome_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "url")
    public JAXBElement<String> createUrl(String value) {
        return new JAXBElement<String>(_Url_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "homeDir")
    public JAXBElement<String> createHomeDir(String value) {
        return new JAXBElement<String>(_HomeDir_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "version")
    public JAXBElement<String> createVersion(String value) {
        return new JAXBElement<String>(_Version_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "installer", name = "hyracks_home")
    public JAXBElement<String> createHyracksHome(String value) {
        return new JAXBElement<String>(_HyracksHome_QNAME, String.class, null, value);
    }

}
