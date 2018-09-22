package pl.butajlo.androidadvanced.details

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bluelinelabs.conductor.Controller
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.screen_repo_details.view.*
import pl.butajlo.androidadvanced.R
import pl.butajlo.androidadvanced.base.BaseController
import javax.inject.Inject

class RepoDetailsController(bundle: Bundle) : BaseController(bundle) {

    companion object {
        const val REPO_NAME_KEY = "repo_name"
        const val REPO_OWNER_KEY = "repo_owner"

        @JvmStatic
        fun newInstance(repoOwner: String, repoName: String): Controller {
            val bundle = Bundle().apply {
                putString(REPO_NAME_KEY, repoName)
                putString(REPO_OWNER_KEY, repoOwner)
            }
            return RepoDetailsController(bundle)
        }
    }

    @Inject lateinit var viewModel: RepoDetailsViewModel
    @Inject lateinit var presenter: RepoDetailsPresenter

    @LayoutRes
    override fun layoutRes() = R.layout.screen_repo_details

    override fun onViewBound(view: View) {
        view.contributor_list.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = ContributorAdapter()
        }
    }

    override fun subscriptions(): Array<Disposable> {
        return arrayOf(
            viewModel.repoDetails()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if(it.loading) {
                            view?.loading_indicator?.visibility = View.VISIBLE
                            view?.content?.visibility = View.GONE
                            view?.tv_error?.apply {
                                visibility = View.GONE
                                text = null
                            }
                        } else {
                            if(it.isSuccess()) {
                                view?.tv_error?.apply {
                                    text = null
                                    visibility = View.GONE
                                }
                            } else {
                                view?.tv_error?.apply {
                                    setText(it.errorRes!!)
                                    visibility = View.VISIBLE
                                }
                            }
                            view?.loading_indicator?.visibility = View.GONE
                            view?.content?.visibility = if(it.isSuccess()) { View.VISIBLE } else { View.GONE }
                            view?.tv_error?.visibility = if(it.isSuccess()) { View.GONE } else { View.VISIBLE }
                            view?.tv_repo_name?.text = it.name
                            view?.tv_updated_date?.text = it.updatedDate
                            view?.tv_repo_description?.text = it.description
                            view?.tv_creation_date?.text = it.createdDate
                        }
                    },
                viewModel.contributors()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if(it.loading) {
                                view?.contributor_loading_indicator?.visibility = View.VISIBLE
                                view?.contributor_list?.visibility = View.GONE
                                view?.tv_contributors_error?.visibility = View.GONE
                                view?.tv_contributors_error?.text = null
                            } else {
                                view?.contributor_loading_indicator?.visibility = View.GONE
                                view?.contributor_list?.visibility = if (it.isSuccess()) { View.VISIBLE } else { View.GONE }
                                view?.tv_contributors_error?.visibility = if(it.isSuccess()) { View.GONE } else { View.VISIBLE }
                                if(it.isSuccess()) {
                                    view?.tv_contributors_error?.text = null
                                    (view?.contributor_list?.adapter as ContributorAdapter).setData(it.contributors)
                                } else {
                                    view?.tv_contributors_error?.setText(it.errorRes!!)
                                }
                            }
                        }
        )
    }

}