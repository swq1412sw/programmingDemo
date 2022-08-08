package com.sie.usergl.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分页的响应信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResult<T> extends Result{
    //总记录数
    private long totalpage;
    //分页后的数据
    private List<T> pageData;

    public PageResult(long totalpage,List<T> pageData){
        this.setFlag(true);
        this.setMessage("分页查询成功");
        this.pageData=pageData;
        this.totalpage=totalpage;
        this.setData(this.pageData);
    }
}
