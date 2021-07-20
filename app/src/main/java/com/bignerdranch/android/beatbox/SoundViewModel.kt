package com.bignerdranch.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/*Когда вы вызываете функцию notifyChange() , она оповещает класс привязки о том,
что все Bindable -свойства ваших объектов были обновлены. Класс привязки вы-
полняет код внутри скобок {} для повторного заполнения представления. Таким
образом, при установке значения звука объект ListItemSoundBinding получит уведом-
ление и вызовет Button.setText(String) , как указано в файле list_item_sound.xml .*/

class SoundViewModel(private val beatBox: BeatBox): BaseObservable() {
    fun onButtonClicked() {
       sound?.let {
           beatBox.play(it)
       }
    }

    var sound: Sound? = null
    set(sound) {
        field = sound
        //notifyChange()
        notifyPropertyChanged(BR.title)
    }
    @get:Bindable
    val title: String?
    get() = sound?.name
}