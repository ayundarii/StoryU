package com.dicoding.storyu.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyu.data.model.Story
import com.dicoding.storyu.databinding.ItemStoryBinding

class ListStoriesAdapter(
    private val onClick: (String) -> Unit
): RecyclerView.Adapter<ListStoriesAdapter.StoryViewHolder>() {

    private var listStories = ArrayList<Story>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listStories: List<Story>) {
        this.listStories.clear()
        this.listStories.addAll(listStories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val recipe = listStories[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = listStories.size

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story){
            binding.root.setOnClickListener{
                onClick(story.id)
            }

            binding.tvName.text = story.name
            Glide.with(binding.root.context)
                .load(story.photoUrl)
                .into(binding.imgStory)
        }
    }
}