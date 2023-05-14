package com.example.mkotestapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mkotestapplication.domain.user_info.GetUsernameUseCase
import com.example.mkotestapplication.domain.user_info.InsertUsernameUseCase
import com.example.mkotestapplication.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getUsernameUseCase: GetUsernameUseCase,
    private val insertUsernameUseCase: InsertUsernameUseCase
) : ViewModel() {

    val user = getUsernameUseCase(Unit).map {
        if (it.isNotEmpty()) it[0] else null
    }

    fun insertUser(user: User) = viewModelScope.launch {
        insertUsernameUseCase.invoke(user)
    }

}