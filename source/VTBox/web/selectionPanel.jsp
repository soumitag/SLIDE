<%-- 
    Document   : selectionPanel
    Created on : 18 Feb, 2017, 8:38:55 PM
    Author     : Soumita
--%>

<%@page import="vtbox.SessionUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="structure.AnalysisContainer"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="structure.Data"%>
<!DOCTYPE html>

<%
try {
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            td {
                font-family: verdana;
                font-size: 12px;
                background-color:#EEEEEE;
            }
        </style>
        <script>
            function chkisnumber(f){
                //alert(f.name);
                var x = document.getElementById(f.name).value;
                //alert(x);
                //alert(!isNaN(parseFloat(x)) && isFinite(x));
                //alert(isNaN(parseFloat(x)) || !isFinite(x));
                if(isNaN(parseFloat(x)) || !isFinite(x)){
                //alert(!isNaN(parseFloat(x)) && isFinite(x));
                    document.getElementById(f.name).value = "0";
                    alert("Please enter a numeric value");
                }
            }
            
            function enabletext(){
                document.getElementById("txtRangeStart").disabled = false;
                document.getElementById("txtRangeEnd").disabled = false;
            }
            
            function disabletext(){
                document.getElementById("txtRangeStart").disabled = true;
                document.getElementById("txtRangeEnd").disabled = true;
            }
        </script>
    </head>
    <%
        String analysis_name = request.getParameter("analysis_name");
        AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
        Data database = analysis.database;
        String data_min = String.format("%.3f", database.DATA_MIN_MAX[0]);
        String data_max = String.format("%.3f", database.DATA_MIN_MAX[1]);
    %>
    <body>
        <form name="SelectionForm" method="get" action="AnalysisReInitializer" target="visualizationPanel"> 
            <input type="hidden" name="analysis_name" value="<%=analysis_name%>" />
            <input type="hidden" name="do_clustering" value="false" />
            <table id="dataselectionTable" width="100%" border=0 align=center cellpadding="2px" cellspacing="2px"  style="padding-left: 5px; padding-right: 5px;">
            
            <%  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   %>
            
            
            <tr> <td colspan='4' align=center height="20"> <b>Data Selection</b> </td></tr>
            <!--
            <tr> 
                <td align="center" height="30px">
                    <input type="button" value="Open Data Selection Panel">
                </td>
            </tr>
            -->
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Replicate Handling: </label></b><br>
                    <input type="radio" name="repHandle" value="0" checked="checked"> Use Separately<br>
                    <input type="radio" name="repHandle" value="1"> Mean<br>
                    <input type="radio" name="repHandle" value="2"> Median
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;">
                    <b><label>Data Clipping: </label></b> &nbsp; 
                    <select id="clippingType" name="clippingType">
                        <option id="none" value="none" >None</option>
                        <option id="absv" value="absv" >Absolute Value</option>
                        <option id="ptile" value="ptile" >Percentile</option>
                    </select>
                    <br><br>
                    Min &nbsp; <input type="text" id="txtClipMin" name="txtClipMin" size="5" onchange="chkisnumber(this)" />
                    &nbsp; 
                    Max &nbsp; <input type="text" id="txtClipMax" name="txtClipMax" size="5" onchange="chkisnumber(this)" />
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;">
                    <% if (database.isDataLogTransformed) { %>
                        <input type="checkbox" id="log2flag" name="log2flag" checked="checked"> Perform log base 2 transformation</input>
                    <% } else { %>
                        <input type="checkbox" id="log2flag" name="log2flag"> Perform log base 2 transformation</input>
                    <% } %>
                </td>
            </tr>
            
            <% if (database.isTimeSeries) { %>
            <tr>
                <td colspan="4" style="padding: 10px;">
                    <b><label>Group By: </label></b> &nbsp;
                    <input type="radio" name="groupBy" value="sample" checked="checked"> Sample &nbsp;
                    <input type="radio" name="groupBy" value="time"> Time<br>
                </td>
            </tr>
            <% } %>
            
            <%  }   %>
            
            <%  if (analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION || 
                    analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {   %>
            
            <tr> <td colspan='4' align=center height="20"> <b>Enrichment Parameters</b> </td> </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label style="line-height: 20px">Significance Level: </label></b>
                    <input type="text" name="txtSignificanceLevel" value="0.05" maxlength="4" size="4" >
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label style="line-height: 25px"> Minimum Functional Group Feature List Intersection: </label></b>
                    <input type="text" name="txtSmall_k" value="0" maxlength="4" size="4" >
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label style="line-height: 25px; padding-bottom: 10px">Minimum Functional Group Size: </label></b>
                    <input type="text" name="txtBig_K" value="0" maxlength="4" size="4" >
                </td>
            </tr>

            <%  }   %>
            
            <tr> <td colspan='4' align=center height="20"> <b>Clustering Parameters</b> </td> </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;">
                    <input type="checkbox" id="do_cluster_flag" name="do_cluster_flag"> Perform Hierarchical Clustering</input>
                </td>
            </tr>
            
            <%  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   %>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Data Scaling for Clustering: </label></b><br>
                    <input type="radio" name="normRules_Clustering" value="0" checked="checked"> None <br>
                    <input type="radio" name="normRules_Clustering" value="1"> Normalize Rows <br>
                    <input type="radio" name="normRules_Clustering" value="2"> Normalize Columns <br>
                </td>
            </tr>
            
            <%  }   %>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Linkage Function: </label></b><br>
                    <input type="radio" name="linkFunc" value="single" checked="checked"> Single<br>
                    <input type="radio" name="linkFunc" value="complete"> Complete<br>
                    <input type="radio" name="linkFunc" value="average"> Average<br>
                    <input type="radio" name="linkFunc" value="median"> Median<br>
                    <input type="radio" name="linkFunc" value="centroid"> Centroid<br>
                    <input type="radio" name="linkFunc" value="ward"> Ward<br>
                    <input type="radio" name="linkFunc" value="weighted"> Weighted<br>                
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;">
                    <b><label>Distance Function: </label></b><br>
                    <input type="radio" name="distFunc" value="euclidean" checked="checked"> Euclidean<br>
                    <input type="radio" name="distFunc" value="cityblock"> Manhattan<br>
                    <input type="radio" name="distFunc" value="cosine"> Cosine<br>
                    <input type="radio" name="distFunc" value="correlation"> Correlation<br>
                    <input type="radio" name="distFunc" value="chebyshev"> Chebyshev<br>
                </td>
            </tr>
            
            <tr> <td colspan='4' align=center height="20"> <b>Visualization Controls</b> </td></tr>
            
            <%  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   %>
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Data Scaling for Visualization: </label></b><br>
                    <input type="radio" name="normRules_Heatmap" value="0" checked="checked"> None <br>
                    <input type="radio" name="normRules_Heatmap" value="1"> Scale Rows to 0-1 Range <br>
                    <input type="radio" name="normRules_Heatmap" value="2"> Mean Center Rows <br>
                    <input type="radio" name="normRules_Heatmap" value="3"> Standardize Rows (Standard Normal) <br>
                </td>
            </tr>
            <%  }   %>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Number of Color Bins: </label></b>
                    <input type="text" name="txtNBins" value="21" maxlength="4" size="4" >
                </td>
            </tr>
            
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Binning Range: </label></b><br>
                    <input type="radio" name="binningRange" value="data_bins" checked="checked" onclick="disabletext()"> Use Min/Max of Data<br>
                    <p style="line-height: 10px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    Min <input type="text" id="txtMin" name="txtMin" size="5" value="<%=data_min%>" disabled />
                    and Max <input type="text" id="txtMax" name="txtMax" size="5" value="<%=data_max%>" disabled />
                    </p>
                    <input type="radio" name="binningRange" value="symmetric_bins" onclick="disabletext()"> Use Symmetric Bins (about 0)<br>
                    <input type="radio" name="binningRange" value="start_end_bins" onclick="enabletext()"> Use Range <br>
                    <p style="line-height: 10px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    Start <input type="text" id="txtRangeStart" name="txtRangeStart" size="5" onchange="chkisnumber(this)" disabled />
                    and End <input type="text" id="txtRangeEnd" name="txtRangeEnd" size="5" onchange="chkisnumber(this)" disabled />
                    </p>
                </td>
            </tr>
            
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Leaf Ordering: </label></b><br>
                    <input type="radio" name="leafOrder" value="0" checked="checked"> Largest Child First<br>
                    <input type="radio" name="leafOrder" value="1"> Smallest Child First<br>
                    <input type="radio" name="leafOrder" value="2"> Most Diverse Child First<br>
                    <input type="radio" name="leafOrder" value="3"> Least Diverse Child First<br>
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Heatmap Color Scheme: </label></b><br>
                    <input type="radio" name="colorScheme" value="row" checked="checked"> Blue-White-Red <br>
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Dendrogram Depth: </label></b>
                    <input type="text" name="txtDendogramDepth" value="20" maxlength="4" size="4" >
                </td>
            </tr>
            
            <tr>
                <td colspan="4" align="center" style="padding: 10px;"> 
                    <input type="hidden" id="vizType" name="vizType" value="Selection">
                    <input type="submit" value="Refresh">
                </td>
            </tr>
            
        </table>
            
        </form>
    </body>
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>