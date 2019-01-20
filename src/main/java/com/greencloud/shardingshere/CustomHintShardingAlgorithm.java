package com.greencloud.shardingshere;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.hint.HintShardingAlgorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 强制路由策略
 * @author leejianhao
 */
public class CustomHintShardingAlgorithm implements HintShardingAlgorithm {

	/**
	 * ds0,ds1
	 * 设置的值为0，制定datasource为0，则路由的datasource为ds0
	 * @param collection
	 * @param shardingValue
	 * @return
	 */
	@Override
	public Collection<String> doSharding(Collection<String> collection, ShardingValue shardingValue) {

		ListShardingValue sv = (ListShardingValue) shardingValue;

		Collection<Integer> setValues = sv.getValues();
		int setValue = setValues.iterator().next();

		Set<String> shardings = new HashSet<>(collection.size());
		for (String col : collection) {
			if (col.contains(setValue+"")) {
				shardings.add(col);
			}
		}

		return shardings;
	}
}
