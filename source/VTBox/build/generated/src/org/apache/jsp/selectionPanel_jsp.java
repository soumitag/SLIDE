package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import graphics.layouts.ScrollViewLayout;
import graphics.layouts.VizualizationHomeLayout;
import vtbox.SessionUtils;
import java.util.ArrayList;
import structure.AnalysisContainer;
import structure.Data;

public final class selectionPanel_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

try {
    
    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);
    Data database = analysis.database;
    String data_min = String.format("%.3f", database.DATA_MIN_MAX[0]);
    String data_max = String.format("%.3f", database.DATA_MIN_MAX[1]);

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <script type = \"text/javascript\" language = \"JavaScript\" src=\"params.js\"></script>\n");
      out.write("        <link rel=\"stylesheet\" href=\"vtbox-main.css\">\n");
      out.write("        <style>\n");
      out.write("            td {\n");
      out.write("                font-family: verdana;\n");
      out.write("                font-size: 12px;\n");
      out.write("                background-color:#EEEEEE;\n");
      out.write("            }\n");
      out.write("        </style>\n");
      out.write("        <script>\n");
      out.write("            function chkisnumber(f){\n");
      out.write("                \n");
      out.write("                var x = document.getElementById(f.name).value;\n");
      out.write("                \n");
      out.write("                if(isNaN(parseFloat(x)) || !isFinite(x)){                \n");
      out.write("                    document.getElementById(f.name).value = \"0\";\n");
      out.write("                    alert(\"Please enter a numeric value\");\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function enabletext(){\n");
      out.write("                document.getElementById(\"txtRangeStart\").disabled = false;\n");
      out.write("                document.getElementById(\"txtRangeEnd\").disabled = false;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function disabletext(){\n");
      out.write("                document.getElementById(\"txtRangeStart\").disabled = true;\n");
      out.write("                document.getElementById(\"txtRangeEnd\").disabled = true;\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function checkDataMin() {\n");
      out.write("                var curr = document.getElementById(\"log2flag\").checked;\n");
      out.write("                if (curr) {\n");
      out.write("                    var data_min = ");
      out.print(database.RAW_DATA_MIN);
      out.write(";\n");
      out.write("                    if (data_min < 0.0) {\n");
      out.write("                        alert(\"Data has negative values. Log base 2 transformation cannot be applied.\");\n");
      out.write("                        document.getElementById(\"log2flag\").checked = false;\n");
      out.write("                    } else if (data_min === 0.0) {\n");
      out.write("                        alert(\"Data has zero values. The minimum non-zero value in the dataset, will be added to all cells. If you do not wish to do this, uncheck the log base 2 transformation option.\");\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function validateParamsAndSubmit() {\n");
      out.write("                var readyToSubmit = true;\n");
      out.write("                var clip = document.getElementById(\"clippingType\").selectedIndex;\n");
      out.write("                if (clip !== 0) {\n");
      out.write("                    var clip_min = parseInt(document.getElementById(\"txtClipMin\").value);\n");
      out.write("                    var clip_max = parseInt(document.getElementById(\"txtClipMax\").value);\n");
      out.write("                    //alert(clip_min);\n");
      out.write("                    //alert(clip_max);\n");
      out.write("                    if (clip_max <= clip_min) {\n");
      out.write("                        alert(\"Maximum clipping value cannot be less than or equal to minimum clipping value.\");\n");
      out.write("                        readyToSubmit = false;\n");
      out.write("                    }\n");
      out.write("                    if (clip === 2) {\n");
      out.write("                        if(clip_min < 1 || clip_min > 99) {\n");
      out.write("                            alert(\"Minimum clipping percentile cannot be less than 1 or greater than 99.\");\n");
      out.write("                            readyToSubmit = false;\n");
      out.write("                        }\n");
      out.write("                        if(clip_max < 1 || clip_max > 99) {\n");
      out.write("                            alert(\"Maximum clipping percentile cannot be less than 1 or greater than 99.\");\n");
      out.write("                            readyToSubmit = false;\n");
      out.write("                        }\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("                \n");
      out.write("                if (readyToSubmit) {\n");
      out.write("                    document.getElementById('SelectionForm').submit();\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("        <form name=\"SelectionForm\" id=\"SelectionForm\" method=\"get\" action=\"AnalysisReInitializer\" target=\"visualizationPanel\"> \n");
      out.write("            <input type=\"hidden\" name=\"analysis_name\" value=\"");
      out.print(analysis_name);
      out.write("\" />\n");
      out.write("            <input type=\"hidden\" name=\"do_clustering\" value=\"false\" />\n");
      out.write("            <table id=\"dataselectionTable\" width=\"100%\" border=0 align=center cellpadding=\"2px\" cellspacing=\"2px\"  style=\"padding-left: 5px; padding-right: 5px;\">\n");
      out.write("            \n");
      out.write("            ");
  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Data Selection</b> </td></tr>\n");
      out.write("            <!--\n");
      out.write("            <tr> \n");
      out.write("                <td align=\"center\" height=\"30px\">\n");
      out.write("                    <input type=\"button\" value=\"Open Data Selection Panel\">\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Replicate Handling: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"repHandle\" value=\"0\" checked=\"checked\"> Show All Replicates<br>\n");
      out.write("                    <input type=\"radio\" name=\"repHandle\" value=\"1\"> Mean<br>\n");
      out.write("                    <input type=\"radio\" name=\"repHandle\" value=\"2\"> Median\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <b><label>Data Clipping: </label></b> &nbsp; \n");
      out.write("                    <select id=\"clippingType\" name=\"clippingType\">\n");
      out.write("                        <option id=\"none\" value=\"none\" >None</option>\n");
      out.write("                        <option id=\"absv\" value=\"absv\" >Absolute Value</option>\n");
      out.write("                        <option id=\"ptile\" value=\"ptile\" >Percentile</option>\n");
      out.write("                    </select>\n");
      out.write("                    <br><br>\n");
      out.write("                    Min &nbsp; <input type=\"text\" id=\"txtClipMin\" name=\"txtClipMin\" size=\"5\" onchange=\"chkisnumber(this)\" />\n");
      out.write("                    &nbsp; \n");
      out.write("                    Max &nbsp; <input type=\"text\" id=\"txtClipMax\" name=\"txtClipMax\" size=\"5\" onchange=\"chkisnumber(this)\" />\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <input type=\"checkbox\" id=\"log2flag\" name=\"log2flag\" onclick=\"checkDataMin()\"> Perform log base 2 transformation</input>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Column Scaling: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"0\" checked=\"checked\"> None <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"1\"> Scale Columns to 0-1 <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"2\"> Make Columns Standard Normal <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Col\" value=\"3\"> Modified Pareto Scaling  <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Row Centering: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"0\" checked=\"checked\"> None <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"1\"> Scale Rows to 0-1 <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"2\"> Mean Center Rows <br>\n");
      out.write("                    <input type=\"radio\" name=\"normRule_Row\" value=\"3\"> Make Rows Standard Normal <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            ");
 if (database.isTimeSeries) { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <b><label>Group Columns By: </label></b> <br>\n");
      out.write("                    <input type=\"radio\" name=\"groupBy\" value=\"sample\" checked=\"checked\"> Factor 1 &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"groupBy\" value=\"time\"> Factor 2<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            \n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            ");
  if (analysis.visualizationType == AnalysisContainer.ONTOLOGY_LEVEL_VISUALIZATION || 
                    analysis.visualizationType == AnalysisContainer.PATHWAY_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Enrichment Parameters</b> </td> </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label style=\"line-height: 20px\">Significance Level: </label></b>\n");
      out.write("                    <input type=\"text\" id=\"txtSignificanceLevel\" name=\"txtSignificanceLevel\" value=\"0.05\" maxlength=\"5\" size=\"5\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label style=\"line-height: 25px\"> Minimum Functional Group Feature List Intersection: </label></b>\n");
      out.write("                    <input type=\"text\" id=\"txtSmall_k\" name=\"txtSmall_k\" value=\"0\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label style=\"line-height: 25px; padding-bottom: 10px\">Minimum Functional Group Size: </label></b>\n");
      out.write("                    <input type=\"text\" id=\"txtBig_K\" name=\"txtBig_K\" value=\"0\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("\n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Clustering Parameters</b> </td> </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <input type=\"checkbox\" id=\"do_cluster_flag\" name=\"do_cluster_flag\"> Perform Hierarchical Clustering</input>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Linkage Function: </label></b><br>\n");
      out.write("                    <!--<input type=\"radio\" name=\"linkFunc\" value=\"single\" checked=\"checked\"> Single<br>-->\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"average\" checked=\"checked\"> Average<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"complete\"> Complete<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"median\"> Median<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"centroid\"> Centroid<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"ward\"> Ward<br>\n");
      out.write("                    <input type=\"radio\" name=\"linkFunc\" value=\"weighted\"> Weighted<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <b><label>Distance Function: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"euclidean\" checked=\"checked\"> Euclidean<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"cityblock\"> Manhattan<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"cosine\"> Cosine<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"correlation\"> Correlation<br>\n");
      out.write("                    <input type=\"radio\" name=\"distFunc\" value=\"chebyshev\"> Chebyshev<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr> <td colspan='4' align=center height=\"20\"> <b>Visualization Controls</b> </td></tr>\n");
      out.write("            \n");
      out.write("            ");
  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <b><label style=\"display: inline-block; padding-bottom: 5px\">Row Label: </label></b> <br>\n");
      out.write("                    <select id=\"identifierType\" name=\"identifierType\">\n");
      out.write("                        \n");
      out.write("                    ");
 if (analysis.database.metadata.hasStandardMetaData())    {   
      out.write("    \n");
      out.write("                        <option id=\"entrez\" value=\"entrez_2021158607524066\" checked=\"checked\">Entrez</option>\n");
      out.write("                        <option id=\"genesymbol\" value=\"genesymbol_2021158607524066\" >Gene Symbol</option>\n");
      out.write("                        <option id=\"refseq\" value=\"refseq_2021158607524066\" >RefSeq Gene</option>\n");
      out.write("                        <option id=\"ensembl_gene_id\" value=\"ensembl_gene_id_2021158607524066\" >Ensembl Gene Id</option>\n");
      out.write("                        <option id=\"ensembl_transcript_id\" value=\"ensembl_transcript_id_2021158607524066\" >Ensembl Transcript Id</option>\n");
      out.write("                        <option id=\"ensembl_protein_id\" value=\"ensembl_protein_id_2021158607524066\" >Ensembl Protein Id</option>\n");
      out.write("                        <option id=\"uniprot_id\" value=\"uniprot_id_2021158607524066\" >Uniprot Id</option>\n");
      out.write("                    ");
  }   
      out.write("\n");
      out.write("                    ");

                        ArrayList <String> nonstandard_metacolnames = analysis.database.metadata.getNonStandardMetaColNames();
                        for (int i=0; i<nonstandard_metacolnames.size(); i++) {
                            String name = nonstandard_metacolnames.get(i);
                    
      out.write("\n");
      out.write("                            <option id=\"");
      out.print(name);
      out.write("\" value=\"");
      out.print(name);
      out.write("\" >");
      out.print(name);
      out.write("</option>\n");
      out.write("                    ");
  }   
      out.write("\n");
      out.write("                    </select>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");
  }   
      out.write("\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Number of Color Bins: </label></b>\n");
      out.write("                    ");
  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("                    <input type=\"text\" id=\"txtNBins\" name=\"txtNBins\" value=\"21\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                    ");
 } else { 
      out.write("\n");
      out.write("                    <input type=\"text\" id=\"txtNBins\" name=\"txtNBins\" value=\"51\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            ");
  if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {   
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Binning Range: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"binningRange\" value=\"data_bins\" checked=\"checked\" onclick=\"disabletext()\"> Use Min/Max of Data<br>\n");
      out.write("                    <p style=\"line-height: 10px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      out.write("                    Min <input type=\"text\" id=\"txtMin\" name=\"txtMin\" size=\"5\" value=\"");
      out.print(data_min);
      out.write("\" disabled />\n");
      out.write("                    and Max <input type=\"text\" id=\"txtMax\" name=\"txtMax\" size=\"5\" value=\"");
      out.print(data_max);
      out.write("\" disabled />\n");
      out.write("                    </p>\n");
      out.write("                    <input type=\"radio\" name=\"binningRange\" value=\"symmetric_bins\" onclick=\"disabletext()\"> Use Symmetric Bins (about 0)<br>\n");
      out.write("                    <input type=\"radio\" name=\"binningRange\" value=\"start_end_bins\" onclick=\"enabletext()\"> Use Range <br>\n");
      out.write("                    <p style=\"line-height: 10px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      out.write("                    Start <input type=\"text\" id=\"txtRangeStart\" name=\"txtRangeStart\" size=\"5\" onchange=\"chkisnumber(this)\" disabled />\n");
      out.write("                    and End <input type=\"text\" id=\"txtRangeEnd\" name=\"txtRangeEnd\" size=\"5\" onchange=\"chkisnumber(this)\" disabled />\n");
      out.write("                    </p>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");
 } else { 
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Binning Range: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"binningRange\" value=\"symmetric_bins\" checked> Use Symmetric Bins (about 0)<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Leaf Ordering: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"0\" checked=\"checked\"> Largest Child First<br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"1\"> Smallest Child First<br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"2\"> Most Diverse Child First<br>\n");
      out.write("                    <input type=\"radio\" name=\"leafOrder\" value=\"3\"> Least Diverse Child First<br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Heatmap Color Scheme: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"colorScheme\" value=\"blue_white_red\" checked=\"checked\"> Blue-White-Red <br>\n");
      out.write("                    <input type=\"radio\" name=\"colorScheme\" value=\"blue_white_maroon\"> Sapphire-White-Maroon <br>\n");
      out.write("                    <input type=\"radio\" name=\"colorScheme\" value=\"green_black_red\"> Green-Black-Red <br>\n");
      out.write("                    <input type=\"radio\" name=\"colorScheme\" value=\"blue_black_yellow\"> Blue-Black-Yellow <br>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Detailed View Cell Size: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"detailed_view_sz\" value=\"");
      out.print(ScrollViewLayout.RESOLUTION_PROFILE_8);
      out.write("\"> XS &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"detailed_view_sz\" value=\"");
      out.print(ScrollViewLayout.RESOLUTION_PROFILE_12);
      out.write("\"> S &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"detailed_view_sz\" value=\"");
      out.print(ScrollViewLayout.RESOLUTION_PROFILE_16);
      out.write("\" checked=\"checked\"> M &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"detailed_view_sz\" value=\"");
      out.print(ScrollViewLayout.RESOLUTION_PROFILE_20);
      out.write("\"> L &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"detailed_view_sz\" value=\"");
      out.print(ScrollViewLayout.RESOLUTION_PROFILE_24);
      out.write("\"> XL\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\">\n");
      out.write("                    <input type=\"checkbox\" id=\"square_cell_flag\" name=\"square_cell_flag\" checked> Use Square Cells in Detailed View </input>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Visualization Panel Lengths: </label></b><br>\n");
      out.write("                    <input type=\"radio\" name=\"panel_lengths\" value=\"");
      out.print(VizualizationHomeLayout.VISUALIZATION_PANE_LENGTH_0);
      out.write("\"> S &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"panel_lengths\" value=\"");
      out.print(VizualizationHomeLayout.VISUALIZATION_PANE_LENGTH_1);
      out.write("\"> M &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"panel_lengths\" value=\"");
      out.print(VizualizationHomeLayout.VISUALIZATION_PANE_LENGTH_2);
      out.write("\" checked=\"checked\"> L &nbsp;\n");
      out.write("                    <input type=\"radio\" name=\"panel_lengths\" value=\"");
      out.print(VizualizationHomeLayout.VISUALIZATION_PANE_LENGTH_3);
      out.write("\"> XL\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("            <!--\n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" style=\"padding: 10px;\"> \n");
      out.write("                    <b><label>Dendrogram Depth: </label></b>\n");
      out.write("                    <input type=\"text\" name=\"txtDendogramDepth\" value=\"20\" maxlength=\"4\" size=\"4\" >\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            -->\n");
      out.write("            \n");
      out.write("            <tr>\n");
      out.write("                <td colspan=\"4\" align=\"center\" style=\"padding: 10px;\"> \n");
      out.write("                    <input type=\"hidden\" id=\"vizType\" name=\"vizType\" value=\"Selection\">\n");
      out.write("                    ");
 if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  { 
      out.write("\n");
      out.write("                    <button type=\"button\" onclick=\"validateParamsAndSubmit()\">Refresh</button>\n");
      out.write("                    ");
 } else { 
      out.write("\n");
      out.write("                    <button type=\"submit\">Refresh</button>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            \n");
      out.write("        </table>\n");
      out.write("            \n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("    \n");
      out.write("    ");
 
        String load_type = request.getParameter("load_type");
        if (load_type != null) {
            if (load_type.equalsIgnoreCase("reopen") || load_type.equalsIgnoreCase("file")) {
                if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {
    
      out.write("\n");
      out.write("    \n");
      out.write("        <script>\n");
      out.write("            \n");
      out.write("            replicateHandling(");
      out.print(analysis.data_transformation_params.replicate_handling);
      out.write(");\n");
      out.write("            checkDataClipMinMax(");
      out.print(analysis.data_transformation_params.getClippingType());
      out.write(',');
      out.write(' ');
      out.print(analysis.data_transformation_params.clip_min);
      out.write(',');
      out.write(' ');
      out.print(analysis.data_transformation_params.clip_max);
      out.write(");\n");
      out.write("            checkLogTransform(");
      out.print(analysis.data_transformation_params.log_transform);
      out.write(");\n");
      out.write("            columnScaling(");
      out.print(analysis.data_transformation_params.column_normalization);
      out.write(");\n");
      out.write("            rowScaling(");
      out.print(analysis.data_transformation_params.row_normalization);
      out.write(");\n");
      out.write("            ");
 if (database.isTimeSeries) { 
      out.write("\n");
      out.write("            groupBy(");
      out.print(analysis.data_transformation_params.getGroupByIndex());
      out.write(");\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            checkHierarchical(");
      out.print(analysis.clustering_params.do_clustering);
      out.write(");\n");
      out.write("            linkageFunc(");
      out.print(analysis.clustering_params.getLinkageIndex());
      out.write(");\n");
      out.write("            distFunc(");
      out.print(analysis.clustering_params.getDistanceFuncIndex());
      out.write(");\n");
      out.write("            colorBins(");
      out.print(analysis.visualization_params.nBins);
      out.write(");\n");
      out.write("            binRange(");
      out.print(analysis.visualization_params.getBinRangeTypeIndex());
      out.write(");\n");
      out.write("            binRangeStartEnd(");
      out.print(analysis.visualization_params.getBinRangeTypeIndex());
      out.write(',');
      out.write(' ');
      out.print(analysis.visualization_params.bin_range_start);
      out.write(',');
      out.write(' ');
      out.print(analysis.visualization_params.bin_range_end);
      out.write(");\n");
      out.write("            leafOrder(");
      out.print(analysis.visualization_params.getLeafOrderingStrategyIndex());
      out.write(");\n");
      out.write("            \n");
      out.write("            detailedViewCellSize(");
      out.print(analysis.visualization_params.detailed_view_map_layout.getResolutionProfileType());
      out.write(");\n");
      out.write("            useSquareCells(");
      out.print(analysis.visualization_params.detailed_view_map_layout.USE_SQUARE_CELLS);
      out.write(");\n");
      out.write("            vizPanelLengths(");
      out.print(analysis.visualization_params.viz_layout.getVisualizationPanelLengthType());
      out.write(");\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("    \n");
      out.write("    ");

                } else {
    
      out.write("\n");
      out.write("        <script>\n");
      out.write("            \n");
      out.write("            setSignificanceLevel(");
      out.print(analysis.enrichment_params.significance_level);
      out.write(");\n");
      out.write("            set_small_k(");
      out.print(analysis.enrichment_params.small_k);
      out.write(");\n");
      out.write("            set_Big_K(");
      out.print(analysis.enrichment_params.big_K);
      out.write(");\n");
      out.write("            checkHierarchical(");
      out.print(analysis.clustering_params.do_clustering);
      out.write(");\n");
      out.write("            linkageFunc(");
      out.print(analysis.clustering_params.getLinkageIndex());
      out.write(");\n");
      out.write("            distFunc(");
      out.print(analysis.clustering_params.getDistanceFuncIndex());
      out.write(");\n");
      out.write("            colorBins(");
      out.print(analysis.visualization_params.nBins);
      out.write(");\n");
      out.write("            leafOrder(");
      out.print(analysis.visualization_params.getLeafOrderingStrategyIndex());
      out.write(");\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("    ");

                }
            } else if (load_type.equalsIgnoreCase("sub_analysis")) {
                if (analysis.visualizationType == AnalysisContainer.GENE_LEVEL_VISUALIZATION)  {
    
      out.write("\n");
      out.write("        <script>\n");
      out.write("            \n");
      out.write("            replicateHandling(");
      out.print(analysis.data_transformation_params.replicate_handling);
      out.write(");\n");
      out.write("            \n");
      out.write("            checkDataClipMinMax(");
      out.print(analysis.data_transformation_params.getClippingType());
      out.write(',');
      out.write(' ');
      out.print(analysis.data_transformation_params.clip_min);
      out.write(',');
      out.write(' ');
      out.print(analysis.data_transformation_params.clip_max);
      out.write(");\n");
      out.write("            checkLogTransform(");
      out.print(analysis.data_transformation_params.log_transform);
      out.write(");\n");
      out.write("            rowScaling(");
      out.print(analysis.data_transformation_params.row_normalization);
      out.write(");\n");
      out.write("            ");
 if (database.isTimeSeries) { 
      out.write("\n");
      out.write("            groupBy(");
      out.print(analysis.data_transformation_params.getGroupByIndex());
      out.write(");\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("            colorBins(");
      out.print(analysis.visualization_params.nBins);
      out.write(");\n");
      out.write("            binRange(");
      out.print(analysis.visualization_params.getBinRangeTypeIndex());
      out.write(");\n");
      out.write("            binRangeStartEnd(");
      out.print(analysis.visualization_params.getBinRangeTypeIndex());
      out.write(',');
      out.write(' ');
      out.print(analysis.visualization_params.bin_range_start);
      out.write(',');
      out.write(' ');
      out.print(analysis.visualization_params.bin_range_end);
      out.write(");\n");
      out.write("            \n");
      out.write("        </script>\n");
      out.write("    ");

                } else {
    
      out.write("\n");
      out.write("        <script>\n");
      out.write("            \n");
      out.write("            colorBins(");
      out.print(analysis.visualization_params.nBins);
      out.write(");\n");
      out.write("\n");
      out.write("        </script>\n");
      out.write("    ");

                }
            }
        }
    
      out.write("\n");
      out.write("    \n");
      out.write("    <script>\n");
      out.write("        rowLabelType('");
      out.print(analysis.visualization_params.row_label_type);
      out.write("');\n");
      out.write("    </script>\n");
      out.write("    \n");
      out.write("    ");

        String isDemo = request.getParameter("isDemo");
        if (isDemo != null) {
            if (isDemo.equalsIgnoreCase("yes")) {
    
      out.write("\n");
      out.write("                <script>\n");
      out.write("                    binRange(");
      out.print(analysis.visualization_params.getBinRangeTypeIndex());
      out.write(");\n");
      out.write("                    binRangeStartEnd(");
      out.print(analysis.visualization_params.getBinRangeTypeIndex());
      out.write(',');
      out.write(' ');
      out.print(analysis.visualization_params.bin_range_start);
      out.write(',');
      out.write(' ');
      out.print(analysis.visualization_params.bin_range_end);
      out.write(");\n");
      out.write("                </script>\n");
      out.write("    ");

            }
        }
    
      out.write("\n");
      out.write("    \n");
      out.write("</html>\n");

  
} catch (Exception e) {

    SessionUtils.logException(session, request, e);
    getServletContext().getRequestDispatcher("/Exception.jsp").forward(request, response);
    
}


    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
