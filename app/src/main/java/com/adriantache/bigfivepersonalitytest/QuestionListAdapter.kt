package com.adriantache.bigfivepersonalitytest

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer


//todo CRITICAL rewrite this
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

    class QuestionViewHolder(
            itemView: View,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View?
            get() = itemView

        fun bind(item: Question) {
//            textView.text = item.text
//            radioButton.isChecked = item.answer == 1
//            radioButton2.isChecked = item.answer == 2
//            radioButton3.isChecked = item.answer == 3
//            radioButton4.isChecked = item.answer == 4
//            radioButton5.isChecked = item.answer == 5
//
//            radioButton.setOnClickListener {
//                item.answer = 1
//                interaction?.onItemSelected()
//            }
//            radioButton2.setOnClickListener {
//                item.answer = 2
//                interaction?.onItemSelected()
//            }
//            radioButton3.setOnClickListener {
//                item.answer = 3
//                interaction?.onItemSelected()
//            }
//            radioButton4.setOnClickListener {
//                item.answer = 4
//                interaction?.onItemSelected()
//            }
//            radioButton5.setOnClickListener {
//                item.answer = 5
//                interaction?.onItemSelected()
//            }
        }
    }

    interface Interaction {
        fun onItemSelected()
    }
}

