package iam.thevoid.noxml.recycler.change

import androidx.recyclerview.widget.RecyclerView

data class OnScrolled(val recyclerView: RecyclerView, val dx: Int, val dy: Int)