package com.dclib.dclib.baselibrary.vo;

/**
 * EventBus消息类
 * Created on 2018/9/3
 *
 * @author daichao
 */
public class EventBusVo<T> {

    /**
     * 类名
     */
    private String tag;
    /**
     * 传递的内容
     */
    private T content;

    public EventBusVo() {
    }

    public EventBusVo(String tag) {
        this.tag = tag;
    }

    public EventBusVo(String tag, T content) {
        this.tag = tag;
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
