package com.mmichalec.githubRepoviewerMVVM.ui.repositories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mmichalec.githubRepoviewerMVVM.R
import com.mmichalec.githubRepoviewerMVVM.data.Repo
import com.mmichalec.githubRepoviewerMVVM.databinding.ItemRepositoryBinding
import java.time.ZonedDateTime


class RepoAdapter(private val listener: RepoAdapter.OnItemClickListener): ListAdapter <Repo, RepoAdapter.RepoViewHolder>(
    REPO_COMPARATOR) {

    lateinit var context: Context;
    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val currentItem = getItem(position)



        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        context = parent.context
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }



    //nested class
    inner class RepoViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if(item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(repo: Repo) {
            binding.apply {

                textViewName.setAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.slide_in_right
                    )
                )
                textViewDescription.setAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.slide_in_left
                    )
                )
                textViewDateOfCreation.setAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.fade_in
                    )
                )
                textViewDateOfLastUpdate.setAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.fade_in
                    )
                )

                textViewName.text = repo.name

                if(repo.description !=null){
                    textViewDescription.text = repo.description
                }else {
                    textViewDescription.text = "<no description>"
                }
                textViewDateOfCreation.text = "Created: ${ZonedDateTime.parse(repo.created_at).dayOfMonth.toString()} ${
                    ZonedDateTime.parse(
                    repo.created_at
                ).month.toString()} ${ZonedDateTime.parse(repo.created_at).year.toString()}"
                textViewDateOfLastUpdate.text =  "Updated: ${ZonedDateTime.parse(repo.updated_at).dayOfMonth.toString()} ${
                    ZonedDateTime.parse(
                    repo.updated_at
                ).month.toString()} ${ZonedDateTime.parse(repo.updated_at).year.toString()}"

            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(repo: Repo)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem == newItem

        }
    }
}