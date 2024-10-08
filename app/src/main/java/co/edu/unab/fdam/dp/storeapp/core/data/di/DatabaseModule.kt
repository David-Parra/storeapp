package co.edu.unab.fdam.dp.storeapp.core.data.di

import android.content.Context
import androidx.room.Room
import co.edu.unab.fdam.dp.storeapp.core.data.local.StoreAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideStoreAppDatabase(@ApplicationContext context: Context): StoreAppDatabase {
        return Room.databaseBuilder(context, StoreAppDatabase::class.java, "store_app_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(storeAppDatabase: StoreAppDatabase) = storeAppDatabase.productDAO()
}
