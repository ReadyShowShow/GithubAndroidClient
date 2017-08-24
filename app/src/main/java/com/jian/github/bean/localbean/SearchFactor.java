package com.jian.github.bean.localbean;

import com.jian.github.utils.Formatter;

import java.util.Date;

import static com.jian.github.base.CommonConstants.NormalCons.LIMIT_20;

/**
 * Description：搜索因子（即各种搜索条件）
 */
public class SearchFactor {
    /**
     * 关键字
     */
    public String keyword;
    /**
     * 项目的语言
     */
    public String language;
    /**
     * 当前页，默认为1
     */
    public int page = 1;
    /**
     * 每页数据量
     */
    public int limit = LIMIT_20;
    /**
     * 项目创建时间
     */
    private Date createDate;

    public String getCreateDate() {
        return createDate == null ? null
                : Formatter.formatDate(createDate, Formatter.FORMAT_YYYY_MM_DD);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "SearchFactor{" +
                "keyword='" + keyword + '\'' +
                ", language='" + language + '\'' +
                ", createDate=" + createDate +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
