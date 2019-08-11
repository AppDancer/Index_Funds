package com.keye.router.main;


import android.util.Log;

import com.keye.router.lib_common.base.utils.AppLogs;
import com.keye.router.main.excel.ExcelManager;
import com.keye.router.main.excel.SheetRepository;
import com.keye.router.main.excel.bean.IndexFundsBean;
import com.keye.router.main.excel.constants.Constants;
import com.keye.router.main.excel.db.ExcelDBHandler;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void readXls() throws Exception {
        ExcelManager instance = ExcelManager.getInstance();
        String filePath = "C:\\Users\\Administrator\\Desktop\\Index_Funds\\IndexFunds\\module_data_processing\\src\\main\\assets\\上证指数_估值.xls";
        Map<String, List<List<String>>> data = instance.analyzeXls(filePath);
        Iterator<Map.Entry<String, List<List<String>>>> iterator = data.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<List<String>>> sheet = iterator.next();
            Iterator<List<String>> iterator1 = sheet.getValue().iterator();
            System.out.println("sheet: " + sheet.getKey());
            while (iterator1.hasNext()) {
                List<String> list = iterator1.next();
                System.out.println(list);
            }
        }
    }

    @Test
    public void parseDate() throws Exception {
        ExcelManager instance = ExcelManager.getInstance();
        String filePath = "C:\\Users\\Administrator\\Desktop\\Index_Funds\\IndexFunds\\module_data_processing\\src\\main\\assets\\上证指数_估值.xls";
        Map<String, List<List<String>>> data = instance.analyzeXls(filePath);
        Iterator<Map.Entry<String, List<List<String>>>> sheetIterator = data.entrySet().iterator();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        while (sheetIterator.hasNext()) {
            Map.Entry<String, List<List<String>>> sheet = sheetIterator.next();
            List<List<String>> indexFundsIterator = sheet.getValue();
            for (int i = 1; i < indexFundsIterator.size(); i++) {
                List<String> beanString = indexFundsIterator.get(i);
                IndexFundsBean bean = new IndexFundsBean();
                try {
                    Date date = sdf.parse(beanString.get(0));
                    System.out.println(beanString.get(0));
                    System.out.println(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 单元测试时，需要修改时间格式：yyyy-MM-dd
     * {@link SheetRepository#analyzeXls(String)} line: 98
     */
    @Test
    public void testSheetRepository() {
        final long startTime = System.currentTimeMillis();
        SheetRepository sheetRepository = new SheetRepository();
        sheetRepository.analyzeXls(Constants.SHANGHAI_FUNDS_PATH)
                .subscribe(new Action1<Map<String, List<IndexFundsBean>>>() {
                    @Override
                    public void call(Map<String, List<IndexFundsBean>> data) {
                        Iterator<Map.Entry<String, List<IndexFundsBean>>> iterator = data.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, List<IndexFundsBean>> sheet = iterator.next();
                            String sheetName = sheet.getKey();
                            List<IndexFundsBean> value = sheet.getValue();
                            for (IndexFundsBean indexFundsBean :
                                    value) {
//                                System.out.println(indexFundsBean.toString());
                            }
                        }
                        System.out.println("解析总时长：" + (System.currentTimeMillis() - startTime));

                    }
                });
    }
}
