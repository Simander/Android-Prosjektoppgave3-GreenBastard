package asimon.gamedev.com.rollaball;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by simander on 26/11/15.
 */
public class EnterScore extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstance){
        /**
         * Inflate the layout for this fragment
         */


        View root = (ViewGroup) inflater.inflate(R.layout.menu_fragment, container, false);
        Button regButt = (Button)root.findViewById(R.id.newgame_mbutton);




        return root;

    }

}
