package com.gp.learn.spring.orm.framework;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.core.common.jdbc.BaseDao;
import javax.core.common.utils.GenericsUtils;
import javax.core.common.utils.StringUtils;
import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

/**
 * @author wjzhu
 * @createDate 2019-04-21 16:56
 */
public abstract class CrudDao<T extends Serializable, PK extends Serializable> implements BaseDao<T, PK> {
    private Logger log = org.apache.log4j.Logger.getLogger(CrudDao.class);

    private String tableName = "";
    private JdbcTemplate jdbcTemplateWrite;
    private JdbcTemplate jdbcTemplateReadOnly;
    private EntityOperation<T> op;
    private DataSource dataSourceReadOnly;
    private DataSource dataSourceWrite;

    protected abstract String getPKColumn();

    protected abstract void setDataSource(DataSource dataSource);

    protected CrudDao() {
        try {
            //		Class<T> entityClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass(), 0);
            op = new EntityOperation<T>(entityClass, this.getPKColumn());
            this.setTableName(op.tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setTableName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            this.tableName = op.tableName;
        } else {
            this.tableName = tableName;
        }
    }

    protected void setDataSourceWrite(DataSource dataSourceWrite) {
        this.dataSourceWrite = dataSourceWrite;
        jdbcTemplateWrite = new JdbcTemplate(dataSourceWrite);
    }

    protected void setDataSourceReadOnly(DataSource dataSourceReadOnly) {
        this.dataSourceReadOnly = dataSourceReadOnly;
        jdbcTemplateReadOnly = new JdbcTemplate(dataSourceReadOnly);
    }

    private String removeFirstAnd(String sql) {
        if (StringUtils.isEmpty(sql)) {
            return sql;
        }
        return sql.trim().toLowerCase().replaceAll("^\\s*and", "") + " ";
    }

    protected String getTableName() {
        return tableName;
    }

    protected DataSource getDataSourceReadOnly() {
        return dataSourceReadOnly;
    }

    protected DataSource getDataSourceWrite() {
        return dataSourceWrite;
    }

    private JdbcTemplate jdbcTemplateReadOnly() {
        return this.jdbcTemplateReadOnly;
    }

    private JdbcTemplate jdbcTemplateWrite() {
        return this.jdbcTemplateWrite;
    }

    @Override
    public List<T> select(QueryRule queryRule) throws Exception {
        QueryRuleSqlBuilder bulider = new QueryRuleSqlBuilder(queryRule);
        String ws = removeFirstAnd(bulider.getWhereSql());
        String whereSql = ("".equals(ws) ? ws : (" where " + ws));
        String sql = "select " + op.allColumn + " from " + getTableName() + whereSql;
        Object[] values = bulider.getValues();
        String orderSql = bulider.getOrderSql();
        orderSql = (StringUtils.isEmpty(orderSql) ? " " : (" order by " + orderSql));
        sql += orderSql;
        log.debug(sql);
        return (List<T>) this.jdbcTemplateReadOnly().query(sql, this.op.rowMapper, values);
    }


}
