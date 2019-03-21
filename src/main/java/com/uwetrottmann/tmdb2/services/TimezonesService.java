package com.uwetrottmann.tmdb2.services;

import com.uwetrottmann.tmdb2.entities.Timezones;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface TimezonesService {

    /**
     * Get the list of supported timezones on TMDb.
     */
    @GET("timezones/list")
    Single<Timezones> timezones();
}
