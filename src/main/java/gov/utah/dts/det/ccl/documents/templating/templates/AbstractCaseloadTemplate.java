package gov.utah.dts.det.ccl.documents.templating.templates;

import gov.utah.dts.det.ccl.actions.caseloadmanagement.CaseloadSortBy;
import gov.utah.dts.det.ccl.documents.FileDescriptor;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.AbstractTemplate;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.model.Person;
import gov.utah.dts.det.ccl.model.enums.RoleType;
import gov.utah.dts.det.ccl.model.view.FacilityCaseloadView;
import gov.utah.dts.det.ccl.service.FacilityService;
import gov.utah.dts.det.ccl.service.PersonService;
import gov.utah.dts.det.ccl.service.UserService;
import gov.utah.dts.det.ccl.view.facility.FacilityStatus;
import gov.utah.dts.det.service.ApplicationService;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public abstract class AbstractCaseloadTemplate extends AbstractTemplate {
	
	protected static final String SPECIALIST_KEY = "specialist";
	protected static final float MARGIN = 36f;
	
	protected static final String FONT_NAME = "Arial";
	protected static final float FONT_SIZE = 10f;
	protected static final Font FONT = FontFactory.getFont(FONT_NAME, FONT_SIZE);
	protected static final Font HEADER_FONT = FontFactory.getFont(FONT_NAME, FONT_SIZE, Font.BOLD);
	protected static final float TABLE_CELL_PADDING = 3f;

	protected static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");
	
	@Autowired
	protected FacilityService facilityService;
	
	@Autowired
	protected PersonService personService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected ApplicationService applicationService;
	
	protected List<Input> inputs;
	
	@Override
	public List<Input> getInputs(Map<String, Object> context) {
		if (inputs == null) {
			inputs = new ArrayList<Input>();
			Map<Long, String> specOpts = new HashMap<Long, String>();
			Set<Person> specialists = userService.getPeople(RoleType.ROLE_LICENSOR_SPECIALIST, true, true, false);
			for (Person spec : specialists) {
				specOpts.put(spec.getId(), spec.getFirstAndLastName());
			}
			
			inputs.add(new Input("specId", "Specialist", null, Long.class, true, specOpts, InputDisplayType.SELECT));
		}
		return inputs;
	}

	@Override
	public void render(Map<String, Object> context, OutputStream outputStream, FileDescriptor descriptor) throws TemplateException {
		Long specialistId = (Long) context.get("specId");
		if (specialistId == null) {
			throw new TemplateException("Specialist id is required.");
		}
		
		CaseloadSortBy sortBy = CaseloadSortBy.getDefaultSortBy();
		String sortByStr = (String) context.get("sortBy");
		if (sortByStr != null) {
			sortBy = CaseloadSortBy.valueOf(sortByStr);
		}
		
		Person specialist = personService.getPerson(specialistId);
		context.put(SPECIALIST_KEY, specialist);
		
		List<FacilityCaseloadView> caseload = getCaseload(specialistId, sortBy);
		
		setFileName(context, descriptor);
		
		try {
			Document document = new Document(PageSize.LETTER.rotate(), MARGIN, MARGIN, MARGIN, MARGIN);
			PdfWriter.getInstance(document, outputStream);
			
			document.open();
			
			document.add(new Paragraph(getReportTitle() + " for " + specialist.getFirstAndLastName(), FONT));
			//columns: name, facility id, address, phone, 1st director(s), status, type, capacity (<2)
			
			PdfPTable table = new PdfPTable(8);
			table.setWidths(new float[]{23f, 25f, 11f, 13f, 5f, 9f, 7f, 7f});
			table.setWidthPercentage(100);
			table.setSpacingBefore(FONT_SIZE);
			
			table.getDefaultCell().setPadding(TABLE_CELL_PADDING);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setBorderWidthBottom(.5f);
			table.setHeaderRows(1);
			
			table.addCell(new Phrase("Facility Name", HEADER_FONT));
			table.addCell(new Phrase("Address", HEADER_FONT));
			table.addCell(new Phrase("Phone", HEADER_FONT));
			table.addCell(new Phrase("1st Director(s)", HEADER_FONT));
			table.addCell(new Phrase("Type", HEADER_FONT));
			table.addCell(new Phrase("Exp Dt", HEADER_FONT));
			table.addCell(new Phrase("Adult Cap", HEADER_FONT));
			table.addCell(new Phrase("Youth Cap", HEADER_FONT));
			
			boolean hasInProcess = false;
			
			for (FacilityCaseloadView fcv : caseload) {
				if (fcv.getStatus() == FacilityStatus.REGULATED || fcv.getStatus() == FacilityStatus.IN_PROCESS) {
					StringBuilder name = new StringBuilder();
					if (fcv.getStatus() == FacilityStatus.IN_PROCESS) {
						hasInProcess = true;
						name.append("* ");
					}
					name.append(fcv.getName());
					
					table.addCell(new Phrase(name.toString(), FONT));
					table.addCell(new Phrase(fcv.getLocationAddress().toString(), FONT));
					table.addCell(new Phrase(fcv.getPrimaryPhone().getFormattedPhoneNumber(), FONT));
					table.addCell(new Phrase(fcv.getDirectorNames(), FONT));
					
					String typeAbbrev = null;
/*
					if (fcv.getLicenseType() != null) {
						typeAbbrev = applicationService.getApplicationPropertyValue("facility.license.type." + fcv.getLicenseType().getId()+ ".abbrev");
					}
*/					
					table.addCell(new Phrase(typeAbbrev != null ? typeAbbrev : "", FONT));
//					table.addCell(new Phrase(fcv.getExpirationDate() != null ? DATE_FORMATTER.format(fcv.getExpirationDate()) : "", FONT));
					table.addCell(new Phrase("", FONT));

//					if (fcv.getAdultTotalSlots() == null) {
						table.addCell(new Phrase(""));
//					} else {
//						table.addCell(new Phrase(fcv.getAdultTotalSlots().toString(), FONT));
//					}
//					if (fcv.getYouthTotalSlots() == null) {
						table.addCell(new Phrase(""));
//					} else {
//						table.addCell(new Phrase(fcv.getYouthTotalSlots().toString(), FONT));
//					}
				}
			}
			
			document.add(table);
			if (hasInProcess) {
				document.add(new Paragraph("* - Facility is in the process of becoming a regulated child care facility.", FONT));
			}
			
			document.close();
		} catch (DocumentException de) {
			throw new TemplateException(de);
		}
	}
	
	public abstract List<FacilityCaseloadView> getCaseload(Long specialistId, CaseloadSortBy sortBy);
	
	public abstract String getReportTitle();
}