package com.example.bayar.moviedb.rest;

import com.example.bayar.moviedb.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id,
                                        @Query("api_key") String apiKey);

    // http://image.tmdb.org/t/p/w500/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg
}
