package com.uwetrottmann.tmdb2.services;

import com.uwetrottmann.tmdb2.entities.Certifications;
import io.reactivex.Single;
import retrofit2.http.GET;


public interface CertificationsService {
    /**
     * Get the Certifications for Movies.
     */
    @GET("certification/movie/list")
    Single<Certifications> movie();

    /**
     * Get the Certifications for TV Shows.
     */
    @GET("certification/tv/list")
    Single<Certifications> tv();
}
