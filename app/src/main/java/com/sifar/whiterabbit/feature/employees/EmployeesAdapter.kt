package com.sifar.whiterabbit.feature.employees

/**
 * Created by theapache64 : Feb 17 Wed,2021 @ 18:34
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sifar.whiterabbit.data.remote.employees.Employee
import com.sifar.whiterabbit.databinding.ItemEmployeeBinding

class EmployeesAdapter(
    context: Context,
    private val employees: List<Employee>,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = employees.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employees[position]
        holder.binding.employee = employee
    }

    inner class ViewHolder(val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}