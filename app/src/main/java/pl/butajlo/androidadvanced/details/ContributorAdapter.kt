package pl.butajlo.androidadvanced.details

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_user_list_item.view.*
import pl.butajlo.androidadvanced.models.Contributor
import pl.butajlo.androidadvanced.R

class ContributorAdapter : RecyclerView.Adapter<ContributorAdapter.ContributorViewHolder>() {

    private val data = ArrayList<Contributor>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_user_list_item, parent, false)
        return ContributorViewHolder(itemView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemId(position: Int) = data[position].id

    fun setData(contributors: List<Contributor>?) {
        if(contributors != null) {
            val diffResult = DiffUtil.calculateDiff(ContributorDiffCallback(data, contributors))
            data.clear()
            data.addAll(contributors)
            diffResult.dispatchUpdatesTo(this)
        } else {
            data.clear()
            notifyDataSetChanged()
        }
    }

    class ContributorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(contributor: Contributor) = with(itemView) {
                tv_user_name.text = contributor.login
                Glide.with(iv_avatar.context)
                        .load(contributor.avatarUrl)
                        .into(iv_avatar)
            }
        }
}