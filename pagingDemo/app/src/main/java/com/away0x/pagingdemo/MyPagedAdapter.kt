package com.away0x.pagingdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.away0x.pagingdemo.data.room.Student
import com.away0x.pagingdemo.databinding.CellBinding

val diffCallback = object : DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.studentNumber == newItem.studentNumber
    }
}

class MyPagedAdapter: PagedListAdapter<Student, MyPagedAdapter.MyViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CellBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
    }

    inner class MyViewHolder(
        private val binding: CellBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student?) {
            if (student == null) {
                binding.textView.text = "loading"
                return
            }

            binding.textView.text = student.studentNumber.toString()
        }

    }


}