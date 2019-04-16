package com.example.ankoobservablecomponents

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.components.setText
import com.example.components.setTextColor
import com.example.components.toFlowableLatest
import org.jetbrains.anko.*

class PageFragment : Fragment(), AnkoComponent<PageFragment> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        context?.let { createView(AnkoContext.create(it, this)) }

    override fun createView(ui: AnkoContext<PageFragment>): View =
        ui.frameLayout {
            textView {
                id = R.id.text
                textSize = dip(16).toFloat()
                text = "text"
                setText(ObservableObject.text.toFlowableLatest())
                setTextColor(ObservableObject.number.map { Color.parseColor("#$it") }.toFlowableLatest())
            }.lparams {
                gravity = Gravity.CENTER
            }
        }
}