package com.example.petmily.model.data.chat.list.local;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ListDao_Interface_Impl implements ListDao_Interface {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChatListSQL> __insertionAdapterOfChatListSQL;

  public ListDao_Interface_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChatListSQL = new EntityInsertionAdapter<ChatListSQL>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ChatListSQL` (`roodId`,`timeLog`,`senderNickname`,`profileImage`,`sender`,`count`,`lastText`,`alarm`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ChatListSQL value) {
        if (value.roodId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.roodId);
        }
        if (value.timeLog == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.timeLog);
        }
        if (value.senderNickname == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.senderNickname);
        }
        if (value.profileImage == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.profileImage);
        }
        if (value.sender == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.sender);
        }
        stmt.bindLong(6, value.count);
        if (value.lastText == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.lastText);
        }
        if (value.alarm == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.alarm);
        }
      }
    };
  }

  @Override
  public void insertMessage(final List<ChatListSQL> chatSQLList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfChatListSQL.insert(chatSQLList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<ChatListSQL> getChatList() {
    final String _sql = "SELECT * FROM ChatListSQL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfRoodId = CursorUtil.getColumnIndexOrThrow(_cursor, "roodId");
      final int _cursorIndexOfTimeLog = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLog");
      final int _cursorIndexOfSenderNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "senderNickname");
      final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImage");
      final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
      final int _cursorIndexOfCount = CursorUtil.getColumnIndexOrThrow(_cursor, "count");
      final int _cursorIndexOfLastText = CursorUtil.getColumnIndexOrThrow(_cursor, "lastText");
      final int _cursorIndexOfAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "alarm");
      final List<ChatListSQL> _result = new ArrayList<ChatListSQL>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ChatListSQL _item;
        final String _tmpRoodId;
        if (_cursor.isNull(_cursorIndexOfRoodId)) {
          _tmpRoodId = null;
        } else {
          _tmpRoodId = _cursor.getString(_cursorIndexOfRoodId);
        }
        final String _tmpTimeLog;
        if (_cursor.isNull(_cursorIndexOfTimeLog)) {
          _tmpTimeLog = null;
        } else {
          _tmpTimeLog = _cursor.getString(_cursorIndexOfTimeLog);
        }
        final String _tmpSenderNickname;
        if (_cursor.isNull(_cursorIndexOfSenderNickname)) {
          _tmpSenderNickname = null;
        } else {
          _tmpSenderNickname = _cursor.getString(_cursorIndexOfSenderNickname);
        }
        final String _tmpProfileImage;
        if (_cursor.isNull(_cursorIndexOfProfileImage)) {
          _tmpProfileImage = null;
        } else {
          _tmpProfileImage = _cursor.getString(_cursorIndexOfProfileImage);
        }
        final String _tmpSender;
        if (_cursor.isNull(_cursorIndexOfSender)) {
          _tmpSender = null;
        } else {
          _tmpSender = _cursor.getString(_cursorIndexOfSender);
        }
        final int _tmpCount;
        _tmpCount = _cursor.getInt(_cursorIndexOfCount);
        final String _tmpLastText;
        if (_cursor.isNull(_cursorIndexOfLastText)) {
          _tmpLastText = null;
        } else {
          _tmpLastText = _cursor.getString(_cursorIndexOfLastText);
        }
        final String _tmpAlarm;
        if (_cursor.isNull(_cursorIndexOfAlarm)) {
          _tmpAlarm = null;
        } else {
          _tmpAlarm = _cursor.getString(_cursorIndexOfAlarm);
        }
        _item = new ChatListSQL(_tmpRoodId,_tmpTimeLog,_tmpSenderNickname,_tmpProfileImage,_tmpSender,_tmpCount,_tmpLastText,_tmpAlarm);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ChatListSQL getRoomId(final String id) {
    final String _sql = "SELECT * FROM ChatListSQL WHERE roodId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfRoodId = CursorUtil.getColumnIndexOrThrow(_cursor, "roodId");
      final int _cursorIndexOfTimeLog = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLog");
      final int _cursorIndexOfSenderNickname = CursorUtil.getColumnIndexOrThrow(_cursor, "senderNickname");
      final int _cursorIndexOfProfileImage = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImage");
      final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
      final int _cursorIndexOfCount = CursorUtil.getColumnIndexOrThrow(_cursor, "count");
      final int _cursorIndexOfLastText = CursorUtil.getColumnIndexOrThrow(_cursor, "lastText");
      final int _cursorIndexOfAlarm = CursorUtil.getColumnIndexOrThrow(_cursor, "alarm");
      final ChatListSQL _result;
      if(_cursor.moveToFirst()) {
        final String _tmpRoodId;
        if (_cursor.isNull(_cursorIndexOfRoodId)) {
          _tmpRoodId = null;
        } else {
          _tmpRoodId = _cursor.getString(_cursorIndexOfRoodId);
        }
        final String _tmpTimeLog;
        if (_cursor.isNull(_cursorIndexOfTimeLog)) {
          _tmpTimeLog = null;
        } else {
          _tmpTimeLog = _cursor.getString(_cursorIndexOfTimeLog);
        }
        final String _tmpSenderNickname;
        if (_cursor.isNull(_cursorIndexOfSenderNickname)) {
          _tmpSenderNickname = null;
        } else {
          _tmpSenderNickname = _cursor.getString(_cursorIndexOfSenderNickname);
        }
        final String _tmpProfileImage;
        if (_cursor.isNull(_cursorIndexOfProfileImage)) {
          _tmpProfileImage = null;
        } else {
          _tmpProfileImage = _cursor.getString(_cursorIndexOfProfileImage);
        }
        final String _tmpSender;
        if (_cursor.isNull(_cursorIndexOfSender)) {
          _tmpSender = null;
        } else {
          _tmpSender = _cursor.getString(_cursorIndexOfSender);
        }
        final int _tmpCount;
        _tmpCount = _cursor.getInt(_cursorIndexOfCount);
        final String _tmpLastText;
        if (_cursor.isNull(_cursorIndexOfLastText)) {
          _tmpLastText = null;
        } else {
          _tmpLastText = _cursor.getString(_cursorIndexOfLastText);
        }
        final String _tmpAlarm;
        if (_cursor.isNull(_cursorIndexOfAlarm)) {
          _tmpAlarm = null;
        } else {
          _tmpAlarm = _cursor.getString(_cursorIndexOfAlarm);
        }
        _result = new ChatListSQL(_tmpRoodId,_tmpTimeLog,_tmpSenderNickname,_tmpProfileImage,_tmpSender,_tmpCount,_tmpLastText,_tmpAlarm);
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
