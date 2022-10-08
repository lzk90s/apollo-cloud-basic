package team.uni.apollo.basic.core.web.model;

import java.util.Collections;
import java.util.List;

/**
 * 分页响应数据
 *
 * @author : qihang.liu
 * @date 2021-09-05
 */
public class PageData<T> {
    private Long pageNo;
    private Long totalCount;
    private List<T> data;

    public PageData() {
    }

    public PageData(Long pageNo, Long totalCount, List<T> data) {
        this.pageNo = pageNo;
        this.totalCount = totalCount;
        this.data = data;
        if (null == data) {
            this.data = Collections.emptyList();
        }
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
