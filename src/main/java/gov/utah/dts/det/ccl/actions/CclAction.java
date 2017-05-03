package gov.utah.dts.det.ccl.actions;

import com.opensymphony.xwork2.Action;

public interface CclAction extends Action {

	public static final String REDIRECT_LIST = "redirect-list";
	public static final String REDIRECT_NEW = "redirect-new";
	public static final String REDIRECT_EDIT = "redirect-edit";
	public static final String REDIRECT_VIEW = "redirect-view";
	public static final String UPLOAD_ATTACHMENT = "upload-attachment";
	public static final String DELETE_ATTACHMENT = "delete-attachment";
	
	public static final String LIST = "list";
	public static final String VIEW = "view";
}