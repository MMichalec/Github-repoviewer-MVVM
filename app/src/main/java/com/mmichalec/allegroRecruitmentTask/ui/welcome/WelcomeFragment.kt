package com.mmichalec.allegroRecruitmentTask.ui.welcome

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mmichalec.allegroRecruitmentTask.R
import com.mmichalec.allegroRecruitmentTask.databinding.FragmentDetailsBinding
import com.mmichalec.allegroRecruitmentTask.databinding.WelcomeScreenBinding
import com.mmichalec.allegroRecruitmentTask.ui.repoDetails.RepoDetailsViewModel
import com.mmichalec.allegroRecruitmentTask.ui.repositories.RepoFragmentDirections

class WelcomeFragment : Fragment(R.layout.welcome_screen) {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = WelcomeScreenBinding.bind(view)
        setHasOptionsMenu(false)


        binding.apply {
            buttonStart.setOnClickListener {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToRepositoriesFragment()
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