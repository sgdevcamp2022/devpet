package com.example.petmily.model.data.chat.room.local;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.petmily.model.data.chat.room.Message;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class RoomDao_Interface_Impl implements RoomDao_Interface {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RoomSQL> __insertionAdapterOfRoomSQL;

  private final EntityDeletionOrUpdateAdapter<RoomSQL> __updateAdapterOfRoomSQL;

  public RoomDao_Interface_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRoomSQL = new EntityInsertionAdapter<RoomSQL>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `RoomSQL` (`roomId`,`senderName`,`receiverName`,`messages`,`timeLog`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RoomSQL value) {
        if (value.roomId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.roomId);
        }
        if (value.senderName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.senderName);
        }
        if (value.receiverName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.receiverName);
        }
        final String _tmp = Converters.ChatMessageToJson(value.messages);
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.timeLog == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.timeLog);
        }
      }
    };
    this.__updateAdapterOfRoomSQL = new EntityDeletionOrUpdateAdapter<RoomSQL>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `RoomSQL` SET `roomId` = ?,`senderName` = ?,`receiverName` = ?,`messages` = ?,`timeLog` = ? WHERE `roomId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RoomSQL value) {
        if (value.roomId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.roomId);
        }
        if (value.senderName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.senderName);
        }
        if (value.receiverName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.receiverName);
        }
        final String _tmp = Converters.ChatMessageToJson(value.messages);
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.timeLog == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.timeLog);
        }
        if (value.roomId == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.roomId);
        }
      }
    };
  }

  @Override
  public void insertMessage(final List<RoomSQL> roomSQLList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfRoomSQL.insert(roomSQLList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateMessage(final RoomSQL roomSQLList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfRoomSQL.handle(roomSQLList);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public RoomSQL getMessage(final String roomId) {
    final String _sql = "SELECT * FROM RoomSQL WHERE roomId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (roomId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, roomId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
      final int _cursorIndexOfSenderName = CursorUtil.getColumnIndexOrThrow(_cursor, "senderName");
      final int _cursorIndexOfReceiverName = CursorUtil.getColumnIndexOrThrow(_cursor, "receiverName");
      final int _cursorIndexOfMessages = CursorUtil.getColumnIndexOrThrow(_cursor, "messages");
      final int _cursorIndexOfTimeLog = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLog");
      final RoomSQL _result;
      if(_cursor.moveToFirst()) {
        final String _tmpRoomId;
        if (_cursor.isNull(_cursorIndexOfRoomId)) {
          _tmpRoomId = null;
        } else {
          _tmpRoomId = _cursor.getString(_cursorIndexOfRoomId);
        }
        final String _tmpSenderName;
        if (_cursor.isNull(_cursorIndexOfSenderName)) {
          _tmpSenderName = null;
        } else {
          _tmpSenderName = _cursor.getString(_cursorIndexOfSenderName);
        }
        final String _tmpReceiverName;
        if (_cursor.isNull(_cursorIndexOfReceiverName)) {
          _tmpReceiverName = null;
        } else {
          _tmpReceiverName = _cursor.getString(_cursorIndexOfReceiverName);
        }
        final List<Message> _tmpMessages;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfMessages)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfMessages);
        }
        _tmpMessages = Converters.jsonToChatMessage(_tmp);
        final String _tmpTimeLog;
        if (_cursor.isNull(_cursorIndexOfTimeLog)) {
          _tmpTimeLog = null;
        } else {
          _tmpTimeLog = _cursor.getString(_cursorIndexOfTimeLog);
        }
        _result = new RoomSQL(_tmpRoomId,_tmpSenderName,_tmpReceiverName,_tmpMessages,_tmpTimeLog);
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
