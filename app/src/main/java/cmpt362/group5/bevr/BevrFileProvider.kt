package cmpt362.group5.bevr

import androidx.core.content.FileProvider

class BevrFileProvider : FileProvider() {
    companion object {
        const val AUTHORITY = "cmpt362.group5.bevr.fileprovider"
    }
}