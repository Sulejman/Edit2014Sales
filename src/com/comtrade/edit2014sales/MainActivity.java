package com.comtrade.edit2014sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;
    

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
            	if(position==1){
	            	ListaFragmentInterface fragment = (ListaFragmentInterface)
	                		mAppSectionsPagerAdapter.instantiateItem(mViewPager, position);
	                if (fragment != null) {
	                    fragment.fragmentBecameVisible();
	                } 
            	}
                actionBar.setSelectedNavigationItem(position);
                
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new DodajSectionFragment();

                case 1:
                    return new ListaSectionFragment();
                    
                default:
                    return new KasaSectionFragment();
                   
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch(position){
        		case 0:
        			return "Dodaj";
        		case 1:
        			return "Lista";
        		default:
        			return "Kasa";
        	}
        }
    }


    public static class DodajSectionFragment extends Fragment 
    					implements View.OnClickListener {
    	
    	Button dodaj;
    	EditText naziv;
    	EditText barkod;
    	EditText cijena;
    	EditText opis;
    	Artikal artikal;
    	ArtikalDAO dao;
    	
    	@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
    		if (container == null) {
    	        return null;
    	    }
            artikal = new Artikal();
            dao = new ArtikalDAO(getActivity().getApplicationContext());
            dao.open();
            View rootView = inflater.inflate(R.layout.fragment_dodaj, container, false);
            dodaj = (Button)rootView.findViewById(R.id.buttonDodaj);
            naziv = (EditText)rootView.findViewById(R.id.naziv);
            barkod = (EditText)rootView.findViewById(R.id.barkod);
            barkod.setInputType(InputType.TYPE_CLASS_NUMBER);
            cijena = (EditText)rootView.findViewById(R.id.cijena);
            cijena.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            opis = (EditText)rootView.findViewById(R.id.opis);
			dodaj.setOnClickListener(this);
            return rootView;
        }

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			artikal.setNaziv(naziv.getText().toString());
			artikal.setCijena(Float.parseFloat(cijena.getText().toString()));
			artikal.setBarkod(Integer.parseInt(barkod.getText().toString()));
			artikal.setOpis(opis.getText().toString());
			
			/*Log.d("CIJENA:", String.valueOf(artikal.getCijena()));
			Log.d("BARKOD:", String.valueOf(artikal.getBarkod()));
			Log.d("NAZIV:",artikal.getNaziv());
			Log.d("OPIS:",artikal.getOpis());
			*/
			dao.addArtikal(artikal);

			Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Artikal " 
					+ artikal.getNaziv() + " dodan", Toast.LENGTH_SHORT);
			
			toast.show();
			
		}

    }
    
    public static class CustomAdapter extends BaseAdapter {
    	
        Context context;
        List<Artikal> artikli;

        CustomAdapter(Context context, List<Artikal> artikli) {
            this.context = context;
            this.artikli = artikli;

        }

        @Override
        public int getCount() {

            return artikli.size();
        }

        @Override
        public Object getItem(int position) {

            return artikli.get(position);
        }

        @Override
        public long getItemId(int position) {

            return artikli.indexOf(getItem(position));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.list_item, null);
            }

            TextView txtNaziv = (TextView) convertView.findViewById(R.id.txt_naziv);
            TextView txtBarkod = (TextView) convertView.findViewById(R.id.txt_barkod);
            TextView txtCijena = (TextView) convertView.findViewById(R.id.txt_cijena);
            TextView txtOpis = (TextView) convertView.findViewById(R.id.txt_opis);
            //ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
            
            Artikal artikal_pos = artikli.get(position);
            // setting the image resource and title
            //imgIcon.setImageResource(artikal_pos.getIcon());
            txtNaziv.setText(artikal_pos.getNaziv());
            txtCijena.setText(String.valueOf(artikal_pos.getCijena()));
            txtBarkod.setText(String.valueOf(artikal_pos.getBarkod()));
            txtOpis.setText(artikal_pos.getOpis());
            
            return convertView;

        }

    }


    public static class ListaSectionFragment extends ListFragment implements ListaFragmentInterface {
    	
    	private static final int ID_DELETE    = 1;
        private static final int ID_EDIT   = 2;
        QuickActionPopup quickActionPopup1 = null;
    	
    	CustomAdapter adapter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
        	//List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        	
     
            //String[] from = { "naziv","opis","barkod","cijena" };
            //int[] to = { R.id.naziv,R.id.opis,R.id.barkod, R.id.cijena};
            //SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.list_item, from, to);
            //setListAdapter(adapter);
        	
        	prepareQuickButton();
     
            return super.onCreateView(inflater, container, savedInstanceState);
        	
        }
        
        public void prepareQuickButton(){
        	QuickActionItem editItem    = new QuickActionItem(ID_DELETE, "Uredi", getResources().getDrawable(R.drawable.edit));
            QuickActionItem deleteItem     = new QuickActionItem(ID_EDIT, "Izbriši", getResources().getDrawable(R.drawable.delete));
            quickActionPopup1 = new QuickActionPopup(getActivity()
            		.getApplicationContext(), QuickActionPopup.HORIZONTAL);
            quickActionPopup1.addActionItem(editItem);
            quickActionPopup1.addActionItem(deleteItem);
            
            quickActionPopup1.setOnActionItemClickListener(new QuickActionPopup.OnActionItemClickListener() {           
                @Override
                public void onItemClick(QuickActionPopup source, int pos, int actionId) {               

                    //filtering items by id
                    if (actionId == ID_EDIT) {
                        Toast.makeText(getActivity().getApplicationContext(), "Edit Clicked", Toast.LENGTH_SHORT).show();
                    } else if (actionId == ID_DELETE) {
                        Toast.makeText(getActivity().getApplicationContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            
            quickActionPopup1.setOnDismissListener(new QuickActionPopup.OnDismissListener() {           
                @Override
                public void onDismiss() {
                    Toast.makeText(getActivity().getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        	super.onActivityCreated(savedInstanceState);
        	ArtikalDAO DAO = new ArtikalDAO(getActivity().getApplicationContext());
        	DAO.open();
        	
            List<Artikal> artikli = DAO.getAllArtikal();
            adapter = new CustomAdapter(getActivity(), artikli);
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        	
        }
        
        @Override
        public void fragmentBecameVisible() {
        	ArtikalDAO DAO = new ArtikalDAO(getActivity().getApplicationContext());
        	DAO.open();
        	
            List<Artikal> artikli = DAO.getAllArtikal();
            adapter = new CustomAdapter(getActivity(), artikli);
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // TODO Auto-generated method stub
            super.onListItemClick(l, v, position, id);
            quickActionPopup1.show(v);
            
        }
    }
    
    public static class KasaSectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_kasa, container, false);
            return rootView;
        }
    }
}
