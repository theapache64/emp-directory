package com.sifar.whiterabbit.feature.employeedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sifar.whiterabbit.R
import com.sifar.whiterabbit.databinding.ActivityEmployeeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeDetailActivity : AppCompatActivity() {

    private val viewModel: EmployeeDetailViewModel by viewModels()

    companion object {

        private const val KEY_EMPLOYEE_ID = "employee_id"

        fun getStartIntent(context: Context, employeeId: Int): Intent {
            return Intent(context, EmployeeDetailActivity::class.java).apply {
                // data goes here
                putExtra(KEY_EMPLOYEE_ID, employeeId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityEmployeeDetailBinding>(
            this,
            R.layout.activity_employee_detail
        )

        val employeeId = intent.getIntExtra(KEY_EMPLOYEE_ID, -1)
        require(employeeId != -1) { "empId missing" }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.init(employeeId)

        // Employee detail
        viewModel.employeeDetails.observe(this) {
            binding.rvEmpDetails.adapter = getEmployeeDetailAdapter(it)
        }


        // Custom back navigation
        viewModel.shouldNavBack.observe(this) { shouldNavBack ->
            if (shouldNavBack) {
                finish()
            }
        }
    }

    private fun getEmployeeDetailAdapter(data: List<EmployeeDetail>) = EmployeeDetailsAdapter(
        this,
        data
    )
}