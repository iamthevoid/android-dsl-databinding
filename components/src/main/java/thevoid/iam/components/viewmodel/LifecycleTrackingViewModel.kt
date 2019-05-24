package thevoid.iam.components.viewmodel

import androidx.lifecycle.*


abstract class LifecycleTrackingViewModel : ViewModel() {

    private var activeCount: Int = 0

    fun registerLifecycle(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(object : LifecycleEventObserver {

            var active = false

            @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    owner.lifecycle.removeObserver(this)
                    return
                }

                activeStateChanged(isActiveState(source.lifecycle.currentState))
            }

            private fun activeStateChanged(active: Boolean) {
                if (active == this.active) {
                    return
                }

                this.active = active
                val wasInactive = activeCount == 0
                activeCount += if (active) 1 else -1

                if (wasInactive && active) {
                    onActive()
                }

                if (activeCount == 0 && !active) {
                    onInactive()
                }
            }
        })
    }

    private fun isActiveState(state: Lifecycle.State): Boolean = state.isAtLeast(Lifecycle.State.STARTED)

    abstract fun onActive()

    abstract fun onInactive()
}