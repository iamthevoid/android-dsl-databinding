package iam.thevoid.noxml

object Config {
    /**
     * This flag serves for Espresso hack. When Espresso is matching [RecyclerView] it creates
     * [RecyclerView.ViewHolder] view, but not attaches it to window, only calls
     * [RecyclerView.Adapter.onBindViewHolder]. This lead the case when View is not subscribing on
     * given data streams.
     *
     * If this flag set to [true] library assumes the Espresso test running and checks from where
     * [RecyclerView.Adapter.onBindViewHolder] called. Check uses reflection, so it too heavy for
     * being always enabled. So this flag is guard and must be set only @Before espresso
     * test starts, not in app runtime
     */
    var ESPRESSO_TEST_RUNNING = false
}