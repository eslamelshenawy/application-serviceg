package gov.saip.applicationservice.common.util;
import java.io.File;
import java.util.logging.Logger;
import java.util.Objects;

public class FontPathFinder {

    private static final Logger logger = Logger.getLogger(FontPathFinder.class.getName());

    public static String getFontsFolderPath() {
        try {
            // Get the fonts folder using the class loader
            String fontsFolderPath = Objects.requireNonNull(FontPathFinder.class.getClassLoader().getResource("fonts")).getPath();
            File fontsFolder = new File(fontsFolderPath);

            if (fontsFolder.exists() && fontsFolder.isDirectory()) {
                logger.info("Found 'fonts' folder at: " + fontsFolder.getAbsolutePath());

                // List all files in the fonts folder
                File[] fontFiles = fontsFolder.listFiles();
                if (fontFiles != null) {
                    logger.info("Available fonts:");
                    for (File fontFile : fontFiles) {
                        logger.info("Font: " + fontFile.getName());
                    }
                } else {
                    logger.warning("No fonts found in the 'fonts' directory.");
                }

                return fontsFolder.getAbsolutePath();
            } else {
                logger.warning("Fonts folder not found in the expected path: " + fontsFolder.getAbsolutePath());
                return null;
            }
        } catch (NullPointerException e) {
            logger.severe("The 'fonts' folder resource was not found in the classpath.");
            return null;
        }
    }
}
