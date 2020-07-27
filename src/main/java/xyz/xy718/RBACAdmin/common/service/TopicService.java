package cloud.catisland.ivory.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cloud.catisland.ivory.common.dao.model.Topic;
import cloud.catisland.ivory.common.dao.repository.TopicRepository;
/**
 * TopicService
/*
 * @Author: Xy718
 * @Date: 2020-05-25 21:13:21
 * @LastEditors: Xy718
 * @LastEditTime: 2020-06-01 17:51:03
 */ 
@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public List<Topic> getAlltpcs(){
        return topicRepository.findAll();
    }

    /**
     * 展示贴分页接口
     * @param page
     * @param count
     * @return
     */
	public Page<Topic> getTopicsPage(int page,int size,Sort sort) {
        Page<Topic> pageResult = topicRepository.findAll(PageRequest.of(page, size, sort));
		return pageResult;
	}
}