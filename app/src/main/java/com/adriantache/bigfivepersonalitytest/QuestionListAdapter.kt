package com.adriantache.bigfivepersonalitytest

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_question_item.view.*


class QuestionListAdapter(private val interaction: Interaction? = null) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Question>() {

        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.id == newItem.id && oldItem.answer == newItem.answer
        }


        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return QuestionViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_question_item,
                        parent,
                        false
                ),
                interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is QuestionViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Question>) {
        differ.submitList(list)
    }


    class QuestionViewHolder
    constructor(
            itemView: View,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Question) = with(itemView) {
            itemView.textView.text = item.text

            itemView.radioButton.isChecked = item.answer == 1
            itemView.radioButton2.isChecked = item.answer == 2
            itemView.radioButton3.isChecked = item.answer == 3
            itemView.radioButton4.isChecked = item.answer == 4
            itemView.radioButton5.isChecked = item.answer == 5

            itemView.radioButton.setOnClickListener {
                item.answer = 1
                interaction?.onItemSelected()
            }
            itemView.radioButton2.setOnClickListener {
                item.answer = 2
                interaction?.onItemSelected()
            }
            itemView.radioButton3.setOnClickListener {
                item.answer = 3
                interaction?.onItemSelected()
            }
            itemView.radioButton4.setOnClickListener {
                item.answer = 4
                interaction?.onItemSelected()
            }
            itemView.radioButton5.setOnClickListener {
                item.answer = 5
                interaction?.onItemSelected()
            }
        }
    }

    interface Interaction {
        fun onItemSelected()
    }
}
