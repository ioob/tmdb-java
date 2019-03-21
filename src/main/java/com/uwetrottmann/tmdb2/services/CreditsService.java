package com.uwetrottmann.tmdb2.services;

import com.uwetrottmann.tmdb2.entities.Credit;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CreditsService {
    /**
     * Get the basic company information for a specific company id.
     *
     * @param credit_id The Credit ID provided by a Movie/TV Show about an Actor or Crew Member.
     */
    @GET("credit/{id}")
    Single<Credit> credit(
            @Path("id") String credit_id
    );
}
