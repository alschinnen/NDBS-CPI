/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
    public void setProperty(java.lang.String name, java.lang.Object value)
    public java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> getSoapHeaders()
    public void setSoapHeaders(java.util.List<com.sap.gateway.ip.core.customdev.util.SoapHeader> soapHeaders) 
       public void clearSoapHeaders()
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
        //Body 
       def body = message.getBody();
     
       //Headers 
       def map = message.getProperties()
       String prop_InitialLoad = map.get("InitialLoad")
       String prop_DeltaRun = map.get("DeltaRun")
       String prop_CostCenter = map.get("CostCenters")
       String prop_CurrentDate = map.get("CurrentDateTime")
       String prop_ControllingArea = map.get("ControllingArea")
       String customQuery = ""
       
       if(prop_InitialLoad == 'TRUE')
       {
           customQuery = ""
       }
       else if(prop_DeltaRun == 'TRUE' && (prop_InitialLoad == 'FALSE' ||  prop_InitialLoad == '' ))
       {
           customQuery = "\$filter=CostCenterCreationDate ge datetime'" + prop_CurrentDate + "'"
       }
       else if((prop_InitialLoad == 'FALSE' ||  prop_InitialLoad == '' ) && (prop_DeltaRun == 'FALSE' ||  prop_DeltaRun == '' ) && (prop_ControllingArea != '' && prop_ControllingArea != null ))
       {
           ControllingArea_split = prop_ControllingArea.split(",");
           if (ControllingArea_split.size() == 0)
           {
               throw new Exception("Configuration Error: Please enter the Controlling Area in expected format");
           }
           else if (ControllingArea_split.size() > 0)
           {
               for (int j=0; j < ControllingArea_split.length; j++) {
    			if (j==0) 
    				customQuery = "\$filter=ControllingArea eq '" + ControllingArea_split[j].trim() + "'";
    			else
    				customQuery = customQuery + " or ControllingArea eq '" + ControllingArea_split[j].trim() + "'"; 	        		        	
		        }
           }
       }
       else if((prop_InitialLoad == 'FALSE' ||  prop_InitialLoad == '' ) && (prop_DeltaRun == 'FALSE' ||  prop_DeltaRun == '' ) && (prop_CostCenter != '' && prop_CostCenter != null ))
       {
           CostCenters_split = prop_CostCenter.split(",");
           if (CostCenters_split.size() == 0)
           {
               throw new Exception("Configuration Error: Please enter the Cost Center in expected format");
           }
           else if (CostCenters_split.size() > 0)
           {
               for (int j=0; j < CostCenters_split.length; j++) {
    			if (j==0) 
    				customQuery = "\$filter=CostCenter eq '" + CostCenters_split[j].trim() + "'";
    			else
    				customQuery = customQuery + " or CostCenter eq '" + CostCenters_split[j].trim() + "'"; 	        		        	
		        }
           }
       }
       else if ((prop_InitialLoad == null || prop_InitialLoad == "") && (prop_DeltaRun == null || prop_DeltaRun == "") && (prop_CostCenter == null || prop_CostCenter == ""))
       {
           throw new Exception("Configuration Error: Please check the configuration");
       }
       else
       {
           throw new Exception("Configuration Error: Please check the configuration");
       }
    
    message.setProperty("CustomQuery", customQuery);
      
       return message;
}