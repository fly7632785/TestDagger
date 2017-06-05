package cn.nekocode.kotgo.component.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class RxLifecycle {
    enum class Event {
        CREATE, ATTACH, CREATE_VIEW, RESTART, START, RESUME,
        PAUSE, STOP, DESTROY_VIEW, DETACH, DESTROY
    }

    val behavior: BehaviorSubject<Event> = BehaviorSubject.create()

    fun onCreate() {
        behavior.onNext(Event.CREATE)
    }

    fun onAttach() {
        behavior.onNext(Event.ATTACH)
    }

    fun onCreateView() {
        behavior.onNext(Event.CREATE_VIEW)
    }

    fun onRestart() {
        behavior.onNext(Event.RESTART)
    }

    fun onStart() {
        behavior.onNext(Event.START)
    }

    fun onResume() {
        behavior.onNext(Event.RESUME)
    }

    fun onPause() {
        behavior.onNext(Event.PAUSE)
    }

    fun onStop() {
        behavior.onNext(Event.STOP)
    }

    fun onDestroyView() {
        behavior.onNext(Event.DESTROY_VIEW)
    }

    fun onDetach() {
        behavior.onNext(Event.DETACH)
    }

    fun onDestroy() {
        behavior.onNext(Event.DESTROY)
    }

    interface Impl {
        val lifecycle: RxLifecycle

        /**
         * 这里定义的bindLifecycle 方法 前面有Observable.  所以只有Observable才可以调用
         *
         * 并且 其方法内部 可以调用 Observable的方法  比如compose
         *
         * 由于这里是已经实现了的    不属于必须实现接口方法  （与java不同）
         */
        fun <T> Observable<T>.bindLifecycle(): Observable<T> {
            return compose(CheckUIDestroiedTransformer<T>(lifecycle))
        }
    }

    private class CheckUIDestroiedTransformer<T>(val lifecycle: RxLifecycle) :
            ObservableTransformer<T, T> {

        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.takeUntil(
                    lifecycle.behavior.skipWhile {
                        it != RxLifecycle.Event.DESTROY_VIEW &&
                                it != RxLifecycle.Event.DESTROY &&
                                it != RxLifecycle.Event.DETACH
                    }
            )
        }
    }
}