package kz.iitu.se242m.yesniyazova.service;

import kz.iitu.se242m.yesniyazova.entity.SamplePoint;
import kz.iitu.se242m.yesniyazova.entity.WeatherHistoryResponse;
import kz.iitu.se242m.yesniyazova.entity.dto.AirFilterDto;
import kz.iitu.se242m.yesniyazova.entity.dto.WeatherFilterDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;

public class XlsReportBuilder implements ReportBuilder {

    @Override
    public byte[] weatherReport(WeatherHistoryResponse d, WeatherFilterDto f) {
        XSSFWorkbook wb = new XSSFWorkbook();

        addSeriesSheet(wb, "Temp",  "Temperature (K)", d.temp());
        addSeriesSheet(wb, "Hum",   "Humidity (%)",     d.hum());
        addSeriesSheet(wb, "Press", "Pressure (hPa)",   d.press());
        addSeriesSheet(wb, "Wind",  "Wind speed (m/s)", d.wind());

        XSSFSheet sum = wb.createSheet("Summary");
        Row r = sum.createRow(0);
        r.createCell(0).setCellValue("Weather report");
        r = sum.createRow(2);
        Map.of("Temp","Temperature", "Hum","Humidity", "Press","Pressure", "Wind","Wind")
                .forEach((k,v)-> {
                    int rn = sum.getLastRowNum()+1;
                    Row row = sum.createRow(rn);
                    row.createCell(0).setCellFormula(String.format("HYPERLINK(\"#'%s'!A1\",\"%s\")", k, v));
                });

        return toBytes(wb);
    }

    private static void addSeriesSheet(XSSFWorkbook wb, String sheetName,
                                       String title, List<SamplePoint> pts) {

        XSSFSheet sh = wb.createSheet(sheetName);
        int row=0;

        Row h = sh.createRow(row++);
        h.createCell(0).setCellValue("Timestamp");
        h.createCell(1).setCellValue(title);

        for (SamplePoint p: pts) {
            Row r = sh.createRow(row++);
            r.createCell(0).setCellValue(p.ts().toString());
            r.createCell(1).setCellValue(p.value());
        }
        autosize(sh,2);

        int last = sh.getLastRowNum();
        XSSFDrawing drawing = sh.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0,0,0,0,3,1, 15,20);

        XSSFChart chart = drawing.createChart(anchor);
        chart.setTitleText(title);
        chart.setTitleOverlay(false);

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Time");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle(title);

        XDDFDataSource<String> xs =
                XDDFDataSourcesFactory.fromStringCellRange(
                        sh, new CellRangeAddress(1, last, 0, 0));
        XDDFNumericalDataSource<Double> ys =
                XDDFDataSourcesFactory.fromNumericCellRange(
                        sh, new CellRangeAddress(1, last, 1, 1));

        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
        XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(xs, ys);
        series.setSmooth(false);
        series.setMarkerStyle(MarkerStyle.NONE);

        chart.plot(data);
    }

    private static void autosize(Sheet sh,int cols){ for(int i=0;i<cols;i++) sh.autoSizeColumn(i); }

    private static byte[] toBytes(Workbook wb){
        try(ByteArrayOutputStream out=new ByteArrayOutputStream()){
            wb.write(out); wb.close(); return out.toByteArray();
        }catch(IOException e){ throw new UncheckedIOException(e); }
    }

    @Override
    public byte[] airReport(List<SamplePoint> points, AirFilterDto f) {
        XSSFWorkbook wb = new XSSFWorkbook();

        addSeriesSheet(wb,
                f.getPollutant().toUpperCase(),
                "Concentration (" + unitOf(f.getPollutant()) + ")",
                points);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            wb.write(bos);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String unitOf(String p) {
        return switch (p) {
            case "aqi"   -> "index";
            case "pm2_5" -> "µg/m³";
            case "pm10"  -> "µg/m³";
            case "co"    -> "µg/m³";
            case "no2"   -> "µg/m³";
            case "so2"   -> "µg/m³";
            case "o3"    -> "µg/m³";
            case "nh3"   -> "µg/m³";
            default      -> "";
        };
    }
}

