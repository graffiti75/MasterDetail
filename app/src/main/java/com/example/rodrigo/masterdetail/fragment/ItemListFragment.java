package com.example.rodrigo.masterdetail.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rodrigo.masterdetail.dummy.DummyContent;

/**
 * A list fragment representing a list of Items. This fragment also supports tablet devices by
 * allowing list items to be given an 'activated' state upon selection. This helps indicate which
 * item is currently being viewed in a {@link ItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks} interface.
 */
public class ItemListFragment extends ListFragment {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    /**
     * The serialization (saved instance state) Bundle key representing the activated item position.
     * Only used on tablets.
     */
    private static final String POSITION_SAVED_INSTANCE = "position_saved_instance";

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mPosition;

    //--------------------------------------------------
    // Callbacks
    //--------------------------------------------------

    /**
     * The fragment's current callback object, which is notified of list item clicks.
     */
    private Callbacks mCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {}
    };

    /**
     * A callback interface that all activities containing this fragment must implement. This
     * mechanism allows activities to be notified of item selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
     * screen orientation changes).
     */
    public ItemListFragment() {}

    //--------------------------------------------------
    // Fragment Life Cycle
    //--------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
            android.R.layout.simple_list_item_activated_1, android.R.id.text1, DummyContent.ITEMS));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION_SAVED_INSTANCE)) {
            setActivatedPosition(savedInstanceState.getInt(POSITION_SAVED_INSTANCE));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the fragment is attached to one)
        // that an item has been selected.
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Serialize and persist the activated item position.
        outState.putInt(POSITION_SAVED_INSTANCE, mPosition);
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    private void setActivatedPosition(int position) {
        mPosition = position;
    }
}