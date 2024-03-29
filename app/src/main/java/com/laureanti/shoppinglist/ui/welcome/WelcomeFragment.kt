package com.laureanti.shoppinglist.ui.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.laureanti.shoppinglist.ShoppingListApp
import com.laureanti.shoppinglist.databinding.FragmentWelcomeBinding
import com.laureanti.shoppinglist.ui.viewmodelfactory.ViewModelFactory
import com.laureanti.shoppinglist.ui.main.MainActivity
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

        showInfo()

        binding.buttonAccept.setOnClickListener {
            launchMainActivity()
            finishWelcomeActivity()
        }
    }

    private fun showInfo() {
        val webView = binding.webViewInfo
        webView.webViewClient = WebViewClient()
        webView.loadUrl(WELCOME_INFO)
        webView.settings.setSupportZoom(true)
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

    interface OnAcceptListener {
        fun OnAccept()
    }

    companion object {
        const val WELCOME_INFO = "file:///android_asset/info.html"

        fun newInstanceWelcomeFragment(): WelcomeFragment {
            return WelcomeFragment()
        }
    }
}