package com.hhh.gifapp.ui.mainFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.hhh.gifapp.R
import com.hhh.gifapp.databinding.FragmentMainBinding
import com.hhh.gifapp.util.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    private var searchGifs: TextInputEditText? = null
    private var gifsRecyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var adapterGif: GifAdapter? = null

    private val viewModelMain by viewModels<MainViewModel>()
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    @SuppressLint("LogNotTimber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchGifs = mBinding.searchGifs
        gifsRecyclerView = mBinding.gifsRecyclerView
        progressBar = mBinding.progressBar

        initAdapter()
        adapterGif?.setClickListener(object: GifClickListener {
            override fun onClickListener(urlCurrent: String) {
                val bundle = Bundle()
                bundle.putString("urlGif", urlCurrent)
                Log.d("checkData", "main $urlCurrent")
                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_mainFragment_to_detailFragment, bundle)
            }
        })

        viewModelMain.gifLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    progressBar?.visibility = View.VISIBLE
                }
                is State.Success -> {
                    progressBar?.visibility = View.INVISIBLE
                    adapterGif?.setDiffer(it.data!!.data)
                    Log.d("checkData", "Main Fragment, success: ${it.data!!.data}")
                    Log.d("checkData", "Main Fragment, success: ${adapterGif?.itemCount}")
                }
                is State.Error -> {
                    progressBar?.visibility = View.INVISIBLE
                    Log.e("checkData", "Main Fragment, error: ${it}")
                }
                else -> {
                    progressBar?.visibility = View.INVISIBLE
                    Log.e("checkData", "Main Fragment, else")
                }
            }
        }

        searchGifs?.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(450)
                text.let {
                    if (it.toString().isNotEmpty()) {
                        viewModelMain.getGifs(query = it.toString())
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        adapterGif = GifAdapter()
        gifsRecyclerView?.apply {
            adapter = adapterGif
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        job?.cancel()
    }
}