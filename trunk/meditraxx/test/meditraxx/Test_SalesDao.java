package meditraxx;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pravat.meditrax.bi.dao.DrugsDao;
import com.pravat.meditrax.bi.domain.DrugDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:test-context.xml")
public class Test_SalesDao {
	
	@Resource
	DrugsDao drugsDao;
	
//	@Resource JdbcTemplate template;
	
	@Test
	public void testSpring() {
//		DriverManagerDataSource ds = (DriverManagerDataSource) template.getDataSource();
//		System.out.println(ds.getUrl());
		
//		DrugDetails drug = drugsDao.getDrug("10016");
		
//		System.out.println(drug);
	}

}
