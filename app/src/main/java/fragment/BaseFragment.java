package fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * Created by Saurabh(aqua) on 12-03-2016.
 */
public abstract class BaseFragment extends Fragment {

    private final String TAG = BaseFragment.class.getSimpleName();

    /**
     * Base fragment created to be extended by every fragment in this application. This class provides
     * dependency injection configuration, ButterKnife Android library configuration and some methods
     * common to every fragment.
     *
     * @author Saurabh
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract int getFragmentLayout();

}
