package com.greencloud.sharding;

import io.shardingsphere.api.HintManager;
import io.shardingsphere.shardingjdbc.spring.datasource.SpringShardingDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShardingTest {

	private static String insert = "INSERT INTO master_base (hotel_group_id, hotel_id, id, rsv_id, is_resrv, " +
			"rsv_man, rsv_man_id, rsv_company, mobile, group_code, group_manager, parent_id, " +
			"rsv_src_id, master_rsvsrc_id, rsv_class, master_id, grp_accnt, grp_flag, block_id, " +
			"biz_date, sta, rmtype, rmno, rmno_son, rmnum, arr, dep, cutoff_days, cutoff_date," +
			" adult, children, res_sta, res_dep, up_rmtype, up_reason, up_remark, up_user, " +
			"rack_rate, nego_rate, real_rate, dsc_reason, dsc_amount, dsc_percent, exp_sta, " +
			"tm_sta, rmpost_sta, is_rmposted, tag0, company_id, agent_id, source_id, member_type, " +
			"member_no, inner_card_id, salesman, arno, building, src, market, rsv_type, channel," +
			" sales_channel, ratecode, ratecode_category, cmscode, packages, specials, amenities, " +
			"is_fix_rate, is_fix_rmno, is_sure, is_permanent, is_walkin, is_secret, is_secret_rate, " +
			"posting_flag, sc_flag, extra_flag, extra_bed_num, extra_bed_rate, crib_num, crib_rate, " +
			"pay_code, limit_amt, credit_no, credit_man, credit_company, charge, pay, credit, " +
			"last_num, last_num_link, rmocc_id, link_id, pkg_link_id, rsv_no, crs_no, where_from, " +
			"where_to, purpose, remark, co_msg, is_send, is_cor_rsv, promotion, create_user, " +
			"create_datetime, modify_user, modify_datetime, sta_ebooking)\n" +
			"VALUES (1, 1, 219045, 219045, 'T', '', 0, '', '', '', '', 0, 0, 0, 'F', 219045, 0, '', 0, '2015-03-27" +

			" 00:00:00', 'R', 'ST', '', '', 1, '2016-11-10 18:00:00', '2016-11-11 13:00:00', 0, '2012-01-01 " +
			"20:00:00', 1, 0, '', NULL, '', '', '', '', 299.00, 299.00, 299.00, '', 0.00, 0.00, '', '', 'F', 'F', " +
			"'', 0, 0, 221, '', '', 0, '', '', '', '01', 'TG', '', '3', '', 'WWJ', 'A', '', '', '', '', 'F', 'F', " +
			"'F', 'F', 'F', 'F', 'F', '0', '', '001000000000000000000000000000', 0, 0.00, 0, 0.00, '', 0.00, '', " +
			"'', '', 0.00, 400.00, 0.00, 1, 0, NULL, 219045, 219045, '1503270013', '', '', '', '', '', '', 'F', " +
			"'F', '', 'ADMIN', '2016-11-10 09:06:39', 'ADMIN', '2016-11-10 09:06:45', NULL);\n";

	private static DataSource ds;

	@BeforeClass
	public static void before() {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-config.xml");
		ListableBeanFactory factory = (ListableBeanFactory)context;
		ds = (SpringShardingDataSource) context.getBean("shardingDataSource");
	}

	@Test
	public void dqlTest() throws SQLException {
		PreparedStatement ps = ds.getConnection().prepareStatement("select * from master_base limit 10");
		ps.execute();
	}

	@Test
	public void dml() throws SQLException {
		// 强制路由
		HintManager hintManager = HintManager.getInstance();

		try {
			//hintManager.addDatabaseShardingValue("master_base", 0);
			// 强制路由到ds0
			hintManager.setDatabaseShardingValue(0);

			Connection connection = ds.getConnection();
			connection.createStatement().execute(insert);

		} finally {
			hintManager.close();
		}
	}


}
