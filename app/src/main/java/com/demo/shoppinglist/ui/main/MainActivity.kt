package com.demo.shoppinglist.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.demo.shoppinglist.BuildConfig
import com.demo.shoppinglist.R
import com.demo.shoppinglist.ShoppingListApp
import com.demo.shoppinglist.databinding.ActivityMainBinding
import com.demo.shoppinglist.domain.ShopItem
import com.demo.shoppinglist.ui.shopitem.ShopItemActivity
import com.demo.shoppinglist.ui.shopitem.ShopItemFragment
import com.demo.shoppinglist.ui.shopitem.ShopListAdapter
import com.demo.shoppinglist.ui.shopitem.ShopListAdapter.Companion.MAX_POOL_SIZE
import com.demo.shoppinglist.ui.shopitem.ShopListAdapter.Companion.VIEW_TYPE_DISABLED
import com.demo.shoppinglist.ui.shopitem.ShopListAdapter.Companion.VIEW_TYPE_ENABLED
import com.demo.shoppinglist.ui.viewmodelfactory.ViewModelFactory
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ShoppingListApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        checkingPaidVersion()

        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun checkingPaidVersion() {
        if (BuildConfig.FLAVOR == FREE_VERSION) {
            viewModel.shopListWidthAds.observe(this) {
                shopListAdapter.submitList(it)
                ifListIsEmptyAddNotification(it.isEmpty())
            }
        } else {
            viewModel.shopList.observe(this) {
                shopListAdapter.submitList(it)
                ifListIsEmptyAddNotification(it.isEmpty())
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setUpRecyclerView() {
        with(binding.rwShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                VIEW_TYPE_ENABLED,
                MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                VIEW_TYPE_DISABLED,
                MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(binding.rwShopList)
    }

    private fun setupSwipeListener(rwShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                if (item is ShopItem) {
                    viewModel.deleteShopItem(item)
                    shopListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rwShopList)
    }

    private fun ifListIsEmptyAddNotification(value: Boolean) {
        if (value) {
            binding.tvAddFirstPurchase.visibility = View.VISIBLE
            binding.tvListMissing.visibility = View.VISIBLE
        } else {
            binding.tvAddFirstPurchase.visibility = View.INVISIBLE
            binding.tvListMissing.visibility = View.INVISIBLE
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }

        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    override fun OnEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    companion object {

        const val FREE_VERSION = "free"

        fun newInstanceMainActivity(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}