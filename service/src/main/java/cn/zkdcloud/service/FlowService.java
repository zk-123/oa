package cn.zkdcloud.service;

import cn.zkdcloud.repository.FlowRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zk
 * @Date 2017/5/28.
 * @Email 2117251154@qq.com
 */
@Service
public class FlowService {

    private static Logger logger = Logger.getLogger(FunctionService.class); //不再在数据中插入日志，日志统一放到日志文件中

    @Autowired
    FlowRepository flowRepository;

    public void add(){

    }
}
