package com.gp.learn.spring.orm;

import com.gp.learn.spring.orm.demo.dao.MemberDao;
import com.gp.learn.spring.orm.demo.dao.OrderDao;
import com.gp.learn.spring.orm.demo.entity.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author wjzhu
 * @createDate 2019-04-21 16:43
 */
@ContextConfiguration(locations = {"classpath:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrmTest
{
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Test
    public void testSelectAllForMember(){
        try {
            List<Member> result = memberDao.selectAll();
            System.out.println(Arrays.toString(result.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
