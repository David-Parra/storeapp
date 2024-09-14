package co.edu.unab.fdam.dp.storeapp.core.data.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) {
    val auth: FirebaseAuth = firebaseAuth
    val firestoreDB: FirebaseFirestore = firebaseFirestore
}