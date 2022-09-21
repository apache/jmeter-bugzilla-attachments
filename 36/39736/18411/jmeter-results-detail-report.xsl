<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" indent="yes" encoding="US-ASCII" doctype-public="-//W3C//DTD HTML 4.01 Transitional//EN" />

<!-- Defined parameters (overrideable) -->
<xsl:param    name="showData" select="'N'"/>

<xsl:key name='threadid' match='/testResults/*' use='@tn' />
<xsl:variable name="threadCount" select="count(/testResults/*/@tn[generate-id()=generate-id(key('threadid',.)/@tn[1])])" />

<xsl:template match="testResults">
	<html>
		<head>
			<title>Load Test Results</title>
			<style type="text/css">
				body {
					font:normal 68% verdana,arial,helvetica;
					color:#000000;
				}
				table tr td, table tr th {
					font-size: 68%;
				}
				table.details tr th{
					font-weight: bold;
					text-align:left;
					background:#a6caf0;
					white-space: nowrap;
				}
				table.details tr td{
					background:#eeeee0;
					white-space: nowrap;
				}
				h1 {
					margin: 0px 0px 5px; font: 165% verdana,arial,helvetica
				}
				h2 {
					margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica
				}
				h3 {
					margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica
				}
				.Failure {
					font-weight:bold; color:red;
				}
				
	
				img
				{
				  border-width: 0px;
				}
				
				.expand_link
				{
				   position=absolute;
				   right: 0px;
				   width: 27px;
				   top: 1px;
				   height: 27px;
				}
				
				.page_details
				{
				   display: none;
				}
                                
                                .page_details_expanded
                                {
                                    display: block;
                                    display/* hide this definition from  IE5/6 */: table-row;
                                }


			</style>
			<script language="JavaScript"><![CDATA[
                           function expand(details_id)
			   {
			      
			      document.getElementById(details_id).className = "page_details_expanded";
			   }
			   
			   function collapse(details_id)
			   {
			      
			      document.getElementById(details_id).className = "page_details";
			   }
			   
			   function change(details_id)
			   {
			      if(document.getElementById(details_id+"_image").src.match("expand"))
			      {
			         document.getElementById(details_id+"_image").src = "collapse.jpg";
			         expand(details_id);
			      }
			      else
			      {
			         document.getElementById(details_id+"_image").src = "expand.jpg";
			         collapse(details_id);
			      } 
                           }
			]]></script>
		</head>
		<body>
		
			<xsl:call-template name="pageHeader" />
			
			<xsl:call-template name="summary" />
			<hr size="1" width="95%" align="left" />
			
			<xsl:call-template name="pagelist" />
			<hr size="1" width="95%" align="left" />
			
			<xsl:call-template name="detail" />

		</body>
	</html>
</xsl:template>

<xsl:template name="pageHeader">
	<h1>Load Test Results</h1>
	<table width="100%">
		<tr>
			<td align="left"></td>
			<td align="right">Designed for use with <a href="http://jakarta.apache.org/jmeter">JMeter</a> and <a href="http://ant.apache.org">Ant</a>.</td>
		</tr>
	</table>
	<hr size="1" />
</xsl:template>

<xsl:template name="summary">
	<h2>Summary</h2>
	<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
		<tr valign="top">
			<th>Tests</th>
			<th>Failures</th>
			<th>Success Rate</th>
			<th>Average Time</th>
			<th>Throughput</th>
			<th>Min Time</th>
			<th>Max Time</th>
		</tr>
		<tr valign="top">
			<xsl:variable name="startTime" select="(/testResults/*)[1]/@ts" />
			<xsl:variable name="finalTime" select="(/testResults/*)[position()=last()]/@ts + (/testResults/*)[position()=last()]/@t" />
			<xsl:variable name="allCount" select="count(/testResults/*)" />
			<xsl:variable name="allFailureCount" select="count(/testResults/*[attribute::s='false'])" />
			<xsl:variable name="allSuccessCount" select="count(/testResults/*[attribute::s='true'])" />
			<xsl:variable name="allSuccessPercent" select="$allSuccessCount div $allCount" />
			<xsl:variable name="allTotalTime" select="sum(/testResults/*/@t)" />
      <xsl:variable name="allAverageTime" select="$allTotalTime div $allCount" />
			<xsl:variable name="allThroughput" select="1000 * $allCount div ($finalTime - $startTime)" />
			<xsl:variable name="allMinTime">
				<xsl:call-template name="min">
					<xsl:with-param name="nodes" select="/testResults/*/@t" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="allMaxTime">
				<xsl:call-template name="max">
					<xsl:with-param name="nodes" select="/testResults/*/@t" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:attribute name="class">
        <xsl:choose>
					<xsl:when test="$allFailureCount &gt; 0">Failure</xsl:when>
				</xsl:choose>
			</xsl:attribute>
			<td>
				<xsl:value-of select="$allCount" />
			</td>
			<td>
				<xsl:value-of select="$allFailureCount" />
			</td>
			<td>
				<xsl:call-template name="display-percent">
					<xsl:with-param name="value" select="$allSuccessPercent" />
				</xsl:call-template>
			</td>
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="$allAverageTime" />
				</xsl:call-template>
			</td>
			<td>
				<xsl:call-template name="display-throughput">
					<xsl:with-param name="value" select="$allThroughput" />
				</xsl:call-template>
			</td>
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="$allMinTime" />
				</xsl:call-template>
			</td>
			<td>
				<xsl:call-template name="display-time">
					<xsl:with-param name="value" select="$allMaxTime" />
				</xsl:call-template>
			</td>
		</tr>
	</table>
