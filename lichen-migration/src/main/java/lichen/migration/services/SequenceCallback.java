package lichen.migration.services;

import lichen.migration.model.SequenceDefinition;

/**
 * 序列回调类
 * @author shen
 *
 */
public interface SequenceCallback {

	void doInSequence(SequenceDefinition seqDefinition) throws Throwable ;

}
