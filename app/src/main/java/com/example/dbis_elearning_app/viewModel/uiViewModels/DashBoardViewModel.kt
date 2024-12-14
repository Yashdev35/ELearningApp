package com.example.dbis_elearning_app.viewModel.uiViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbis_elearning_app.data.student.model.DashboardData
import com.example.dbis_elearning_app.data.student.repository.StuDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.dbis_elearning_app.data.student.repository.ResultCus

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: StuDataRepo
) : ViewModel() {
    private val _dashboardState = MutableStateFlow<ResultCus<DashboardData>>(ResultCus.Initial())
    val dashboardState = _dashboardState.asStateFlow()

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        viewModelScope.launch {
            _dashboardState.value = ResultCus.Loading()
            _dashboardState.value = repository.getDashboard()
        }
    }
}