package com.example.touska

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.data.local.sharedpref.PrefManager
import com.example.touska.utils.ThemeTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(prefManager: PrefManager) : ViewModel() {

    private val _theme = mutableStateOf(prefManager.getValue(PrefManager.THEME,String::class,ThemeTypes.SYSTEM))
    val them: State<String> = _theme

    fun setTheme(theme : String) {
        _theme.value=theme
    }

}