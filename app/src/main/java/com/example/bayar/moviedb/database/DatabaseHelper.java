package com.example.bayar.moviedb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movie.db";

    public static final String MOVIE_TABLE = "movies";
    public static final String MOVIE_ID = "_id";
    public static final String MOVIE_POSTER_PATH = "poster_path";
    public static final String MOVIE_ADULT = "is_adult";
    public static final String MOVIE_OVERVIEW = "overview";
    public static final String MOVIE_RELEASE_DATE = "release_date";
    public static final String MOVIE_ORIGINAL_TITLE = "original_title";
    public static final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_BACKDROP_PATH = "backdrop_path";
    public static final String MOVIE_POPULARITY = "popularity";
    public static final String MOVIE_VOTE_COUNT = "vote_count";
    public static final String MOVIE_HAS_VIDEO = "video";
    public static final String MOVIE_VOTE_AVERAGE = "vote_average";

    public static final String[] MOVIE_COLUMNS = {MOVIE_ID, MOVIE_POSTER_PATH, MOVIE_ADULT, MOVIE_OVERVIEW,
    MOVIE_RELEASE_DATE, MOVIE_ORIGINAL_TITLE, MOVIE_ORIGINAL_LANGUAGE,
    MOVIE_TITLE, MOVIE_BACKDROP_PATH, MOVIE_POPULARITY, MOVIE_VOTE_COUNT, MOVIE_HAS_VIDEO,
    MOVIE_VOTE_AVERAGE};

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateTable = String.format(
                "CREATE TABLE %s(" +
                        "%s integer primary key, " +
                        "%s text, " +
                        "%s integer, " +
                        "%s text, " +
                        "%s text, " +
                        "%s text, " +
                        "%s text, " +
                        "%s text, " +
                        "%s text, " +
                        "%s real, " +
                        "%s integer, " +
                        "%s integer, " +
                        "%s real)",
                MOVIE_TABLE, MOVIE_ID, MOVIE_POSTER_PATH, MOVIE_ADULT, MOVIE_OVERVIEW,
                MOVIE_RELEASE_DATE, MOVIE_ORIGINAL_TITLE, MOVIE_ORIGINAL_LANGUAGE,
                MOVIE_TITLE, MOVIE_BACKDROP_PATH, MOVIE_POPULARITY, MOVIE_VOTE_COUNT, MOVIE_HAS_VIDEO,
                MOVIE_VOTE_AVERAGE
        );

        sqLiteDatabase.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
