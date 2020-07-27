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
 * 角色表
 * @Author: Xy718
 * @Date: 2020-05-25 21:13:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-10 16:22:48
 */
@Data
@Entity
@Table(name = "role")
@EntityListeners(AuditingEntityListener.class)
public class Role {
    
    // roleID
    @Id
    @GeneratedValue
    private long rid;

    // 角色名称
    @Column(nullable = false)
    private String role_name;
    
    // 角色简介
    @Column
    private String role_intro;

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