package cz.crusty.pokemon.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cz.crusty.common.ui.dialog.ProgressDialogFragment

abstract class BaseFragment : Fragment() {

    protected fun setTitle(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }

    protected fun setSubtitle(subtitle: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = subtitle
    }

    // just some simple loading handling, the worst solution :)
    protected fun showLoading(show: Boolean) {
        childFragmentManager.beginTransaction().apply {
            if (show) {
                ProgressDialogFragment().show(this, PROGRESS_DIALOG)
            }
            else {
                val fragment = childFragmentManager.findFragmentByTag(PROGRESS_DIALOG)
                fragment?.let {
                    remove(it)
                    commit()
                }
            }
        }
    }

    companion object {
        private const val PROGRESS_DIALOG: String = "progress_dialog"
    }
}