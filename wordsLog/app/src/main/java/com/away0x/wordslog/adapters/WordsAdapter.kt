package com.away0x.wordslog.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.away0x.wordslog.R
import com.away0x.wordslog.data.room.Word
import com.away0x.wordslog.viewmodels.WordViewModel

val diffCallback = object : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return (oldItem.word == newItem.word)
                && (oldItem.chineseMeaning == newItem.chineseMeaning)
                && (oldItem.chineseInvisible == newItem.chineseInvisible)
    }
}

class WordsAdapter(
    private val useCardView: Boolean,
    private val wordViewModel: WordViewModel
) : ListAdapter<Word, WordsAdapter.WordViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = LayoutInflater.from(parent.context).run {
            if (useCardView) {
                inflate(R.layout.cell_card, parent, false)
            } else {
                inflate(R.layout.cell_normal, parent, false)
            }
        }

        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = getItem(position)
        holder.bind(word, position)
    }

    inner class WordViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
        private val textViewEnglish: TextView = itemView.findViewById(R.id.textViewEnglish)
        private val textViewChinese: TextView = itemView.findViewById(R.id.textViewChinese)
        private val aSwitchChineseInvisible: Switch = itemView.findViewById(R.id.switchChineseInvisible)

        init {
            itemView.setOnClickListener {
                val uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=${textViewEnglish.text}")
                val intent = Intent(Intent.ACTION_VIEW).also { it.data = uri }
                itemView.context.startActivity(intent)
            }

            aSwitchChineseInvisible.setOnCheckedChangeListener { _, isChecked ->
                val word = itemView.getTag(R.id.word_for_view_holder) as Word
                if (isChecked) {
                    textViewChinese.visibility = View.GONE
                    word.chineseInvisible = true
                } else {
                    textViewChinese.visibility = View.VISIBLE
                    word.chineseInvisible = false
                }
                wordViewModel.updateWords(word)
            }
        }

        fun bind(word: Word, index: Int) {
            itemView.setTag(R.id.word_for_view_holder, word)
            textViewNumber.text = (index + 1).toString()
            textViewEnglish.text = word.word
            textViewChinese.text = word.chineseMeaning

            if (word.chineseInvisible) {
                textViewChinese.visibility = View.GONE
                aSwitchChineseInvisible.isChecked = true
            } else {
                textViewChinese.visibility = View.VISIBLE
                aSwitchChineseInvisible.isChecked = false
            }
        }

    }

}