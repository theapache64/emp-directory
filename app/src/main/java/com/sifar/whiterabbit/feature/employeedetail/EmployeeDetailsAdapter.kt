package com.sifar.whiterabbit.feature.employeedetail

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 19:46
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sifar.whiterabbit.databinding.ItemEmployeeDetailBinding

class EmployeeDetailsAdapter(
    context: Context,
    private val employeeDetails: List<EmployeeDetail>
) : RecyclerView.Adapter<EmployeeDetailsAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEmployeeDetailBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = employeeDetails.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employeeDetail = employeeDetails[position]
        holder.binding.employeeDetail = employeeDetail
    }

    inner class ViewHolder(val binding: ItemEmployeeDetailBinding) :
        RecyclerView.ViewHolder(binding.root)
}