package creeper.core.services.db;

/**
 * 数据库操作接口
 * @author shen
 *
 */
public interface DatabaseMigration {
	
	/**
	 * 执行安装脚本
	 * @return
	 */
	public void dbSetup();
	
}
