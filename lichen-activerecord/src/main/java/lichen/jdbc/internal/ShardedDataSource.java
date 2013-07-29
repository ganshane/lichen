package lichen.jdbc.internal;

import javax.sql.DataSource;

/**
 * User: erdinc
 * Date: Oct 15, 2009
 * Time: 11:59:24 PM
 */
public interface ShardedDataSource {
   public DataSource getDataSource(int shardNo);
   public int getShardCount();
   public void runMaintenanceJob();
}
