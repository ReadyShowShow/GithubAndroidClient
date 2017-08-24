package com.jian.github.base;

/**
 * Description：常量类
 */
public class CommonConstants {

    /**
     * GitHub搜索需要用到的常量
     */
    public static class GitHub {
        // -----------搜索的类别---------
        public static final String SORT_UPDATED = "updated";
        public static final String SORT_STARS = "stars";
        public static final String SORT_FORKS = "forks";
        // -----------搜索结果排序方式---------
        public static final String ORDER_DESC = "desc";
        public static final String ORDER_ASC = "asc";
        // -----------搜索的语言-----------
        public static final String LANG_ALL = "";
        public static final String LANG_JAVA = "Java";
        public static final String LANG_OC = "Objective-C";
        public static final String LANG_SWIFT = "swift";
        public static final String LANG_C = "C";
        public static final String LANG_CPP = "C++";
        public static final String LANG_PHP = "PHP";
        public static final String LANG_JS = "JavaScript";
        public static final String LANG_PYTHON = "Python";
        public static final String LANG_RUBY = "Ruby";
        public static final String LANG_C_SHARP = "C#";
        public static final String LANG_SHELL = "Shell";
    }

    public static class NormalCons {
        /**
         * 分页--每页20条数据
         */
        public static final int LIMIT_20 = 20;
        /**
         * 分页--每页10条数据
         */
        public static final int LIMIT_10 = 10;
    }
}
