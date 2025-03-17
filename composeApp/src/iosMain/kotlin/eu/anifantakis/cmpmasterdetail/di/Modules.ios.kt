package eu.anifantakis.cmpmasterdetail.di

import androidx.lifecycle.SavedStateHandle
import eu.anifantakis.cmpmasterdetail.core.data.preferences.Vault
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

        single<Vault> {
            Vault()
        }

        factory<SavedStateHandle> {
            SavedStateHandle()
        }
    }