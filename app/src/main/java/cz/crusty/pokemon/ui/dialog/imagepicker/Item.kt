package cz.crusty.pokemon.ui.dialog.imagepicker

sealed class Item(val image: Int, val text: String) {
    class Camera(image: Int, text: String) : Item(image, text)
    class Gallery(image: Int, text: String) : Item(image, text)
}
