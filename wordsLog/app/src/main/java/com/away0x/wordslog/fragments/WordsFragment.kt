package com.away0x.wordslog.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.*
import com.away0x.wordslog.Injection

import com.away0x.wordslog.R
import com.away0x.wordslog.adapters.WordsAdapter
import com.away0x.wordslog.data.room.Word
import com.away0x.wordslog.databinding.FragmentWordsBinding
import com.away0x.wordslog.viewmodels.WordViewModel
import com.google.android.material.snackbar.Snackbar

class WordsFragment : Fragment() {

    private lateinit var binding: FragmentWordsBinding
    private val viewModel: WordViewModel by viewModels { Injection.provideWordViewModelFactory(requireActivity()) }

    private lateinit var myAdapter1: WordsAdapter
    private lateinit var myAdapter2: WordsAdapter

    private var filteredWords: LiveData<List<Word>> = MutableLiveData()
    private var allWords = listOf<Word>()
    private var undoAction = false

    private lateinit var dividerItemDecoration: DividerItemDecoration

    companion object {
        private const val VIEW_TYPE_SHP = "view_type_shp"
        private const val IS_USING_CARD_VIEW = "is_using_card_view"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        initFloatingActionButton()
        initItemTouchEvent()

        filteredWords = viewModel.getAllWords()
        filteredWords.observe(viewLifecycleOwner, Observer<List<Word>> {
            val size = myAdapter1.itemCount
            allWords = it
            if (size != it.size) {
                if (size < it.size && !undoAction) {
                    binding.recyclerView.smoothScrollBy(0, -200)
                }
                undoAction = false
                myAdapter1.submitList(it)
                myAdapter2.submitList(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)
        initMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearData -> {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setTitle("清空数据")
                builder.setPositiveButton("确定") { _, _ ->
                    viewModel.deleteAllWords()
                }
                builder.setNegativeButton("取消") { _, _ -> }
                builder.create()
                builder.show()
            }
            R.id.switchViewType -> {
                val shp = getShp()
                val isUsingCardView = getIsUsingCardView(shp)
                val editor = shp.edit()

                if (isUsingCardView) {
                    binding.recyclerView.adapter = myAdapter1
                    binding.recyclerView.addItemDecoration(dividerItemDecoration)
                    editor.putBoolean(IS_USING_CARD_VIEW, false)
                } else {
                    binding.recyclerView.adapter = myAdapter2
                    binding.recyclerView.addItemDecoration(dividerItemDecoration)
                    editor.putBoolean(IS_USING_CARD_VIEW, true)
                }

                editor.apply()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initMenu(menu: Menu) {
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.maxWidth = 1000
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(query: String?): Boolean {
                if (query == null) return false
                val pattern = query.trim()

                filteredWords.removeObservers(viewLifecycleOwner) // 先移除之前在 onActivityCreated 中添加的 observer
                filteredWords = viewModel.findWordsWithPattern(pattern)
                filteredWords.observe(viewLifecycleOwner, Observer {
                    val size = myAdapter1.itemCount
                    allWords = it
                    if (size != it.size) {
                        // 提交的列表数据会在后台进行差异化比较，根据比对结果，来刷新页面
                        myAdapter1.submitList(it)
                        myAdapter2.submitList(it)
                    }
                })
                return true
            }
        })
    }

    private fun initRecyclerView() {
        val isUsingCardView = getIsUsingCardView(getShp())
        dividerItemDecoration = DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)

        myAdapter1 = WordsAdapter(false, viewModel)
        myAdapter2 = WordsAdapter(true, viewModel)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = object : DefaultItemAnimator() {
                override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
                    super.onAnimationFinished(viewHolder)

                    val linearLayoutManager = layoutManager as LinearLayoutManager?
                    if (linearLayoutManager != null) {
                        val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
                        val lastPosition = linearLayoutManager.findLastVisibleItemPosition()
                        for (i in firstPosition..lastPosition) {
                            val holder = findViewHolderForAdapterPosition(i) as WordsAdapter.WordViewHolder?
                            holder?.textViewNumber?.text = (i + 1).toString()
                        }
                    }
                }
            }

            if (isUsingCardView) {
                adapter = myAdapter2
            } else {
                adapter = myAdapter1
                addItemDecoration(dividerItemDecoration)
            }
        }
    }

    private fun initFloatingActionButton() {
        binding.floatingActionButton.setOnClickListener {
            val navController = Navigation.findNavController(it)
            val action = WordsFragmentDirections.actionWordsFragmentToAddFragment()
            navController.navigate(action)
        }
    }

    private fun initItemTouchEvent() {
        ItemTouchHelper(ItemTouchCallback())
            .attachToRecyclerView(binding.recyclerView)
    }

    private fun getShp(): SharedPreferences {
        return requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE)
    }

    private fun getIsUsingCardView(shp: SharedPreferences): Boolean {
        return shp.getBoolean(IS_USING_CARD_VIEW, false)
    }

    inner class ItemTouchCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {

        private val wasteIcon: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_delete_forever_black_24dp)
        private val background: Drawable = ColorDrawable(Color.LTGRAY)

        // 拖拽排序的操作由于动作的和数据更新不同步，所以不能这样简单处理。拖拽速度快了就出错了。
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val wordToDelete = allWords[viewHolder.adapterPosition] ?: return
            viewModel.deleteWords(wordToDelete)

            Snackbar.make(binding.wordsFragmentView, "删除了一个词汇", Snackbar.LENGTH_SHORT)
                .setAction("撤销") {
                    undoAction = true
                    viewModel.insertWords(wordToDelete)
                }
                .show()
        }

        // 在滑动的时候，画出浅灰色背景和垃圾桶图标
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            if (wasteIcon == null) return

            val itemView = viewHolder.itemView
            val iconMargin = (itemView.height - wasteIcon.intrinsicHeight) / 2

            val iconLeft: Int
            val iconRight: Int
            val iconTop: Int = itemView.top + iconMargin
            val iconBottom: Int = iconTop + wasteIcon.intrinsicHeight

            val backTop: Int = itemView.top
            val backBottom: Int = itemView.bottom
            val backLeft: Int
            val backRight: Int

            when {
                dX > 0 -> {
                    backLeft = itemView.left
                    backRight = itemView.left + dX.toInt()
                    iconLeft = itemView.left + iconMargin
                    iconRight = iconLeft + wasteIcon.intrinsicWidth

                    background.setBounds(backLeft, backTop, backRight, backBottom)
                    wasteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                }
                dX < 0 -> {
                    backRight = itemView.right
                    backLeft = itemView.right + dX.toInt()
                    iconRight = itemView.right - iconMargin
                    iconLeft = iconRight - wasteIcon.intrinsicWidth

                    background.setBounds(backLeft, backTop, backRight, backBottom)
                    wasteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                }
                else -> {
                    background.setBounds(0, 0, 0, 0)
                    wasteIcon.setBounds(0, 0, 0, 0)
                }
            }

            background.draw(c)
            wasteIcon.draw(c)
        }
    }

}
