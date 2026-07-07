import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	
	message.setProperty("ConcurToken", body);

	return message;
	
	
}