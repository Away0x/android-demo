package net.away0x.lib_base.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.away0x.lib_base.widgets.DefaultTextWatcher

fun <T> Observable<T>.execute(lifecycleProvider: LifecycleProvider<*>, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
    this.observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .subscribe(onNext, onError)
}

fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.onClick(method: () -> Unit) {
    this.setOnClickListener { method() }
}

/**
 * 按钮是否可用 (根据 edittext 判断)
 */
fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn = this

    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}