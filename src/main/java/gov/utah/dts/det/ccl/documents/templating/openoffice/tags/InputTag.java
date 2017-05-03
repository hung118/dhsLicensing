package gov.utah.dts.det.ccl.documents.templating.openoffice.tags;

import gov.utah.dts.det.admin.view.KeyValuePair;
import gov.utah.dts.det.ccl.documents.Input;
import gov.utah.dts.det.ccl.documents.InputDisplayType;
import gov.utah.dts.det.ccl.documents.templating.TemplateException;
import gov.utah.dts.det.ccl.documents.templating.openoffice.TemplateContext;
import gov.utah.dts.det.util.DateUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputTag extends AbstractTemplateTag {

	private static final Logger logger = LoggerFactory.getLogger(InputTag.class);

	protected String var;
	protected String label;
	protected String required;
	protected String displayType;
	protected String options;
	protected String optionKey;
	protected String optionValue;
	
	public InputTag(TemplateContext templateContext, Map<String, Object> tagContext) {
		super(templateContext, tagContext);
	}
	
	@Override
	public String getType() {
		return "input";
	}
	
	public String getVar() {
		return var;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getRequired() {
		return required;
	}
	
	public void setRequired(String required) {
		this.required = required;
	}
	
	public String getDisplayType() {
		return displayType;
	}
	
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	
	public String getOptions() {
		return options;
	}
	
	public void setOptions(String options) {
		this.options = options;
	}
	
	public String getOptionKey() {
		return optionKey;
	}
	
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}
	
	public String getOptionValue() {
		return optionValue;
	}
	
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	@Override
	public void doStart() throws TemplateException {
		logger.debug("In input tag: " + label);
		if (!templateContext.isInMetadataSection()) {
			throw new TemplateException("Input tag cannot be placed outside the metadata section.");
		}
		try {
			setRenderingAttributes();
			if (templateContext.isScanPhase() && evaluating) {
				logger.debug("Evaluating input tag: " + label);
				
				InputDisplayType dispType = null;
				if (StringUtils.isNotBlank(displayType)) {
					dispType = InputDisplayType.valueOf(displayType.toUpperCase());
				}
				
				Map<Object, String> optMap = new LinkedHashMap<Object, String>();
				if (StringUtils.isNotBlank(options)) {
					try {
						String opts = options;
						if (options.startsWith("#{")) {
							opts = options.replaceFirst("\\#\\{", "#@java.util.LinkedHashMap@{");
						}
						Object value = Ognl.getValue(opts, templateContext.getExpressionContext(), templateContext.getExpressionRoot());
						if (value instanceof Map<?, ?>) {
							Map<?, ?> map = (Map<?, ?>) value;
							for (Entry<?, ?> entry : map.entrySet()) {
								optMap.put(entry.getKey().toString(), entry.getValue().toString());
							}
						} else if (value instanceof Collection<?>) {
							Collection<?> coll = (Collection<?>) value;
							
							for (Object obj : coll) {
								String optKey = null;
								String optVal = null;
								if (optionKey == null) {
									optKey = obj.toString();
								} else {
									optKey = Ognl.getValue(optionKey, obj).toString();
								}
								if (optionValue == null) {
									optVal = obj.toString();
								} else {
									optVal = Ognl.getValue(optionValue, obj).toString();
								}
								optMap.put(optKey, optVal);
							}
						} else {
							throw new TemplateException("input options " + options + " must be a collection or map.");
						}
					} catch (ClassCastException cce) {
						throw new TemplateException("input options " + options + " must be a collection or map.");
					}
				}
				
				if (dispType == InputDisplayType.TIME) {
					List<KeyValuePair> times = DateUtils.getTimesFifteenMinuteIncrements();
					for (KeyValuePair time : times) {
						optMap.put(time.getName(), time.getValue());
					}
					dispType = InputDisplayType.SELECT;
				}
				
				Input input = new Input(var, label, null, String.class, required == null ? Boolean.FALSE : new Boolean(required), optMap, dispType);
				
				templateContext.getInputs().add(input);
			}
		} catch (Exception e) {
			throw new TemplateException("Unable to evaluate prompt tag - value: " + var, e);
		}
	}
	
	@Override
	public void doEnd() throws TemplateException {
		
	}
}