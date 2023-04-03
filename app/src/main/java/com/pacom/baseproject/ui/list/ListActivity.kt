package com.pacom.baseproject.ui.list

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pacom.baseproject.BR
import com.pacom.baseproject.R
import com.pacom.baseproject.base.*
import com.pacom.baseproject.customview.RecyclerViewItemDecoration
import com.pacom.baseproject.databinding.ActivityListBinding
import com.pacom.baseproject.databinding.ItemListBinding
import com.pacom.baseproject.ui.manager.EmptyDataObserve
import com.pacom.baseproject.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : BaseActivity<ActivityListBinding, ListViewModel>() {
    override fun getViewModelClass() = ListViewModel::class.java

    override fun getLayoutId() = R.layout.activity_list

    override fun getBindingVariable() = BR.viewModel
    lateinit var mAdapter: BaseAdapter<String, ItemListBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listData = arrayListOf(
            "A", "B", "C", "D", "E", "F", "G", "H",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "A", "B", "C", "D", "E", "F", "G", "H"
        )

        mViewBinding.rcv.apply {
            mAdapter = BaseAdapter(
                context = this@ListActivity,
                layoutID = R.layout.item_list,
                bindingInterface = object :
                    GenericSimpleRecyclerBindingInterface<String, ItemListBinding> {
                    override fun bindView(
                        item: String,
                        view: ItemListBinding,
                        onItemClick: ((String) -> Unit)?,
                        position: Int
                    ) {
                        view.data = item
                        view.root.setOnSingleClickListener {
                            onItemClick?.invoke(item)
                        }
                        view.executePendingBindings()
                    }
                },
                onItemClickListener = { data ->
                    Toast.makeText(this@ListActivity, "Data is $data", Toast.LENGTH_LONG).show()
                })
            mAdapter.submitList(listData)
            mAdapter.registerAdapterDataObserver(
                EmptyDataObserve(
                    this,
                    mViewBinding.viewEmpty.root
                )
            )
            adapter = mAdapter
            val lm = LinearLayoutManager(this@ListActivity)
            layoutManager = lm
//            addItemDecoration(DividerItemDecoration(this@ListActivity, lm.orientation))
            addItemDecoration(
                RecyclerViewItemDecoration(
                    this@ListActivity,
                    R.drawable.line_separate
                )
            )
            val selectionTracker = SelectionTracker.Builder<Long>(
                "material",
                mViewBinding.rcv,
                GenericItemKeyProvider(mViewBinding.rcv),
                GenericItemLookup(mViewBinding.rcv),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()

            selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if (selectionTracker.selection.size() > 0) {
                        val selectionItem = selectionTracker.selection.map {
                            mAdapter.currentList[it.toInt()]
                        }
                        selectionItem.forEach {
                            Log.d("items", "$it")
                        }
                    }
                }
            })
            setHasFixedSize(true)
            mAdapter.selectionTracker = selectionTracker
        }
    }
}