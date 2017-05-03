import gov.utah.dts.det.ccl.model.EMessage;
import gov.utah.dts.det.util.MailDispatcher;

import javax.activation.FileDataSource;
import javax.activation.DataSource;

public class EmailTester {
	public static void main(String args[]) {
		EMessage message = new EMessage();
       	message.setSubject("Re: Rule R501-2-6.B.2 variance request");
       	message.setHeader("Utah Department of Human Services Office of Licensing");
       	message.setSalutation("Dear :Contact Name:,");
       	StringBuilder sb = new StringBuilder();
       	sb.append("The Department of Human Services, Office of Licensing has reviewed your request dated :DATE: ");
       	sb.append("requesting a varance of R501-2-6.B.2 for the continuation of the placement of a student beyond age ");
       	sb.append("17 and has APPROVED your request for a variance.<br/><br/>");
       	sb.append("A copy of the approval letter has been attached.");
       	message.setMessage(sb.toString());
       	//DataSource headerImage = new FileDataSource("C:\\_dev\\webapps\\suite\\src\\main\\webapp\\images\\itd-logo.png");
       	//message.setHeaderImage(headerImage);
       	message.setHeaderUrl("http://dhslic.dev.utah.gov/dhsLicensing");
       	message.setFooter(true);
       	message.addRecipient("papadutch64@gmail.com");
       	MailDispatcher.send(message);
	}

}
