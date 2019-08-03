package iam.thevoid.common

interface ViewModelBindingProvider {
    val bindings: List<ViewModelBinding>
}

class SingleBindingProvider(private val binding: ViewModelBinding) :
    ViewModelBindingProvider {
    override val bindings: List<ViewModelBinding> by lazy { listOf(binding) }
}

class ListBindingProvider(override val bindings: List<ViewModelBinding>) :
    ViewModelBindingProvider