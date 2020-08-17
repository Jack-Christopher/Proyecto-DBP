package com.example.administradordetareas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class BaseHelper extends SQLiteOpenHelper
{
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_EVENTO+ " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_FECHA+ " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_HORA+ " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPCION+ " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";


    public BaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);

    }

    public static final class FeedReaderContract
    {
        private FeedReaderContract() {}

        public static class FeedEntry implements BaseColumns
        {
            public static final String TABLE_NAME = "TAREAS";
            public static final String COLUMN_NAME_EVENTO = "EVENTO";
            public static final String COLUMN_NAME_FECHA = "FECHA";
            public static final String COLUMN_NAME_HORA = "HORA";
            public static final String COLUMN_NAME_DESCRIPCION = "DESCRIPCION";
        }
    }

}














