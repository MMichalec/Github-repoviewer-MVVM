package com.mmichalec.githubRepoviewerMVVM.ui.welcome

import android.app.Application
import android.content.Intent
import android.inputmethodservice.Keyboard
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
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
    private var isFirstVisit = true
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val anim = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fade_in)
        val anim2 = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fade_in)
        val anim3 = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fade_in)
        val anim4 = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fade_out)


        val binding = WelcomeScreenBinding.bind(view)
        setHasOptionsMenu(false)



        binding.apply {

            anim.startOffset = 2000
            textViewByMMichalec.startAnimation(anim)

            if(isFirstVisit){
                anim2.startOffset = 3500
            }

            editTextRepoName.startAnimation(anim2)

            if(editTextRepoName.text.isNullOrBlank()){
                buttonStart.visibility = View.GONE
            }

            buttonStart.setOnClickListener {
                view.clearFocus()
                val githubUser = editTextRepoName.text.toString()
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToRepositoriesFragment(githubUser)
                findNavController().navigate(action)
                editTextRepoName.text.clear()
                isFirstVisit = false
            }
            val url = Uri.parse("https://www.linkedin.com/in/mateusz-michalec-b2aa7b124/")
            val intent = Intent(Intent.ACTION_VIEW, url)

            textViewWelcome!!.apply {
                setOnClickListener{
                    startActivity(intent)
                }
            }


            editTextRepoName.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(p0?.length!! > 0 && buttonStart.visibility == View.GONE){
                        buttonStart.visibility = View.VISIBLE
                        buttonStart.startAnimation(anim3)
                    }
                    if(p0.isEmpty() && buttonStart.visibility != View.GONE) {
                        buttonStart.startAnimation(anim4)
                        buttonStart.visibility = View.GONE
                    }
                }
            })

            editTextRepoName.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    view.clearFocus()
                    val githubUser = editTextRepoName.text.toString()
                    val action = WelcomeFragmentDirections.actionWelcomeFragmentToRepositoriesFragment(githubUser)
                    findNavController().navigate(action)
                    editTextRepoName.text.clear()
                    isFirstVisit = false
                    return@OnKeyListener true
                }
                false
            })

            }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}