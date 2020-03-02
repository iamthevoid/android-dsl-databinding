package iam.thevoid.noxml.demo.ui.mvvm.scroll

import androidx.lifecycle.ViewModel
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.rx.data.fields.RxList

class ScrollViewModel : ViewModel() {


    val images by lazy { RxList(listOf(
        "https://wallpapershome.com/images/wallpapers/grass-720x1280-5k-4k-wallpaper-osx-green-dew-168.jpg",
        "https://3.bp.blogspot.com/-HBxSdT2_yfI/WuBUlEENPQI/AAAAAAAAAjk/u8HZIwA1peU1C8Ig1KwBpysIuQtqCYIWwCLcBGAs/s1600/0-02-08-6dcf65b991b29986d9cf466c655b3496462fb4b14ee5682943bcaafe91198ba1_587e4a85.jpg",
        "https://img.alicdn.com/bao/uploaded/TB1pwD7QVXXXXcSXVXXXXXXXXXX.jpg",
        "https://cdn.shopify.com/s/files/1/0274/8763/products/fe4be4a1cfb32e094fdbb928da70f7f8.jpg?v=1561991664"
    )) }

    val pagerBindings = ItemBindings.of(String::class.java) { ImageItem(it) }

    val items by lazy { RxList(createItems()) }

    val binding = ItemBindings.of(Integer::class.java) { IntItem(it) }

    private fun createItems() = (1..100).map { it }
}