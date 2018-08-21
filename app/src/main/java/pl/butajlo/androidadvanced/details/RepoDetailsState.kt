package pl.butajlo.androidadvanced.details

data class RepoDetailsState @JvmOverloads constructor(
        val loading: Boolean,
        val name: String? = null,
        val description: String? = null,
        val createdDate: String? = null,
        val updatedDate: String? = null,
        val errorRes: Int? = null
)