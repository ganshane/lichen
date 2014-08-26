package lichen.creeper.user.db;

import javax.inject.Inject;

import lichen.migration.services.Migration;
import lichen.migration.services.MigrationHelper;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.util.ByteSource;

/**
 * 添加默认管理员用户
 * @author shen
 *
 */
public class Migrate_20140826101010_AddAdminUser implements Migration{
    @Inject
    private MigrationHelper _helper;
    
    @Override
    public void up() throws Throwable {
    	DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName(DefaultPasswordService.DEFAULT_HASH_ALGORITHM);
        hashService.setHashIterations(DefaultPasswordService.DEFAULT_HASH_ITERATIONS);
        hashService.setGeneratePublicSalt(true);
        //用Shiro方法加密密码
    	String passwordEncrypted = new Shiro1CryptFormat().format(hashService.computeHash(new HashRequest.Builder().setSource(ByteSource.Util.bytes("1234")).build()));;
        _helper.executeSQL("insert into users(id,name,pass,status) values('00000000000000000000000000000000','webmaster','"+passwordEncrypted+"',0)");
    }

    @Override
    public void down() throws Throwable {
    	_helper.executeSQL("delete from users where id='00000000000000000000000000000000'");
    }
}
