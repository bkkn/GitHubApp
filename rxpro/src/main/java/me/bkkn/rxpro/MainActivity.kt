package me.bkkn.rxpro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import me.bkkn.rxpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val logBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.observableButton.setOnClickListener {
            clearLog()
            getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    return@map it.toLowerCase()
                }
                .map {
                    it.reversed()
                }
//                .toList()
         //       .take(2)
//                .filter {
//                    it.first().digitToInt().mod(2) == 0
//                }
            //    .toList()
            //    .flatMapObservable { it.toObservable() }
                .doOnNext {
                    logMessage("doOnNext")
                }
                .doOnComplete() {
                    logMessage("doOnComplete")
                }
                .doOnSubscribe() {
                    logMessage("doOnSubscribe")
                }
                .doOnDispose() {
                    logMessage("doOnDispose")
                }
                .subscribeBy(
                    onNext = { logMessage("onNext: $it") },
                    onError = { logMessage("onError$it") },
                    // onComplete = { logMessage("onComplete") }
                )
        }
        binding.singleButton.setOnClickListener {
            clearLog()
            logMessage("singleButton")
        }
        binding.maybeButton.setOnClickListener {
            clearLog()
            logMessage("maybeButton")
        }
        binding.completableButton.setOnClickListener {
            clearLog()
            logMessage("completableButton")
        }

    }

    private fun getObservable(): Observable<String> {
        return Observable.create {
            it.onNext("Hello1")
            it.onNext("Hello2")
            it.onNext("Hello3")
            it.onNext("Hello4")
            it.onNext("Hello5")
        //    it.onComplete()

            Thread{
                Thread.sleep(1_000)
                it.onNext("ololo")
            }.start()
        }
    }

    private fun logMessage(message: String) {
        logBuilder.appendLine(message)
        binding.logTextView.text = logBuilder.toString()
    }

    private fun clearLog() {
        logBuilder.clear()
        binding.logTextView.text = "Log cleared"
    }

}