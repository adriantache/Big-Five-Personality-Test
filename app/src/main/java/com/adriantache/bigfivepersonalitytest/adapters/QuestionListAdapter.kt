package com.adriantache.bigfivepersonalitytest.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adriantache.bigfivepersonalitytest.R
import com.adriantache.bigfivepersonalitytest.models.Question
import com.adriantache.bigfivepersonalitytest.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_question_item.view.*
import kotlin.system.measureNanoTime

/**
 * RecyclerView adapter for the questions list in QuizActivity
 **/
class QuestionListAdapter(private val interaction: Interaction? = null) :
        RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.uniqueId == newItem.uniqueId && oldItem.answer == newItem.answer
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
                parent.inflate(R.layout.layout_question_item, false),
                interaction
        )
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        //todo remove performance profiling
        Log.i("QuestionListAdapter", "RecyclerView item processing time: ${measureNanoTime { holder.bind(differ.currentList[position]) } / 1_000_000f} ms")
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Question>) {
        differ.submitList(list)
    }

    class QuestionViewHolder(
            view: View,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(view), LayoutContainer {
        private val v = view

        override val containerView: View?
            get() = itemView

        init {
            view.radioButton.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, 1)
            }
            view.radioButton2.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, 2)
            }
            view.radioButton3.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, 3)
            }
            view.radioButton4.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, 4)
            }
            view.radioButton5.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, 5)
            }
        }

        fun bind(question: Question) {
            v.textView.text = question.text

            v.radioGroup.setOnCheckedChangeListener(null)
            v.radioGroup.clearCheck()
            v.radioGroup.tag = adapterPosition

            setRadios(question.answer)
        }

        private fun setRadios(answer: Int) {
            v.radioButton.isChecked = answer == 1
            v.radioButton2.isChecked = answer == 2
            v.radioButton3.isChecked = answer == 3
            v.radioButton4.isChecked = answer == 4
            v.radioButton5.isChecked = answer == 5
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, selection: Int)
    }
}

