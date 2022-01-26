package uz.ali.testalif.fragments.done

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
import uz.ali.testalif.databinding.FragmentDoneBinding

@AndroidEntryPoint
class FragmentDone:Fragment() {
    lateinit var binding:FragmentDoneBinding
    lateinit var viewModel: DoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentDoneBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(requireActivity()).get(DoneViewModel::class.java)
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
        viewModel.getTaskDoneObserver("true").observe(requireActivity(), {
            binding.Recycler.adapter = ItemsAdapter(it)

            binding.taskCount.setText("${it.size} Задача")
        })
    }

}