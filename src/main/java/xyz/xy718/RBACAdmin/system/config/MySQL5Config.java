package xyz.xy718.RBACAdmin.system.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Jpa的Mysql连接配置
 * @Author: Xy718
 * @Date: 2020-05-28 23:26
 * @LastEditors: Xy718
 * @LastEditTime: 2020-05-28 23:58
 */ 
@Deprecated
public class MySQL5Config extends MySQL5InnoDBDialect {

    /**
     * 由Jpa自动生成表的编码和排序规则
     */
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_bin";
    }
}