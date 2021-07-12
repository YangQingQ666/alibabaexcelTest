package cn.mpy.exceltest.utils;

import cn.mpy.exceltest.constant.ExcelConstant;
import cn.mpy.exceltest.entity.People;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Excel 工具类
 * @author mpy
 */
public class ExcelUtil {

    /**
     * 下载文件
     * @param response
     * @param clazz
     * @param data
     * @param fileName
     */
    public static void downLoadExcel(HttpServletResponse response, Class<?> clazz, List<?> data, String fileName) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            if (data != null) {
                int size = data.size();
                if (size > ExcelConstant.PER_SHEET_ROW_COUNT) {
                    ListSplitUtil listSplitUtil = new ListSplitUtil();
                    List<List<People>> sheets = listSplitUtil.split(data, ExcelConstant.PER_SHEET_ROW_COUNT);
                    ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz).build();
                    for (int i = 0; i < sheets.size(); i++) {
                        WriteSheet writeSheet1 = EasyExcel.writerSheet(i, "sheet" + (i + 1)).build();
                        excelWriter.write(sheets.get(i), writeSheet1);
                    }
                    excelWriter.finish();
                    response.flushBuffer();
                } else {
                    ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), clazz).build();
                    WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "sheet1").build();
                    excelWriter.write(data, writeSheet1);
                    excelWriter.finish();
                    response.flushBuffer();
                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * 上传文件
     * @param file
     * @param clazz
     * @param process
     */
    public static void importExcel(MultipartFile file, Class<?> clazz,Consumer<List<?>> process){
        File file1 = null;
        try {
            file1 = ExcelUtil.multipartFileToFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file1);
        ExcelReader excelReader = excelReaderBuilder.build();
        List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
        System.out.println(sheets.size());
        try {
            InputStream in = file.getInputStream();
            for (int i = 0; i < sheets.size(); i++) {
                //获取sheet对象
                ReadSheet readBoxServerSheet =
                        EasyExcel.readSheet(i).head(clazz).registerReadListener(ExcelUtil.getListener(process,800)).build();
                excelReader.read(readBoxServerSheet);
            }
            excelReader.finish();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelUtil.delteTempFile(file1);
    }


    /**
     * @param <T>        实体类
     * @param consumer   传入一个消费者接口对象，作用：业务逻辑处理
     * @param thresshold 批处理阈值，作用：减轻数据库的压力
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<?>> consumer, int thresshold) {
        return new AnalysisEventListener<T>() {
            /**
             * 存储员工对象
             */
            List<T> list = new ArrayList<>(thresshold);
            //easyExcel每次从Excel中读取thresshold行数据就会调用一次invoke方法
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                list.add(t);
                if (list.size() >= thresshold) {
                    consumer.accept(list);
                    list.clear();
                }
            }
            //easyExcel在将Excel表中数据读取完毕后，最终执行此方法
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                //最后,如果size<thresshold就在这里进行数据的处理
                if (list.size() > 0) {
                    consumer.accept(list);
                }
            }
        };
    }

    /**
     * @param file 文件
     * @return File toFile
     * @MethodName: getFileName
     * @Description: 将MultipartFile转为File
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if (!(file.equals("")&&file.getSize() <= 0)) {
            InputStream ins =file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }


    /**
     * 获取流文件
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
