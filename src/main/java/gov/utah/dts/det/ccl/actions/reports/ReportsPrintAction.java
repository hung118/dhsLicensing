package gov.utah.dts.det.ccl.actions.reports;

import gov.utah.dts.det.ccl.actions.facility.BaseFacilityAction;
import gov.utah.dts.det.ccl.actions.reports.generators.ExpiredLicensesReport;
import gov.utah.dts.det.ccl.actions.reports.generators.FacilityLicenseDetailReport;
import gov.utah.dts.det.ccl.actions.reports.generators.FacilityLicenseSummaryReport;
import gov.utah.dts.det.ccl.actions.reports.generators.LicenseRenewalLettersReport;
import gov.utah.dts.det.ccl.actions.reports.generators.LicenseRenewalsListReport;
import gov.utah.dts.det.ccl.actions.reports.generators.LivescansIssuedReport;
import gov.utah.dts.det.ccl.actions.reports.generators.OpenApplicationsReport;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.view.FacilityLicenseView;
import gov.utah.dts.det.ccl.model.view.SortableFacilityView;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.service.PersonService;
import gov.utah.dts.det.ccl.service.TrackingRecordScreeningService;
import gov.utah.dts.det.ccl.sort.enums.FacilityLicenseSummarySortBy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

@SuppressWarnings("serial")
@Results({
	@Result(name = "redirect-index", location = "index", type = "redirectAction")
})
public class ReportsPrintAction extends BaseFacilityAction implements ServletRequestAware, ServletResponseAware {
	
	protected final Log log = LogFactory.getLog(getClass());

	private Long specialistId;
	private Long technicianId;
	private Date startDate;
	private Date endDate;
	private FacilityLicenseSummarySortBy facLicenseSummarySortBy;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private FacilityService facilityService;
	private PersonService personService;
	private TrackingRecordScreeningService screeningService;

