package gov.saip.applicationservice.util;

import lombok.SneakyThrows;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class WordUtil {




    @SneakyThrows
    public void convertHtmlToWordFile3(StringBuilder sb) throws Docx4JException, JAXBException {
        //.. HTML Code

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(new PartName("/hw.html"));
        afiPart.setBinaryData(sb.toString().getBytes(StandardCharsets.UTF_8));
        afiPart.setContentType(new ContentType("text/html"));
        afiPart.getAltChunkType();
        Relationship altChunkRel = wordMLPackage.getMainDocumentPart().addTargetPart(afiPart);
        CTAltChunk ac = Context.getWmlObjectFactory().createCTAltChunk();
        ac.setId(altChunkRel.getId());
        wordMLPackage.getMainDocumentPart().addObject(ac);
        wordMLPackage.getContentTypeManager().addDefaultContentType("html", "text/html");
        File wordFile = new File(System.getProperty("user.dir") + "/html_output.docx");

        wordMLPackage.save(wordFile);


        System.out.println("done and AbsolutePath is ==>> " + wordFile.getAbsolutePath() + " Path is ==>> " + wordFile.getPath()
                + "and path name is ==>> " + System.getProperty("user.dir"));

    }
}
