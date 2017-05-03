package gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters;

import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.FailureToProvideInformationLetterDSPDC;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.FailureToProvideInformationLetterFC;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.FailureToProvideInformationLetterTX;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.FingerprintCardFeeOnlyLetterFC;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.FingerprintCardFeeOnlyLetterTX;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.FingerprintCardRequestLetterFC;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.FingerprintCardRequestLetterTX;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.LivescanAuthorization;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.LivescanAuthorizationB1591;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.LivescanAuthorizationB1606;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.MultiStateOffenderLetterFC;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.letters.reports.MultiStateOffenderLetterTX;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningLetter;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningMain;
import gov.utah.dts.det.ccl.model.enums.ScreeningLetterType;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningLetterService;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 
 * @author jtorres
 * 
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-list", location = "list-letters", type = "redirectAction", params = { "screeningId", "${screeningId}" }),
	@Result(name = "redirect-print", location = "list-letters", type = "redirectAction", params = { "screeningId", "${screeningId}", "letterId", "${screeningLetter.id}" }), 
	@Result(name = "input", location = "letters_form.jsp") })
public class TrackingRecordScreeningLettersAction extends BaseTrackingRecordScreeningAction implements ServletRequestAware, ServletResponseAware, Preparable {

	private static final Logger log = LoggerFactory.getLogger(TrackingRecordScreeningLettersAction.class);

	private final String REDIRECT_LIST = "redirect-list";
	private final String REDIRECT_PRINT = "redirect-print";

	private TrackingRecordScreeningLetterService letterService;
	private TrackingRecordScreeningLetter screeningLetter;
	private ScreeningLetterType letterType;
	private Long letterId;
	private HttpServletRequest request;
    private HttpServletResponse response;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	@SkipValidation
	@Action(value = "new-letter")
	public String doNewLetter() {
		if (screeningLetter == null) {
			screeningLetter = new TrackingRecordScreeningLetter();
			screeningLetter.setLetterDate(new Date());
		}
		return INPUT;
	}

	@Action(value = "save-letter")
	@Validations(
		conversionErrorFields = { 
			@ConversionErrorFieldValidator(fieldName = "screeningLetter.letterDate", message = "Letter date is not a valid date. (MM/DD/YYYY)", shortCircuit = true) 
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "letterType", message = "Letter type is reqired.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "screeningLetter.letterDate", message = "Letter date is required.", shortCircuit = true) 
		}
	)
	public String doSaveLetter() {
		screeningLetter.setTrackingRecordScreening(getTrackingRecordScreening());
		screeningLetter.setLetterType(letterType);
		// Make sure that the screening has a main record.
		if (screeningLetter.getTrackingRecordScreening().getTrsMain() == null) {
			TrackingRecordScreeningMain trsMain = new TrackingRecordScreeningMain();
			trsMain.setId(screeningLetter.getTrackingRecordScreening().getId());
			screeningLetter.getTrackingRecordScreening().setTrsMain(trsMain);
		}
		if (letterType.equals(ScreeningLetterType.NAA)) {
			screeningLetter.getTrackingRecordScreening().getTrsMain().setNaaDate(screeningLetter.getLetterDate());
		}
		if (letterType.equals(ScreeningLetterType.FPI_DSPDC) || 
			letterType.equals(ScreeningLetterType.FPI_FC) || 
			letterType.equals(ScreeningLetterType.FPI_TX)) 
		{
			screeningLetter.getTrackingRecordScreening().getTrsMain().setFpiDate(screeningLetter.getLetterDate());
		} else {
			screeningLetter.setDetails(null);
		}
		screeningLetter = letterService.save(screeningLetter);
		letterService.evict(screeningLetter);

		if (!screeningLetter.getLetterType().equals(ScreeningLetterType.NAA)) {
			return REDIRECT_PRINT;
		} else {
			return REDIRECT_LIST;
		}
	}

	@Override
	public void validate() {
		if (letterType.equals(ScreeningLetterType.FPI_DSPDC) || 
			letterType.equals(ScreeningLetterType.FPI_TX) || 
			letterType.equals(ScreeningLetterType.FPI_FC)) 
		{
			if (StringUtils.isBlank(screeningLetter.getDetails())) {
				addFieldError("screeningLetter.details", "FPI Details is required.");
			}
		}
		if (letterType.equals(ScreeningLetterType.FCR_FC) || 
			letterType.equals(ScreeningLetterType.FPF_FC) || 
			letterType.equals(ScreeningLetterType.FPI_FC) || 
			letterType.equals(ScreeningLetterType.MSO_FC)) 
		{
			if (StringUtils.isBlank(screeningLetter.getAddress().getAddressOne())) {
				addFieldError("screeningLetter.address.addressOne", "Address One is required.");
			}
			if (StringUtils.isBlank(screeningLetter.getAddress().getZipCode())) {
				addFieldError("screeningLetter.address.zipCode", "Zip Code is required.");
			}
			if (StringUtils.isBlank(screeningLetter.getAddress().getCity())) {
				addFieldError("screeningLetter.address.city", "City is required.");
			}
			if (StringUtils.isBlank(screeningLetter.getAddress().getState())) {
				addFieldError("screeningLetter.address.state", "State is required.");
			}
		}
	}

