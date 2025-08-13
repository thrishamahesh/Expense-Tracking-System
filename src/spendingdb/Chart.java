package spendingdb;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chart extends JFrame {

    public Chart(String title) {
        super(title);

        // Show dialog to choose chart type
        int option = JOptionPane.showOptionDialog(this, "Choose Chart Type", "Chart Type", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Category Bar Chart", "Date Line Chart"}, "Category Bar Chart");

        if (option == JOptionPane.YES_OPTION) {
            createCategoryChart();
        } else if (option == JOptionPane.NO_OPTION) {
            createDateChart();
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createCategoryChart() {
        CategoryDataset dataset = createCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Category Bar Chart",
                "Category",
                "Amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);

        ChartPanel chartPanel = new ChartPanel(chart);
        getContentPane().add(chartPanel);
    }

    private CategoryDataset createCategoryDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            String query = "SELECT category, SUM(amount) AS total_amount FROM spendings GROUP BY category";
            try (PreparedStatement statement = DbConnect.c.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String category = resultSet.getString("category");
                    int totalAmount = resultSet.getInt("total_amount");
                    dataset.addValue(totalAmount, "Amount", category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    private void createDateChart() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Date Line Chart",
                "Date",
                "Amount",
                createTimeSeriesCollection(),
                true,
                true,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        DateAxis xAxis = (DateAxis) plot.getDomainAxis();
        xAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

        ChartPanel chartPanel = new ChartPanel(chart);
        getContentPane().add(chartPanel);
    }

    private TimeSeriesCollection createTimeSeriesCollection() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        try {
            String query = "SELECT sdate, SUM(amount) AS total_amount FROM spendings GROUP BY sdate";
            try (PreparedStatement statement = DbConnect.c.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                TimeSeries timeSeries = new TimeSeries("Amount");
                while (resultSet.next()) {
                    Date date = resultSet.getDate("sdate");
                    double totalAmount = resultSet.getDouble("total_amount");
                    timeSeries.add(new org.jfree.data.time.Day(date), totalAmount);
                }
                dataset.addSeries(timeSeries);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Chart("Chart Example"));
    }
}
