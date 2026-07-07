
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body
       def xml = message.getBody(String.class);
      def completeXml = new XmlSlurper().parseText(xml);
     
      def BPAddressIDs = completeXml.A_BusinessPartnerType.to_BusinessPartnerAddress.A_BusinessPartnerAddressType.'**'.findAll{node->node.name() == 'AddressIDByExternalSystem'}*.text();
     String list = String.join(",",BPAddressIDs);
      //String list = listString.replaceAll('ADDRESS_REFERENCE-','');
     message.setProperty("BPAddressIDs",list)
       
       return message;
}