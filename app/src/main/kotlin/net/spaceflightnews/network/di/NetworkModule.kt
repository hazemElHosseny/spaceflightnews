package net.spaceflightnews.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import net.spaceflightnews.BuildConfig
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Hilt Module for network configuration dependencies provider
 *
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    /**
     * provides the Json that will be used in the serialization/deserialization for network calls
     */
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * provides okHttpClient that will be used in retrofit
     */
    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .apply {
            // add logger on debug builds
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                )
            }
        }
        .build()

    /**
     * provides Retrofit that will be used for networking
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        networkJson: Json,
        okhttpCallFactory: Call.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory(okhttpCallFactory)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
    }
}
