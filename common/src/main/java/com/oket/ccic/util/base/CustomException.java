package com.oket.ccic.util.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: oketmicroservice
 * @description: 自定义异常
 * @author: dzp
 * @create: 2021-05-27 14:52
 **/
@Getter
@Setter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private Object info;
}
