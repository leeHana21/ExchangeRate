package com.github.hanalee.exchangerateproject.view

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.github.hanalee.exchangerateproject.R
import com.github.hanalee.exchangerateproject.databinding.ActivityMainBinding
import com.github.hanalee.exchangerateproject.domain.viewmodel.MainViewModel
import com.github.hanalee.exchangerateproject.extension.toToast
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "ExchangeRate"
        const val KRW = "KRW"
        const val JPY = "JPY"
        const val PHP = "PHP"
    }

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputMethodManager: InputMethodManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        setContentView(binding.root)
        initView()
        initObserver()
        getExchangeRate()
    }

    private fun initView() = with(binding) {
        rgReceiptCountryName.setOnCheckedChangeListener { _, radioBtnId ->
            when (radioBtnId) {
                R.id.rb_kr -> {
                    changeRate(KRW)
                }
                R.id.rb_jp -> {
                    changeRate(JPY)
                }
                R.id.rb_ph -> {
                    changeRate(PHP)
                }
            }
        }

        etRemittance.textChanges().debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe {
                changeRemittance()
            }

    }

    private fun changeRate(country: String) {
        mainViewModel.changeRate(country)
    }

    private fun changeRemittance() {
        binding.etRemittance.text!!.trim().toString().let {
            if (it.isNotBlank()) {
                if (it.toInt() in 1..10000) {
                    inputMethodManager.hideSoftInputFromWindow(binding.etRemittance.windowToken, 0)
                    mainViewModel.changeRemittance(it.toDouble())
                } else {
                    inputMethodManager.hideSoftInputFromWindow(binding.etRemittance.windowToken, 0)
                    runOnUiThread {
                        this.toToast("송금액이 바르지 않습니다.")
                    }
                }
            }
        }
    }

    private fun initObserver() = with(mainViewModel) {
        apiMsg.observe(this@MainActivity){
            this@MainActivity.toToast(it)
        }
        selectedRate.observe(this@MainActivity) {
            binding.tvExchangeRateName.text = it
        }
        rateViewTime.observe(this@MainActivity) {
            binding.tvViewTimeName.text = it
        }
        remittance.observe(this@MainActivity) {
            binding.tvRateResult.text = it
        }
    }

    private fun getExchangeRate() {
        mainViewModel.getExchangeRate()
    }
}