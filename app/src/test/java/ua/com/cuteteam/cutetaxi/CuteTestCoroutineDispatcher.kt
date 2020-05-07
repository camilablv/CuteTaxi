package ua.com.cuteteam.cutetaxi

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object CuteTestCoroutineDispatcher : CoroutineDispatcher(){
    override fun dispatch(context: CoroutineContext, block: Runnable) = block.run()
}