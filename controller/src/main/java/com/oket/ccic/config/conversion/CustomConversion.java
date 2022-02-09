package com.oket.ccic.config.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: Layer
 * @description: 自定义转换
 * @author: dzp
 * @create: 2021-08-06 10:11
 **/
@Component
public class CustomConversion implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        SimpleDateFormat format;

        if (s.length() == 10) {
            format = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
