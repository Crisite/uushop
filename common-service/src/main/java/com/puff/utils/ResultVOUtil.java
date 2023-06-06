package com.puff.utils;

import com.puff.vo.ResultVO;

public class ResultVOUtil {
    public static ResultVO success(Object data) {
        return new ResultVO<>(1,"成功",data);
    }

    public static ResultVO fail(String msg) {
        return new ResultVO<>(-1, msg, null);
    }
}
