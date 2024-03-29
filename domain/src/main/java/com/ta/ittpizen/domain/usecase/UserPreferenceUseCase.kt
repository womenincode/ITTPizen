package com.ta.ittpizen.domain.usecase

import com.ta.ittpizen.domain.model.preference.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserPreferenceUseCase {

    val userPreference: Flow<UserPreference>

    suspend fun updateUserPreference(userPreference: UserPreference)

}