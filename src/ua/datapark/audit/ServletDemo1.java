package ua.datapark.audit;

/* -----------------
* ServletDemo1.java
* -----------------
* (C) Copyright 2002-2004, by Object Refinery Limited.
*
*/
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
//import org.jfree.chart.ChartPanel;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;

import org.jfree.chart.renderer.category.BarRenderer;
//import org.jfree.chart.renderer.category.CategoryItemRenderer;

import java.awt.Color;
import java.awt.Font;
//import java.awt.Dimension;
//import java.awt.BasicStroke;

import java.io.FileInputStream;

/**
* A basic servlet that returns a PNG image file generated by JFreeChart.
* This class is described in the JFreeChart Developer Guide in the
* "Servlets" chapter.
*/
public class ServletDemo1 extends HttpServlet {
	static final long serialVersionUID = 1;
	/**
	 * Creates a new demo.
	 */
	public ServletDemo1() {
		// 	nothing required
	}
	/**
	* Processes a GET request.
	*
	* @param request the request.
	* @param response the response.
	*
	* @throws ServletException if there is a servlet related problem.
	* @throws IOException if there is an I/O problem.
	*/
	public void doGet(HttpServletRequest request, HttpServletResponse response)
									throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		
		//String colr_qua = "49BF2C";
		
		//String z0 = "EEEEEE";
		//String z1 = "E1F2FF";
		//String z2 = "FFFED5";
		//String z3 = "FFEAEA";

		//String z0 = "EEEEEE";
		String z1 = "B2E4FF";
		String z2 = "FFFDA6";
		String z3 = "FFC2C2";
		//String str = "";

		String ks = request.getParameter("ks");
		String obl = request.getParameter("obl");
		if (ks==null && obl==null) { }
		String point = request.getParameter("point");
		if (point==null) { }

		String day1 = request.getParameter("day1");
		if (day1==null) { }
		String month1 = request.getParameter("month1");
		if (month1==null) { }
		String year1 = request.getParameter("year1");
		if (year1==null) { }

		String day2 = request.getParameter("day2");
		if (day2==null) { }
		String month2 = request.getParameter("month2");
		if (month2==null) { }
		String year2 = request.getParameter("year2");
		if (year2==null) { }

//		if ( (ks==null && obl==null) || point==null 
//				|| day1==null || month1==null || year1==null
//				|| day2==null || month2==null || year2==null ) { return; }

		_Range cc = new _Range();
		//_Range myBean = new _Range();

		//this.getServletContext().setAttribute("myBean", myBean);


		if (point.equals("0")) {
			cc.loadDataRangeZero(ks, year1+"-"+month1+"-"+day1, year2+"-"+month2+"-"+day2, "on");
		} else {
			cc.loadDataRange(point, year1+"-"+month1+"-"+day1, year2+"-"+month2+"-"+day2, "on");
		}

		
		try {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			
			for (int i=1;i<cc.DataRangeSize();i++) {
				dataset.addValue(cc.getDataRangeByNameDouble(i,"p_qua_1"), "����� 1 (������)", cc.getDataRangeByName(i,"dat").substring(0,5));
				dataset.addValue(cc.getDataRangeByNameDouble(i,"p_qua_2"), "����� 2 (����������)", cc.getDataRangeByName(i,"dat").substring(0,5));
				dataset.addValue(cc.getDataRangeByNameDouble(i,"p_qua_3"), "����� 3 (������)", cc.getDataRangeByName(i,"dat").substring(0,5));
			}

	        Font font = Font.createFont(Font.TRUETYPE_FONT,
	                new FileInputStream(this.getServletContext().getRealPath("../../shared/fonts/tahoma.ttf")));
	        Font fontb = Font.createFont(Font.TRUETYPE_FONT,
	                new FileInputStream(this.getServletContext().getRealPath("../../shared/fonts/tahomabd.ttf")));
	        float size = 12.0f;
	        font = font.deriveFont(size);
	        fontb = fontb.deriveFont(size);

			JFreeChart chart = ChartFactory.createBarChart("������� ������", null, "\u043A\u0412\u0442\u00B7\u0433",
								dataset, PlotOrientation.VERTICAL, true, true, false);
			chart.setBackgroundPaint(new Color(0xFFFFFF));
			
			//ChartPanel chartPanel = new ChartPanel(chart);
			//chartPanel.setPreferredSize(new Dimension(500, 270));
			
			//chart.setContentPanel(chartPanel);
	        
			CategoryPlot plot = chart.getCategoryPlot();
			plot.setNoDataMessage("���� �����");
			plot.setBackgroundAlpha(0.6f);
			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setDrawBarOutline(true); //default =true
			renderer.setSeriesPaint(0, new Color(Integer.parseInt(z1,16)));
			renderer.setSeriesPaint(1, new Color(Integer.parseInt(z2,16)));
			renderer.setSeriesPaint(2, new Color(Integer.parseInt(z3,16)));
						
//			CategoryItemRenderer catrenderer = plot.getRenderer();
//			catrenderer.setItemLabelsVisible(null); // clears the ALL series flag
//			catrenderer.setSeriesItemLabelsVisible(0, true);
//			catrenderer.setSeriesItemLabelsVisible(1, false);
//			catrenderer.setItemLabelsVisible(true);
//			catrenderer.setItemLabelFont(new Font("SansSerif", Font.PLAIN, 10));
//			catrenderer.setItemLabelFont(font);
			
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setLabelFont(fontb);
			domainAxis.setTickLabelFont(font.deriveFont(size-1));
			domainAxis.setLabel(domainAxis.getTickLabelFont().getName());

			ValueAxis valueAxis = plot.getRangeAxis();
			valueAxis.setTickLabelFont(font);
			
			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out, chart, 40+42*cc.DataRangeSize()+(cc.DataRangeSize()<2?200:0), 300);
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally { out.close(); }
	}
}