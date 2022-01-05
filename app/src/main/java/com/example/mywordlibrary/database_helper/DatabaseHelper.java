package com.example.mywordlibrary.database_helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mywordlibrary.models.Word;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "my_notes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NOTE_CREATE =
            "CREATE TABLE " + TablesInfo.WordsEntry.TABLE_NAME + " (" +
                    TablesInfo.WordsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TablesInfo.WordsEntry.COLUMN_WORD + " TEXT ," +
                    TablesInfo.WordsEntry.COLUMN_MEANING + " TEXT ," +
                    TablesInfo.WordsEntry.COLUMN_SPELLING + " TEXT ," +
                    TablesInfo.WordsEntry.COLUMN_EXAMPLE + " TEXT ," +
                    TablesInfo.WordsEntry.COLUMN_IS_LEARNING + " INT " +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_NOTE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TablesInfo.WordsEntry.TABLE_NAME);

        onCreate(db);
    }

    public void addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TablesInfo.WordsEntry.COLUMN_WORD, word.getWord());
        cv.put(TablesInfo.WordsEntry.COLUMN_MEANING, word.getMeaning());
        cv.put(TablesInfo.WordsEntry.COLUMN_SPELLING, word.getSpelling());
        cv.put(TablesInfo.WordsEntry.COLUMN_EXAMPLE, word.getExample());
        cv.put(TablesInfo.WordsEntry.COLUMN_IS_LEARNING,1);


        long result = db.insert(TablesInfo.WordsEntry.TABLE_NAME, null, cv);

        if (result > -1)
            Log.i("DatabaseHelper", "Not başarıyla kaydedildi");
        else
            Log.i("DatabaseHelper", "Not kaydedilemedi");

        db.close();
    }


    @SuppressLint("Range")
    public ArrayList<Word> getLearningWordList() {
        ArrayList<Word> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TablesInfo.WordsEntry.COLUMN_ID,
                TablesInfo.WordsEntry.COLUMN_WORD,
                TablesInfo.WordsEntry.COLUMN_MEANING,
                TablesInfo.WordsEntry.COLUMN_SPELLING,
                TablesInfo.WordsEntry.COLUMN_EXAMPLE,
                TablesInfo.WordsEntry.COLUMN_IS_LEARNING,
        };

        Cursor c = db.rawQuery("SELECT * FROM "+TablesInfo.WordsEntry.TABLE_NAME+" WHERE "+TablesInfo.WordsEntry.COLUMN_IS_LEARNING+"= 1",null);
        while (c.moveToNext()) {
            data.add(new Word(
                    c.getInt(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_ID)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_WORD)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_MEANING)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_SPELLING)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_EXAMPLE)),
                    c.getInt(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_IS_LEARNING))
            ));

        }

        c.close();
        db.close();

        return data;
    }

    @SuppressLint("Range")
    public ArrayList<Word> getLearnedWordList() {
        ArrayList<Word> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TablesInfo.WordsEntry.COLUMN_ID,
                TablesInfo.WordsEntry.COLUMN_WORD,
                TablesInfo.WordsEntry.COLUMN_MEANING,
                TablesInfo.WordsEntry.COLUMN_SPELLING,
                TablesInfo.WordsEntry.COLUMN_EXAMPLE,
                TablesInfo.WordsEntry.COLUMN_IS_LEARNING,
        };

        Cursor c = db.rawQuery("SELECT * FROM "+TablesInfo.WordsEntry.TABLE_NAME+" WHERE "+TablesInfo.WordsEntry.COLUMN_IS_LEARNING+"= 0",null);
        while (c.moveToNext()) {
            data.add(new Word(
                    c.getInt(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_ID)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_WORD)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_MEANING)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_SPELLING)),
                    c.getString(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_EXAMPLE)),
                    c.getInt(c.getColumnIndex(TablesInfo.WordsEntry.COLUMN_IS_LEARNING))
            ));

        }

        c.close();
        db.close();

        return data;
    }

    public void updateWord(Word word){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TablesInfo.WordsEntry.COLUMN_ID,word.getId());
        cv.put(TablesInfo.WordsEntry.COLUMN_WORD, word.getWord());
        cv.put(TablesInfo.WordsEntry.COLUMN_MEANING, word.getMeaning());
        cv.put(TablesInfo.WordsEntry.COLUMN_SPELLING, word.getSpelling());
        cv.put(TablesInfo.WordsEntry.COLUMN_EXAMPLE, word.getExample());
        cv.put(TablesInfo.WordsEntry.COLUMN_IS_LEARNING,word.isLearning());
        Log.i("DatabaseHelper:ID",Integer.toString(word.getId()));

        long result = db.update(TablesInfo.WordsEntry.TABLE_NAME, cv, TablesInfo.WordsEntry.COLUMN_ID+"=?",new String[]{Integer.toString(word.getId())});


        if (result > -1)
            Log.i("DatabaseHelper", "Not başarıyla güncellendi");
        else
            Log.i("DatabaseHelper", "Not başarıyla güncellenemedi");

        db.close();

    }
}
