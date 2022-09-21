package com.example.wss;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSEncryptionPart;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.components.crypto.Merlin;
import org.apache.ws.security.message.WSSecEncrypt;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.message.WSSecTimestamp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;

import javax.net.ssl.*;
import javax.xml.transform.dom.DOMSource;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SOAPSecurity {

    private KeyStore keystore;
    private KeyManagerFactory keyManagerFactory;
    private String keystorePassword;

    private TrustManagerFactory trustManagerFactory;
    private KeyStore truststore;
    private String truststorePassword;

    private Crypto crypto;

    public SOAPSecurity(String pathToKeystore, String keystorePassword, String pathToTruststore, String truststorePassword) throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException, KeyManagementException, WSSecurityException {

        keystore = KeyStore.getInstance("JKS");
        InputStream fileReader = new FileInputStream(new File(pathToKeystore));
        keystore.load(fileReader, keystorePassword.toCharArray());
        this.keystorePassword = keystorePassword;

        keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keystore, keystorePassword.toCharArray());

        Properties properties = new Properties();
        properties.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
        crypto = CryptoFactory.getInstance(properties);
        ((Merlin) crypto).setKeyStore(keystore);

        truststore = KeyStore.getInstance("JKS");
        fileReader = new FileInputStream(new File(pathToTruststore));
        truststore.load(fileReader, truststorePassword.toCharArray());
        this.truststorePassword = truststorePassword;

        trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
        trustManagerFactory.init(truststore);
    }

    public static String secureSoapMessageFromFile(String messagePath, String pathToKeystore, String keystorePassword,
                                    String pathToTruststore, String trustStorePassword, int timeToLive,
                                    String signingAlias, String encryptAlias) throws
            SAXException, ParserConfigurationException, SOAPException, IOException, WSSecurityException,
            TransformerException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {

        SOAPSecurity soapSecurity = new SOAPSecurity(pathToKeystore, keystorePassword, pathToTruststore, trustStorePassword);
        SOAPMessage soapMessage = SOAPSecurity.createSOAPRequestFromFile(messagePath);
        return soapSecurity.applyWSSecurity(soapMessage, timeToLive, signingAlias, encryptAlias);
    }

    public static String secureSoapMessageFromString(String messageString, String pathToKeystore, String keystorePassword,
                                           String pathToTruststore, String trustStorePassword, int timeToLive,
                                           String signingAlias, String encryptAlias) throws
            SAXException, ParserConfigurationException, SOAPException, IOException, WSSecurityException,
            TransformerException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException {

        SOAPSecurity soapSecurity = new SOAPSecurity(pathToKeystore, keystorePassword, pathToTruststore, trustStorePassword);
        SOAPMessage soapMessage = SOAPSecurity.createSOAPRequestFromString(messageString);
        return soapSecurity.applyWSSecurity(soapMessage, timeToLive, signingAlias, encryptAlias);
    }

    public interface SOAPDocWriter {
        Document writeDocument(String s, DocumentBuilder documentBuilder) throws IOException, SAXException;
    }

    public static SOAPMessage createSOAPRequestFromFile(String messagePath) throws SOAPException, IOException, ParserConfigurationException, SAXException {

        SOAPDocWriter pathWriter = (s, d) -> {
            File messageFile = new File(s);
            return d.parse(new InputSource(new FileInputStream(messageFile)));
        };

        return createSOAPRequestLambda(messagePath, pathWriter);
    }

    public static SOAPMessage createSOAPRequestFromString(String messageString) throws SOAPException, IOException, ParserConfigurationException, SAXException {

        SOAPDocWriter stringWriter = (s, d) -> d.parse(new InputSource(new StringReader(s)));
        return createSOAPRequestLambda(messageString, stringWriter);
    }

    private static SOAPMessage createSOAPRequestLambda(String s, SOAPDocWriter w) throws SOAPException, IOException, ParserConfigurationException, SAXException
    {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

        SOAPBody soapBody = soapEnvelope.getBody();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = w.writeDocument(s, documentBuilder);

        soapBody.addDocument(document);

        soapMessage.saveChanges();

        return soapMessage;
    }

    public static Document toDocument(SOAPMessage soapMsg) throws TransformerConfigurationException, TransformerException, SOAPException, IOException {
        Source src = soapMsg.getSOAPPart().getContent();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMResult result = new DOMResult();
        transformer.transform(src, result);
        return (Document) result.getNode();
    }

    /**
     * Secures a soap message according to the given security actions
     *
     * @param soapMessage the soap message to be secured
     * @param timestampTimeToLive optional: the time to live for the timestamp
     * @param signatureKeyAlias optional: the alias for the signature key in the keystore
     * @param encryptionKeyAlias optional: the alias for the encryption key in the keystore
     * @throws WSSecurityException
     * @throws IOException
     * @throws SOAPException
     * @throws TransformerException
     */
    public String applyWSSecurity(SOAPMessage soapMessage,
                                  int timestampTimeToLive,
                                  String signatureKeyAlias,
                                  String encryptionKeyAlias) throws WSSecurityException, IOException, SOAPException, TransformerException {

        Document soapMessageDocument = toDocument(soapMessage);

        // add security header
        WSSecHeader securityHeader = new WSSecHeader();
        securityHeader.setMustUnderstand(false);
        securityHeader.insertSecurityHeader(soapMessageDocument);

        WSSecTimestamp timestamp = null;
        // timestamp document
        timestamp = new WSSecTimestamp();
        timestamp.setTimeToLive(timestampTimeToLive);
        timestamp.build(soapMessageDocument, securityHeader);

        // sign document
        WSSecSignature signatureBuilder = new WSSecSignature();
        signatureBuilder.setUserInfo(signatureKeyAlias, keystorePassword);
        signatureBuilder.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
        signatureBuilder.setSignatureAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        List<WSEncryptionPart> signatureParts = new ArrayList<WSEncryptionPart>();

        WSEncryptionPart timestampPart = new WSEncryptionPart(timestamp.getId());
        signatureParts.add(timestampPart);

        WSEncryptionPart bodyPart = new WSEncryptionPart(WSConstants.ELEM_BODY, WSConstants.URI_SOAP11_ENV, "Element");
        signatureParts.add(bodyPart);
        signatureBuilder.setParts(signatureParts);

        signatureBuilder.build(soapMessageDocument, crypto, securityHeader);

         // encrypt document
         WSSecEncrypt encrypt = new WSSecEncrypt();
         encrypt.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
         encrypt.setSymmetricEncAlgorithm(WSConstants.AES_128_GCM);
         encrypt.setKeyEncAlgo(WSConstants.KEYTRANSPORT_RSAOEP);
         encrypt.setUserInfo(encryptionKeyAlias, keystorePassword);

         List<WSEncryptionPart> encryptionParts = new ArrayList<WSEncryptionPart>();
         WSEncryptionPart encryptionSignaturePart = new WSEncryptionPart("Signature", WSConstants.SIG_NS, "Element");
         WSEncryptionPart encryptionBodyPart = new WSEncryptionPart("Body", WSConstants.URI_SOAP11_ENV, "Content");
         encryptionParts.add(encryptionBodyPart);
         encryptionParts.add(encryptionSignaturePart);
         encrypt.setParts(encryptionParts);
         encrypt.build(soapMessageDocument, crypto, securityHeader);


        DOMSource domSource = new DOMSource(soapMessageDocument);
        soapMessage.getSOAPPart().setContent(domSource);
        soapMessage.saveChanges();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        String strMsg = new String(out.toByteArray());
        return strMsg;
    }
}


