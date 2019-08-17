package com.vip.vjtools.vjkit.io.excle;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: lvrongzhuan
 * @Description: ali easyExcel工具类
 * @Date: 2019/5/17 16:36
 * @Version: 1.0
 * modified by:
 */
public class EasyExcleUtils {
    private EasyExcleUtils() {
    }

    /**
     * 读取 Excel(多个 sheet)
     *
     * @param excel  文件
     * @param aClass 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static <T> AnalysisEventListener<T> readExcel(MultipartFile excel, Class<? extends BaseRowModel> aClass, AnalysisEventListener<T> eventListener) throws IOException {
        String fileName = excel.getOriginalFilename();
        boolean b = !fileName.toLowerCase().endsWith(".xls") && !fileName.toLowerCase().endsWith(".xlsx");
        if (b) {
            throw new IOException("上传文件格式不正确");
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(excel.getInputStream())) {
            ExcelReader excelReader = new ExcelReader(bufferedInputStream, null, eventListener, false);
            for (Sheet sheet : excelReader.getSheets()) {
                sheet.setClazz(aClass);
                excelReader.read(sheet);
            }
        } catch (IOException e) {
            throw new IOException("读取文件异常", e);
        }
        return eventListener;
    }

    public static <T> AnalysisEventListener<T> readExcel(InputStream inputStream, Class<? extends BaseRowModel> clazz,AnalysisEventListener<T> eventListener) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            ExcelReader reader = new ExcelReader(bufferedInputStream, null, eventListener, false);
            reader.read(new Sheet(1, 1, clazz));
            return eventListener;
        } catch (IOException e) {
            throw new IOException("读取文件异常", e);
        }
    }


    /**
     * 导出 Excel ：一个 sheet，带表头
     *
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                  String fileName, String sheetName, Class<? extends BaseRowModel> clazz) throws IOException {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, clazz);
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        writer.finish();
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws IOException {
        // 告诉浏览器用什么软件可以打开此文件
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
        return response.getOutputStream();
    }


}
