package com.example.ankoobservablecomponents

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.frameLayout
import java.util.*

class JustFragment : Fragment(), AnkoComponent<JustFragment> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        context?.let { createView(AnkoContext.create(it, this)) }

    override fun createView(ui: AnkoContext<JustFragment>): View =
        ui.frameLayout {
            val alpha : Int = 0xFF000000.toInt()
            backgroundColor = alpha +
                Random().nextInt(0x100) * 0x10000 +
                        Random().nextInt(0x100) * 0x100 +
                        Random().nextInt(0x100)
        }
}