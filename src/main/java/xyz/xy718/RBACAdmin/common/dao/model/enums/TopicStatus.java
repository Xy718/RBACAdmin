package cloud.catisland.ivory.common.dao.model.enums;

/**
 * 帖子状态
 * @Author: Xy718
 * @Date: 2020-06-22 11:19:12
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-22 11:25:21
 */
public enum TopicStatus {
    /**
     * 被删除
     */
    DELETED("-1"),
    /**
     * 正常
     */
    NORMAL("0"),
    ;

    String code;
    TopicStatus(String code){
        this.code=code;
    }
}
