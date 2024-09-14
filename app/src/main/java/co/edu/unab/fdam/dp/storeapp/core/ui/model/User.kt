package co.edu.unab.fdam.dp.storeapp.core.ui.model

import co.edu.unab.fdam.dp.storeapp.core.data.network.entity.UserEntity

data class User(
    var id: String = "",
    var name: String,
    var document: Long,
    var email: String,
    var image: String = "https://picsum.photos/seed/picsum/200/300",
)

fun User.toUserEntity(): UserEntity = UserEntity(
    id = this.id,
    name = this.name,
    document = this.document,
    email = this.email,
)

fun UserEntity.toUser(): User = User(
    id = this.id,
    name = this.name,
    document = this.document,
    email = this.email,
    image = "https://picsum.photos/seed/${this.id}/200/300",
)
