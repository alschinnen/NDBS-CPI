import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;

	def props = message.getProperties();
	def VendorResponse = ''
	
	String AddVendorGroup = props.get("VendorGroup") as String
	String BusinessPartner = props.get("BusinessPartnerNumber") as String
	String VendorGroup = props.get("VendorGroup") as String
	

	
	
	
// identify the concur API response 	
	def VendorResponseXML = new XmlSlurper().parseText(body);
	
	if(VendorResponseXML.Vendor.Status.Message.isEmpty()){
	    VendorResponse = '<BPNumber>' + BusinessPartner + '</BPNumber>' + '<VendorCreateStatus>Successful</VendorCreateStatus>'
	}
	else{
	    VendorResponse = '<BPNumber>' + BusinessPartner + '</BPNumber>' + '<VendorCreateStatus>' + VendorResponseXML.Vendor.Status.Message + '</VendorCreateStatus>'
	    message.setProperty("VendorCreateStatus", "Fail")
	    
	}
	
	message.setProperty("VendorResponse", VendorResponse)
	message.setHeader("Content-Type", "application/json;charset=UTF-8")


// for later vendor group processing	
	message.setBody(VendorGroup);
	
	return message;
}