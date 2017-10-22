package org.apache.jsp.WEB_002dINF.jspf;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import structure.AnalysisContainer;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import graphics.Heatmap;
import utils.Logger;
import structure.Data;

public final class histogram_005ffragment_jspf extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

    String analysis_name = request.getParameter("analysis_name");
    AnalysisContainer analysis = (AnalysisContainer)session.getAttribute(analysis_name);

    double y_offset = 0.0;
    String y_offset_str = request.getParameter("y_offset");
    if (y_offset_str != null) {
        try {
            y_offset = Double.parseDouble(y_offset_str);
        } catch (Exception e) {
            y_offset = 0.0;
        }
    }
    
    Heatmap heatmap = analysis.heatmap;
    Logger logger = analysis.logger;

    heatmap.hist.normalizeHist();
    double y_label_width = 60.0;
    //double x_label_height = 80.0;
    double histWidth = 400.0;
    double histHeight = 200.0;
    //double imgWidth = histWidth + y_label_width;
    //double imgHeight = histHeight + x_label_height;
    double barWidth = (histWidth) / heatmap.hist.frequencies.length;
    List<Integer> list = new ArrayList<Integer>();
    List<Double> normedFreqs = new ArrayList<Double>();
    heatmap.hist.normalizeHist();
    for (int i = 0; i < heatmap.hist.normedFrequencies.length; i++) {
        list.add(heatmap.hist.frequencies[i]);
        normedFreqs.add(heatmap.hist.normedFrequencies[i]);
    }

    double max_val = Collections.max(list);
    double max_normed_val = Collections.max(normedFreqs);
    double scaling_factor = histHeight / max_val;
    //double v = Math.ceil((max_val + (double)10/2) / 10) * 10;
    DecimalFormat df = new DecimalFormat("0.00");
    DecimalFormat df2 = new DecimalFormat("0.00");
    DecimalFormat df3 = new DecimalFormat("0.000");

    int exponent = 0;
    double decival = max_val;
    while (decival > 9.0) {
        exponent++;
        decival = decival / 10.0;
    }

    int num_y_ticks = 10;

    int num_x_tick_skips = 1;
    if (barWidth < 20.0) {
        num_x_tick_skips = (int) Math.ceil(20.0 / barWidth);
    }

      out.write("\n");
      out.write("\n");
      out.write("<g id=\"histogram_g\">\n");
      out.write("\n");
      out.write("    <g class=\"bar\">\n");
      out.write("        ");
 for (int i = 0; i < heatmap.hist.normedFrequencies.length; i++) {
                double h = heatmap.hist.frequencies[i] * scaling_factor;
                double x_bar = (i * barWidth) + y_label_width;
                double y_bar = histHeight - h;
        
      out.write("\n");
      out.write("        <rect x=\"");
      out.print(x_bar);
      out.write("\" y=\"");
      out.print(y_offset + y_bar);
      out.write("\" width=\"");
      out.print((barWidth - 1));
      out.write("\" height=\"");
      out.print(h);
      out.write("\" style=\"fill:rgb(");
      out.print((int) heatmap.hist.rgb[i][0]);
      out.write(',');
      out.print((int) heatmap.hist.rgb[i][1]);
      out.write(',');
      out.print((int) heatmap.hist.rgb[i][2]);
      out.write(");stroke-width:1;stroke:rgb(47,79,79)\"  />\n");
      out.write("\n");
      out.write("        ");
 } 
      out.write("\n");
      out.write("    </g>\n");
      out.write("\n");
      out.write("    <g class=\"xaxis\">\n");
      out.write("        ");
 for (int i = 0; i < heatmap.hist.normedFrequencies.length; i = i + num_x_tick_skips) {
                double x_text = ((i + 0.5) * barWidth) + y_label_width;
                double y_text = y_offset + histHeight + 15;    // the additional 15 is to shift the text down, so that the left edge of the text and not the center of text is at histHeight
        
      out.write("\n");
      out.write("        <line x1=\"");
      out.print(y_label_width);
      out.write("\" x2=\"");
      out.print((y_label_width + histWidth));
      out.write("\" y1=\"");
      out.print(y_offset + histHeight);
      out.write("\" y2=\"");
      out.print(y_offset + histHeight);
      out.write("\" stroke=\"black\" vector-effect=\"non-scaling-stroke\"></line>\n");
      out.write("        <line x1=\"");
      out.print(x_text);
      out.write("\" x2=\"");
      out.print(x_text);
      out.write("\" y1=\"");
      out.print(y_offset + histHeight);
      out.write("\" y2=\"");
      out.print(y_offset + histHeight + 5);
      out.write("\" stroke=\"black\" vector-effect=\"non-scaling-stroke\"></line>\n");
      out.write("        <text x=\"");
      out.print(x_text - 5.0);
      out.write("\" y=\"");
      out.print(y_text);
      out.write("\" font-family=\"Verdana\" font-size=\"12\" text-anchor=\"start\" transform=\"rotate(45,");
      out.print(x_text);
      out.write(',');
      out.print(y_text);
      out.write(")\">");
      out.print( df2.format(heatmap.hist.bincenters[i]));
      out.write("</text>\n");
      out.write("\n");
      out.write("        ");
 }
      out.write("\n");
      out.write("\n");
      out.write("        <text x=\"");
      out.print((histWidth / 2.0) + y_label_width);
      out.write("\" y=\"");
      out.print((y_offset + histHeight + 50));
      out.write("\" font-family=\"Verdana\" font-size=\"14\" text-anchor=\"middle\">Value</text>\n");
      out.write("    </g>\n");
      out.write("\n");
      out.write("    <g class=\"yaxis\">\n");
      out.write("        <line x1=\"");
      out.print(y_label_width);
      out.write("\" y1=\"");
      out.print(y_offset);
      out.write("\" x2=\"");
      out.print(y_label_width);
      out.write("\" y2=\"");
      out.print((y_offset + histHeight));
      out.write("\" stroke=\"black\"></line>\n");
      out.write("        <line x1=\"");
      out.print((histWidth + y_label_width));
      out.write("\" y1=\"");
      out.print(y_offset);
      out.write("\" x2=\"");
      out.print((histWidth + y_label_width));
      out.write("\" y2=\"");
      out.print((y_offset + histHeight));
      out.write("\" stroke=\"black\"></line>\n");
      out.write("            <g class=\"y_ticks\">\n");
      out.write("                ");

                    double y_tick_step = (max_val * scaling_factor) / num_y_ticks;
                    double y_val_step_normed = max_normed_val / num_y_ticks;
                    double y_val_step = max_val / num_y_ticks;
                    for (int i = 0; i < num_y_ticks; i++) {
                        double y_tick_pos = (i * y_tick_step) + y_offset;
                        double y_val_actual = max_normed_val - (i * y_val_step_normed);
                        double y_val_actual_right = (max_val - (i * y_val_step)) / Math.pow(10, exponent);
                
      out.write("\n");
      out.write("                <line x1=\"");
      out.print(y_label_width - 5);
      out.write("\" x2=\"");
      out.print(y_label_width);
      out.write("\" y1=\"");
      out.print(y_tick_pos);
      out.write("\" y2=\"");
      out.print(y_tick_pos);
      out.write("\" stroke=\"black\"></line>\n");
      out.write("                <text x=\"");
      out.print(y_label_width - 22);
      out.write("\" y=\"");
      out.print(y_tick_pos + 4);
      out.write("\" font-family=\"Verdana\" font-size=\"12\" text-anchor=\"middle\" >");
      out.print( df.format(y_val_actual));
      out.write("</text>\n");
      out.write("                <line x1=\"");
      out.print((histWidth + y_label_width));
      out.write("\" x2=\"");
      out.print((histWidth + y_label_width) + 5);
      out.write("\" y1=\"");
      out.print(y_tick_pos);
      out.write("\" y2=\"");
      out.print(y_tick_pos);
      out.write("\" stroke=\"black\"></line>\n");
      out.write("                <text x=\"");
      out.print((histWidth + y_label_width + 7));
      out.write("\" y=\"");
      out.print(y_tick_pos + 4);
      out.write("\" font-family=\"Verdana\" font-size=\"12\" text-anchor=\"start\" >");
      out.print( df3.format(y_val_actual_right));
      out.write("</text>\n");
      out.write("                ");

                    }
                
      out.write("\n");
      out.write("                <text x=\"");
      out.print((histWidth + y_label_width + 50));
      out.write("\" y=\"");
      out.print((y_offset + 4));
      out.write("\" font-family=\"Verdana\" font-size=\"12\" text-anchor=\"start\" >x10</text>\n");
      out.write("                <text x=\"");
      out.print((histWidth + y_label_width + 73));
      out.write("\" y=\"");
      out.print(y_offset);
      out.write("\" font-family=\"Verdana\" font-size=\"6\" text-anchor=\"start\" >");
      out.print((exponent));
      out.write("</text>\n");
      out.write("            </g>\n");
      out.write("\n");
      out.write("        <text x=\"");
      out.print(y_label_width - 40);
      out.write("\" y=\"");
      out.print((histHeight / 2.0) + y_offset);
      out.write("\" font-family=\"Verdana\" font-size=\"14\" text-anchor=\"middle\" transform=\"rotate(-90,");
      out.print((y_label_width - 40));
      out.write(',');
      out.print(((histHeight / 2.0) + y_offset));
      out.write(")\">Probability</text>\n");
      out.write("        <text x=\"");
      out.print((histWidth + y_label_width) + 50);
      out.write("\" y=\"");
      out.print((histHeight / 2.0) + y_offset);
      out.write("\" font-family=\"Verdana\" font-size=\"14\" text-anchor=\"middle\" transform=\"rotate(90,");
      out.print((histWidth + y_label_width) + 50);
      out.write(',');
      out.print(((histHeight / 2.0) + y_offset));
      out.write(")\">Count</text>\n");
      out.write("    </g>\n");
      out.write("\n");
      out.write("</g>");
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
