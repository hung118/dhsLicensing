package gov.utah.dts.det.ccl.actions.trackingrecordscreening.cbscomm;

import gov.utah.dts.det.ccl.actions.trackingrecordscreening.BaseTrackingRecordScreeningAction;
import gov.utah.dts.det.ccl.actions.trackingrecordscreening.cbscomm.reports.ConvictionsLetter;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConviction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConvictionLetter;
import gov.utah.dts.det.ccl.model.enums.ScreeningConvictionLetterType;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningConvictLtrService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningConvictionService;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	@Result(name = "redirect-list", location = "list-conviction-letters", type = "redirectAction", params = {"screeningId", "${screeningId}"}),
	@Result(name = "redirect-print", location = "list-conviction-letters", type = "redirectAction", params = {"screeningId", "${screeningId}", "letterId", "${convictionLetter.id}"}),
	@Result(name = "input", location = "conviction_letters_form.jsp")
})
public class ConvictionLettersAction extends BaseTrackingRecordScreeningAction implements ServletRequestAware, ServletResponseAware, Preparable {

	private static final Logger log = LoggerFactory.getLogger(ConvictionLettersAction.class);

	private final String REDIRECT_LIST = "redirect-list";
	private final String REDIRECT_PRINT = "redirect-print";

	private TrackingRecordScreeningConvictLtrService letterService;
	private TrackingRecordScreeningConvictionService convictionService;
	private TrackingRecordScreeningConvictionLetter convictionLetter;
	private ScreeningConvictionLetterType letterType;
	private Long letterId;
    private HttpServletRequest request;
	private HttpServletResponse response;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Action(value="new-conviction-letter")
	public String doNewLetter() {
		if (convictionLetter == null) {
			convictionLetter = new TrackingRecordScreeningConvictionLetter();
			convictionLetter.setLetterDate(new Date());
		}
		return INPUT;
	}

	@Action(value="save-conviction-letter")
	@Validations(
		conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "convictionLetter.letterDate", message = "Letter date is not a valid date. (MM/DD/YYYY)", shortCircuit = true)
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "letterType", message = "Letter type is reqired.", shortCircuit = true),
			@RequiredFieldValidator(fieldName = "convictionLetter.letterDate", message = "Letter date is required.", shortCircuit = true)
		}
	)
	public String doSaveLetter() {
		convictionLetter.setTrackingRecordScreening(getTrackingRecordScreening());
		convictionLetter.setLetterType(letterType);
		convictionLetter = letterService.save(convictionLetter);
		letterService.evict(convictionLetter);
		
		return REDIRECT_PRINT;
	}

	@Action(value="print-conviction-letter")
	public void doPrint() {
		if (letterId != null && screeningId != null) {
			convictionLetter = letterService.load(letterId);
			if (convictionLetter != null && convictionLetter.getId() != null &&
				convictionLetter.getTrackingRecordScreening() != null && convictionLetter.getTrackingRecordScreening().getId() != null) 
			{
				try {
					ByteArrayOutputStream ba = null;
					if (convictionLetter.getLetterType().equals(ScreeningConvictionLetterType.APPROVAL) ||
							convictionLetter.getLetterType().equals(ScreeningConvictionLetterType.DENIAL)) 
					{
						List<TrackingRecordScreeningConviction> convictionsList = new ArrayList<TrackingRecordScreeningConviction>();
						convictionsList = convictionService.getConvictionsForLetter(screeningId);
						ba = ConvictionsLetter.generate(convictionLetter, convictionsList, request);
					}
					if (ba != null && ba.size() > 0) {
						// This is where the response is set
						String filename = getTrackingRecordScreening().getPerson().getFirstAndLastName() + " - Convictions " + convictionLetter.getLetterType() + ".pdf";
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
				        document.add(new Paragraph("An error occurred while generating the convictions letter document.", FontFactory.getFont("Times-Roman", 12, Font.NORMAL)));
				        document.close();
						if (ba != null && ba.size() > 0) {
							// This is where the response is set
							//String filename = getTrackingRecordScreening().getPerson().getFirstAndLastName() + " - " + convictionLetter.getLetterType() + ".pdf";
							response.setContentLength(ba.size());
				            response.setContentType("application/pdf");
				            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				            response.setHeader("Pragma", "no-cache");
				            response.setDateHeader("Expires", 0);
				            //response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\""); 
				            response.setHeader("Content-disposition", "attachment");
				            
							ServletOutputStream out = response.getOutputStream();
							ba.writeTo(out);
							out.flush();
						}

					} catch (Exception e) {
						log.error("AN ERROR OCCURRED GENERATING ERROR PDF DOCUMENT: "+e);
					}
				}
			}
		}
	}

	@SkipValidation
	@Action(value="delete-conviction-letter")
	public String doDelete() {
		if (letterId != null) {
			convictionLetter = letterService.load(letterId);
			if (convictionLetter != null) {
				letterService.delete(letterId);
				letterService.evict(convictionLetter);
			}
		}
		return REDIRECT_LIST;
	}


	public TrackingRecordScreeningConvictionLetter getConvictionLetter() {
		return convictionLetter;
	}

	public void setConvictionLetter(TrackingRecordScreeningConvictionLetter convictionLetter) {
		this.convictionLetter = convictionLetter;
	}

	public Long getLetterId() {
		return letterId;
	}

	public void setLetterId(Long letterId) {
		this.letterId = letterId;
	}

	public ScreeningConvictionLetterType getLetterType() {
		return letterType;
	}

	public void setLetterType(ScreeningConvictionLetterType letterType) {
		this.letterType = letterType;
	}

	public List<ScreeningConvictionLetterType> getLetterTypes() {
		return Arrays.asList(ScreeningConvictionLetterType.values());
	}

	public void setTrackingRecordScreeningConvictLtrService(TrackingRecordScreeningConvictLtrService letterService) {
		this.letterService = letterService;
	}

	public void setTrackingRecordScreeningConvictionService(TrackingRecordScreeningConvictionService convictionService) {
		this.convictionService = convictionService;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
	    this.response = response;
	}

}
