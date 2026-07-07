import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;
import javax.mail.util.ByteArrayDataSource;
import org.apache.camel.impl.DefaultAttachment;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def dataSource = new ByteArrayDataSource(body, 'Text/CSV'); //Set MIME type
	def attachment = new DefaultAttachment(dataSource);
    message.addAttachmentObject("Vendor Master Data Processing Log.txt", attachment);  

	


	return message;
}