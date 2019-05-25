package thevoid.iam.components.mvvm

interface ViewModelBundleProvider {
    val bundles: List<ViewModelBundle>
}

class SingleBundleProvider(private val bundle: ViewModelBundle) : ViewModelBundleProvider {
    override val bundles: List<ViewModelBundle> by lazy { listOf(bundle) }
}

class ListBundleProvider(override val bundles: List<ViewModelBundle>) : ViewModelBundleProvider