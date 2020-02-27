package iam.thevoid.noxml.core.mvvm

interface ViewModelBindingProvider {
    val bindings: List<iam.thevoid.noxml.core.mvvm.ViewModelBinding>
}

class SingleBindingProvider(private val binding: iam.thevoid.noxml.core.mvvm.ViewModelBinding) :
    ViewModelBindingProvider {
    override val bindings: List<iam.thevoid.noxml.core.mvvm.ViewModelBinding> by lazy { listOf(binding) }
}

class ListBindingProvider(override val bindings: List<iam.thevoid.noxml.core.mvvm.ViewModelBinding>) :
    ViewModelBindingProvider