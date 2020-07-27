package cloud.catisland.ivory.system.model.BO;

import java.util.Date;
import java.util.List;

import cloud.catisland.ivory.common.dao.model.User;
import lombok.Data;

/**
 * 帖子的BO
 * @Author: Xy718
 * @Date: 2020-06-25 16:17:40
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-25 16:28:42
 */
@Data
public class TopicBO {
    
    // TopicID
    private long tid;

    // 发布用户ID
    private long uid;

    // 发布用户名
    private String user_name;

    // 标题
    private String title;

    // 内容
    private String content;

    // 图片集合
    private List<String> imgs;

    // 发布时间
    private Date create_time;

    private Date update_time;

    //帖子状态 -1/0  删除/正常
    private String status_flag="0";

    private User user;
}

