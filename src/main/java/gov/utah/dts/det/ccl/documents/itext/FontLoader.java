package gov.utah.dts.det.ccl.documents.itext;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.FontFactory;

public class FontLoader {

	private static final Logger logger = LoggerFactory.getLogger(FontLoader.class);
	
	public FontLoader(String fontBase) {
		//register fonts
		FontFactory.registerDirectories();
		File fontBaseDir = new File(fontBase);
		registerFonts(fontBaseDir);
	}
	
	private void registerFonts(File directory) {
		int fontsRegistered = FontFactory.registerDirectory(directory.getAbsolutePath());
		logger.info("Registered " + fontsRegistered + " fonts from directory: " + directory.getAbsolutePath());
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				registerFonts(file);
			}
		}
	}
}