package iam.thevoid.noxml.demo.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import iam.thevoid.ae.setRippleClickAnimation
import iam.thevoid.noxml.anko.rx.AnkoRxLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import iam.thevoid.noxml.adapterview.ItemBindings
import iam.thevoid.noxml.demo.R
import iam.thevoid.noxml.recycler.setItems
import iam.thevoid.rxe.mapSelf
import iam.thevoid.noxml.rx2.extensions.view.gone
import iam.thevoid.noxml.rx2.extensions.textview.setText

class SelectActivity : AppCompatActivity() {

    val binding = ItemBindings.of(Item::class) { ItemLayout(onItemClick, it) }

    val onItemClick: (Item) -> Unit = {
        startActivity(intentFor<ContainerActivity>(ContainerActivity.ITEM to it.name))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(frameLayout {
            recyclerView {
                overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                layoutManager = LinearLayoutManager(this@SelectActivity)
                setItems(Item.values().asList(), binding)
            }.lparams(matchParent, wrapContent, Gravity.CENTER)
        })
    }

    class ItemLayout(val onItemClick: (Item) -> Unit, parent: ViewGroup) :
        AnkoRxLayout<Item>(parent) {
        override fun createView(ui: AnkoContext<AnkoRxLayout<Item>>): View =
            ui.frameLayout {

                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)

                textView {
                    textColor = Color.BLACK
                    textSizeDimen = R.dimen.text_size_title
                    setRippleClickAnimation()
                    padding = dip(16)
                    setText(itemChanges.mapSelf { title })
                    setOnClickListener { item.get()?.also(onItemClick) }
                }

                view {
                    backgroundColor = Color.LTGRAY
                    gone(itemChanges.map { it == Item.values()[Item.values().count() - 1] })
                }.lparams(matchParent, dip(1), Gravity.BOTTOM)
            }

    }
}
