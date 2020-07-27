package cloud.catisland.ivory.system.controller;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cloud.catisland.ivory.common.service.TopicService;

/**
 * Index控制器，用于主页的数据控制
 * @Author: Xy718
 * @Date: 2020-05-25 16:53:42
 * @LastEditors: Xy718
 * @LastEditTime: 2020-05-29 14:21:40
 */ 
@RestController
@RequestMapping("/index")
public class IndexController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private TopicService topicService;

    
}