</xsl:template>

<xsl:template name="pagelist">
	<h2>Pages</h2>
	<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
		<tr valign="top">
			<th>URL</th>
			<th>Tests</th>
			<th>Failures</th>
			<th>Success Rate</th>
			<th>Average Time</th>
			<th>Throughput</th>
			<th>Min Time</th>
			<th>Max Time</th>
			<th></th>
		</tr>
		<xsl:for-each select="/testResults/*[not(@lb = preceding::*/@lb)]">
			<xsl:variable name="start" select="(../*[@lb = current()/@lb])[1]/@ts" />
			<xsl:variable name="final" select="(../*[@lb = current()/@lb])[position()=last()]/@ts + (../*[@lb = current()/@lb])[position()=last()]/@t" />
			<xsl:variable name="lb" select="@lb" />
			<xsl:variable name="count" select="count(../*[@lb = current()/@lb])" />
			<xsl:variable name="failureCount" select="count(../*[@lb = current()/@lb][attribute::s='false'])" />
			<xsl:variable name="successCount" select="count(../*[@lb = current()/@lb][attribute::s='true'])" />
			<xsl:variable name="successPercent" select="$successCount div $count" />
			<xsl:variable name="totalTime" select="sum(../*[@lb = current()/@lb]/@t)" />
			<xsl:variable name="averageTime" select="$totalTime div $count" />
			<xsl:variable name="throughput" select="1000 * $count div ($final - $start)" />
			<xsl:variable name="minTime">
				<xsl:call-template name="min">
					<xsl:with-param name="nodes" select="../*[@lb = current()/@lb]/@t" />
				</xsl:call-template>
			</xsl:variable>
			<xsl:variable name="maxTime">
				<xsl:call-template name="max">
					<xsl:with-param name="nodes" select="../*[@lb = current()/@lb]/@t" />
				</xsl:call-template>
			</xsl:variable>
			<tr valign="top">
				<xsl:attribute name="class">
					<xsl:choose>
						<xsl:when test="$failureCount &gt; 0">Failure</xsl:when>
					</xsl:choose>
				</xsl:attribute>
				<td>
				<xsl:if test="$failureCount > 0">
				  <a><xsl:attribute name="href">#<xsl:value-of select="$lb" /></xsl:attribute>
				  <xsl:value-of select="$lb" />
				  </a>
				</xsl:if>
				<xsl:if test="0 >= $failureCount">
				  <xsl:value-of select="$lb" />
				</xsl:if>
				</td>
				<td>
					<xsl:value-of select="$count" />
				</td>
				<td>
					<xsl:value-of select="$failureCount" />
				</td>
				<td>
					<xsl:call-template name="display-percent">
						<xsl:with-param name="value" select="$successPercent" />
					</xsl:call-template>
				</td>
				<td>
					<xsl:call-template name="display-time">
						<xsl:with-param name="value" select="$averageTime" />
					</xsl:call-template>
				</td>
				<td>
				<xsl:call-template name="display-throughput">
					<xsl:with-param name="value" select="$throughput" />
				</xsl:call-template>
				</td>
				<td>
					<xsl:call-template name="display-time">
						<xsl:with-param name="value" select="$minTime" />
					</xsl:call-template>
				</td>
				<td>
					<xsl:call-template name="display-time">
						<xsl:with-param name="value" select="$maxTime" />
					</xsl:call-template>
				</td>
				<td align="center">
				   <a href="">
				      <xsl:attribute name="href"><xsl:text/>javascript:change('page_details_<xsl:value-of select="position()" />')</xsl:attribute>
				      <img src="expand.jpg" alt="expand/collapse"><xsl:attribute name="id"><xsl:text/>page_details_<xsl:value-of select="position()" />_image</xsl:attribute></img>				      
				   </a>
				</td>
			</tr>
			
                        <tr class="page_details">
                           <xsl:attribute name="id"><xsl:text/>page_details_<xsl:value-of select="position()" /></xsl:attribute>
                           <td colspan="8" bgcolor="#FF0000">
                              <div align="center">
			         <b>Details for Page "<xsl:value-of select="$lb" />"</b>
			         <table bordercolor="#000000" border="1"  cellpadding="0" cellspacing="0" width="95%">
			         <tr>
			            <th>Thread</th>
			            <th>Iteration</th>
			            <th>Time</th>
			            <th>Success</th>
			            <!--
			            itterations = position() - thread stuff
			            t="312"
			            ts="1053622047640"
			            rm="OK"
			            tn="Thread Group-1"
			            dt="text"
			            lb="/CansysAandA/processAuthentication.do"
			            s="true"
			            -->
			         </tr>
			         		         
			         <xsl:for-each select="../*[@lb = $lb and @tn!= $lb]">			         			            
			            <tr>
			               <td><xsl:value-of select="@tn" /></td>
			               <td><xsl:value-of select="position()" /></td>
			               <td><xsl:value-of select="@t" />ms</td>
			               <td><xsl:value-of select="@s" /></td>
			            </tr>
			         </xsl:for-each>
			         
			         </table>
			      </div>
                           </td>
                        </tr>
			
		</xsl:for-each>
	</table>
