package gov.utah.dts.det.ccl.model.enums;

public enum ScreeningLetterType {

	FCR_TX("Finger Print Card Request (TX)"),
	FCR_FC("Finger Print Card Request (FC)"),
	FPF_TX("Finger Print Card Request (Fee Only - TX)"),
	FPF_FC("Finger Print Card Request (Fee Only - FC)"),
	LS("Livescan Authorization"),
	MSO_TX("Multi State Offender (TX)"),
	MSO_FC("Multi State Offender (FC)"),
	FPI_TX("Failure to Provide Information (TX)"),
	FPI_FC("Failure to Provide Information (FC)"),
	FPI_DSPDC("Failure to Provide Information (DSPDC)"),
	NAA("Notice of Agency Action (Denied)");
	
	private final String label;
	
	private ScreeningLetterType(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}