package com.oket.ccic.util.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @program: microservice
 * @description: 前后端交互封装实体类
 * @author: dzp
 * @create: 2021-05-18 18:58
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "前后端交互封装实体类")
public class Message implements Serializable {

    @ApiModelProperty(value = "请求结果标志-成功或失败")
    private boolean flag;

    @ApiModelProperty(value = "响应码")
    private Integer code;

    @ApiModelProperty(value = "请求的结果数据")
    private Object data;

    /**
     * 操作成功
     *
     * @param obj
     * @return
     */
    public static Message ok(Object obj) {
        return new Message(true, CodeEnum.C_SUCCESS.getCode(), obj);
    }

    /**
     * 操作成功
     *
     * @return
     */
    public static Message ok() {
        return new Message(true, CodeEnum.C_SUCCESS.getCode(), null);
    }

    /**
     * 操作失败
     *
     * @param obj
     * @return
     */
    public static Message error(Object obj) {
        return new Message(false, CodeEnum.C_FAILURE.getCode(), obj);
    }

    /**
     * 操作失败
     *
     * @return
     */
    public static Message error() {
        return new Message(false, CodeEnum.C_FAILURE.getCode(), null);
    }
}
