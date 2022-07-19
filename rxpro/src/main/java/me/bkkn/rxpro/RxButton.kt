package me.bkkn.rxpro

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import java.util.concurrent.TimeUnit


    class RxButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
    ) : AppCompatButton(context, attrs) {

        private lateinit var myEmitter: Observer<String>

        fun clicks(observer: Observer<String>) {
            myEmitter = observer
            // создаем новый обсервабл
    //        val createObserver = Observable.create(ObservableOnSubscribe<String> {
    ////            myEmitter.onNext("Hello World")
    ////            myEmitter.onComplete()
    //        })
    //            .throttleFirst(5000, TimeUnit.MILLISECONDS)

            this.setOnClickListener {
                // эмитер, который подали снаружи испускает новое значение
                myEmitter.onNext("next click ")
            }

    //        Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<Any?> ->
    //            emitter.setCancellation {
    //                button.setOnClickListener(null)
    //                emitter.onCompleted()
    //            }
    //            button.setOnClickListener { value: T? -> emitter.onNext(value) }
    //        }, Emitter.BackpressureMode.DROP)

        }

    }