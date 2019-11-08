package com.lcmobile.kotlinbestpractices

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcmobile.kotlinbestpractices.app.MainAdapter
import com.lcmobile.kotlinbestpractices.mvvm.ViewModelInterface
import com.lcmobile.kotlinbestpractices.mvvm.ViewState
import com.lcmobile.kotlinbestpractices.mvvm.viewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), ViewModelInterface<MainViewModel> {

    private val mainAdapter = MainAdapter()

    override val viewModel by viewModel<MainViewModel>(MainViewModel.FACTORY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupViewModel(this)

        recyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.divider
                    )?.let(::setDrawable)
                }
            )
        }

        fab.setOnClickListener {
            viewModel.generateMagicNumber()
        }
    }

    override fun onStateChanged(state: ViewState) {
        when (state) {
            is MainViewState.MagicNumberState -> handlerMagicNumberState(state)
            is MainViewState.Loading -> handlerLoading(state)
        }
    }

    private fun handlerMagicNumberState(state: MainViewState.MagicNumberState) {
        mainAdapter.apply {
            data = state.list
            notifyDataSetChanged()
        }
    }

    private fun handlerLoading(state: MainViewState.Loading) {
        loading.visibility = when (state) {
            is MainViewState.Loading.Show -> View.VISIBLE
            is MainViewState.Loading.Hide -> View.GONE
        }
    }
}
