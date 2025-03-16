package eu.anifantakis.cmpmasterdetail.di

import eu.anifantakis.cmpmasterdetail.movies.data.database.MoviesDatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> {
            OkHttp.create()
        }

        single { MoviesDatabaseFactory(androidApplication()) }
    }