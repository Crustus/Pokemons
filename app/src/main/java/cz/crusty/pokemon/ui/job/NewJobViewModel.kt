package cz.crusty.pokemon.ui.job

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.crusty.common.util.logThread
import cz.crusty.pokemon.repository.remote.API
import cz.crusty.pokemon.repository.remote.Result
import cz.crusty.pokemon.repository.remote.model.NewJobResponse
import timber.log.Timber

class NewJobViewModel(
    private val api: API
) : ViewModel() {

    private var _imageUri = MutableLiveData<Uri?>()
    val imageUri get() = _imageUri as LiveData<Uri?>

    private val _text = MutableLiveData<NewJobResponse>()
    val text get() = _text as LiveData<NewJobResponse>

    init {

    }

    fun loadJobs() {
        api.getJobs {
            when (it) {
                is Result.Success -> {
                    Timber.d("result: %s", it.body?.jobs)
                }
                is Result.Error -> {
                    Timber.e("error: %s", it)
                }
            }
        }
    }

    fun setImageUri(uri: Uri?) {
        _imageUri.postValue(uri)
    }
}