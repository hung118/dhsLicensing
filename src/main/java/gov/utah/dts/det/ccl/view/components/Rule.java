package gov.utah.dts.det.ccl.view.components;

import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.service.RuleService;
import gov.utah.dts.det.util.spring.AppContext;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts2.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

public class Rule extends Component {
	
	private static final Logger logger = LoggerFactory.getLogger(Rule.class);

    public Rule(ValueStack stack) {
        super(stack);
    }
    
    private String ruleId;
    private String format;
    
    public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
    
    public void setFormat(String format) {
		this.format = format;
	}

    public boolean start(Writer writer) {
        boolean result = super.start(writer);
        
        if (ruleId == null) {
        	return result;
        } else {
        	ruleId = stripExpressionIfAltSyntax(ruleId);
        }

        ruleId = (String) getStack().findValue(ruleId, String.class, throwExceptionOnELFailure);
        
        RuleService ruleService = (RuleService) AppContext.getApplicationContext().getBean("ruleService");
        
        RuleSubSection rule = ruleService.loadRuleSubSectionById(new Long(ruleId));
        
        StringBuffer ruleNum = new StringBuffer();
        ruleNum.append(rule.getSection().getRule().getNumber());
        ruleNum.append("-");
        ruleNum.append(rule.getSection().getNumber());
        ruleNum.append(rule.getNumber());
        
        
        format = format.replaceAll("RNUM", StringEscapeUtils.escapeHtml(ruleNum.toString()));
        format = format.replaceAll("RDESC", StringEscapeUtils.escapeHtml(rule.getName()));
        
        try {
        	writer.write(format);
        } catch (IOException e) {
            logger.info("Could not print out rule value '" + ruleId + "'", e);
        }

        return result;
    }
}
