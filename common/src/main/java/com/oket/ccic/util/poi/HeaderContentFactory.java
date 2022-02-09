package com.oket.ccic.util.poi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Layer
 * @description: 表头内容工厂
 * @author: dzp
 * @create: 2021-07-12 13:31
 **/
@Configuration
public class HeaderContentFactory {

    /**
     * 罐底表-静压力表表头内容
     *
     * @return
     */
    @Bean("bsHeaderContent")
    public HeaderContent getBottomAndStaticHeaderContent() {
        String[] headerStr0 = {
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9"
        };
        //"起始行，截止行，起始列，截止列"--合并单元格用
        String[] headerNum0 = {
                "0,1,1,1",
                "0,1,2,2",
                "0,1,3,3",
                "0,1,4,4",
                "0,1,5,5",
                "0,1,6,6",
                "0,1,7,7",
                "0,1,8,8",
                "0,1,9,9",
                "0,1,10,10",
        };
        //封装返回
        List<String[]> headerStrList = new ArrayList<>();
        List<String[]> headerNumList = new ArrayList<>();
        headerStrList.add(headerStr0);
        headerNumList.add(headerNum0);
        return new HeaderContent(headerStrList, headerNumList);
    }
}
