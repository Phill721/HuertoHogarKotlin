import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cosa.data.repository.UsuarioRepository
import com.example.cosa.presentation.viewmodel.SessionViewModel

class SessionViewModelFactory(
    private val usuarioRepository: UsuarioRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(usuarioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
