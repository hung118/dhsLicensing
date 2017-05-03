package gov.utah.dts.det.reports;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;

public class FloatingLinedPdfPCell implements PdfPCellEvent {
    /**
     * @see com.itextpdf.text.pdf.PdfPCellEvent#cellLayout(com.itextpdf.text.pdf.PdfPCell,
     *      com.itextpdf.text.Rectangle, com.itextpdf.text.pdf.PdfContentByte[])
     */
    public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
        float x1 = position.getLeft() + 2;
        float x2 = position.getRight() - 2;
        float y1 = position.getBottom() + 2;
        float y2 = position.getBottom() - 2;
        PdfContentByte canvas = canvases[PdfPTable.LINECANVAS];
        canvas.moveTo(x1, y2);
        canvas.lineTo(x2, y2);
        canvas.moveTo(x1, y1);
        canvas.stroke();
    }
}
