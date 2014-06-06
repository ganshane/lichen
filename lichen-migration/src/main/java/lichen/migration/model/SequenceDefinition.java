package lichen.migration.model;

/**
 * 序列的定义，提供增加序列属性的办法.
 * @author shen
 *
 */
public interface SequenceDefinition {
	
	/**
	 * 最小值
	 * @param minvalue
	 */
	void minValue(int minvalue);
	
	/**
	 * 最大值
	 * @param maxvalue
	 */
	void maxValue(int maxvalue);
	
	/**
	 * 起始值
	 * @param start
	 */
	void start(int start);
	
	/**
	 * 增量
	 * @param increment
	 */
	void increment(int increment);
	
	/**
	 * 获取最小值
	 * @return
	 */
	int getMinValue();
	
	/**
	 * 获取最大值
	 * @return
	 */
	int getMaxValue();
	
	/**
	 * 获取起始值
	 * @return
	 */
	int getStart();
	
	/**
	 * 获取增量
	 * @return
	 */
	int getIncrement();
	
}
