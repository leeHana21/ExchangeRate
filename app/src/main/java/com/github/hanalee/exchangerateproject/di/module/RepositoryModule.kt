package com.github.hanalee.exchangerateproject.di.module

import com.github.hanalee.exchangerateproject.domain.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MainRepository() }
}