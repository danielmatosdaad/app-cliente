<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tns="http://www.projeto.com.br/metadado/bean"
	xmlns:projeto="http://url.projeto/componentes" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://xmlns.jcp.org/jsf/core" xmlns:h="http://xmlns.jcp.org/jsf/html"
	xmlns:ui="http://xmlns.jcp.org/jsf/facelets" xmlns:cc="http://xmlns.jcp.org/jsf/composite">

	<xsl:output method="xml" />

	<xsl:template match="*">
		<componente xmlns="" nomeComponente="{name()}">
			<xsl:for-each select="@*">
				<propriedade>
					<nome>
						<xsl:value-of select="name()" />
					</nome>
					<valor>
						<xsl:value-of select="." />
					</valor>
				</propriedade>
			</xsl:for-each>
			<xsl:apply-templates select="*|text()" />
		</componente>
	</xsl:template>
</xsl:stylesheet>