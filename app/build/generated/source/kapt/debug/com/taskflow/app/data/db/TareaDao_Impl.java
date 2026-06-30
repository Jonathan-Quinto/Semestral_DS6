package com.taskflow.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.taskflow.app.model.Tarea;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TareaDao_Impl implements TareaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tarea> __insertionAdapterOfTarea;

  private final EntityDeletionOrUpdateAdapter<Tarea> __deletionAdapterOfTarea;

  private final EntityDeletionOrUpdateAdapter<Tarea> __updateAdapterOfTarea;

  private final SharedSQLiteStatement __preparedStmtOfAsignarParticipante;

  private final SharedSQLiteStatement __preparedStmtOfDesasignar;

  public TareaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTarea = new EntityInsertionAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tareas` (`id`,`titulo`,`descripcion`,`prioridad`,`categoria`,`completada`,`fechaCreacion`,`fechaLimite`,`creadaPor`,`asignadoA`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitulo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitulo());
        }
        if (entity.getDescripcion() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescripcion());
        }
        if (entity.getPrioridad() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPrioridad());
        }
        if (entity.getCategoria() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategoria());
        }
        final int _tmp = entity.getCompletada() ? 1 : 0;
        statement.bindLong(6, _tmp);
        if (entity.getFechaCreacion() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFechaCreacion());
        }
        if (entity.getFechaLimite() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFechaLimite());
        }
        statement.bindLong(9, entity.getCreadaPor());
        if (entity.getAsignadoA() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getAsignadoA());
        }
      }
    };
    this.__deletionAdapterOfTarea = new EntityDeletionOrUpdateAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tareas` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTarea = new EntityDeletionOrUpdateAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tareas` SET `id` = ?,`titulo` = ?,`descripcion` = ?,`prioridad` = ?,`categoria` = ?,`completada` = ?,`fechaCreacion` = ?,`fechaLimite` = ?,`creadaPor` = ?,`asignadoA` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitulo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitulo());
        }
        if (entity.getDescripcion() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescripcion());
        }
        if (entity.getPrioridad() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPrioridad());
        }
        if (entity.getCategoria() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategoria());
        }
        final int _tmp = entity.getCompletada() ? 1 : 0;
        statement.bindLong(6, _tmp);
        if (entity.getFechaCreacion() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFechaCreacion());
        }
        if (entity.getFechaLimite() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFechaLimite());
        }
        statement.bindLong(9, entity.getCreadaPor());
        if (entity.getAsignadoA() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getAsignadoA());
        }
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfAsignarParticipante = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tareas SET asignadoA = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDesasignar = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tareas SET asignadoA = NULL WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertar(final Tarea tarea, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTarea.insert(tarea);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object eliminar(final Tarea tarea, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTarea.handle(tarea);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizar(final Tarea tarea, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTarea.handle(tarea);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object asignarParticipante(final int tareaId, final int participanteId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfAsignarParticipante.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, participanteId);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, tareaId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfAsignarParticipante.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object desasignar(final int tareaId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDesasignar.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, tareaId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDesasignar.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object obtenerPorId(final int id, final Continuation<? super Tarea> $completion) {
    final String _sql = "SELECT * FROM tareas WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Tarea>() {
      @Override
      @Nullable
      public Tarea call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final Tarea _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _result = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Tarea>> obtenerPorLider(final int liderId) {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        WHERE creadaPor = ?\n"
            + "        ORDER BY completada ASC,\n"
            + "        CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Tarea>> obtenerPorParticipante(final int participanteId) {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        WHERE asignadoA = ?\n"
            + "        ORDER BY completada ASC,\n"
            + "        CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, participanteId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Tarea>> obtenerSinAsignar(final int liderId) {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        WHERE creadaPor = ? AND asignadoA IS NULL\n"
            + "        ORDER BY CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Tarea>> obtenerPorCategoriaYLider(final int liderId, final String categoria) {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        WHERE creadaPor = ? AND categoria = ?\n"
            + "        ORDER BY completada ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    _argIndex = 2;
    if (categoria == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoria);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Tarea>> obtenerPorCategoriaYParticipante(final int participanteId,
      final String categoria) {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        WHERE asignadoA = ? AND categoria = ?\n"
            + "        ORDER BY completada ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, participanteId);
    _argIndex = 2;
    if (categoria == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoria);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Tarea>> buscarPorLider(final int liderId, final String texto) {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        WHERE creadaPor = ? AND titulo LIKE '%' || ? || '%'\n"
            + "        ORDER BY completada ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    _argIndex = 2;
    if (texto == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, texto);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Tarea>> buscarPorParticipante(final int participanteId, final String texto) {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        WHERE asignadoA = ? AND titulo LIKE '%' || ? || '%'\n"
            + "        ORDER BY completada ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, participanteId);
    _argIndex = 2;
    if (texto == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, texto);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object contarTotalPorLider(final int liderId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE creadaPor = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarCompletadasPorLider(final int liderId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE creadaPor = ? AND completada = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarTotalPorParticipante(final int participanteId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE asignadoA = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, participanteId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarCompletadasPorParticipante(final int participanteId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE asignadoA = ? AND completada = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, participanteId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarPorCategoriaYLider(final int liderId, final String categoria,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE creadaPor = ? AND categoria = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    _argIndex = 2;
    if (categoria == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoria);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarPorCategoriaYParticipante(final int participanteId, final String categoria,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE asignadoA = ? AND categoria = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, participanteId);
    _argIndex = 2;
    if (categoria == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoria);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarSinAsignarPorLider(final int liderId,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE creadaPor = ? AND asignadoA IS NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, liderId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Tarea>> obtenerTodas() {
    final String _sql = "\n"
            + "        SELECT * FROM tareas \n"
            + "        ORDER BY completada ASC,\n"
            + "        CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tareas"}, new Callable<List<Tarea>>() {
      @Override
      @NonNull
      public List<Tarea> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrioridad = CursorUtil.getColumnIndexOrThrow(_cursor, "prioridad");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfCompletada = CursorUtil.getColumnIndexOrThrow(_cursor, "completada");
          final int _cursorIndexOfFechaCreacion = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaCreacion");
          final int _cursorIndexOfFechaLimite = CursorUtil.getColumnIndexOrThrow(_cursor, "fechaLimite");
          final int _cursorIndexOfCreadaPor = CursorUtil.getColumnIndexOrThrow(_cursor, "creadaPor");
          final int _cursorIndexOfAsignadoA = CursorUtil.getColumnIndexOrThrow(_cursor, "asignadoA");
          final List<Tarea> _result = new ArrayList<Tarea>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarea _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            final String _tmpDescripcion;
            if (_cursor.isNull(_cursorIndexOfDescripcion)) {
              _tmpDescripcion = null;
            } else {
              _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            }
            final String _tmpPrioridad;
            if (_cursor.isNull(_cursorIndexOfPrioridad)) {
              _tmpPrioridad = null;
            } else {
              _tmpPrioridad = _cursor.getString(_cursorIndexOfPrioridad);
            }
            final String _tmpCategoria;
            if (_cursor.isNull(_cursorIndexOfCategoria)) {
              _tmpCategoria = null;
            } else {
              _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            }
            final boolean _tmpCompletada;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCompletada);
            _tmpCompletada = _tmp != 0;
            final String _tmpFechaCreacion;
            if (_cursor.isNull(_cursorIndexOfFechaCreacion)) {
              _tmpFechaCreacion = null;
            } else {
              _tmpFechaCreacion = _cursor.getString(_cursorIndexOfFechaCreacion);
            }
            final String _tmpFechaLimite;
            if (_cursor.isNull(_cursorIndexOfFechaLimite)) {
              _tmpFechaLimite = null;
            } else {
              _tmpFechaLimite = _cursor.getString(_cursorIndexOfFechaLimite);
            }
            final int _tmpCreadaPor;
            _tmpCreadaPor = _cursor.getInt(_cursorIndexOfCreadaPor);
            final Integer _tmpAsignadoA;
            if (_cursor.isNull(_cursorIndexOfAsignadoA)) {
              _tmpAsignadoA = null;
            } else {
              _tmpAsignadoA = _cursor.getInt(_cursorIndexOfAsignadoA);
            }
            _item = new Tarea(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpPrioridad,_tmpCategoria,_tmpCompletada,_tmpFechaCreacion,_tmpFechaLimite,_tmpCreadaPor,_tmpAsignadoA);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object contarTotal(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarCompletadas(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE completada = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object contarPorCategoria(final String categoria,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM tareas WHERE categoria = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (categoria == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, categoria);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
