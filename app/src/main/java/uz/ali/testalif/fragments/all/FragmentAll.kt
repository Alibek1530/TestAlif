package uz.ali.testalif.fragments.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.ali.testalif.adapters.ItemsAdapter
import uz.ali.testalif.databinding.FragmentAllBinding

@AndroidEntryPoint
class FragmentAll:Fragment() {
    lateinit var binding:FragmentAllBinding
    lateinit var viewModel: AllViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentAllBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(AllViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        setRecData()
    }

    fun setRecycler() {
        binding.Recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun setRecData() {
        viewModel.getAllTaskObserver().observe(requireActivity(), {
            binding.Recycler.adapter = ItemsAdapter(it)

            binding.taskCount.setText("${it.size} Задача")
        })
    }

}