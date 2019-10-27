//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2019.10.27 时间 04:41:58 PM CST 
//


package pers.te.sa.ws.demo;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pers.te.sa.ws.demo package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pers.te.sa.ws.demo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserResponse }
     * 
     */
    public UserResponse createUserResponse() {
        return new UserResponse();
    }

    /**
     * Create an instance of {@link ValidateRequest }
     * 
     */
    public ValidateRequest createValidateRequest() {
        return new ValidateRequest();
    }

    /**
     * Create an instance of {@link UserBatchRequest }
     * 
     */
    public UserBatchRequest createUserBatchRequest() {
        return new UserBatchRequest();
    }

    /**
     * Create an instance of {@link UserRequest }
     * 
     */
    public UserRequest createUserRequest() {
        return new UserRequest();
    }

}
