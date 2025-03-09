package es.studium.mispedidospendientes.ui.tiendas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TiendasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TiendasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}