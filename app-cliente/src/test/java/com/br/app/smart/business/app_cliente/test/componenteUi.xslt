<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tns="http://www.projeto.com.br/metadado/bean"
	xmlns:projeto="http://url.projeto/componentes" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://xmlns.jcp.org/jsf/core" xmlns:h="http://xmlns.jcp.org/jsf/html"
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets" xmlns:cc="http://xmlns.jcp.org/jsf/composite">
	<xsl:template match="*/ui:composition/*">
	
		<xsl:element name="metadado" namespace="">
			<xsl:element name="conteudo" namespace="">
				<xsl:for-each select="@*">
					<xsl:element name="componente"
						namespace="">
						<xsl:attribute name="nomeComponente">
    				  <xsl:value-of select="name(..)" />
    				   </xsl:attribute>
						<xsl:element name="propriedade" namespace="">
							<xsl:attribute name="nome">
    				  <xsl:value-of select="name()" />
    				   </xsl:attribute>
							<xsl:attribute name="valor">
    				  <xsl:value-of select="." />
    				   </xsl:attribute>
						</xsl:element>
					</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>