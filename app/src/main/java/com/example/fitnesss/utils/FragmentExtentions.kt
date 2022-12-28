package com.example.fitnesss.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <reified VM : ViewModel> Fragment.viewModelCreator(noinline creator: ViewModelCreator<VM>): Lazy<VM> {
    return viewModels { ViewModelFactory(creator) }
}

//fun <T> Flow<T>.observeOnCreateStateFlow(lifecycleOwner: LifecycleOwner, body: (T) -> Unit){
//    lifecycleOwner.repeatJob(Lifecycle.State.CREATED){
//        collect{
//            body(it)
//        }
//    }
//}
//
//private fun LifecycleOwner.repeatJob(
//    state: Lifecycle.State,
//    block: suspend CoroutineScope.() -> Unit
//): Job {
//    return lifecycleScope.launch { repeatOnLifecycle(state, block) }
//}