package com.mmichalec.githubRepoviewerMVVM.ui.welcome

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mmichalec.githubRepoviewerMVVM.R
import com.mmichalec.githubRepoviewerMVVM.databinding.WelcomeScreenBinding

class WelcomeFragment : Fragment(R.layout.welcome_screen) {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val anim = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fade_in)

        val binding = WelcomeScreenBinding.bind(view)
        setHasOptionsMenu(false)



        binding.apply {

            anim.startOffset = 2000
            textViewByMMichalec.startAnimation(anim)

            buttonStart.setOnClickListener {
                val githubUser = editTextRepoName.text.toString()
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToRepositoriesFragment(githubUser)
                findNavController().navigate(action)
            }
            val url = Uri.parse("https://www.linkedin.com/in/mateusz-michalec-b2aa7b124/")
            val intent = Intent(Intent.ACTION_VIEW, url)

            textViewWelcome!!.apply {
                setOnClickListener{
                    startActivity(intent)
                }
            }
        }


    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}