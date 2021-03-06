package com.example.bayar.moviedb.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bayar.moviedb.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    public static final String TAG = DatabaseManager.class.getSimpleName();

    private SQLiteDatabase mDatabase;

    public DatabaseManager(Context context) {
        mDatabase = new DatabaseHelper(context).getWritableDatabase();
    }

    public void insertMovies(List<Movie> movieList) {
        Log.d(TAG, "insertMovies(): ");
        for (Movie movie : movieList) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(DatabaseHelper.MOVIE_POSTER_PATH, movie.getPosterPath());
            contentValues.put(DatabaseHelper.MOVIE_ADULT, movie.getAdult() ? 1 : 0);
            contentValues.put(DatabaseHelper.MOVIE_OVERVIEW, movie.getOverview());
            contentValues.put(DatabaseHelper.MOVIE_RELEASE_DATE, movie.getReleaseDate());
            contentValues.put(DatabaseHelper.MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
            contentValues.put(DatabaseHelper.MOVIE_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
            contentValues.put(DatabaseHelper.MOVIE_TITLE, movie.getTitle());
            contentValues.put(DatabaseHelper.MOVIE_BACKDROP_PATH, movie.getBackdropPath());
            contentValues.put(DatabaseHelper.MOVIE_POPULARITY, movie.getPopularity());
            contentValues.put(DatabaseHelper.MOVIE_VOTE_COUNT, movie.getVoteCount());
            contentValues.put(DatabaseHelper.MOVIE_HAS_VIDEO, movie.getHasVideo() ? 1 : 0);
            contentValues.put(DatabaseHelper.MOVIE_VOTE_AVERAGE, movie.getVoteAverage());

            mDatabase.insert(DatabaseHelper.MOVIE_TABLE, null, contentValues);
            Log.d(TAG, "Movie " + movie.getTitle() + " was successfully added into database");
        }
    }

    public void removeMoviesFromDatabase() {
        Log.d(TAG, "removeMoviesFromDatabase(): ");
        mDatabase.delete(DatabaseHelper.MOVIE_TABLE, null, null);
        Log.d(TAG, "All rows are successfully removed.");
    }

    public int getRowCount() {
        Log.d(TAG, "getRowCount(): ");

        String query = "SELECT count(*) FROM " + DatabaseHelper.MOVIE_TABLE;
        Cursor cursor = mDatabase.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        Log.d(TAG, "count = " + count);

        return count;
    }

    public List<Movie> getMovies() {
        Log.d(TAG, "getMovies(): ");

        List<Movie> movieList = new ArrayList<>();
        Movie movie;

        Cursor cursor = mDatabase.query(DatabaseHelper.MOVIE_TABLE, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(DatabaseHelper.MOVIE_ID);
            int idPosterPath = cursor.getColumnIndex(DatabaseHelper.MOVIE_POSTER_PATH);
            int idAdult = cursor.getColumnIndex(DatabaseHelper.MOVIE_ADULT);
            int idOverview = cursor.getColumnIndex(DatabaseHelper.MOVIE_OVERVIEW);
            int idReleaseDate = cursor.getColumnIndex(DatabaseHelper.MOVIE_RELEASE_DATE);
            int idOriginalTitle = cursor.getColumnIndex(DatabaseHelper.MOVIE_ORIGINAL_TITLE);
            int idOriginalLanguage = cursor.getColumnIndex(DatabaseHelper.MOVIE_ORIGINAL_LANGUAGE);
            int idTitle = cursor.getColumnIndex(DatabaseHelper.MOVIE_TITLE);
            int idBackdropPath = cursor.getColumnIndex(DatabaseHelper.MOVIE_BACKDROP_PATH);
            int idPopularity = cursor.getColumnIndex(DatabaseHelper.MOVIE_POPULARITY);
            int idVoteCount = cursor.getColumnIndex(DatabaseHelper.MOVIE_VOTE_COUNT);
            int idHasVideo = cursor.getColumnIndex(DatabaseHelper.MOVIE_HAS_VIDEO);
            int idVoteAverage = cursor.getColumnIndex(DatabaseHelper.MOVIE_VOTE_AVERAGE);

            do {
                boolean isAdult = cursor.getInt(idAdult) == 1;
                boolean hasVideo = cursor.getInt(idHasVideo) == 1;
                movie = new Movie(
                        cursor.getInt(idIndex),
                        cursor.getString(idPosterPath),
                        isAdult,
                        cursor.getString(idOverview),
                        cursor.getString(idReleaseDate),
                        cursor.getString(idOriginalTitle),
                        cursor.getString(idOriginalLanguage),
                        cursor.getString(idTitle),
                        cursor.getString(idBackdropPath),
                        cursor.getDouble(idPopularity),
                        cursor.getInt(idVoteCount),
                        hasVideo,
                        cursor.getDouble(idVoteAverage));
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "movieList.size(): " + movieList.size());

        cursor.close();
        return movieList;
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }
}






















