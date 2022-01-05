package com.example.mywordlibrary.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywordlibrary.AddNewWordActivity;
import com.example.mywordlibrary.R;
import com.example.mywordlibrary.database_helper.DatabaseHelper;
import com.example.mywordlibrary.databinding.FragmentMyWordsTabBinding;
import com.example.mywordlibrary.models.Word;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMyWordsTabBinding binding;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public void onResume() {
        super.onResume();
        pageViewModel.setIndex(pageViewModel.currentIndex);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMyWordsTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final LinearLayout linearLayout = binding.wordsTabLayout;

        Log.i("phf","view triggered");
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<ArrayList<Word>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Word> wl) {
                linearLayout.removeAllViews();


                for(int i=0;i< wl.size();i++){

                    CardView cardView = new CardView(getContext());
                    cardView.setPadding(20,0,20,0);

                    TextView wordText = new TextView(getContext());
                    TextView meaningText = new TextView(getContext());
                    TextView spellingText = new TextView(getContext());
                    TextView exampleText = new TextView(getContext());

                    wordText.setText("Word: "+wl.get(i).getWord());
                    wordText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    wordText.setTypeface(wordText.getTypeface(), Typeface.BOLD);

                    meaningText.setText("Meaning: "+wl.get(i).getMeaning());
                    meaningText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    meaningText.setTypeface(meaningText.getTypeface(), Typeface.BOLD);

                    spellingText.setText("Spelling: "+wl.get(i).getSpelling());
                    spellingText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    spellingText.setTypeface(spellingText.getTypeface(), Typeface.BOLD);

                    exampleText.setText("Example: "+wl.get(i).getExample());
                    exampleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    exampleText.setTypeface(exampleText.getTypeface(), Typeface.BOLD);

                    LinearLayout linearLayoutHorizontal = new LinearLayout(getContext());
                    linearLayoutHorizontal.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    params.setMargins(20,20,20,20);
                    linearLayoutHorizontal.setPadding(0,20,0,20);
                    cardView.setLayoutParams(params);
                    cardView.setCardBackgroundColor(Color.argb(100,(int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));

                    Word currentWord = wl.get(i);
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Learned?")
                                    .setMessage(currentWord.isLearning()==1 ? "Are you sure you want to change this entry as learned?" :"Are you sure you want to change this entry as learning?" )

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.i("plf",currentWord.getWord());
                                            DatabaseHelper db = new DatabaseHelper(getContext());
                                            currentWord.setLearning(currentWord.isLearning()==0 ? 1:0);

                                            db.updateWord(currentWord);
                                            linearLayout.removeAllViews();
                                            pageViewModel.setIndex(pageViewModel.currentIndex);

                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    });
                    cardView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            Intent intent = new Intent(getContext(), AddNewWordActivity.class);
                            intent.putExtra("id",currentWord.getId());
                            intent.putExtra("word",currentWord.getWord());
                            intent.putExtra("meaning",currentWord.getMeaning());
                            intent.putExtra("spelling",currentWord.getSpelling());
                            intent.putExtra("example",currentWord.getExample());
                            intent.putExtra("isLearning",currentWord.isLearning());
                            startActivity(intent);

                            return true;
                        }
                    });
                    linearLayoutHorizontal.setGravity(Gravity.CENTER);

                    linearLayoutHorizontal.addView(wordText);
                    linearLayoutHorizontal.addView(meaningText);
                    linearLayoutHorizontal.addView(spellingText);
                    linearLayoutHorizontal.addView(exampleText);
                    cardView.addView(linearLayoutHorizontal);
                    linearLayout.addView(cardView);



                }

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}