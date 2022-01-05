package com.example.mywordlibrary.database_helper;


import android.provider.BaseColumns;

public class TablesInfo {

    public static final class WordsEntry implements BaseColumns {
        public static final String TABLE_NAME = "words";

        public static final String COLUMN_ID = "word_id";
        public static final String COLUMN_WORD = "word_word";
        public static final String COLUMN_MEANING = "word_meaning";
        public static final String COLUMN_SPELLING = "word_spelling";
        public static final String COLUMN_EXAMPLE = "word_example";
        public static final String COLUMN_IS_LEARNING = "word_is_learning";

    }

}