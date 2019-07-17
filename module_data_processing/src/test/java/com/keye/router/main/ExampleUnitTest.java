package com.keye.router.main;


import android.util.Log;

import com.keye.router.main.excel.ExcelManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    //    @Mock
//    SharedPreferences.Editor mMockEditor
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
}
