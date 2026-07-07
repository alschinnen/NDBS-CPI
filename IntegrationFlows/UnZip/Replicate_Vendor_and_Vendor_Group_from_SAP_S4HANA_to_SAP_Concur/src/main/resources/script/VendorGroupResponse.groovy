import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;

	def props = message.getProperties();
	def VendorGroupResponse = ''
	
	String CompanyCode = props.get("VendorGroupCompanyCode") as String
	String BusinessPartner = props.get("BusinessPartnerNumber") as String
	
	
	
	
	def VendorGroupResponseXML = new XmlSlurper().parseText(body);
	
	if(VendorGroupResponseXML.VendorGroup.Status.Message.isEmpty()){
	    VendorGroupResponse = '<VendorGroups><VendorGroup>' + CompanyCode + '</VendorGroup>' + '<VendorGroupCreateStatus>' + BusinessPartner + ' is maintained successfully' + '</VendorGroupCreateStatus></VendorGroups>'
	}
	else{
	    VendorGroupResponse = '<VendorGroups><VendorGroup>' + CompanyCode + '</VendorGroup>' + '<VendorGroupCreateStatus>' + BusinessPartner + ':' + VendorGroupResponseXML.VendorGroup.Status.Message + '</VendorGroupCreateStatus></VendorGroups>'
	}

	message.setBody(VendorGroupResponse);
	return message;
}