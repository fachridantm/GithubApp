package com.dicoding.githubapp.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.githubapp.R
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.ui.FavoriteAdapter
import com.dicoding.githubapp.di.FavoriteModuleDependencies
import com.dicoding.githubapp.favorite.databinding.ActivityFavoriteUserBinding
import com.dicoding.githubapp.favorite.di.DaggerFavoriteComponent
import com.dicoding.githubapp.favorite.di.ViewModelFactory
import com.dicoding.githubapp.ui.detail.DetailUserActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var favoriteUserBinding: ActivityFavoriteUserBinding
    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter(::favoriteItemClicked) }

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)

        favoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(favoriteUserBinding.root)

        initView()
        setupData()
        setupAdapter()
    }

    private fun initView() {
        supportActionBar?.apply {
            title = getString(R.string.title_favorite)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupData() {
        favoriteViewModel.getFavoritedUsers().observe(this) {
            showLoading(true)
            if (!it.isNullOrEmpty()) {
                showLoading(false)
                showLottie(false)
            } else {
                showLottie(true)
                showLoading(false)
            }
            favoriteAdapter.submitList(it)
        }
    }

    private fun setupAdapter() {
        favoriteUserBinding.rvFav.apply {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        favoriteUserBinding.apply {
            pbFav.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            rvFav.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun showLottie(isLottieVisible: Boolean) {
        favoriteUserBinding.apply {
            lottieNoFavoriteUser.visibility = if (isLottieVisible) View.VISIBLE else View.INVISIBLE
            tvLottieNoFavoriteUser.visibility =
                if (isLottieVisible) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun favoriteItemClicked(user: User) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}