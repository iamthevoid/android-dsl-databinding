package iam.thevoid.noxml.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

abstract class BaseFragment : Fragment(), AnkoComponent<BaseFragment> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context?.let { createView(AnkoContext.create(it, this, false)) }
    }

}