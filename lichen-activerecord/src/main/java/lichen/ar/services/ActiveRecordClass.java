package lichen.ar.services;

/**
 * @author jcai
 */
public interface ActiveRecordClass<T> {
    /**
     * 得到实体的class名称.
     * @return 实体的class名称
     */
    Class<T> entityClass();
    String pk();
}
