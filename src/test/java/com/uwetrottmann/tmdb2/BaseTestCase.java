package com.uwetrottmann.tmdb2;

import com.google.common.util.concurrent.RateLimiter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public abstract class BaseTestCase {

    private static final boolean PRINT_REQUESTS = true;

    // Do NOT use this API key in your application, it is only for testing tmdb-java!
    private static final String TEST_API_KEY = "25da90e9f8f0b3892d8bdeb6c3d6267d";
    // limit requests for tests to avoid hitting TMDb rate limit (40 requests/10 seconds)
    @SuppressWarnings("UnstableApiUsage") private static final RateLimiter rateLimiter = RateLimiter.create(5);

    private static final Tmdb unauthenticatedInstance = new BaseTestCase.TestTmdb(TEST_API_KEY);
    private static final Tmdb authenticatedInstance  = new BaseTestCase.TestTmdb(TEST_API_KEY);

    protected static Tmdb getUnauthenticatedInstance() {
        return unauthenticatedInstance;
    }

    protected static Tmdb getAuthenticatedInstance() {
        return authenticatedInstance;
    }

    private static class TestTmdb extends Tmdb {
        public TestTmdb(String apiKey) {
            super(apiKey);
        }

        @Override
        protected void setOkHttpClientDefaults(OkHttpClient.Builder builder) {
            final Tmdb instance = this;
            builder.authenticator(new TmdbAuthenticator(instance));
            builder.addInterceptor(chain -> {
                rateLimiter.acquire();
                return TmdbInterceptor.handleIntercept(chain, instance);
            });
            if (PRINT_REQUESTS) {
                // add logging, standard output is easier to read
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
        }
    }

}
