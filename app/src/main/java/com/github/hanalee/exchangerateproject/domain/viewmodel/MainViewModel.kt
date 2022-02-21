package com.github.hanalee.exchangerateproject.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.hanalee.exchangerateproject.domain.repository.MainRepository
import com.github.hanalee.exchangerateproject.domain.retrofit.entity.response.RateResponse
import com.github.hanalee.exchangerateproject.utils.UtilFunction
import com.github.hanalee.exchangerateproject.view.MainActivity.Companion.JPY
import com.github.hanalee.exchangerateproject.view.MainActivity.Companion.KRW
import com.github.hanalee.exchangerateproject.view.MainActivity.Companion.PHP
import com.github.hanalee.exchangerateproject.view.MainActivity.Companion.TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MainViewModel : ViewModel(), KoinComponent {
    private val mainRepository: MainRepository by inject()
    private lateinit var rateResponse: RateResponse.Quotes
    private var nowRemittance: Double? = null
    private var nowSelectedRate: Pair<String, Double>? = null
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d(TAG, "Caught $exception")
    }

    private val _selectedRate = MutableLiveData<String>()
    val selectedRate: LiveData<String>
        get() = _selectedRate

    private val _rateViewTime = MutableLiveData<String>()
    val rateViewTime: LiveData<String>
        get() = _rateViewTime

    private val _remittance = MutableLiveData<String>()
    val remittance: LiveData<String>
        get() = _remittance

    private val _apiMsg = MutableLiveData<String>()
    val apiMsg: LiveData<String>
        get() = _apiMsg

    fun getExchangeRate() = viewModelScope.launch(handler) {
        val response = mainRepository.getExchangeRate()
        if (response.success) {
            rateResponse = response.quotes
            convertViewTime(response.timestamp)
        } else {
            _apiMsg.value = "환율 정보를 정상적으로 불러오지 못했습니다."
        }
    }

    private fun convertViewTime(timestamp: Int) {
        _rateViewTime.value = UtilFunction.getDateWithFormat(timestamp)
    }

    fun changeRate(country: String) {
        when (country) {
            KRW -> {
                convertRate(rateResponse.usdKrw, country)
            }
            JPY -> {
                convertRate(rateResponse.usdJpy, country)
            }
            PHP -> {
                convertRate(rateResponse.usdPhp, country)
            }
        }
        if (nowRemittance != null) {
            calculateCashWithRate()
        } else {
            _remittance.value = "송금액을 입력하세요."
        }
    }

    private fun convertRate(rate: Double, country: String) {
        nowSelectedRate = Pair(country, rate)
        _selectedRate.value = "${UtilFunction.getMoneyWithFormat(rate)} $country / USD"
    }

    fun changeRemittance(cash: Double) {
        nowRemittance = cash
        if (nowSelectedRate != null) {
            calculateCashWithRate()
        } else {
            _remittance.postValue("수취 국가를 선택하세요.")
        }
    }

    private fun calculateCashWithRate() {
        val cashWithRate = nowRemittance!! * nowSelectedRate!!.second
        val moneyFormat = UtilFunction.getMoneyWithFormat(cashWithRate)
        _remittance.postValue("수취 금액은 $moneyFormat ${nowSelectedRate!!.first} 입니다.")
    }
}