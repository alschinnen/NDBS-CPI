<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
	    <entry xmlns:m="http://schemas.microsoft.com/ado/2007/08/dataservices/metadata" xmlns:d="http://schemas.microsoft.com/ado/2007/08/dataservices">
	    <xsl:for-each select="entry/content/m:properties">
	        <CustomerID>
                <xsl:value-of select="d:CustomerID"/> 
	        </CustomerID>     
        	<CustomerName>
	        	<xsl:value-of select="d:CustomerName"/>
	        </CustomerName>
	    </xsl:for-each>
	    </entry>
    </xsl:template>		
</xsl:stylesheet>