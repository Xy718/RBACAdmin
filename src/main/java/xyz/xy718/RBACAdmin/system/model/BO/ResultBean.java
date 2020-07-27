package cloud.catisland.ivory.system.model.BO;

import java.io.Serializable;

import lombok.Data;

/**
 * Ajax基本返回类型
 * <pre>
 * {
 *  "code":"X",
 *  "msg":"XXX",
 *  "data:{}
 * }
 * </pre>
 * @Author: Xy718
 * @Date: 2020-05-25 23:02:50
 * @LastEditors: Xy718
 * @LastEditTime: 2020-05-29 11:18:31
 */ 
@Data
public class ResultBean implements Serializable {

    private static final long serialVersionUID = -8276264968757808344L;

    private static final int SUCCESS = 0;

    public static final int FAIL = -1;

    private String msg = "操作成功";

    private int code = SUCCESS;

    private Object data;

    private ResultBean() {
        super();
    }
    private ResultBean(String msg, Object data, int code) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public static ResultBean success() {
        return success("操作成功");
    }

    public static ResultBean success(String msg) {
        return success(msg, null);
    }

    public static ResultBean successData(Object data) {
        return success("操作成功", data);
    }


    public static ResultBean success(Object data) {
        return success("操作成功", data);
    }

    public static ResultBean success(String msg, Object data) {
        return new ResultBean(msg, data, SUCCESS);
    }

    public static ResultBean error(String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(FAIL);
        resultBean.setMsg(msg);
        return resultBean;
    }

}