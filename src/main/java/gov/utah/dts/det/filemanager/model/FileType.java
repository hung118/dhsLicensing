package gov.utah.dts.det.filemanager.model;

public enum FileType {
	
	TXT("text/plain"),
	DOC("application/msword"),
	CSV("text/csv"),
	XLS("application/vnd.ms-excel"),
	RTF("text/rtf"),
	WPD("application/wordperfect"),
	PDF("application/pdf"),
	ODT("application/vnd.oasis.opendocument.text"),
	ODS("application/vnd.oasis.opendocument.spreadsheet");
	
	private final String mimeType;
	
	private FileType(String mimeType){
		this.mimeType = mimeType;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	public static FileType valueOfMimeType(String mimeType) {
		for (FileType fileType : FileType.values()) {
			if (fileType.getMimeType().equalsIgnoreCase(mimeType)) {
				return fileType;
			}
		}
		throw new IllegalArgumentException(mimeType + " is not a valid mime type");
	}
}