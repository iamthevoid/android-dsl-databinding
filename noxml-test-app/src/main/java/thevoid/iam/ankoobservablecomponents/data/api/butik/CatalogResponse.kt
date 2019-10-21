package thevoid.iam.ankoobservablecomponents.data.api.butik

import com.google.gson.annotations.SerializedName

data class CatalogResponse(

    @SerializedName("products")
    val items: List<Product> = emptyList(),

    @SerializedName("current_page")
    val currentPage: Int = 0,

    @SerializedName("last_page")
    val lastPage: Int = 0,

    @SerializedName("total")
    val total: Long = 0L

) {
    @get:JvmName("isLastPage")
    val isLastPage
        get() = currentPage == lastPage
}
