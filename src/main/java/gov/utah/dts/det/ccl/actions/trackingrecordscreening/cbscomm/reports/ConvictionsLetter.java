package gov.utah.dts.det.ccl.actions.trackingrecordscreening.cbscomm.reports;

import gov.utah.dts.det.ccl.model.TrackingRecordScreening;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConviction;
import gov.utah.dts.det.ccl.model.TrackingRecordScreeningConvictionLetter;
import gov.utah.dts.det.ccl.view.YesNoList;
import gov.utah.dts.det.reports.FloatingLinedPdfPCell;
import gov.utah.dts.det.reports.FloatingPdfPCell;
import gov.utah.dts.det.reports.LetterheadStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvictionsLetter {

	private static final Logger log = LoggerFactory.getLogger(ConvictionsLetter.class);

	// Define report fonts
	static final Font mediumfont = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
	static final Font smallfont = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);
	static final Font mediumfontI = FontFactory.getFont("Times-Roman", 12, Font.ITALIC);

	static final Float fixedLeading = 14.0f; // The paragraph line spacing

	private static SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy");
	private static SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	
	public static ByteArrayOutputStream generate(TrackingRecordScreeningConvictionLetter convictionLetter, List<TrackingRecordScreeningConviction> convictionsList, HttpServletRequest request) throws Exception {
		ByteArrayOutputStream ba = null;
		try {
			ba = new ByteArrayOutputStream();
			writePdf(convictionLetter, convictionsList, ba, request);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw (ex);
		}
		return ba;
	}

	private static void writePdf(TrackingRecordScreeningConvictionLetter convictionLetter, List<TrackingRecordScreeningConviction> convictions, OutputStream ba, HttpServletRequest request)
	throws IOException, DocumentException, BadElementException, Exception {
		Document document = null;
		Paragraph paragraph = null;
		PdfPTable datatable = null;
		PdfPTable detail = null;
		document = new Document(PageSize.LETTER, 50, 50, 190, 100);
		PdfWriter writer = PdfWriter.getInstance(document, ba);

		document.open();
		
		LetterheadStamper.stampLetter(writer, request);

		paragraph = getParagraph();
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.add(new Phrase("Department of Human Services", mediumfont));
		document.add(paragraph);

		paragraph.clear();
		paragraph.add(new Phrase("Office of Licensing", mediumfont));
		document.add(paragraph);

		paragraph.clear();
		paragraph.add(new Phrase("Criminal Background Screening Unit", mediumfont));
		document.add(paragraph);

		// Add report date
		paragraph.clear();
		paragraph.add(new Phrase(df.format(convictionLetter.getLetterDate()), mediumfont));
		document.add(paragraph);

		paragraph = getParagraph();
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.add(new Phrase(convictionLetter.getLetterType().getLabel(), mediumfont));
		paragraph.setSpacingBefore(30);
		document.add(paragraph);

		document.add(getProgramTable(convictionLetter.getTrackingRecordScreening()));
		document.add(getApplicantTable(convictionLetter.getTrackingRecordScreening()));

		paragraph = getParagraph();
		paragraph.add(new Phrase("List of convictions/charges found in the background search, UCA 62A-2-120(6)(a)(ii)", mediumfont));
		paragraph.setSpacingBefore(10);
		document.add(paragraph);

		paragraph = getParagraph();
		paragraph.add(new Phrase("PLEASE ATTACH A COPY OF THIS SUMMARY TO THE NEXT BACKGROUND SCREENING APPLICATION.", mediumfont));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		paragraph.setSpacingBefore(10);
		paragraph.setSpacingAfter(5);
		document.add(paragraph);

		// Add the conviction listing table header
		datatable = getConvictionsReportTable();
		datatable.getDefaultCell().setPadding(0);
		datatable.getDefaultCell().setPaddingTop(8);
		datatable.getDefaultCell().setBorderWidth(0);
		datatable.getDefaultCell().setBorderWidthTop(2);
		datatable.addCell(getHeaderTable());
		datatable.getDefaultCell().setPaddingTop(0);
		datatable.getDefaultCell().setBorderWidthTop(0);
		datatable.setHeaderRows(1);
		if (convictions != null && convictions.size() > 0) {
			for (int i = 0; i < convictions.size(); i++) {
				TrackingRecordScreeningConviction conviction = convictions.get(i);
				detail = getDetailTable();
				detail.addCell(new Phrase(conviction == null || conviction.getConvictionType() == null || conviction.getConvictionType().getValue() == null ? " " 
					: conviction.getConvictionType().getValue(), smallfont));
				detail.addCell(new Phrase(conviction == null || conviction.getConvictionDesc() == null ? " " 
					: conviction.getConvictionDesc(), smallfont));
				detail.addCell(new Phrase(conviction == null || conviction.getConvictionDate() == null ? " " 
					: df2.format(conviction.getConvictionDate()), smallfont));
				detail.addCell(new Phrase(conviction == null || conviction.getCourtInfo() == null ? " " 
					: conviction.getCourtInfo(), smallfont));
				if (i + 1 == convictions.size()) {
					datatable.getDefaultCell().setPaddingBottom(8);
					datatable.getDefaultCell().setBorderWidthBottom(2);
				}
				datatable.addCell(detail);
			}
		} else {
			detail = getDetailTable();
			detail.getDefaultCell().setColspan(4);
			detail.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			detail.addCell(new Phrase("There are no convictions to report.", smallfont));
			datatable.getDefaultCell().setPaddingBottom(8);
			datatable.getDefaultCell().setBorderWidthBottom(2);
			datatable.addCell(detail);
		}
		document.add(datatable);

		document.add(getListingFooterTable(convictionLetter.getTrackingRecordScreening()));

		document.close();
	}

	private static PdfPTable getProgramTable(TrackingRecordScreening screening) throws BadElementException, DocumentException,
	Exception {
		PdfPTable table = new PdfPTable(2);
		// format the tables
		int headerwidths[] = { 12, 88 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.setSpacingBefore(20);
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(PdfPTable.ALIGN_BOTTOM);
		table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

		table.addCell(new Phrase("Program:", mediumfont));
		table.addCell(new Phrase(screening.getFacility().getName().toUpperCase(), mediumfont));

		return table;
	}

	private static PdfPTable getApplicantTable(TrackingRecordScreening screening) throws BadElementException, DocumentException,
	Exception {
		PdfPTable table = new PdfPTable(2);
		// format the tables
		int headerwidths[] = { 12, 88 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		table.setSpacingBefore(10);
		table.getDefaultCell().setLeading(fixedLeading, 0);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(new Phrase("Applicant:", mediumfont));
		table.addCell(new Phrase(screening.getFirstAndLastName().toUpperCase() + " (" + screening.getPersonIdentifier() + ")", mediumfont));

		return table;
	}

	private static PdfPTable getConvictionsReportTable() throws BadElementException, DocumentException, Exception {
		PdfPTable table = new PdfPTable(1);
		// format the tables
		table.setWidthPercentage(100);
		table.getDefaultCell().setPadding(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
		return table;
	}

	private static PdfPTable getHeaderTable() throws BadElementException, DocumentException, Exception {
		return getListingReportTable(true);
	}

	private static PdfPTable getDetailTable() throws BadElementException, DocumentException, Exception {
		return getListingReportTable(false);
	}

	private static PdfPTable getListingReportTable(boolean header) throws BadElementException, DocumentException, Exception {
		PdfPTable table = new PdfPTable(4);
		FloatingLinedPdfPCell floatingLinedCell = new FloatingLinedPdfPCell();
		FloatingPdfPCell floatingCell = new FloatingPdfPCell();
		// format the tables
		int headerwidths[] = { 12, 38, 11, 39 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.setWidthPercentage(100);
		if (header) {
			table.getDefaultCell().setCellEvent(floatingLinedCell);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(new Phrase("Type", smallfont));
			table.addCell(new Phrase("Conviction/Charge", smallfont));
			table.addCell(new Phrase("Date", smallfont));
			table.addCell(new Phrase("Court Information", smallfont));
		} else {
			table.getDefaultCell().setCellEvent(floatingCell);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setLeading(fixedLeading, 0);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		}

		return table;
	}

	private static PdfPTable getListingFooterTable(TrackingRecordScreening screening) throws BadElementException, DocumentException, Exception {
		PdfPTable table = new PdfPTable(4);
		// format the tables
		table.setWidthPercentage(100);
		int headerwidths[] = { 40, 14, 10, 36 }; // percentage
		table.setWidths(headerwidths); // percentage
		table.getDefaultCell().setPaddingTop(5);
		table.getDefaultCell().setPaddingBottom(0);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(new Phrase("CBS Committee Decision:", mediumfont));
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(new Phrase(screening == null || screening.getCbsComm() == null || screening.getCbsComm().getCbsCommitteeDecision() == null ? "" 
			: YesNoList.retYesNoList(screening.getCbsComm().getCbsCommitteeDecision()).getDisplayName(), mediumfont));
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(new Phrase("Date:", mediumfont));
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(new Phrase(screening == null || screening.getCbsComm() == null || screening.getCbsComm().getDecisionDate() == null ? " " 
			: df2.format(screening.getCbsComm().getDecisionDate()), mediumfont));

		return table;
	}

	// This method is used to return a new paragraph with a set fixed leading line space for adjusting paragraph line height
	private static Paragraph getParagraph() {
		return new Paragraph(fixedLeading);
	}
}
