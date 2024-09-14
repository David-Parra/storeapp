package co.edu.unab.fdam.dp.storeapp.core.data.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseApp(@ApplicationContext context: Context): FirebaseApp? {
        return FirebaseApp.initializeApp(context)
    }

    @Provides
    fun provideFirebaseAuth(firebaseApp: FirebaseApp?): FirebaseAuth {
        firebaseApp?.name ?: throw IllegalStateException("FirebaseApp is null")
        return FirebaseAuth.getInstance()
    }

    //firestore
    @Provides
    fun provideFirestore(firebaseApp: FirebaseApp?): FirebaseFirestore {
        firebaseApp?.name ?: throw IllegalStateException("FirebaseApp is null")
        return Firebase.firestore
    }
}
