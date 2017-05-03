package gov.utah.dts.det.ccl.actions.facility.information.license;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.actions.facility.information.license.reports.LicenseCertificate;
import gov.utah.dts.det.ccl.actions.facility.information.license.reports.LicenseLetter;
import gov.utah.dts.det.ccl.model.License;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-view", location = "licenses-list", type = "redirectAction", params = {"facilityId", "${facilityId}"}),
	@Result(name = "input", location = "license_form.jsp"),
	@Result(name = "view", location = "licenses_list.jsp")
})
public class LicensesPrintAction extends BaseFacilityAction implements ServletRequestAware, ServletResponseAware, Preparable {
	
	protected final Log log = LogFactory.getLog(getClass());

	private License license;
	private HttpServletRequest request;
	private HttpServletResponse response;

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	@Action(value = "print-license-cert")
	public void doPrintCertificate() {
		loadLicense();

		try {
			ByteArrayOutputStream ba = LicenseCertificate.generate(license, request);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "license_certificate.pdf";
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

	@Action(value = "print-license-letter")
	public void doPrintLetter() {
		loadLicense();
		

		try {
			ByteArrayOutputStream ba = LicenseLetter.generate(license, request);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "license_letter.pdf";
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

	
	private void loadLicense() {
		if (license != null && license.getId() != null) {
			license = getFacility().getLicense(license.getId());
		}
	}

	public License getLicense() {
		return license;
	}
	
	public void setLicense(License license) {
		this.license = license;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}