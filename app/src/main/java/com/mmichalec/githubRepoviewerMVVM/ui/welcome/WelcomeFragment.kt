package com.mmichalec.githubRepoviewerMVVM.ui.welcome

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mmichalec.githubRepoviewerMVVM.R
import com.mmichalec.githubRepoviewerMVVM.databinding.WelcomeScreenBinding
import com.mmichalec.githubRepoviewerMVVM.util.NetworkConnection

class WelcomeFragment : Fragment(R.layout.welcome_screen) {
    private var isFirstVisit = true
    private val viewModel: WelcomeViewModel by viewModels()
    private var isConnection = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val networkConnection = NetworkConnection(activity?.applicationContext!!)
        networkConnection.observe(viewLifecycleOwner, Observer { isConnected ->
            isConnection = isConnected
            if (!isConnected) {
                noNetworkPopup()
            }
        })


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

    private fun showInfoDialog(message: String) {
        val builder = AlertDialog.Builder(this@WelcomeFragment.context)
        builder.setTitle("WARNING")
        builder.setMessage(message)

        builder.setPositiveButton("Show cached data") { dialogInterface: DialogInterface, i: Int ->
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToRepositoriesFragment("")
            findNavController().navigate(action)
        }

        builder.setNeutralButton("Try again") { dialogInterface: DialogInterface, i: Int ->
            if(!isConnection)
                noNetworkPopup()
        }
        builder.show()
    }

    private fun noNetworkPopup() {
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                showInfoDialog("Application is working in an offline mode.\n\nYou can only display repositories of last checked user. If this is your first use of please make sure you have valid internet connection before continuing.")
            }
        })
    }
}