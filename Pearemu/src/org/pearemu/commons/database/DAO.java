package org.pearemu.commons.database;

import java.sql.ResultSet;
/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public abstract class DAO<T extends Model> {

    protected abstract String tableName();

    protected String primaryKey() {
        return "id";
    }
    
    abstract protected T createByResultSet(ResultSet RS);
}
