package cn.zkdcloud.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/** 操作日志类
 * @Author zk
 * @Date 2017/5/19.
 * @Email 2117251154@qq.com
 */
@Entity
public class OperatorLog {

    @Id
    @GeneratedValue(generator="idGenerator")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    private String operatorLogId;

    @Column
    private String operatorContent;

    @Column
    private Date opreratorCreateDate;

    @Column
    private String operatorType;

    public String getOperatorLogId() {
        return operatorLogId;
    }

    public void setOperatorLogId(String operatorLogId) {
        this.operatorLogId = operatorLogId;
    }

    public String getOperatorContent() {
        return operatorContent;
    }

    public void setOperatorContent(String operatorContent) {
        this.operatorContent = operatorContent;
    }

    public Date getOpreratorCreateDate() {
        return opreratorCreateDate;
    }

    public void setOpreratorCreateDate(Date opreratorCreateDate) {
        this.opreratorCreateDate = opreratorCreateDate;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
}
