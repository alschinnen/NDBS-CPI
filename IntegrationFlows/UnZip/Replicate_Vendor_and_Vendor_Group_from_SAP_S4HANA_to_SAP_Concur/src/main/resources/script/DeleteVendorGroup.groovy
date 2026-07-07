import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;

	def props = message.getProperties();
	
	def VendorGroups = new XmlSlurper().parseText(body);
	
	String DeleteVendorGroupQuery = '&groupName=' + VendorGroups.VendorGroup.Name
	
	message.setProperty("DeleteVendorGroupQuery",DeleteVendorGroupQuery);

	return message;
}