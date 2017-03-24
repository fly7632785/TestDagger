package com.jafir.testdagger.db;

import com.jafir.testdagger.model.User;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by jafir on 2017/3/24.
 */
@Database(name = MyFlowDB.NAME,version = MyFlowDB.VERSION)
public class MyFlowDB {

    public static final String NAME = "mydb";
    public static final int VERSION = 2;


    @Migration(version = 2, database = MyFlowDB.class)
    public static class Migration2 extends AlterTableMigration<User> {

        public Migration2(Class<User> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "fid");
        }
    }
}
