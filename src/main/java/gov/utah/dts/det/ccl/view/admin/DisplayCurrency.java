package gov.utah.dts.det.ccl.view.admin;

import java.text.NumberFormat;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * The displaytag wrapper class to display currency field.
 * 
 * @author HNGUYEN
 *
 */
public class DisplayCurrency implements DisplaytagColumnDecorator {

	protected Log logger = org.apache.commons.logging.LogFactory.getLog(this.getClass());
	
	public DisplayCurrency() {
		
	}
	
	@Override
	public Object decorate(Object columnValue, PageContext pageContext,
			MediaTypeEnum media) throws DecoratorException {

		logger.debug("Entering decorate ...");
		
		String target = "";
		
		if (columnValue == null) return target;
		
		Double money = null;
		if (columnValue instanceof String) {
			if ("".equals(((String) columnValue).trim())) return target;
			money = new Double((String)columnValue);
		} else {
			money = (Double)columnValue;
		}
		
		NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(); 
		target = formatCurrency.format(money);
		
		return target;
	}

}
