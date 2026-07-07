import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);

	def props = message.getProperties();
	
	String CompanyCode = props.get("VendorGroupCompanyCode") as String
	
	String AddVendorGroupStr ='{"vendorGroup":[{"Name":"' + CompanyCode + '"}]}'
	
	message.setBody(AddVendorGroupStr);
	return message;
}