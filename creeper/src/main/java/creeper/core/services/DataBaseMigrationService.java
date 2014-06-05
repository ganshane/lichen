package creeper.core.services;

/**
 * 数据库操作接口
 * @author shen
 *
 */
public interface DataBaseMigrationService {
	
	/**
	 * 执行安装脚本
	 * @return
	 */
	public void dbSetup();
	
}
