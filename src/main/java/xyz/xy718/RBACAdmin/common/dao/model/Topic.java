package cloud.catisland.ivory.common.dao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * 展示贴orm对象
 * @Author: Xy718
 * @Date: 2020-05-25 21:13:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-01 16:44:39
 */
@Data
@Entity
@Table(name = "topic")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Topic {

    // TopicID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long tid;

    // // 发布用户ID
    // @Column(name="uid",nullable = false,unique = false)
    // private long uid;

    // 标题
    @Column
    private String title;

    // 内容
    @Column
    private String content;

    // 图片集合
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private List<String> imgs;

    // 发布时间
    @CreatedDate
    @Column(nullable = false)
    private Date create_time;

    @LastModifiedDate
    @Column(nullable = false)
    private Date update_time;

    //帖子状态 -1/0  删除/正常
    @Column(nullable = false) 
    private String status_flag="0";

    // @Transient
    @ManyToOne
    @JoinColumn(name="uid",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private User pub_userinfo;
}

