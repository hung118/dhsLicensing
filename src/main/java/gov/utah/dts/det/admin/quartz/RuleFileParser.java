package gov.utah.dts.det.admin.quartz;

import gov.utah.dts.det.ccl.model.RuleSection;
import gov.utah.dts.det.ccl.model.RuleSubSection;
import gov.utah.dts.det.ccl.model.enums.RuleCategory;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

public class RuleFileParser {

	private String section;
	private String sectionTitle;
	private String heading;
	
	private String fileName;
	private String rulePrefix;
	private ArrayList<RuleSection> rules = new ArrayList<RuleSection>();
	private int lineCount;

	public static void main(String[] args) {
		try {
			RuleFileParser rp = new RuleFileParser();
			rp.parse("/Users/chadsmith/temp/test/processed/2012-10-29/r501-13.rtf_s");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int parse(String filePath) throws Exception {
		return parse(new File(filePath));
	}

	public int parse(File file) throws Exception {

		if (file != null && file.exists() && file.canRead()) {
			fileName = file.getName();
			if (rulePrefix == null) {
				rulePrefix = (file.getName().substring(0, file.getName().indexOf("-"))).toUpperCase();
			}

			// read the contents of the file 
			//	Note: this strips out the RTF formatting (so that is nice)
			FileInputStream stream = new FileInputStream(file);
			RTFEditorKit kit = new RTFEditorKit();  
			Document doc = kit.createDefaultDocument();  
			kit.read(stream, doc, 0);  
			String plainText = doc.getText(0, doc.getLength());  
			
			return parseText(plainText);
			
		} else {
			throw new Exception("Supplied file["+file+"] is null, doesn't exist, or can't be read!");
		}
	}

	public int parseText(String t) throws Exception {
		ArrayList<String> rawRules = new ArrayList<String>();
		rules = new ArrayList<RuleSection>();
		lineCount = 0;
		
		StringTokenizer st = new StringTokenizer(t, "\n");
		String rule = "";
		while (st.hasMoreElements()) {
			String line = st.nextToken()+"\n";
			// do we have a new rule
			if (!"".equals(line) && line.startsWith("KEY:")) {
				if (!"".equals(rule)) {
					rawRules.add(rule);
				}
				break;
			}
			
			if (line.startsWith(rulePrefix)) {
				if (!"".equals(rule.trim())) {
					rawRules.add(rule);
				}
				/*
				if (!"".equals(rule.trim())) {
					RuleSection r = parseRule(rule);
					r.setSortOrder(new Double(rules.size()+1));
					rules.add(r);
				}
				*/
				if (line.indexOf("-") > -1) {
					String[] split = line.split("-");
					if (split.length == 2) {
						section = line;
						sectionTitle = line.trim();
						if (sectionTitle.indexOf(". ") >-1) {
							sectionTitle = sectionTitle.substring(sectionTitle.indexOf(". ")+2);
							sectionTitle = sectionTitle.trim();
						}
						lineCount++;
						continue;
					}
					rule = line;
				} else {
					this.heading = line;
					lineCount++;
					continue;
				}
			} else {
				rule += line;
			}

			lineCount++;
		}
//
//		if (false) {
//			System.out.println("RuleFileParser: "+"raw rule count: "+rawRules.size());
//			for (String _rule : rawRules) {
//				String s = _rule.substring(0, _rule.indexOf("\n"));
//				System.out.println("---\n"+s); 
//			}
//			System.out.println("RuleFileParser: heading="+heading); 
//			System.out.println("RuleFileParser: sectionTitle="+sectionTitle);
//			System.out.println("RuleFileParser: rulePrefix="+rulePrefix);
//		}
//	
		if (rawRules.size() > 0) {
			int rCnt = 0;
			for (Iterator<String> iterator = rawRules.iterator(); iterator.hasNext();) {
				String raw = iterator.next();
				RuleSection r = null;
				try {
					r = parseRule(raw);
				} catch (Exception e) {
					e.printStackTrace(System.err);
					r = handleParseError(raw);
				}
				rCnt++;
				r.setSortOrder(new Double(rCnt));
				rules.add(r);
			}
		}
		
		return rules.size();
	}
	
	/**
	 * This method just takes the first line as the section header info
	 * and stuff every other line into a single sub section element
	 * @param s (text to parse)
	 * @return
	 */
	private RuleSection handleParseError(String s) {
		
		RuleSection section = new RuleSection();
		section.setCreatedBy("SYSTEM");
		section.setCategory(RuleCategory.PENDING);
		section.setVersionDate(new java.util.Date());

		StringTokenizer st = new StringTokenizer(s, "\n");
		int cnt = 0;
		String subText = "";
		while (st.hasMoreElements()) {
			String line = st.nextToken();
			line = line+"\n";
			cnt++;
			
			if (cnt == 1) {
				String f = line.substring(0, line.indexOf(" "));
				String b = line.substring(line.indexOf(" "));
				f = f.trim(); b = b.trim();

				if (f.endsWith(".")) 
					f = f.substring(0, f.length()-1);

				String secNum = f.substring(f.indexOf("-")+1);
				String secBase = secNum.substring(0, secNum.indexOf("-"));
				String num = secNum.substring(secNum.indexOf("-")+1);
				section.setId(Long.parseLong(num));
				section.setActive(false);
				section.setName(b);
				section.setTitle(sectionTitle);
				section.setSectionBase(Integer.parseInt(secBase));
				section.setNumber(Integer.parseInt(num));
				section.setVersionDate(new java.util.Date());
				
			} else if (cnt > 1) {
				subText += line;
			}
		}
		
		RuleSubSection subSection = new RuleSubSection();
		subSection.setNumber("1.");
		subSection.setSortOrder(new Double(1));
		subSection.setRuleContent(subText);
		subSection.setCategory(RuleCategory.PENDING);
		subSection.setSection(section);
		subSection.setCreatedBy("SYSTEM");
		subSection.setVersionDate(new java.util.Date());
		
		section.addSubSection(subSection);

		return section;
	}
	
	private RuleSection parseRule(String s) throws ParseException {
		
		String p1 = "\\(\\d{1,10}\\)"; // accept up to 10 digits
		String p2 = "\\d{1,10}\\.";
		String p3 = "[A-Z].";
		String p4 = "\\([A-Z]\\)";
		
		String sep = null;	// separator pattern
		String sectionComments = null;
		
		String sub = ""; // short for sub-section
		ArrayList<String> subs = new ArrayList<String>(); // short sub-section list
		StringTokenizer st = new StringTokenizer(s, "\n");
		
		// if it's only 2 lines then there are no subsections
		if (st.countTokens() == 2) {
			subs.add(s);
			return convertToBeans(subs, sep, sectionComments);
		}
		
		int cnt = 0;
		
		while (st.hasMoreElements()) {
			String line = st.nextToken();
			line = line+"\n";
			cnt++;
			// determine what the separating pattern is (i.e. type of numbering system they used in the file)
			if (cnt == 1) {
				subs.add(line); // add the header
			} else if (cnt == 2) {
				// figure out what number system is used
				if (matchFirst(p1, line))
					sep = p1;
				else if (matchFirst(p2, line))
					sep = p2;
				else if (matchFirst(p3, line))
					sep = p3;
				else if (matchFirst(p4, line))
					sep = p4;
				else { 
					if (sectionComments != null)  
						sectionComments += line;
					else
						sectionComments = line;
					cnt--;
//					String msg = "Subsection numbering system not supported [supported formats: (1) or (A) or 1. or A.]";
//					System.err.println(getClass().getName()+": Error parsing file: "+fileName);
//					System.err.println(msg+"... Error in rule block ...");
//					System.err.println(s);
//					throw new ParseException(msg, 1);
				}
				sub = line;
			} else {
				// determine what to do w/ the next line
				if (matchFirst(sep, line)) {
					subs.add(sub);
					sub = line;
				} else {
					sub += line;
				}
			}
		}
		subs.add(sub);
		
		return convertToBeans(subs, sep, sectionComments);
	}
	
	private RuleSection convertToBeans(ArrayList<String> raw, String sep, String sectionComments) {
		RuleSection section = null;
		for (int i = 0; i < raw.size(); i++) {
			String line = raw.get(i);
			line = line.trim();
			if (i == 0) {
				//section
				section = new RuleSection();
				section.setCreatedBy("SYSTEM");
				section.setCategory(RuleCategory.PENDING);
				section.setVersionDate(new java.util.Date());
				section.setComment(sectionComments);
				String f = line.substring(0, line.indexOf(" "));
				String b = line.substring(line.indexOf(" "));
				f = f.trim(); b = b.trim();

				if (f.endsWith(".")) 
					f = f.substring(0, f.length()-1);
				//String[] split = f.split("-");

				String secNum = f.substring(f.indexOf("-")+1);
				String secBase = secNum.substring(0, secNum.indexOf("-"));
				String num = secNum.substring(secNum.indexOf("-")+1);
				section.setId(Long.parseLong(num));
				section.setActive(false);
				section.setTitle(sectionTitle);
				section.setName(b);
				section.setSectionBase(Integer.parseInt(secBase));
				section.setNumber(Integer.parseInt(num));
				section.setVersionDate(new java.util.Date());

			} else {
				// subsection
				RuleSubSection subSection = new RuleSubSection();
				subSection.setCreatedBy("SYSTEM");
				subSection.setVersionDate(new java.util.Date());
				
				String subSecNum = line.substring(0, line.indexOf(" "));
				String text = line.substring(line.indexOf(" ")+1);

				if (subSecNum.indexOf(".") > -1) {
					String[] s = subSecNum.split("\\.");
					if (s.length > 1) {
						subSecNum = subSecNum.substring(0, subSecNum.indexOf(".")+1);
						text = subSecNum.substring(subSecNum.indexOf(".")+1) + text; 
					}
				} else if (subSecNum.indexOf(")") > -1) {
					String[] s = subSecNum.split("\\)");
					if (s.length > 1) {
						subSecNum = subSecNum.substring(0, subSecNum.indexOf(")")+1);
						text = subSecNum.substring(subSecNum.indexOf(")")+1) + text; 
					}
				}

				subSection.setNumber(subSecNum);
				subSection.setSortOrder(new Double(i));
				subSection.setRuleContent(text);
				subSection.setCategory(RuleCategory.PENDING);
				subSection.setSection(section);
				subSection.setCreatedBy("SYSTEM");
				subSection.setVersionDate(new java.util.Date());

				section.addSubSection(subSection);
			}
		}
		
		return section;
	}
	
	int cnt = 0;
	public boolean matchFirst(String p, String s){
		s = s.trim();
		if (s.toLowerCase().startsWith("table")) {
			return false;
		}
		cnt++;
		if (s.indexOf(")(") > -1) {
			s = s.replaceAll("\\)\\(", ") (");
		}
		if (s.indexOf(" ") > -1) {
			String[] s2 = s.split(" ");
			if (s2.length > 1)
				s = s2[0].trim();
		}
		boolean r = (Pattern.compile(p)).matcher(s).find();
		if (r && (s.indexOf(".") == -1 && s.indexOf(")") == -1 && s.indexOf("]") == -1)) {
			r = false;
		}
		
		return r;
	}

	public String getRulePrefix() {
		return rulePrefix;
	}

	public void setRulePrefix(String rulePrefix) {
		this.rulePrefix = rulePrefix;
	}

	public String getFileName() {
		return fileName;
	}


	public int getLineCount() {
		return lineCount;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("fileName=" + fileName + "\n");
		sb.append("rulePrefix=" + rulePrefix + "\n");
		sb.append("lineCount=" + lineCount + "\n");
		sb.append("heading=" + heading + "\n");
		sb.append("section=" + section + "\n");

		sb.append("rules["+rules.size()+"]...\n");
		for (Iterator<RuleSection> iterator = rules.iterator(); iterator.hasNext();) {
			sb.append("---\n");
			RuleSection rule = iterator.next();
			if (rule.getSubSections() != null) {
				for (Iterator<RuleSubSection> iterator2 = rule.getSubSections().iterator(); iterator2.hasNext();) {
					RuleSubSection sub = iterator2.next();
					sb.append(" > "+sub+"\n");
				}
			}
		}
		
		return sb.toString();
	}
	
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public List<RuleSection> getRules() {
		return this.rules;
	}
}
