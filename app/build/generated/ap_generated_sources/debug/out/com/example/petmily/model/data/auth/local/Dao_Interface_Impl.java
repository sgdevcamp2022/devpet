package com.example.petmily.model.data.auth.local;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Dao_Interface_Impl implements Dao_Interface {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TokenSQL> __insertionAdapterOfTokenSQL;

  public Dao_Interface_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTokenSQL = new EntityInsertionAdapter<TokenSQL>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `TokenSQL` (`userId`,`accessToken`,`refreshToken`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TokenSQL value) {
        if (value.userId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.userId);
        }
        if (value.getAccessToken() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAccessToken());
        }
        if (value.getRefreshToken() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRefreshToken());
        }
      }
    };
  }

  @Override
  public void insertToken(final TokenSQL token) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTokenSQL.insert(token);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public TokenSQL getToken() {
    final String _sql = "SELECT * FROM TokenSQL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfAccessToken = CursorUtil.getColumnIndexOrThrow(_cursor, "accessToken");
      final int _cursorIndexOfRefreshToken = CursorUtil.getColumnIndexOrThrow(_cursor, "refreshToken");
      final TokenSQL _result;
      if(_cursor.moveToFirst()) {
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        final String _tmpAccessToken;
        if (_cursor.isNull(_cursorIndexOfAccessToken)) {
          _tmpAccessToken = null;
        } else {
          _tmpAccessToken = _cursor.getString(_cursorIndexOfAccessToken);
        }
        final String _tmpRefreshToken;
        if (_cursor.isNull(_cursorIndexOfRefreshToken)) {
          _tmpRefreshToken = null;
        } else {
          _tmpRefreshToken = _cursor.getString(_cursorIndexOfRefreshToken);
        }
        _result = new TokenSQL(_tmpUserId,_tmpAccessToken,_tmpRefreshToken);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
