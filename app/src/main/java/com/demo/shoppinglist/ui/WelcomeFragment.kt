package com.demo.shoppinglist.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.demo.shoppinglist.ShoppingListApp
import com.demo.shoppinglist.databinding.FragmentWelcomeBinding
import javax.inject.Inject


class WelcomeFragment : Fragment() {

    lateinit var viewModel: WelcomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as ShoppingListApp).component
    }

    var _binding: FragmentWelcomeBinding? = null
    val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private lateinit var onAcceptListener: OnAcceptListener

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnAcceptListener) {
            onAcceptListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onAcceptListener.OnAccept()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[WelcomeViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        observeViewModel()

        binding.buttonAccept.setOnClickListener {
            launchMainActivity()
            finishWelcomeActivity()
        }
    }

    private fun finishWelcomeActivity() {
        viewModel.finishWork()
    }

    private fun launchMainActivity() {
        val intent = MainActivity.newInstanceMainActivity(requireContext())
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    interface OnAcceptListener {
        fun OnAccept()
    }

    companion object {
        fun newInstanceWelcomeFragment(): WelcomeFragment {
            return WelcomeFragment()
        }
    }
}