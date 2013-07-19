package lichen.migration.core;

import lichen.migration.core.model.Table;



public interface CreateTableCallback{

	public  void doCreateAction(Table t);
}
