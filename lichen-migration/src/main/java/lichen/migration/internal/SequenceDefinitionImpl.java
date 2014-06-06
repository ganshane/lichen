package lichen.migration.internal;

import lichen.migration.model.SequenceDefinition;

/**
 * 序列属性定义
 * @author shen
 * 
 */
class SequenceDefinitionImpl implements SequenceDefinition {

	private int _minvalue;
	private int _maxvalue;
	private int _start;
	private int _increment;

	private DatabaseAdapter _adapter;

	public SequenceDefinitionImpl(DatabaseAdapter adapter) {
		this._adapter = adapter;
	}

	@Override
	public void increment(int increment) {
		this._increment = increment;
	}

	@Override
	public void maxValue(int maxvalue) {
		this._maxvalue = maxvalue;
	}

	@Override
	public void minValue(int minvalue) {
		this._minvalue = minvalue;
	}

	@Override
	public void start(int start) {
		this._start = start;
	}

	@Override
	public int getIncrement() {
		return _increment;
	}

	@Override
	public int getMaxValue() {
		return _maxvalue < _minvalue ? _minvalue : _maxvalue;
	}

	@Override
	public int getMinValue() {
		return _minvalue > _start ? _start : _minvalue;
	}

	@Override
	public int getStart() {
		return _start;
	}

}
