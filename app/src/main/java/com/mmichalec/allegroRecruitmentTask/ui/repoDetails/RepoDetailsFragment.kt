package com.mmichalec.allegroRecruitmentTask.ui.repoDetails

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mmichalec.allegroRecruitmentTask.R
import com.mmichalec.allegroRecruitmentTask.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZonedDateTime

@AndroidEntryPoint
class RepoDetailsFragment: Fragment(R.layout.fragment_details) {

    private val args by navArgs<RepoDetailsFragmentArgs>()

    private val viewModel: RepoDetailsViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)



        binding.apply {


            textViewRepoName.apply {
                text = args.repoName
            }

               viewModel.repoDetails.observe(viewLifecycleOwner){
                   if(textViewUrl.text != null){
                       progressBar.visibility = View.GONE
                   }

                   textViewId.text = it.id.toString()
                   if(it.description == null){
                       textViewDescription.visibility = View.GONE
                       labelDescription.visibility = View.GONE
                       cardView.visibility = View.GONE
                   }
                   textViewDescription.text = it.description
                   textViewDateOfCreation.text = "Created:\n${ZonedDateTime.parse(it.created_at).dayOfMonth.toString()} ${ZonedDateTime.parse(it.created_at).month.toString()} ${ZonedDateTime.parse(it.created_at).year.toString()}"
                   textViewDateOfLastUpdate.text = "Last update:\n${ZonedDateTime.parse(it.updated_at).dayOfMonth.toString()} ${ZonedDateTime.parse(it.updated_at).month.toString()} ${ZonedDateTime.parse(it.updated_at).year.toString()}"
                   textViewWatchers.text = "${it.watchers_count}\nwatchers"
                   textViewSubscribers.text = "${it.subscribers_count}\nsubscribers"
                   textViewLanguage.text= it.language

                   val url = Uri.parse(it.html_url)
                   val intent = Intent(Intent.ACTION_VIEW,url)

                   textViewUrl.apply {

                       text = it.html_url
                       setOnClickListener {
                           startActivity(intent)
                       }
                   }

               }

            }
        }
    }
