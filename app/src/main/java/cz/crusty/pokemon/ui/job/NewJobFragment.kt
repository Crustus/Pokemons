package cz.crusty.pokemon.ui.job

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import cz.crusty.pokemon.R
import cz.crusty.pokemon.ui.dialog.imagepicker.ImagePickerBottomSheet
import cz.crusty.pokemon.ui.dialog.imagepicker.Item
import kotlinx.android.synthetic.main.fragment_new_job.*
import kotlinx.android.synthetic.main.fragment_new_job.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NewJobFragment : Fragment() {

    private lateinit var getCameraImage: ActivityResultLauncher<Uri>
    private lateinit var getGalleryImage: ActivityResultLauncher<String>
    private val viewModel: NewJobViewModel by viewModel()

    private var uriToCapture: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate() %s", this)

        getCameraImage = registerForActivityResult(ActivityResultContracts.TakePicture()) { taken: Boolean ->
            Timber.d("camera taken: %s", taken)
            if (taken) {
                viewModel.setImageUri(uriToCapture)
            }
            else {
                uriToCapture?.path?.let {
                    File(it).delete()
                }
                uriToCapture = null
            }
        }

        getGalleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            Timber.d("gallery taken: %s", it)
            viewModel.setImageUri(it)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_new_job, container, false)

        root.apply {
            photo.setOnClickListener {
                val bottomSheet = ImagePickerBottomSheet()
                bottomSheet.setOnClickListener {
                    Timber.d("onClick() %s", it)
                    when(it) {
                        is Item.Camera -> takePicture()
                        is Item.Gallery -> pickFromGallery()
                    }
                }
                bottomSheet.show(childFragmentManager, ImagePickerBottomSheet.TAG)
            }

            submit.setOnClickListener {
                viewModel.loadJobs();
            }
        }

        viewModel.apply {
            text.observe(viewLifecycleOwner, {
                description.setText(it.toString())
            })

            imageUri.observe(viewLifecycleOwner, {
                Timber.d("imageUri: %s", it)
                if (it == null)
                    photo.setImageResource(android.R.drawable.ic_menu_report_image)
                else
                    photo.setImageURI(it)
            })
        }

        return root
    }

    private fun takePicture() {
        uriToCapture = getUriContentProvider()
        if (uriToCapture != null) {
            getCameraImage.launch(uriToCapture)
        }
    }

    private fun pickFromGallery() {
        getGalleryImage.launch("image/*")
    }

    @SuppressLint("SimpleDateFormat")
    private fun getUriContentProvider(): Uri? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).also {
            return FileProvider.getUriForFile(
                requireContext(),
                "com.example.android.fileprovider",
                it
            )
        }
    }
}