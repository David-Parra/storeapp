package co.edu.unab.fdam.dp.storeapp.core.data.network.entity

data class UserEntity(
    var id: String = "",
    var name: String,
    var document: Long,
    var email: String
) {
    //empty constructor
    constructor() : this("", "", 0, "")
}