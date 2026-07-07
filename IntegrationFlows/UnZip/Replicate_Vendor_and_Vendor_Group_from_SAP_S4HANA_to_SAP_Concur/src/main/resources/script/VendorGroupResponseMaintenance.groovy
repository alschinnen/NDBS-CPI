import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;

	def props = message.getProperties();
	def VendorGroupResponse = ''
	
	String VendorResponse = props.get("VendorResponse") as String
	String BusinessPartner = props.get("BusinessPartnerNumber") as String
    String CompanyCodeString = props.get("CompanyCodeList") as String
    def CompanyCodeList = new ArrayList();

    CompanyCodeList = CompanyCodeString.tokenize(',')
    
	
	def VendorGroupResponseXML = new XmlSlurper().parseText(body);
	def i = 0
	
	
	if(VendorGroupResponseXML.VendorGroup.isEmpty()){
	    VendorGroupResponse = '<VendorGroups><VendorGroup>' + CompanyCodeList.get(i) + '</VendorGroup>' + '<VendorGroupCreateStatus>' + BusinessPartner + ' is maintained successfully' + '</VendorGroupCreateStatus></VendorGroups>'
	}
	else{
	    VendorGroupResponseXML.VendorGroup.each{
	    VendorGroup ->
	    
	    if(VendorGroup.Status.Message.isEmpty()){
	    VendorGroupResponse = VendorGroupResponse + '<VendorGroups><VendorGroup>' + CompanyCodeList.get(i) + '</VendorGroup>' + '<VendorGroupCreateStatus>' + BusinessPartner + ' is maintained successfully' + '</VendorGroupCreateStatus></VendorGroups>'
	        
	    }
	    else{
	    VendorGroupResponse = VendorGroupResponse + '<VendorGroups><VendorGroup>' + CompanyCodeList.get(i) + '</VendorGroup>' + '<VendorGroupCreateStatus>' + BusinessPartner + ':' + VendorGroup.Status.Message + '</VendorGroupCreateStatus></VendorGroups>'
	    }
	    i++
	}
	}
	
	
	
    String payload = VendorResponse + VendorGroupResponse
	
	
	message.setBody(payload);
	return message;
}