	@Action(value = "print-open-applications")
	public void doPrintOpenApplications() {
		Person person = null;
		if (specialistId != null) {
			person = personService.getPerson(specialistId);
		}
		if (person == null || person.getId() == null) {
			return;
		}

		try {
			List<SortableFacilityView> facilities = facilityService.getOpenApplicationsBySpecialist(specialistId);
			ByteArrayOutputStream ba = OpenApplicationsReport.generate(person, facilities);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "";
				if (person != null) {
					if (StringUtils.isNotBlank(person.getFirstName())) {
						filename += person.getFirstName();
					}
					if (StringUtils.isNotBlank(person.getLastName())) {
						if (filename.length() > 0) {
							filename += "_";
						}
						filename += person.getLastName();
					}
				}
				if (filename.length() > 0) {
					filename += "_";
				}
				filename += "open_applications.pdf";
				sendToResponse(ba, filename);
			}
		} catch (Exception ex) {
			generateErrorPdf();
		}
	}

	@Action(value = "print-facility-license-summary")
	public void doPrintLicensorFacilitySummary() {
		Person person = null;
		if (specialistId != null) {
			person = personService.getPerson(specialistId);
		}
		if (person == null || person.getId() == null) {
			return;
		}

		try {
			if (facLicenseSummarySortBy == null) {
				facLicenseSummarySortBy = FacilityLicenseSummarySortBy.getDefaultSortBy();
			}
			List<FacilityLicenseView> licenses = facilityService.getFacilityLicenseSummary(specialistId, endDate, facLicenseSummarySortBy);
			ByteArrayOutputStream ba = FacilityLicenseSummaryReport.generate(person, endDate, facLicenseSummarySortBy, licenses);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "";
				if (person != null) {
					if (StringUtils.isNotBlank(person.getFirstName())) {
						filename += person.getFirstName();
					}
					if (StringUtils.isNotBlank(person.getLastName())) {
						if (filename.length() > 0) {
							filename += "_";
						}
						filename += person.getLastName();
					}
				}
				if (filename.length() > 0) {
					filename += "_";
				}
				filename += "facility_license_summary.pdf";
				sendToResponse(ba, filename);
			}
		} catch (Exception ex) {
			generateErrorPdf();
		}
	}

	@Action(value = "print-facility-license-detail")
	public void doPrintLicensorFacilityDetail() {
		Person person = null;
		if (specialistId != null) {
			person = personService.getPerson(specialistId);
		}
		if (person == null || person.getId() == null) {
			return;
		}

		try {
			List<FacilityLicenseView> licenses = facilityService.getFacilityLicenseDetail(specialistId, endDate);
			ByteArrayOutputStream ba = FacilityLicenseDetailReport.generate(person, endDate, licenses);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "";
				if (person != null) {
					if (StringUtils.isNotBlank(person.getFirstName())) {
						filename += person.getFirstName();
					}
					if (StringUtils.isNotBlank(person.getLastName())) {
						if (filename.length() > 0) {
							filename += "_";
						}
						filename += person.getLastName();
					}
				}
				if (filename.length() > 0) {
					filename += "_";
				}
				filename += "facility_license_detail.pdf";
				sendToResponse(ba, filename);
			}
		} catch (Exception ex) {
			generateErrorPdf();
		}
	}

	@Action(value = "print-expired-licenses")
	public void doPrintExpiredLicenses() {
		Person person = null;
		if (specialistId != null) {
			person = personService.getPerson(specialistId);
		}
		if (person == null || person.getId() == null) {
			return;
		}

		try {

			List<FacilityLicenseView> licenses = facilityService.getExpiringLicensesBySpecialist(new Date(), specialistId);
			ByteArrayOutputStream ba = ExpiredLicensesReport.generate(person, licenses);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "";
				if (person != null) {
					if (StringUtils.isNotBlank(person.getFirstName())) {
						filename += person.getFirstName();
					}
					if (StringUtils.isNotBlank(person.getLastName())) {
						if (filename.length() > 0) {
							filename += "_";
						}
						filename += person.getLastName();
					}
				}
				if (filename.length() > 0) {
					filename += "_";
				}
				filename += "expired_licenses.pdf";
				sendToResponse(ba, filename);
			}
		} catch (Exception ex) {
			generateErrorPdf();
		}
	}

	@Action(value = "print-license-renewal-list")
	public void doPrintLicenseRenewalList() {
		Person person = null;
		if (specialistId != null) {
			person = personService.getPerson(specialistId);
		}
		if (person == null || person.getId() == null) {
			return;
		}

		try {
			// Default endDate to the last day of the current month
			if (endDate == null) {
				Calendar cal = Calendar.getInstance();
				int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				cal.set(Calendar.DAY_OF_MONTH, maxDay);
				endDate = cal.getTime();
			}
			
			List<FacilityLicenseView> licenses = facilityService.getRenewalLicensesBySpecialist(endDate, specialistId);
			ByteArrayOutputStream ba = LicenseRenewalsListReport.generate(person, endDate, licenses);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "";
				if (person != null) {
					if (StringUtils.isNotBlank(person.getFirstName())) {
						filename += person.getFirstName();
					}
					if (StringUtils.isNotBlank(person.getLastName())) {
						if (filename.length() > 0) {
							filename += "_";
						}
						filename += person.getLastName();
					}
				}
				if (filename.length() > 0) {
					filename += "_";
				}
				filename += "license_renewal_list.pdf";
				sendToResponse(ba, filename);
			}
		} catch (Exception ex) {
			generateErrorPdf();
		}
	}

	@Action(value = "print-license-renewal-letters")
	public void doPrintLicenseRenewalLetters() {
		Person person = null;
		if (specialistId != null) {
			person = personService.getPerson(specialistId);
		}
		if (person == null || person.getId() == null) {
			return;
		}

		try {
			// Default endDate to the last day of the current month
			if (endDate == null) {
				Calendar cal = Calendar.getInstance();
				int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				cal.set(Calendar.DAY_OF_MONTH, maxDay);
				endDate = cal.getTime();
			}
			
			List<FacilityLicenseView> licenses = facilityService.getFosterCareRenewalLicensesBySpecialist(endDate, specialistId);
			ByteArrayOutputStream ba = LicenseRenewalLettersReport.generate(licenses);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "";
				if (person != null) {
					if (StringUtils.isNotBlank(person.getFirstName())) {
						filename += person.getFirstName();
					}
					if (StringUtils.isNotBlank(person.getLastName())) {
						if (filename.length() > 0) {
							filename += "_";
						}
						filename += person.getLastName();
					}
				}
				if (filename.length() > 0) {
					filename += "_";
				}
				filename += "license_renewal_letters.pdf";
				sendToResponse(ba, filename);
			}
		} catch (Exception ex) {
			generateErrorPdf();
		}
	}

	@Action(value = "print-livescans-issued")
	public void doPrintLivescansIssued() {
		Person person = null;
		if (technicianId != null) {
			person = personService.getPerson(technicianId);
		}
		if (person == null || person.getId() == null) {
			return;
		}

		try {
			List<TrackingRecordScreening> screenings = screeningService.getLivescansIssued(technicianId, startDate, endDate);
			ByteArrayOutputStream ba = LivescansIssuedReport.generate(person, startDate, endDate, screenings);
			if (ba != null && ba.size() > 0) {
				// This is where the response is set
				String filename = "";
				if (person != null) {
					if (StringUtils.isNotBlank(person.getFirstName())) {
						filename += person.getFirstName();
					}
					if (StringUtils.isNotBlank(person.getLastName())) {
						if (filename.length() > 0) {
							filename += "_";
						}
						filename += person.getLastName();
					}
				}
				if (filename.length() > 0) {
					filename += "_";
				}
				filename += "livescans_issued.pdf";
				sendToResponse(ba, filename);
			}
		} catch (Exception ex) {
			generateErrorPdf();
		}
	}

	private void generateErrorPdf() {
		// Generate an error pdf
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			Document document = new Document(PageSize.A4, 50, 50, 100, 100);
			@SuppressWarnings("unused")
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			document.open();
			document.add(new Paragraph("An error occurred while generating the report document.", FontFactory.getFont("Times-Roman", 12, Font.NORMAL)));
			document.close();
			if (ba != null && ba.size() > 0) {
				sendToResponse(ba, null);
			}
		} catch (Exception e) {
			log.error("AN ERROR OCCURRED GENERATING ERROR PDF DOCUMENT: " + e);
		}
	}
	
	private void sendToResponse(ByteArrayOutputStream ba, String filename) 
	throws IOException {
		if (ba != null && ba.size() > 0) {
			response.setContentLength(ba.size());
			response.setContentType("application/pdf");
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			if (StringUtils.isNotBlank(filename)) {
				response.setHeader("Content-disposition", "attachment; filename=\"" + filename + "\"");
			} else {
				response.setHeader("Content-disposition", "attachment");
			}
	
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();
		}
	}

	public Long getSpecialistId() {
		return specialistId;
	}
	
	public void setSpecialistId(Long specialistId) {
		this.specialistId = specialistId;
	}

	public Long getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Long technicianId) {
		this.technicianId = technicianId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public FacilityLicenseSummarySortBy getFacLicenseSummarySortBy() {
		return facLicenseSummarySortBy;
	}
	
	public void setFacLicenseSummarySortBy(FacilityLicenseSummarySortBy facLicenseSummarySortBy) {
		this.facLicenseSummarySortBy = facLicenseSummarySortBy;
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
	
	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
	public void setTrackingRecordScreeningService(TrackingRecordScreeningService trackingRecordScreeningService) {
		this.screeningService = trackingRecordScreeningService;
	}
}