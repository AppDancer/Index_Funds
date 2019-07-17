package com.keye.router.main;



import com.keye.router.main.excel.ExcelManager;

import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void addition_isCorrect() throws Exception {
        ExcelManager instance = ExcelManager.getInstance();
        String filePath = "C:\\Users\\Administrator\\Desktop\\Index_Funds\\IndexFunds\\module_data_processing\\src\\main\\assets\\上证指数_估值.xls";
        Map<String, List<List<String>>> data = instance.analyzeXls(filePath);
        System.out.print(data);
    }

}