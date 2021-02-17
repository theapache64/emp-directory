package com.sifar.whiterabbit.feature.employees

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sifar.whiterabbit.R
import com.sifar.whiterabbit.data.remote.employees.Employee
import com.sifar.whiterabbit.databinding.ActivityEmployeesBinding
import com.sifar.whiterabbit.feature.employeedetail.EmployeeDetailActivity
import com.sifar.whiterabbit.util.calladapter.flow.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeesActivity : AppCompatActivity() {

    private val viewModel: EmployeesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEmployeesBinding>(
            this,
            R.layout.activity_employees
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Retry callback
        binding.lvEmployees.setRetryCallback {
            viewModel.loadEmployees()
        }

        // Loading data
        viewModel.employeesResp.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    with(binding) {
                        gContent.visibility = View.GONE
                        lvEmployees.showLoading(R.string.emps_loading)
                    }
                }
                is Resource.Success -> {
                    with(binding) {
                        gContent.visibility = View.VISIBLE
                        lvEmployees.hideLoading()

                        // For better performance, we can call notifyDataSetChanged() or use DiffUtil
                        rvEmployees.adapter = getEmployeesAdapter(it)
                    }
                }
                is Resource.Error -> {
                    with(binding) {
                        gContent.visibility = View.GONE
                        lvEmployees.showError(it.errorData)
                    }
                }
            }
        }

        // Search
        viewModel.searchInput.observe(this) {
            viewModel.onSearchInputChanged(it)
        }

        viewModel.launchEmployeeDetail.observe(this) { empId ->
            EmployeeDetailActivity.getStartIntent(this, empId).run {
                startActivity(this)
            }
        }
    }

    private fun getEmployeesAdapter(it: Resource.Success<List<Employee>>) =
        EmployeesAdapter(
            context = this,
            employees = it.data
        ) { position ->
            viewModel.onEmployeeClicked(it.data[position])
        }
}