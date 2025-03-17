package eu.anifantakis.cmpmasterdetail.di

import CMPMasterDetail.composeApp.BuildConfig
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import eu.anifantakis.cmpmasterdetail.core.presentation.ui.base.scaffold.ScaffoldViewModel
import eu.anifantakis.cmpmasterdetail.movies.data.MoviesRepositoryImpl
import eu.anifantakis.cmpmasterdetail.movies.data.database.MoviesDatabase
import eu.anifantakis.cmpmasterdetail.movies.data.database.MoviesDatabaseFactory
import eu.anifantakis.cmpmasterdetail.movies.data.datasource.LocalMoviesDataSourceImpl
import eu.anifantakis.cmpmasterdetail.movies.data.datasource.RemoteMoviesDataSourceImpl
import eu.anifantakis.cmpmasterdetail.movies.data.networking.MoviesHttpClient
import eu.anifantakis.cmpmasterdetail.movies.domain.MoviesRepository
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.LocalMoviesDataSource
import eu.anifantakis.cmpmasterdetail.movies.domain.datasource.RemoteMoviesDataSource
import eu.anifantakis.cmpmasterdetail.movies.presentation.MoviesListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    single<MoviesHttpClient> {
        MoviesHttpClient(
            engine = get(),
            baseUrl = BuildConfig.BASE_URL_MOVIES,
            apiKey = BuildConfig.API_KEY_MOVIES,
            logging = true
        )
    }

    single {
        get<MoviesDatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<MoviesDatabase>().moviesDao }

    // Remote+Local Movies Datasource
    singleOf(::RemoteMoviesDataSourceImpl).bind<RemoteMoviesDataSource>()
    singleOf(::LocalMoviesDataSourceImpl).bind<LocalMoviesDataSource>()
    // Movies Repository
    singleOf(::MoviesRepositoryImpl).bind<MoviesRepository>()

    viewModelOf(::ScaffoldViewModel)
    viewModelOf(::MoviesListViewModel)


}