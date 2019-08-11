package com.keye.router.main.excel;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * 货币数字，String和Number互转
 * Created by Administrator on 2019-08-11.
 */

public class NumberUtils {

    public static float priceStringToNumber(String price) {
        if (price == null || "".equals(price))
            return 0;
        NumberFormat format = NumberFormat.getInstance();
        try {
            return format.parse(price).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String priceToString(float price) {
        NumberFormat format = NumberFormat.getInstance();
        String result = format.format(price);
        return result;
    }
}
