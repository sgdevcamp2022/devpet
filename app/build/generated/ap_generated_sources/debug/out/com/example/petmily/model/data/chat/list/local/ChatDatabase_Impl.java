package com.example.petmily.model.data.chat.list.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChatDatabase_Impl extends ChatDatabase {
  private volatile ListDao_Interface _listDaoInterface;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ChatListSQL` (`roodId` TEXT NOT NULL, `timeLog` TEXT, `senderNickname` TEXT, `profileImage` TEXT, `sender` TEXT, `count` INTEGER NOT NULL, `lastText` TEXT, `alarm` TEXT, PRIMARY KEY(`roodId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'eb241e02935c86185dddaefdaefb3d21')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `ChatListSQL`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsChatListSQL = new HashMap<String, TableInfo.Column>(8);
        _columnsChatListSQL.put("roodId", new TableInfo.Column("roodId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatListSQL.put("timeLog", new TableInfo.Column("timeLog", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatListSQL.put("senderNickname", new TableInfo.Column("senderNickname", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatListSQL.put("profileImage", new TableInfo.Column("profileImage", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatListSQL.put("sender", new TableInfo.Column("sender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatListSQL.put("count", new TableInfo.Column("count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatListSQL.put("lastText", new TableInfo.Column("lastText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChatListSQL.put("alarm", new TableInfo.Column("alarm", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChatListSQL = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChatListSQL = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChatListSQL = new TableInfo("ChatListSQL", _columnsChatListSQL, _foreignKeysChatListSQL, _indicesChatListSQL);
        final TableInfo _existingChatListSQL = TableInfo.read(_db, "ChatListSQL");
        if (! _infoChatListSQL.equals(_existingChatListSQL)) {
          return new RoomOpenHelper.ValidationResult(false, "ChatListSQL(com.example.petmily.model.data.chat.list.local.ChatListSQL).\n"
                  + " Expected:\n" + _infoChatListSQL + "\n"
                  + " Found:\n" + _existingChatListSQL);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "eb241e02935c86185dddaefdaefb3d21", "b4b883cba3d9119ded2335fa79b9a939");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "ChatListSQL");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `ChatListSQL`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ListDao_Interface.class, ListDao_Interface_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public ListDao_Interface chatListDao() {
    if (_listDaoInterface != null) {
      return _listDaoInterface;
    } else {
      synchronized(this) {
        if(_listDaoInterface == null) {
          _listDaoInterface = new ListDao_Interface_Impl(this);
        }
        return _listDaoInterface;
      }
    }
  }
}