</xsl:template>

<xsl:template name="detail">
	<xsl:variable name="allFailureCount" select="count(/testResults/*[attribute::s='false'])" />

	<xsl:if test="$allFailureCount > 0">
		<h2>Failure Detail</h2>

		<xsl:for-each select="/testResults/*[not(@lb = preceding::*/@lb)]">

			<xsl:variable name="failureCount" select="count(../*[@lb = current()/@lb][attribute::s='false'])" />

			<xsl:if test="$failureCount > 0">
				<h3><xsl:value-of select="@lb" /><a><xsl:attribute name="name"><xsl:value-of select="@lb" /></xsl:attribute></a></h3>

				<table class="details" border="0" cellpadding="5" cellspacing="2" width="95%">
				<tr valign="top">
					<th>Response</th>
					<th>Failure Message</th>
					<xsl:if test="$showData = 'y'">
					   <th>Response Data</th>
					</xsl:if>
				</tr>
			
				<xsl:for-each select="/testResults/*[@lb = current()/@lb][attribute::s='false']">
					<tr>
						<td><xsl:value-of select="@rc" /> - <xsl:value-of select="@rm" /></td>
						<td><xsl:value-of select="assertionResult/failureMessage" /></td>
						<xsl:if test="$showData = 'y'">
							<td><xsl:value-of select="./binary" /></td>
						</xsl:if>
					</tr>
				</xsl:for-each>
				
				</table>
			</xsl:if>

		</xsl:for-each>
	</xsl:if>
</xsl:template>

<xsl:template name="max">
     <xsl:param name="nodes" select="/.."/>
     <xsl:param name="max"/>
  <xsl:variable name="count" select="count($nodes)"/>
  <xsl:variable name="aNode" select="$nodes[ceiling($count div 2)]"/>
  <xsl:choose>
    <xsl:when test="not($count)">
      <xsl:value-of select="number($max)"/>
    </xsl:when>
    <xsl:when test="number($aNode) != number($aNode)">
      <xsl:value-of select="number($aNode)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="max">
        <xsl:with-param name="nodes" select="$nodes[not(. &lt;= number($aNode))]"/>
        <xsl:with-param name="max">
          <xsl:choose>
            <xsl:when test="not($max) or $aNode > $max">
              <xsl:value-of select="$aNode"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="$max"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
   
<xsl:template name="min">
  <xsl:param name="nodes" select="/.."/>
  <xsl:param name="min"/>
  <xsl:variable name="count" select="count($nodes)"/>
  <xsl:variable name="aNode" select="$nodes[ceiling($count div 2)]"/>
  <xsl:choose>
    <xsl:when test="not($count)">
      <xsl:value-of select="number($min)"/>
    </xsl:when>
    <xsl:when test="number($aNode) != number($aNode)">
      <xsl:value-of select="number($aNode)"/>
    </xsl:when>
    <xsl:otherwise>
      <xsl:call-template name="min">
        <xsl:with-param name="nodes" select="$nodes[not(. >= number($aNode))]"/>
        <xsl:with-param name="min">
          <xsl:choose>
            <xsl:when test="not($min) or $aNode &lt; $min">
              <xsl:value-of select="$aNode"/>
            </xsl:when>
            <xsl:otherwise>
              <xsl:value-of select="$min"/>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:with-param>
      </xsl:call-template>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template name="display-throughput">
	<xsl:param name="value" />
	<xsl:value-of select="format-number($value,'0.0 requests/s')" />
</xsl:template>

<xsl:template name="display-percent">
	<xsl:param name="value" />
	<xsl:value-of select="format-number($value,'0.00%')" />
</xsl:template>

<xsl:template name="display-time">
	<xsl:param name="value" />
	<xsl:value-of select="format-number($value,'0 ms')" />
</xsl:template>
	
</xsl:stylesheet>
