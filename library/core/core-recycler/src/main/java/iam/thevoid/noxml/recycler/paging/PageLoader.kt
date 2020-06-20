package iam.thevoid.noxml.recycler.paging

interface PageLoader {
   data class Page<PAGE_INDEX, T> (val items : List<T>, val pageIndex : PAGE_INDEX, val isLastPage : Boolean)
}