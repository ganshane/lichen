package lichen.migration.model;

/**
 * 指定列的精度
 * @author jcai
 */
public interface Precision extends ColumnOption{
    /**
     * 得到列的精度
     * @return 列的精度
     */
    public int getValue();
}
