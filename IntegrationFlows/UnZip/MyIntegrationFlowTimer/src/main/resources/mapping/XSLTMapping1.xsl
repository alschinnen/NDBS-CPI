<?xml version="1.0"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
        <xsl:for-each select="//d:entry">
		    <SalesOrder>
			    <item>
				    <SalesOrderID>
					    <xsl:value-of select="//entry/content/m:properties/d:SalesOrderID" />
				    </SalesOrderID>
				    <ItemPosition>
					    <xsl:value-of select="//entry/content/m:properties/d:ItemPosition" />
				    </ItemPosition>
			    </item>
	        </SalesOrder>
	    </xsl:for-each> 
	</xsl:template>		
</xsl:stylesheet>