package pers.te.sa.ws.demo;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class Config extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext)
    {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/api/soap/*");
    }

    @Bean(name = "MailServiceWsdl")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema Schema)
    {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("MailServicePort");
        wsdl11Definition.setLocationUri("/api/soap/EmailService");
        wsdl11Definition.setTargetNamespace("sa.te.pers/ws/demo");
        wsdl11Definition.setSchema(Schema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema()
    {
        return new SimpleXsdSchema(new ClassPathResource("soap.xsd"));
    }
}
