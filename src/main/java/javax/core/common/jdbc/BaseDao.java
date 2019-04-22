package javax.core.common.jdbc;


import com.gp.learn.spring.orm.framework.QueryRule;

import java.util.List;

/**
 * Created by Tom.
 */
public interface BaseDao<T,PK> {
    /**
     * 获取列表
     * @param queryRule 查询条件
     * @return
     */
    List<T> select(QueryRule queryRule) throws Exception;
}
