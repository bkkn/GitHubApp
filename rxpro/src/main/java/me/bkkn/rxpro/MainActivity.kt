package me.bkkn.rxpro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import me.bkkn.rxpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val logBuilder = StringBuilder()

    companion object {
        var cnt = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.observableButton.setOnClickListener {
            clearLog()
            Observable.just("Hello1", "hello2", "Hello3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    return@map it.toLowerCase()
                }
                .map {
                    it.reversed()
                }
//                .toList()
                .take(2)
                .filter {
                    it.first().toInt().mod(2) == 0
                }
                .toList()
                .flatMapObservable { it.toObservable() }
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


        useRxButton1()

        useRxButton2()

    }

    fun useRxButton1() {
// мы хотим подписать ретрофит на отслеживание нажатий на кнопку
        val observer = object : Observer<String> {

            override fun onSubscribe(d: Disposable) {
                logMessage("subscribed on rxButton")
            }

            override fun onNext(t: String) {
                logMessage("rxButton")
                logMessage(t + cnt++)
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {

            }

        }

        binding.rxButton1.clicks(observer)
    }

    fun useRxButton2() {
        val onClickAction: (String) -> Unit = {
            logMessage(it)
        }
        binding.rxButton2.clicks(onClickAction)
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