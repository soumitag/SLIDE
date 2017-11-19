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
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    Data database = analysis.database;
    String data_min = String.format("%.3f", database.DATA_MIN_MAX[0]);
    String data_max = String.format("%.3f", database.DATA_MIN_MAX[1]);
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type = "text/javascript" language = "JavaScript" src="params.js"></script>
        <link rel="stylesheet" href="vtbox-main.css">
        <style>
            td {
                font-family: verdana;
                font-size: 12px;
                background-color:#EEEEEE;
            }
        </style>
        <script>
            function chkisnumber(f){
                
                var x = document.getElementById(f.name).value;
                
                if(isNaN(parseFloat(x)) || !isFinite(x)){                
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
            
            function checkDataMin() {
                var curr = document.getElementById("log2flag").checked;
                if (curr) {
                    var data_min = <%=database.RAW_DATA_MIN%>;
                    if (data_min < 0.0) {
                        alert("Data has negative values. Log base 2 transformation cannot be applied.");
                        document.getElementById("log2flag").checked = false;
                    } else if (data_min === 0.0) {
                        alert("Data has zero values. The minimum non-zero value in the dataset, will be added to all cells. If you do not wish to do this, uncheck the log base 2 transformation option.");
                    }
                }
            }
            
            function validateParamsAndSubmit() {
                var readyToSubmit = true;
                var clip = document.getElementById("clippingType").selectedIndex;
                if (clip !== 0) {
                    var clip_min = parseInt(document.getElementById("txtClipMin").value);
                    var clip_max = parseInt(document.getElementById("txtClipMax").value);
                    //alert(clip_min);
                    //alert(clip_max);
                    if (clip_max <= clip_min) {
                        alert("Maximum clipping value cannot be less than or equal to minimum clipping value.");
                        readyToSubmit = false;
                    }
                    if (clip === 2) {
                        if(clip_min < 1 || clip_min > 99) {
                            alert("Minimum clipping percentile cannot be less than 1 or greater than 99.");
                            readyToSubmit = false;
                        }
                        if(clip_max < 1 || clip_max > 99) {
                            alert("Maximum clipping percentile cannot be less than 1 or greater than 99.");
                            readyToSubmit = false;
                        }
                    }
                }
                
                if (readyToSubmit) {
                    document.getElementById('SelectionForm').submit();
                }
            }
            
        </script>
    </head>
    
    <body>
        <form name="SelectionForm" id="SelectionForm" method="get" action="AnalysisReInitializer" target="visualizationPanel"> 
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
                    <input type="checkbox" id="log2flag" name="log2flag" onclick="checkDataMin()"> Perform log base 2 transformation</input>
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Column Scaling: </label></b><br>
                    <input type="radio" name="normRule_Col" value="0" checked="checked"> None <br>
                    <input type="radio" name="normRule_Col" value="1"> Scale Columns to 0-1 <br>
                    <input type="radio" name="normRule_Col" value="2"> Make Columns Standard Normal <br>
                    <input type="radio" name="normRule_Col" value="3"> Modified Pareto Scaling  <br>
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Row Scaling: </label></b><br>
                    <input type="radio" name="normRule_Row" value="0" checked="checked"> None <br>
                    <input type="radio" name="normRule_Row" value="1"> Scale Rows to 0-1 <br>
                    <input type="radio" name="normRule_Row" value="2"> Mean Center Rows <br>
                    <input type="radio" name="normRule_Row" value="3"> Make Rows Standard Normal <br>
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
                    <input type="text" id="txtSignificanceLevel" name="txtSignificanceLevel" value="0.05" maxlength="5" size="5" >
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label style="line-height: 25px"> Minimum Functional Group Feature List Intersection: </label></b>
                    <input type="text" id="txtSmall_k" name="txtSmall_k" value="0" maxlength="4" size="4" >
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label style="line-height: 25px; padding-bottom: 10px">Minimum Functional Group Size: </label></b>
                    <input type="text" id="txtBig_K" name="txtBig_K" value="0" maxlength="4" size="4" >
                </td>
            </tr>

            <%  }   %>
            
            <tr> <td colspan='4' align=center height="20"> <b>Clustering Parameters</b> </td> </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;">
                    <input type="checkbox" id="do_cluster_flag" name="do_cluster_flag"> Perform Hierarchical Clustering</input>
                </td>
            </tr>
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Linkage Function: </label></b><br>
                    <!--<input type="radio" name="linkFunc" value="single" checked="checked"> Single<br>-->
                    <input type="radio" name="linkFunc" value="average" checked="checked"> Average<br>
                    <input type="radio" name="linkFunc" value="complete"> Complete<br>
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
            
            
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Number of Color Bins: </label></b>
                    <%  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   %>
                    <input type="text" id="txtNBins" name="txtNBins" value="21" maxlength="4" size="4" >
                    <% } else { %>
                    <input type="text" id="txtNBins" name="txtNBins" value="51" maxlength="4" size="4" >
                    <% } %>
                </td>
            </tr>
            
            
            <%  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   %>
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
            <% } else { %>
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Binning Range: </label></b><br>
                    <input type="radio" name="binningRange" value="symmetric_bins" checked> Use Symmetric Bins (about 0)<br>
                </td>
            </tr>
            <% } %>
            
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
            <!--
            <tr>
                <td colspan="4" style="padding: 10px;"> 
                    <b><label>Dendrogram Depth: </label></b>
                    <input type="text" name="txtDendogramDepth" value="20" maxlength="4" size="4" >
                </td>
            </tr>
            -->
            
            <tr>
                <td colspan="4" align="center" style="padding: 10px;"> 
                    <input type="hidden" id="vizType" name="vizType" value="Selection">
                    <% if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  { %>
                    <button type="button" onclick="validateParamsAndSubmit()">Refresh</button>
                    <% } else { %>
                    <button type="submit">Refresh</button>
                    <% } %>
                </td>
            </tr>
            
        </table>
            
        </form>
    </body>
    
    
    <% 
        String load_type = request.getParameter("load_type");
        if (load_type != null) {
            if (load_type.equalsIgnoreCase("reopen") || load_type.equalsIgnoreCase("file")) {
                if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {
    %>
    
        <script>
            
            replicateHandling(<%=analysis.data_transformation_params.replicate_handling%>);
            checkDataClipMinMax(<%=analysis.data_transformation_params.getClippingType()%>, <%=analysis.data_transformation_params.clip_min%>, <%=analysis.data_transformation_params.clip_max%>);
            checkLogTransform(<%=analysis.data_transformation_params.log_transform%>);
            columnScaling(<%=analysis.data_transformation_params.column_normalization%>);
            rowScaling(<%=analysis.data_transformation_params.row_normalization%>);
            groupBy(<%=analysis.data_transformation_params.getGroupByIndex()%>);
            checkHierarchical(<%=analysis.clustering_params.do_clustering%>);
            linkageFunc(<%=analysis.clustering_params.getLinkageIndex()%>);
            distFunc(<%=analysis.clustering_params.getDistanceFuncIndex()%>);
            colorBins(<%=analysis.visualization_params.nBins%>);
            binRange(<%=analysis.visualization_params.getBinRangeTypeIndex()%>);
            binRangeStartEnd(<%=analysis.visualization_params.getBinRangeTypeIndex()%>, <%=analysis.visualization_params.bin_range_start%>, <%=analysis.visualization_params.bin_range_end%>);
            leafOrder(<%=analysis.visualization_params.getLeafOrderingStrategyIndex()%>);
            
        </script>
    
    <%
                } else {
    %>
        <script>
            
            setSignificanceLevel(<%=analysis.enrichment_params.significance_level%>);
            set_small_k(<%=analysis.enrichment_params.small_k%>);
            set_Big_K(<%=analysis.enrichment_params.big_K%>);
            checkHierarchical(<%=analysis.clustering_params.do_clustering%>);
            linkageFunc(<%=analysis.clustering_params.getLinkageIndex()%>);
            distFunc(<%=analysis.clustering_params.getDistanceFuncIndex()%>);
            colorBins(<%=analysis.visualization_params.nBins%>);
            leafOrder(<%=analysis.visualization_params.getLeafOrderingStrategyIndex()%>);
            
        </script>
    <%
                }
            } else if (load_type.equalsIgnoreCase("sub_analysis")) {
                if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {
    %>
        <script>
            
            replicateHandling(<%=analysis.data_transformation_params.replicate_handling%>);
            groupBy(<%=analysis.data_transformation_params.getGroupByIndex()%>);
            colorBins(<%=analysis.visualization_params.nBins%>);
            binRange(<%=analysis.visualization_params.getBinRangeTypeIndex()%>);
            binRangeStartEnd(<%=analysis.visualization_params.getBinRangeTypeIndex()%>, <%=analysis.visualization_params.bin_range_start%>, <%=analysis.visualization_params.bin_range_end%>);
            
        </script>
    <%
                } else {
    %>
        <script>
            
            colorBins(<%=analysis.visualization_params.nBins%>);

        </script>
    <%
                }
            }
        }
    %>
</html>
<%
  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}

%>