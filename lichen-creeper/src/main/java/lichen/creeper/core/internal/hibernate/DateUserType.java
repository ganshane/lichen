package lichen.creeper.core.internal.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

/**
 * 实现针对数据库中存放的秒数进行特定读取
 * @author jcai
 */
public class DateUserType implements UserType{
    private int sqlType = Types.INTEGER;
    @Override
    public int[] sqlTypes() {
        return new int[]{sqlType};
    }

    @Override
    public Class<?> returnedClass() {
        return Date.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if(x==y){
            return true;
        }else if(x==null||y==null){
            return false;
        }else {
            return x.equals(y);
        }
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        int i = rs.getInt(names[0]);
        if(i != 0)
            return new Date(i*1000);
        else
            return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if(value == null){
            st.setNull(index,sqlType);
        }else{
            st.setInt(index, (int) (((Date)value).getTime()/1000));
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return ((Date)value).getTime();
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return new Date((Long) cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