	@SkipValidation
	@Action(value = "print-letter")
	public void doPrint() {
		if (letterId != null && screeningId != null) {
			screeningLetter = letterService.load(letterId);
			if (screeningLetter != null && 
				screeningLetter.getId() != null && 
				screeningLetter.getTrackingRecordScreening() != null && 
				screeningLetter.getTrackingRecordScreening().getId() != null) 
			{
				try {
					ByteArrayOutputStream ba = null;
					if (screeningLetter.getLetterType().equals(ScreeningLetterType.FCR_FC)) {
						ba = FingerprintCardRequestLetterFC.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.FCR_TX)) {
						ba = FingerprintCardRequestLetterTX.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.FPF_FC)) {
						ba = FingerprintCardFeeOnlyLetterFC.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.FPF_TX)) {
						ba = FingerprintCardFeeOnlyLetterTX.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.FPI_DSPDC)) {
						ba = FailureToProvideInformationLetterDSPDC.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.FPI_FC)) {
						ba = FailureToProvideInformationLetterFC.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.FPI_TX)) {
						ba = FailureToProvideInformationLetterTX.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.LS)) {
						if (screeningLetter.getTrackingRecordScreening() != null && 
							screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null && 
							screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling() != null &&
							screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue().equalsIgnoreCase("B1591"))
						{
							ba = LivescanAuthorizationB1591.generate(screeningLetter);
						} else if (screeningLetter.getTrackingRecordScreening() != null && 
									screeningLetter.getTrackingRecordScreening().getTrsDpsFbi() != null && 
									screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling() != null &&
									screeningLetter.getTrackingRecordScreening().getTrsDpsFbi().getBilling().getValue().equalsIgnoreCase("B1606"))
						{
							ba = LivescanAuthorizationB1606.generate(screeningLetter);
						} else {
							ba = LivescanAuthorization.generate(screeningLetter);
						}
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.MSO_FC)) {
						ba = MultiStateOffenderLetterFC.generate(screeningLetter, request);
					} else if (screeningLetter.getLetterType().equals(ScreeningLetterType.MSO_TX)) {
						ba = MultiStateOffenderLetterTX.generate(screeningLetter, request);
					}
					if (ba != null && ba.size() > 0) {
						// This is where the response is set
						String filename = getTrackingRecordScreening().getPerson().getFirstAndLastName() + " - "
								+ screeningLetter.getLetterType() + ".pdf";
						response.setContentLength(ba.size());
						response.setContentType("application/pdf");
						response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
						response.setHeader("Pragma", "no-cache");
						response.setDateHeader("Expires", 0);
						response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");

						ServletOutputStream out = response.getOutputStream();
						ba.writeTo(out);
						out.flush();
					}
				} catch (Exception ex) {
					// Generate an error pdf
					ByteArrayOutputStream ba = null;
					try {
						ba = new ByteArrayOutputStream();
						Document document = new Document(PageSize.A4, 50, 50, 100, 100);
						@SuppressWarnings("unused")
						PdfWriter writer = PdfWriter.getInstance(document, ba);
						document.open();
						document.add(new Paragraph("An error occurred while generating the letter document.", FontFactory.getFont(
								"Times-Roman", 12, Font.NORMAL)));
						document.close();
						if (ba != null && ba.size() > 0) {
							// This is where the response is set
							// String filename = getTrackingRecordScreening().getPerson().getFirstAndLastName() + " - " +
							// screeningLetter.getLetterType() + ".pdf";
							response.setContentLength(ba.size());
							response.setContentType("application/pdf");
							response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
							response.setHeader("Pragma", "no-cache");
							response.setDateHeader("Expires", 0);
							// response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");
							response.setHeader("Content-disposition", "attachment");

							ServletOutputStream out = response.getOutputStream();
							ba.writeTo(out);
							out.flush();
						}

					} catch (Exception e) {
						log.error("AN ERROR OCCURRED GENERATING ERROR PDF DOCUMENT: " + e);
					}
				}
			}
		}
	}

	@SkipValidation
	@Action(value = "delete-letter")
	public String doDelete() {
		if (letterId != null) {
			screeningLetter = letterService.load(letterId);
			if (screeningLetter != null) {
				if (screeningLetter.getTrackingRecordScreening() != null) {
					// The following if block removes letter dates from the main tab for specific letter types.
					if (screeningLetter.getLetterType().equals(ScreeningLetterType.NAA) ||
						screeningLetter.getLetterType().equals(ScreeningLetterType.FPI_DSPDC) || 
						screeningLetter.getLetterType().equals(ScreeningLetterType.FPI_FC) || 
						screeningLetter.getLetterType().equals(ScreeningLetterType.FPI_TX))
					{
						if (screeningLetter.getTrackingRecordScreening().getTrsMain() == null) {
							TrackingRecordScreeningMain trsMain = new TrackingRecordScreeningMain();
							trsMain.setId(screeningLetter.getTrackingRecordScreening().getId());
							screeningLetter.getTrackingRecordScreening().setTrsMain(trsMain);
						}
						if (screeningLetter.getLetterType().equals(ScreeningLetterType.NAA)) {
							screeningLetter.getTrackingRecordScreening().getTrsMain().setNaaDate(null);
						} else {
							screeningLetter.getTrackingRecordScreening().getTrsMain().setFpiDate(null);
						}
					}
					letterService.delete(letterId);
					letterService.evict(screeningLetter);
				}
			}
		}
		return REDIRECT_LIST;
	}

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public void setTrackingRecordScreeningLetterService(TrackingRecordScreeningLetterService letterService) {
		this.letterService = letterService;
	}

	public ScreeningLetterType getLetterType() {
		return letterType;
	}

	public void setLetterType(ScreeningLetterType letterType) {
		this.letterType = letterType;
	}

	public List<ScreeningLetterType> getLetterTypes() {
		return Arrays.asList(ScreeningLetterType.values());
	}

	public TrackingRecordScreeningLetter getScreeningLetter() {
		return screeningLetter;
	}

	public void setScreeningLetter(TrackingRecordScreeningLetter screeningLetter) {
		this.screeningLetter = screeningLetter;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
