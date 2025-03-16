package eu.anifantakis.cmpmasterdetail.di

import androidx.lifecycle.SavedStateHandle
import eu.anifantakis.cmpmasterdetail.movies.data.database.MoviesDatabaseFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {

        single<HttpClientEngine> {
            Darwin.create()
        }

        single { MoviesDatabaseFactory() }

        factory<SavedStateHandle> {
            SavedStateHandle()
        }
    }