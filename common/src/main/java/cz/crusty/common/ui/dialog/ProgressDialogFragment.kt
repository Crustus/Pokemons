package cz.crusty.common.ui.dialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import cz.crusty.common.R


class ProgressDialogFragment : DialogFragment(R.layout.dialog_progress) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }
}