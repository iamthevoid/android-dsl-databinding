package thevoid.iam.ankoobservablecomponents.data.api.butik

import com.google.gson.annotations.SerializedName


open class Product {

    companion object {
        const val URL_NAME_ALT_1 = "slug"
        const val URL_NAME_ALT_2 = "url"
        const val NAME = "name"
        const val URL_NAME = "url_name"
        const val PHOTOBANK_ID = "photobank_id"
    }


    @SerializedName(PHOTOBANK_ID)
    var photobankId: Long = 0

    @SerializedName(NAME)
    var name: String? = null

    @SerializedName(URL_NAME, alternate = [URL_NAME_ALT_1, URL_NAME_ALT_2])
    var urlName: String? = null


    @SerializedName("parent_category_id")
    var parentCategoryId: Long = 0

    @SerializedName("product_gid")
    var gid: String? = null

    @SerializedName("brand_name")
    var brand: String? = null

    @SerializedName("seo_name")
    var seoName: String? = null

    @SerializedName("discount_type")
    var discountType: String? = null

    @SerializedName("dress_style")
    var dressStyle: String? = null

    @SerializedName("sku")
    var sku: String? = ""

    @SerializedName("sku_article")
    var skuArticle: String? = null

    @SerializedName("color")
    var color: String? = null

    @SerializedName("sex")
    var sex: Int = 0


    @SerializedName("already_at_wishlist")
    var favorite: Boolean = false

    @SerializedName("active")
    var active: Boolean = false
}
