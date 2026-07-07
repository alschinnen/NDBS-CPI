import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.msglog.MessageLogFactory;
import groovy.util.XmlSlurper;

def Message processData(Message message) {
	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	int count = 0;
    def CompanyCodeList = ''
	def map = message.getProperties();
	String InitialLoad = map.get("InitialLoad") as String
	def BPList = new XmlSlurper().parseText(body);
	def VendorGroup = ''
	def CompanyCode = ''
	def SupplierIsBlockedForPosting = ''
	String VendorCompanyCodeList = map.get("VendorCompanyCodeList") as String
	def VendorList = new XmlSlurper().parseText(VendorCompanyCodeList);


//procssing

	String BusinessPartner = BPList.A_BusinessPartnerType.BusinessPartner

	String Supplier = BPList.A_BusinessPartnerType.Supplier


// vendor group logic	
	
	VendorList.Vendor.each{
		Vendor ->
		if(Vendor.Supplier == Supplier){
				CompanyCode = '<Name>' + Vendor.CompanyCode + '</Name>'
				
				BPList.A_BusinessPartnerType.to_Supplier.A_SupplierType.to_SupplierCompany.A_SupplierCompanyType.each{
				    entry ->
				        if(Vendor.CompanyCode == entry.CompanyCode){
				            if(entry.SupplierIsBlockedForPosting == 'false'){
				                SupplierIsBlockedForPosting = '<SupplierIsBlockedForPosting>false</SupplierIsBlockedForPosting>' 
				                count++
				            }
				            else{
				                SupplierIsBlockedForPosting = '<SupplierIsBlockedForPosting>true</SupplierIsBlockedForPosting>' 
				                count++
				            }
				        }
				}
				
				VendorGroup = VendorGroup + '<VendorGroup>' + CompanyCode + SupplierIsBlockedForPosting + '</VendorGroup>'

		}
		
	}
	
	
	
	VendorGroup = '<VendorGroups>' + VendorGroup + '</VendorGroups>'
	
	
	String VendorGroupQuery ="vendorCode=" + BusinessPartner + "&addressCode=" + BusinessPartner
	
	message.setProperty("VendorGroupQuery" , VendorGroupQuery)
	message.setProperty("VendorGroup" , VendorGroup)
	message.setProperty("BusinessPartnerNumber" , BusinessPartner)
	message.setProperty("CompanyCodeList" , CompanyCodeList)

// if initial load, and there is only one vendor with one company code and marked as delete	
	
	if(count == 1&&SupplierIsBlockedForPosting == '<SupplierIsBlockedForPosting>true</SupplierIsBlockedForPosting>'&&InitialLoad == "true"){
	    	message.setProperty("InitialLoadDeleteMark" , "true")
	}
	else{
	        message.setProperty("InitialLoadDeleteMark" , "false")
	}

	

	return message;
}