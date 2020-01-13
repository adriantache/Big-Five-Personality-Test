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
 * @param interaction callback on RadioButton click (optional)
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

    override fun getItemId(position: Int): Long {
        return differ.currentList[position].uniqueId.toLong()
    }

    fun submitList(list: List<Question>) {
        differ.submitList(list)
    }

    class QuestionViewHolder(
            v: View,
            private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(v), LayoutContainer {
        private val view = v

        override val containerView: View?
            get() = itemView

        fun bind(question: Question) {
            view.textView.text = question.text

            setRadios(question.answer)

            if (interaction != null) {
                view.radioButton.setOnClickListener {
                    interaction.onItemSelected(adapterPosition, 1)
                }
                view.radioButton2.setOnClickListener {
                    interaction.onItemSelected(adapterPosition, 2)
                }
                view.radioButton3.setOnClickListener {
                    interaction.onItemSelected(adapterPosition, 3)
                }
                view.radioButton4.setOnClickListener {
                    interaction.onItemSelected(adapterPosition, 4)
                }
                view.radioButton5.setOnClickListener {
                    interaction.onItemSelected(adapterPosition, 5)
                }
            }
        }

        private fun setRadios(answer: Int) {
            //bug fix: clear RadioGroup selection before setting the values
            // otherwise checked answers sometimes disappear on scroll
            view.radioGroup.clearCheck()

            if (answer == 0) return //skip setting checked if no answer is selected

            when (answer) {
                1 -> view.radioButton.isChecked = true
                2 -> view.radioButton2.isChecked = true
                3 -> view.radioButton3.isChecked = true
                4 -> view.radioButton4.isChecked = true
                5 -> view.radioButton5.isChecked = true
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, selection: Int)
    }
}
