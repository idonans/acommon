package com.idonans.acommon.internal.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.idonans.acommon.AppContext;
import com.idonans.acommon.data.ProcessManager;
import com.idonans.acommon.lang.CommonLog;
import com.idonans.acommon.util.IOUtil;

/**
 * 基于 SQLite 模拟 KV 数据库
 * Created by idonans on 16-4-13.
 */
public class SimpleDB {

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "t_simple";
    private static final String COLUMN_KEY = "c_key";
    private static final String COLUMN_VALUE = "c_value";
    private static final String COLUMN_UPDATE = "c_update";
    private static final String SQL_CREATE_TABLE = "create table t_simple (" +
            "c_key text not null primary key" +
            ",c_value text" +
            ",c_update integer" +
            ")";
    private static final String SQL_CREATE_INDEX = "create index index_simple_update on t_simple(c_update)";

    private final SQLiteOpenHelper mOpenHelper;

    /**
     * 实现中会在数据库名前附加当前进程标识
     */
    public SimpleDB(@NonNull String databaseName) {
        String dbName = ProcessManager.getInstance().getProcessTag() + "_" + databaseName;
        mOpenHelper = new SQLiteOpenHelper(AppContext.getContext(), dbName, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_TABLE);
                db.execSQL(SQL_CREATE_INDEX);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                throw new UnsupportedOperationException(db.getPath() + " onUpgrade " + oldVersion + "->" + newVersion);
            }
        };
    }

    @CheckResult
    public String get(@Nullable String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        Cursor cursor = null;
        try {
            SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_VALUE}, COLUMN_KEY + "=?", new String[]{key}, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(cursor);
        }
        return null;
    }

    public void set(@Nullable String key, @Nullable String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (TextUtils.isEmpty(value)) {
            remove(key);
            return;
        }

        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_KEY, key);
            contentValues.put(COLUMN_VALUE, value);
            contentValues.put(COLUMN_UPDATE, System.currentTimeMillis());
            db.replace(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void touch(@Nullable String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }

        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_UPDATE, System.currentTimeMillis());
            db.update(TABLE_NAME, contentValues, COLUMN_KEY + "=?", new String[]{key});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除多余的旧数据(按时间倒序)，保留指定条数的数据. 返回删除的数据的条数，如果不满足删除条件返回 -1.
     */
    public int trim(int maxRows) {
        if (maxRows < 1) {
            return -1;
        }

        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();

            Cursor cursor = null;
            long lastUpdate = 0L;
            try {
                cursor = db.query(TABLE_NAME, new String[]{COLUMN_UPDATE}, null, null, null, null, COLUMN_UPDATE + " desc", (maxRows - 1) + ",1");
                if (cursor.moveToFirst()) {
                    lastUpdate = cursor.getLong(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.closeQuietly(cursor);
            }

            if (lastUpdate <= 0) {
                return -1;
            }

            return db.delete(TABLE_NAME, COLUMN_UPDATE + "<?", new String[]{String.valueOf(lastUpdate)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 清空数据，返回删除数据的条数, 如果出错，返回 -1.
     */
    public int clear() {
        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
            return db.delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void remove(@Nullable String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_KEY + "=?", new String[]{key});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果失败，返回-1。
     */
    public int count() {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
            cursor = db.rawQuery("select count(*) from " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(cursor);
        }
        return -1;
    }

    /**
     * only for debug
     */
    public void printAllRows() {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
            String dbName = this.mOpenHelper.getDatabaseName();
            String dbPath = db.getPath();
            String tag = "SimpleDB " + dbPath + "[" + dbName + "]";
            CommonLog.d("--" + tag + "--");
            cursor = db.query(TABLE_NAME, new String[]{COLUMN_KEY, COLUMN_VALUE, COLUMN_UPDATE}, null, null, null, null, COLUMN_UPDATE + " desc");
            String key;
            String value;
            long update;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                key = cursor.getString(0);
                value = cursor.getString(1);
                update = cursor.getLong(2);
                CommonLog.d(dbName + " " + update + ", " + key + ", " + value);
            }
            CommonLog.d("--" + tag + "-- end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(cursor);
        }
    }

}
