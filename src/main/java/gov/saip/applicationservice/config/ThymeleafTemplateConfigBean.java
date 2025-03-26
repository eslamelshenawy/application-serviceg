package gov.saip.applicationservice.config;


import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafTemplateConfigBean {

    public ClassLoaderTemplateResolver pdfTemplateResolver() {
        ClassLoaderTemplateResolver pdfTemplateResolver = new ClassLoaderTemplateResolver();
        pdfTemplateResolver.setPrefix("/templates/");
        pdfTemplateResolver.setSuffix(".html");
        pdfTemplateResolver.setTemplateMode(TemplateMode.HTML);
        pdfTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        pdfTemplateResolver.setCacheable(false);
        return pdfTemplateResolver;
    }


}
