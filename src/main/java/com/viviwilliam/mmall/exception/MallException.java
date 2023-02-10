package com.viviwilliam.mmall.exception;

import com.viviwilliam.mmall.enums.ResultEnum;

public class MallException  extends RuntimeException{

    public MallException(ResultEnum resultEnum){
        super(resultEnum.getMsg());

    }
}
