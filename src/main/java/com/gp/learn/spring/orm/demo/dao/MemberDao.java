package com.gp.learn.spring.orm.demo.dao;

import com.gp.learn.spring.orm.demo.entity.Member;
import com.gp.learn.spring.orm.framework.CrudDao;
import com.gp.learn.spring.orm.framework.QueryRule;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * @author wjzhu
 * @createDate 2019-04-21 16:43
 */
@Repository
public class MemberDao extends CrudDao<Member,Long>{

    @Override
    protected String getPKColumn() {
        return "id";
    }

    @Override
    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource){
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }

    public List<Member> selectAll() throws  Exception{
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andLike("name","Tom");
        return super.select(queryRule);
    }
}
