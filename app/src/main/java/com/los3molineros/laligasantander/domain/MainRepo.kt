package com.los3molineros.laligasantander.domain

import com.los3molineros.laligasantander.common.Resource

interface MainRepo {
    suspend fun getData(): Boolean
}