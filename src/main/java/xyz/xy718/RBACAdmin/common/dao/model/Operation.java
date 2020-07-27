package cloud.catisland.ivory.common.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
/**
 * 操作URL
 * @Author: Xy718
 * @Date: 2020-05-25 21:13:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-04 14:24:37
 */
@Data
@Entity
@Table(name = "operation")
@EntityListeners(AuditingEntityListener.class)
public class Operation {
    
    // operationID
    @Id
    @GeneratedValue
    private long oid;

    // 操作URL
    @Column(nullable = false, unique = true)
    private String url;

    // 操作名称（简介）
    @Column(nullable = false)
    private String oper_intro;

    // 权限节点
    @Column(nullable = false, unique = true)
    private String oper_perm;

    // 创建时间
    @CreatedDate
    @Column(nullable = false)
    private Date create_time;

    //修改时间
    @LastModifiedDate
    @Column(nullable = false)
    private Date update_time;

    //操作状态 -1/0  删除/正常
    @Column(nullable = false)
    private String status_flag="0";
}