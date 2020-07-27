package cloud.catisland.ivory.common.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import cloud.catisland.ivory.common.dao.model.Topic;
/**
 * @Author: Xy718
 * @Date: 2020-05-25 17:38:13
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-01 17:17:28
 */ 
public interface TopicRepository extends JpaRepository<Topic,Long>{

	public Page<Topic> findAll(Pageable pageable);
}