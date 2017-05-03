/**
 * $Rev: 7 $:
 * $LastChangedDate: 2009-02-18 12:32:20 -0700 (Wed, 18 Feb 2009) $:
 * $Author: danolsen $:
 */
package gov.utah.dts.det.ccl.struts2.interceptor;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletRedirectResult;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * An Interceptor to preserve an actions ValidationAware messages across a
 * redirect result.
 *
 * It makes the assumption that you always want to preserve messages across a
 * redirect and restore them to the next action if they exist.
 *
 * The way this works is it looks at the result type after a action has executed
 * and if the result was a redirect (ServletRedirectResult) or a redirectAction
 * (ServletActionRedirectResult) and there were any errors, messages, or
 * fieldErrors they are stored in the session. Before the next action executes
 * it will check if there are any messages stored in the session and add them to
 * the next action.
 * 
 * 
 * ** NOTE THIS WAS TAKEN FROM WEB...USED TO PERSIT MESSAGES ACROSS REDIRECTS
 *
 */
public class RedirectMessageInterceptor extends MethodFilterInterceptor
{
    private static final long  serialVersionUID    = -1847557437429753540L;

    public static final String FIELD_ERRORS_KEY    = "RedirectMessageInterceptor_FieldErrors";
    public static final String ACTION_ERRORS_KEY   = "RedirectMessageInterceptor_ActionErrors";
    public static final String ACTION_MESSAGES_KEY = "RedirectMessageInterceptor_ActionMessages";

    /**
     * Dummy constructor
     */
    public RedirectMessageInterceptor(){
    	
    }

    /**
     * main intercept function...in charge of persisting messages across redirects
     */
    public String doIntercept(ActionInvocation invocation) throws Exception{
    	
    	// grab the action
        Object action = invocation.getAction();
        
        // if it is a validation aware...we want to intercept and run
        // the before on it
        if (action instanceof ValidationAware){
            before(invocation, (ValidationAware) action);
        }

        //
        // result of the invocation
        //
        String result = invocation.invoke();

        //
        // if it is a validation aware, we intercept the response
        //
        if (action instanceof ValidationAware)
        {
            after(invocation, (ValidationAware) action);
        }
        
        //
        // return the result
        //
        return result;
    }

    /**
     * Retrieve the errors and messages from the session and add them to the
     * action.
     */
    @SuppressWarnings("unchecked")
    protected void before(ActionInvocation invocation,
                          ValidationAware validationAware) throws Exception{
        Map session = invocation.getInvocationContext().getSession();

        Collection actionErrors = (Collection) session
            .remove(ACTION_ERRORS_KEY);
        Collection actionMessages = (Collection) session
            .remove(ACTION_MESSAGES_KEY);
        Map fieldErrors = (Map) session
            .remove(FIELD_ERRORS_KEY);

        // save the errors
        if (actionErrors != null && actionErrors.size() > 0){
            for (Object e : actionErrors)
            {
            	String error = (String) e;
                validationAware.addActionError(error);
            }
        }

        //
        // save the messages
        //
        if (actionMessages != null && actionMessages.size() > 0){
            for (Object m : actionMessages){
            	String message = (String) m;
                validationAware.addActionMessage(message);
            }
        }

        // field errors
        // save those too
        //
        if (fieldErrors != null && fieldErrors.size() > 0)
        {
            for (Object me : fieldErrors.entrySet())
            {
            	Map.Entry entry = (Entry) me;
                validationAware.addFieldError(
                		(String)entry.getKey(), (String)entry.getValue());
            }
        }

    }

    /**
     * If the result is a redirect then store error and messages in the session.
     * 
     */
    @SuppressWarnings("unchecked")
    protected void after(ActionInvocation invocation,
                         ValidationAware validationAware) throws Exception{
    	
    	// grab the result
        Result result = invocation.getResult();

        // if it is a redirect then make sure we add it to the action
        if (result instanceof ServletRedirectResult
            || result instanceof ServletActionRedirectResult)
        {
            ActionContext actionContext = invocation.getInvocationContext();
            HttpServletRequest request = (HttpServletRequest) actionContext
                .get(StrutsStatics.HTTP_REQUEST);

            /*
             * If the session doesn't already exist then it's too late to create
             * one as the response has already been committed. This is really
             * only to handle the 'unusual' case of a browser refresh after the
             * session has expired. In this case the messages are lost.
             */
            HttpSession session = request.getSession(false);
            if (session != null){
            	
                Collection actionErrors = validationAware.getActionErrors();
                if (actionErrors != null && actionErrors.size() > 0){
                	
                    session.setAttribute(ACTION_ERRORS_KEY, actionErrors);
                }

                Collection actionMessages = validationAware.getActionMessages();
                if (actionMessages != null && actionMessages.size() > 0){
                	
                    session.setAttribute(ACTION_MESSAGES_KEY, actionMessages);
                }

                Map fieldErrors = validationAware.getFieldErrors();
                if (fieldErrors != null && fieldErrors.size() > 0){
                	
                    session.setAttribute(FIELD_ERRORS_KEY, fieldErrors);
                }
            }
        }
    }

}