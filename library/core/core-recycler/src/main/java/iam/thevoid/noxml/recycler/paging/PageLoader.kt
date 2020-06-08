package iam.thevoid.noxml.recycler.paging

interface PageLoader {
   data class Response<PAGE, T> (val items : List<T>, val page : PAGE, val isLastPage : Boolean)
}