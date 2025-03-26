package gov.saip.applicationservice.config;

import com.aspose.words.License;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsposeLicenseConfig {

    public AsposeLicenseConfig() {
        try {
            License license = new License();
            license.setLicense(getClass().getResourceAsStream("/Aspose.WordsProductFamily.lic"));
            System.out.println("Aspose Loaded Successfully ");
        } catch (Exception e) {
            System.out.println("Aspose Exception "+ e.getMessage());
            e.printStackTrace();
        }
    }

}
