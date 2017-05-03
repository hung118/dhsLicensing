package gov.utah.dts.det.ccl.documents.reporting.reports;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReportTable<T> {

	private SortedSet<String> rows = new TreeSet<String>();
	private SortedSet<String> columns = new TreeSet<String>();
	private Map<ReportTableKey, T> tableData = new HashMap<ReportTableKey, T>();
	
	public void addRow(String row) {
		rows.add(row);
	}
	
	public SortedSet<String> getRows() {
		return rows;
	}
	
	public void addColumn(String column) {
		columns.add(column);
	}
	
	public SortedSet<String> getColumns() {
		return columns;
	}
	
	public void addTableDataItem(String row, String column, T item) {
		tableData.put(new ReportTableKey(row, column), item);
	}
	
	public T getTableDataItem(String row, String column) {
		return tableData.get(new ReportTableKey(row, column));
	}
}