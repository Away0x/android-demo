package com.away0x.room.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.away0x.room.R
import com.away0x.room.data.room.Word
import com.away0x.room.viewmodels.WordViewModel

class MyAdapter(
    private val useCardView: Boolean = false,
    val viewModel: WordViewModel
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var allWords = listOf<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = if (useCardView) {
            layoutInflater.inflate(R.layout.cell_card, parent, false)
        } else {
            layoutInflater.inflate(R.layout.cell_normal, parent, false)
        }

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allWords.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(allWords[position], position)
    }

    inner class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
        var textViewEnglish: TextView = itemView.findViewById(R.id.textViewEnglish)
        var textViewChinese: TextView = itemView.findViewById(R.id.textViewChinese)
        var aSwitchChineseInvisible: Switch = itemView.findViewById(R.id.switchChineseInvisible)

        init {
            itemView.setOnClickListener {
                val uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=${textViewEnglish.text}")
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = uri
                itemView.context.startActivity(intent)
            }
        }

        fun bind(word: Word, index: Int) {
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

            aSwitchChineseInvisible.setOnCheckedChangeListener { compoundButton, isChecked ->
                if (!compoundButton.isPressed) return@setOnCheckedChangeListener // 避免 setChecked 时触发该 linsener
                // 其他解决方法: 在上面设置 aSwitchChineseInvisible.isChecked 之前设置 aSwitchChineseInvisible.setOnCheckedChangeListener(null)
                //             避免 setChecked 时触发事件回调

                if (isChecked) {
                    textViewChinese.visibility = View.GONE
                    word.chineseInvisible = true
                    viewModel.updateWords(word)
                } else {
                    textViewChinese.visibility = View.VISIBLE
                    word.chineseInvisible = false
                    viewModel.updateWords(word)
                }
            }
        }

    }

}