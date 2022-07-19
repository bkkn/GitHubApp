package me.bkkn.rxpro

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.rxjava3.core.Observable

private val TAG = MainActivity::class.java.simpleName

class RxButton2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

//    val observable = Observable.create { subscriber ->
//        subscriber.onNext("")
//    }

    fun clicks(onClickAction: (String) -> Unit) {
        this.setOnClickListener {
            Observable
                .just(Unit)
                .subscribe {
                    onClickAction("ololo")
                }
        } // On each click, emit a event with a string "A" in it and then consume the event with a subscriber.
    }
}