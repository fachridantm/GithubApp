package com.dicoding.githubapp.ui.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.githubapp.R
import com.dicoding.githubapp.core.domain.model.User
import com.dicoding.githubapp.core.ui.FollowAdapter
import com.dicoding.githubapp.databinding.FragmentFollowBinding
import com.dicoding.githubapp.ui.detail.DetailUserActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FollowFragment : Fragment() {

    private var position: Int = 0

    private var _followBinding: FragmentFollowBinding? = null
    private val followBinding get() = _followBinding

    private val followAdapter: FollowAdapter by lazy { FollowAdapter(::followItemClicked) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _followBinding = FragmentFollowBinding.inflate(inflater, container, false)
        return followBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
    }

    fun setErrorText(username: String, position: Int) {
        when (position) {
            0 -> followBinding?.tvLottieEmptyList?.text =
                getString(
                    R.string.empty_list,
                    username,
                    getString(R.string.followers).lowercase(Locale.getDefault())
                )
            1 -> followBinding?.tvLottieEmptyList?.text =
                getString(
                    R.string.empty_list,
                    username,
                    getString(R.string.following).lowercase(Locale.getDefault())
                )
        }
    }

    fun setupData(user: List<User>) {
        if (user.isNotEmpty()) {
            followAdapter.submitList(user)
            showLottie(false)
        } else {
            showLottie(true)
        }
    }

    fun showShimmer(isLoading: Boolean) {
        followBinding?.apply {
            pbFollow.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            rvFollow.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun showLottie(isLottieVisible: Boolean) {
        followBinding?.apply {
            lottieEmptyList.visibility = if (isLottieVisible) View.VISIBLE else View.GONE
            tvLottieEmptyList.visibility = if (isLottieVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setupAdapter() {
        followBinding?.rvFollow?.apply {
            setHasFixedSize(true)
            adapter = followAdapter
        }
    }

    private fun followItemClicked(user: User) {
        val intent = Intent(requireContext(), DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(intent)
    }

    companion object {
        const val FOLLOWERS = 0
        const val FOLLOWING = 1

        fun newInstance(position: Int): FollowFragment {
            val fragment = FollowFragment()
            fragment.position = position
            return fragment
        }
    }
}