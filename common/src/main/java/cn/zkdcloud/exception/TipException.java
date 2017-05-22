package cn.zkdcloud.exception;

import org.apache.log4j.Logger;

/**
 * @Author zk
 * @Date 2017/5/17.
 * @Email 2117251154@qq.com
 */
public class TipException extends RuntimeException {
    private static Logger logger = Logger.getLogger(TipException.class);
    public TipException(String msg){
        super(msg);
    }
}
