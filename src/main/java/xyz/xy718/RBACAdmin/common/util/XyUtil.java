package xyz.xy718.RBACAdmin.common.util;

import cn.hutool.core.date.DateUtil;

public class XyUtil {
    public static String getOSSFilePath(){
        String today= DateUtil.today();
        return "/"+today.replace("-", "/");
    }
}