package com.aicontent.globalInsightApp.data

import com.aicontent.globalInsightApp.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Utils.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideSqlDriver(app: Application): SqlDriver {
//        return AndroidSqliteDriver(
//            schema = PersonDatabase.Schema,
//            context = app,
//            name = "person.db"
//        )
//    }
//
//    @Provides
//    @Singleton
//    fun providePersonDataSource(driver: SqlDriver): PersonDataSource {
//        return PersonDataSourceImpl(PersonDatabase(driver))
//    }
}