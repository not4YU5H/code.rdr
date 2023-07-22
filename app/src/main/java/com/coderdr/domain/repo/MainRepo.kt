package com.coderdr.domain.repo

import kotlinx.coroutines.flow.Flow

interface MainRepo {
    fun startScanner(): Flow<String?>
}