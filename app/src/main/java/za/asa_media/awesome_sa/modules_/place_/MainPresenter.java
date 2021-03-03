package za.asa_media.awesome_sa.modules_.place_;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;

import za.asa_media.awesome_sa.R;
import za.asa_media.awesome_sa.support.check_permissions.CheckNetworkState;

import static android.content.Context.LOCATION_SERVICE;

public class MainPresenter {
    private Activity mContext;
    private HashMap<String, String> mHasNearbyPlace;
    private LocationManager manager = null;

    public MainPresenter(Activity mContext) {
        this.mContext = mContext;
        mHasNearbyPlace = new HashMap<>();
        try {
            if (manager == null) {
                manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean onStartCheck() {

        //whether provider is connected
        if (!new CheckNetworkState(mContext).checkNetwork()) {
            new CheckNetworkState(mContext).alert();

            //check provider provides data
        } else if (!new CheckNetworkState(mContext).isOnline()) {

            Toast.makeText(mContext, "Internet not working!", Toast.LENGTH_SHORT).show();
            //ok about internet
            //check GPS ENABLED
        } else if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            new AlertDialog.Builder(mContext)
                    .setTitle("Enable GPS Provider")
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mContext.finish();
                    mContext.moveTaskToBack(true);
                }
            }).show();

        } else {
            //Everything is enabled
            return true;
        }
        return false;
    }

    public String getNearByGooglePlaces(String key) {

        mHasNearbyPlace.put("Things to do", "keyword=Things to do");
        mHasNearbyPlace.put("Attractions", "keyword=Attractions");
        mHasNearbyPlace.put("Cheap Flights", "keyword=Cheap Flight");
        mHasNearbyPlace.put("Car Rentals", "keyword=Car Rental");
        mHasNearbyPlace.put("Restaurants", "keyword=Restaurant");
        mHasNearbyPlace.put("Sushi", "keyword=sushi");
        mHasNearbyPlace.put("Pizza", "keyword=pizza");
        mHasNearbyPlace.put("Vegan & Vegetarian", "keyword=vegetarian");
        mHasNearbyPlace.put("Coffee", "keyword=Coffee");
        mHasNearbyPlace.put("Burger Joints", "keyword=Burger");
        mHasNearbyPlace.put("Vineyards & Tastings", "keyword=Wine Farms & Wine Estates");
        mHasNearbyPlace.put("Craft Beer", "keyword=craftbeer");
        mHasNearbyPlace.put("Game Reserves", "keyword=Game Reserves");
        mHasNearbyPlace.put("Nightlife", "keyword=nightlife");
        mHasNearbyPlace.put("Art & Crafts", "keyword=artgallery");
        mHasNearbyPlace.put("Hotels & Accommodation", "keyword=hotel&accomodation");
        mHasNearbyPlace.put("Events & Concerts", "keyword=event&concert");
        mHasNearbyPlace.put("Shopping malls", "keyword=Shoppingmalls");
        mHasNearbyPlace.put("Kids Activities", "keyword=Kids Activities");
        mHasNearbyPlace.put("Transport & Taxi", "keyword=Airport Shuttle Service");
        mHasNearbyPlace.put("Golf Venues", "keyword=golfclub");
        mHasNearbyPlace.put("Tennis courts", "keyword=tennis");
        mHasNearbyPlace.put("Yoga Studios", "keyword=yoga");
        mHasNearbyPlace.put("Conference Venues", "keyword=Conference");
        mHasNearbyPlace.put("Organic shops", "keyword=organic shops");
        mHasNearbyPlace.put("Men's Grooming & Barber", "keyword=Mens Barber");
        mHasNearbyPlace.put("Home makers & improvers", "keyword=Home Improvers");
        mHasNearbyPlace.put("Handyman", "keyword=Handyman");
        mHasNearbyPlace.put("Electricians", "keyword=electrician");
        mHasNearbyPlace.put("Plumbers", "keyword=plumbers");
        mHasNearbyPlace.put("Banks", "keyword=bank");
        mHasNearbyPlace.put("ATM Machines", "keyword=atm");
        mHasNearbyPlace.put("Embassies", "keyword=Embassies");
        //    mHasNearbyPlace.put("Currency Converter", "CurrencyConverter&keyword=CurrencyConverter");

        //   mHasNearbyPlace.put("Nearby", "Nearby&keyword=Nearby");
        mHasNearbyPlace.put("Hospitals", "keyword=hospital");
        mHasNearbyPlace.put("Wedding Venues", "keyword=Wedding");
        mHasNearbyPlace.put("Ticket Purchase", "keyword=ticket");
        mHasNearbyPlace.put("Movie Theaters", "keyword=movietheater");
        mHasNearbyPlace.put("Pharmacies", "keyword=pharmacy");
        mHasNearbyPlace.put("Gym", "keyword=gym");

        //  mHasNearbyPlace.put("Zoo", "zoo");
        mHasNearbyPlace.put("Gas Stations", "keyword=Gas Station");
        mHasNearbyPlace.put("Retail therapy", "keyword=Clothing Store");

        //  hotels and accommodations dropdown lis
        mHasNearbyPlace.put("3 Star hotels", "keyword=3 Star Hotels");
        mHasNearbyPlace.put("4 Star hotels", "keyword=4 Star Hotels");
        mHasNearbyPlace.put("5 Star hotels", "keyword=5 Star Hotels");
        mHasNearbyPlace.put("Bed and breakfast", "keyword=Break Fast");
        mHasNearbyPlace.put("Guest houses", "keyword=guesthouse");
        mHasNearbyPlace.put("Self-Catering", "keyword=selfcatering");
        mHasNearbyPlace.put("Golf estate accommodation", "keyword=Golf Estate Accommodation");
        mHasNearbyPlace.put("Villa accommodation", "keyword=Villa accommodatio");
        mHasNearbyPlace.put("Backpackers", "keyword=backpackers");
        mHasNearbyPlace.put("Resorts", "keyword=Resorts");
        mHasNearbyPlace.put("Lodges", "keyword=Lodges");
        mHasNearbyPlace.put("Holiday Homes", "keyword=Holiday Homes");
        mHasNearbyPlace.put("Camping", "keyword=Camping");
        mHasNearbyPlace.put("Caravan", "keyword=Caravan");
        mHasNearbyPlace.put("Health Resorts", "keyword=Health Resorts");
        mHasNearbyPlace.put("Honeymoon retreats", "keyword=Honeymoon");
        mHasNearbyPlace.put("Game Lodges", "keyword=Game Lodges");
        mHasNearbyPlace.put("Apartments", "keyword=Apartments");
        mHasNearbyPlace.put("Ski Resorts", "keyword=Ski Hotels");
        //  mHasNearbyPlace.put("Pet friendly accommodation", "petfriendlyaccommodation&keyword=petfriendlyaccommodation");


        //  things to do futher types....
        // New categiry at 27_feb_2018

        mHasNearbyPlace.put("Surfing", "keyword=Surfing");
        mHasNearbyPlace.put("Surf Schools", "keyword=Surf Schools");
        mHasNearbyPlace.put("Ski schools", "keyword=SnowSki schools");
        mHasNearbyPlace.put("Snowboard hire", "keyword=Board hire");
        mHasNearbyPlace.put("Tourist information center", "keyword=Tourist information center");


        mHasNearbyPlace.put("Bike Trail Tours", "keyword=Bike Trail Tours");
        mHasNearbyPlace.put("Hiking Trail Tours", "keyword=Hiking Trail Tours");
        mHasNearbyPlace.put("Canoe Adventure", "keyword=Canoe");
        mHasNearbyPlace.put("Kayak rides & hire", "keyword=Kayak");
        mHasNearbyPlace.put("Zip Lining", "keyword=Zip Lining");
        mHasNearbyPlace.put("Quad Biking", "keyword=Quad Biking");
        mHasNearbyPlace.put("Shooting Range", "keyword=Shooting Range");
        mHasNearbyPlace.put("Adventure Outdoor Centres", "keyword=Adventure");
        mHasNearbyPlace.put("Shark Cage Diving", "keyword=Shark Cage Diving");
        mHasNearbyPlace.put("Yacht Trips", "keyword=Yacht Trips");
        mHasNearbyPlace.put("Boat Trips", "keyword=Boat Trips");
        mHasNearbyPlace.put("Paint Ball Adventure Games", "keyword=Paint Ball adventure");
        mHasNearbyPlace.put("Team Building", "keyword=Team Building");
        mHasNearbyPlace.put("Kart Racing", "keyword=Kart Racing");
        mHasNearbyPlace.put("Horse Riding", "keyword=Horse Riding");
        mHasNearbyPlace.put("Sky Diving", "keyword=Sky Diving");
 //     mHasNearbyPlace.put("Parachute", "");
        mHasNearbyPlace.put("Para Sailing", "keyword=Para Sailing");
 //     mHasNearbyPlace.put("Gyrocopter", "Paragliding&keyword=Paraglidin");
        mHasNearbyPlace.put("Hot Air Balloon Rides", "keyword=Hot Air Ballooning");
        mHasNearbyPlace.put("Jet Ski Hire", "keyword=Jet Ski");
        mHasNearbyPlace.put("Bike hire", "keyword=Bike Hire");
        mHasNearbyPlace.put("Drone Racing", "keyword=Drone Racing");
 //     mHasNearbyPlace.put("Eco Racing", "Paragliding&keyword=Paraglidin");
        mHasNearbyPlace.put("Fly Fishing", "keyword=Fly Fishing");
        mHasNearbyPlace.put("Kite Surfing", "keyword=Kite Surfing");
        mHasNearbyPlace.put("Mountaineering", "keyword=Mountaineering");
        mHasNearbyPlace.put("Water Skiing", "keyword=Water Sports");
        mHasNearbyPlace.put("Rock Climbing", "keyword=Rock Climbing");

        mHasNearbyPlace.put("SUP boarding", "keyword=SUP boarding");
        mHasNearbyPlace.put("Paragliding", "keyword=Paragliding");
 //     mHasNearbyPlace.put("Retail therapy","Retailtherapy&keyword=Retailtherapy");

 //     Hashmap Home Improvers
        mHasNearbyPlace.put("Awnings", "keyword=Awnings");
        mHasNearbyPlace.put("Building supplies", "keyword=Building supplies");
        mHasNearbyPlace.put("Building contractors", "keyword=Building contractors");
        mHasNearbyPlace.put("Home services", "keyword=Home service");
        mHasNearbyPlace.put("Carports", "keyword=Carports");
        mHasNearbyPlace.put("Home automation services", "keyword=Home automation services");
        mHasNearbyPlace.put("Home audio & visual services", "keyword=Home audio & visual services");
        mHasNearbyPlace.put("CCTV home installers", "keyword=CCTV");
        mHasNearbyPlace.put("Home décor services", "keyword=Interior Decor Services & Exterior Service");
        mHasNearbyPlace.put("Home furniture stores", "keyword=Home furniture stores");
        mHasNearbyPlace.put("Home appliance stores", "keyword=Home appliance stores");
        mHasNearbyPlace.put("Doors", "keyword=Doors");
        mHasNearbyPlace.put("Glass & Windows", "keyword=Glass & Windows");
        mHasNearbyPlace.put("Home security", "keyword=Home Security");
        mHasNearbyPlace.put("Gates & Fencing", "keyword=Gates & Fencing");
        mHasNearbyPlace.put("Pool installers", "keyword=Pool installers");
        mHasNearbyPlace.put("Garden & Landscape services", "keyword=Garden & Landscape services");
        mHasNearbyPlace.put("Home air conditioning & heating", "keyword=Home air conditioning & heating");
        mHasNearbyPlace.put("Kitchen design & renovations", "keyword=Kitchen design & renovations");
        mHasNearbyPlace.put("Bathroom design & renovations", "keyword=Bathroom design & renovation");
        mHasNearbyPlace.put("Roofing & Ceilings", "keyword=Roofing & Ceilings");
        mHasNearbyPlace.put("Home paving", "keyword=Home paving");
        mHasNearbyPlace.put("Home flooring", "keyword=Home flooring");
        mHasNearbyPlace.put("Home lighting", "keyword=Home lighting");
        mHasNearbyPlace.put("Home fabrics", "keyword=Fabric Shops &  Haberdashery Shops");

//      New Entry
        mHasNearbyPlace.put("Bars", "keyword=Bars");
        mHasNearbyPlace.put("Wine Bars", "keyword=Wine");
        mHasNearbyPlace.put("Whisky Bars", "keyword=Whisky");
        mHasNearbyPlace.put("Gin Bars", "keyword=Ginbar");
        mHasNearbyPlace.put("Night Clubs", "keyword=Night Clubs");
        mHasNearbyPlace.put("Gay Bars", "keyword=Gay Bars");
        mHasNearbyPlace.put("Casinos", "keyword=Casinos");
        mHasNearbyPlace.put("Revue Bars", "keyword=Revue Bars");

//      New Gym Entries
        mHasNearbyPlace.put("Pilates", "keyword=Pilates");
        mHasNearbyPlace.put("Crossfit Gym", "keyword=Crossfit Gym");
        mHasNearbyPlace.put("Boot Camp", "keyword=Boot Camp");
        mHasNearbyPlace.put("Calisthenics training", "keyword=Calisthenics");
        mHasNearbyPlace.put("Functional training gym", "keyword=Functional training gym");
        mHasNearbyPlace.put("Outdoor cardio training", "keyword=cardio");
        mHasNearbyPlace.put("Boxing gyms", "keyword=Boxing Gyms");
        mHasNearbyPlace.put("Brazilian jiu jitsu gym", "keyword=Brazilian jiu jitsu gym");
        mHasNearbyPlace.put("Capoeira", "keyword=Capoeira");
        mHasNearbyPlace.put("MMA Gym", "keyword=MMA");
        mHasNearbyPlace.put("Krav Maga", "keyword=Krav Maga");
        mHasNearbyPlace.put("Kick Boxing", "keyword=Kick Boxing");
        mHasNearbyPlace.put("Ballet", "keyword=Ballet");
        mHasNearbyPlace.put("Pole Dancing", "keyword=Pole Dancing");
        mHasNearbyPlace.put("Zumba Classes", "keyword=Zumba Classes");
        mHasNearbyPlace.put("Street workout", "keyword=Street workout");
        mHasNearbyPlace.put("Dance studios", "keyword=Dance studios");
        mHasNearbyPlace.put("Tai Chi classes", "keyword=Tai Chi classes");
        mHasNearbyPlace.put("AcroYoga", "keyword=AcroYoga Studio");

//      Restaurants Dropdown List
        mHasNearbyPlace.put("African", "keyword=African Restaurants");
        mHasNearbyPlace.put("American", "keyword=American Restaurants");
        mHasNearbyPlace.put("Argentinean", "keyword=Argentinean Restaurants");
        mHasNearbyPlace.put("Asian", "keyword=Asian Restaurants");
        mHasNearbyPlace.put("Austrian", "keyword=Austrian Restaurants");
        mHasNearbyPlace.put("Bakery", "keyword=Bakery");
        mHasNearbyPlace.put("Banting", "keyword=Bantin");
        mHasNearbyPlace.put("Belgian", "keyword=Belgian Restaurants");
        mHasNearbyPlace.put("Bistro", "keyword=Bistro Rrestaurants");
        mHasNearbyPlace.put("Braai", "keyword=Braai Rrestaurants");
        mHasNearbyPlace.put("Brazilian", "keyword=Brazilian Rrestaurants");
        mHasNearbyPlace.put("Breakfast", "keyword=Breakfast");
        mHasNearbyPlace.put("Buffet", "keyword=Buffet Rrestaurants");
        mHasNearbyPlace.put("Cajun", "keyword=Cajun Rrestaurants");
        mHasNearbyPlace.put("Californian", "keyword=Californian Restaurants");
        mHasNearbyPlace.put("Cape Malay", "keyword=Cape Malay Restaurants");
        mHasNearbyPlace.put("Cape Provencale", "keyword=CapeProvencale Restaurants");
        mHasNearbyPlace.put("Caribbean", "keyword=Caribbean Restaurants");
        mHasNearbyPlace.put("Chinese", "keyword=Chinese Restaurants");
        mHasNearbyPlace.put("Cocktails", "keyword=Cocktails Restaurants");
        mHasNearbyPlace.put("Coffee shop", "keyword=Coffeeshop");
        mHasNearbyPlace.put("Contemporary", "keyword=Contemporary Restaurants");
        mHasNearbyPlace.put("Continental", "keyword=Continental Restaurants");
        mHasNearbyPlace.put("Country", "keyword=Country Restaurants");
        mHasNearbyPlace.put("Cuban", "keyword=Cuban Restaurants");
        mHasNearbyPlace.put("Deli", "keyword=Deli Restaurants");
        mHasNearbyPlace.put("Dim Sum", "keyword=Dim Sum Restaurants");
        mHasNearbyPlace.put("Dutch", "keyword=Dutch Restaurants");
        mHasNearbyPlace.put("Egyptian", "keyword=Egyptian Restaurants");
        mHasNearbyPlace.put("Ethiopian", "keyword=Ethiopian Restaurants");
        mHasNearbyPlace.put("European", "keyword=European Restaurants");
        mHasNearbyPlace.put("Fine Dining", "keyword=Fine Dining Restaurants");
        mHasNearbyPlace.put("French", "&keyword=French Restaurants");
        mHasNearbyPlace.put("Fusion", "keyword=Fusion Restaurants");
        mHasNearbyPlace.put("Game", "keyword=Game Restaurants");
        mHasNearbyPlace.put("German", "keyword=German Restaurants");
        mHasNearbyPlace.put("Greek", "keyword=Greek Restaurants");
        mHasNearbyPlace.put("Grills", "keyword=Grills");
        mHasNearbyPlace.put("Halaal", "keyword=Halaal Restaurants");
        mHasNearbyPlace.put("Health", "keyword=Health Restaurants");

        mHasNearbyPlace.put("Indian", "keyword=Indian Restaurants");
        mHasNearbyPlace.put("Indonesian", "keyword=Indonesian Restaurants");
        mHasNearbyPlace.put("International", "keyword=International Restaurants");
        mHasNearbyPlace.put("Italian", "keyword=Italian Restaurants");
        mHasNearbyPlace.put("Japanese", "keyword=Japanese Restaurants");
        mHasNearbyPlace.put("Kosher", "keyword=Kosher Restaurants");
        mHasNearbyPlace.put("Kurdish", "keyword=Iranian Restaurants");
        mHasNearbyPlace.put("Lebanese", "keyword=Lebanese Restaurants");
        mHasNearbyPlace.put("Light meals", "keyword=Light meals");
        mHasNearbyPlace.put("Malaysian", "&keyword=Malaysian Restaurants");
        mHasNearbyPlace.put("Mauritian", "keyword=Mauritian Restaurants");
        mHasNearbyPlace.put("Medieval Feast", "keyword=Medieval Restaurants");
        mHasNearbyPlace.put("Mediterranean", "keyword=Mediterranean Restaurants");
        mHasNearbyPlace.put("Mexican", "keyword=Mexican Restaurants");
        mHasNearbyPlace.put("Moroccan", "keyword=Moroccan Restaurants");
        mHasNearbyPlace.put("Mozambican", "keyword=Mozambican Restaurants");
        mHasNearbyPlace.put("Organic", "keyword=Organic Restaurants");
        mHasNearbyPlace.put("Pancakes", "keyword=Pancakes");
        mHasNearbyPlace.put("Portuguese", "keyword=Portuguese Restaurants");
        mHasNearbyPlace.put("Pub Meals", "keyword=Pub Meals");
        mHasNearbyPlace.put("Russian", "keyword=Russian Restaurants");
        mHasNearbyPlace.put("Seafood", "keyword=Seafood Restaurants");
        mHasNearbyPlace.put("South African", "keyword=African Restaurants");
        mHasNearbyPlace.put("Spanish", "keyword=Spanish Restaurants");
        mHasNearbyPlace.put("Steakhouse", "keyword=Steak house");
        mHasNearbyPlace.put("Swiss", "keyword=Swiss Restaurants");
        mHasNearbyPlace.put("Tapas", "keyword=Tapas Restaurants");
        mHasNearbyPlace.put("Teppanyaki", "keyword=Teppanyaki Restaurants");
        mHasNearbyPlace.put("Thai", "keyword=Thai Restaurants");
        mHasNearbyPlace.put("Traditional", "keyword=Traditional Restaurants");
        mHasNearbyPlace.put("Turkish", "keyword=Turkishl Restaurants");
        mHasNearbyPlace.put("Vegan", "keyword=Vegan");
        mHasNearbyPlace.put("Vegetarian", "keyword=Vegetarian Restaurants");
        mHasNearbyPlace.put("Vietnamese", "keyword=Vietnamese Restaurants");
        mHasNearbyPlace.put("Wine Bar", "keyword=Wine Bar");
        mHasNearbyPlace.put("Fishing", "keyword=Fishing");
        mHasNearbyPlace.put("Wind Surfing", "keyword=Wind Surfing");
        mHasNearbyPlace.put("Helicopter tours", "keyword=Helicopter tours");
        mHasNearbyPlace.put("Fast Food Restaurant", "keyword=Fast Food Restaurant");


//      Retail Therapy Drop Down
        mHasNearbyPlace.put("High fashion stores", "keyword=High fashion stores");
        mHasNearbyPlace.put("Jewellery stores", "keyword=Jewellery stores");
        mHasNearbyPlace.put("Designer fashion stores", "keyword=Designer fashion stores");
        mHasNearbyPlace.put("Vintage clothing stores", "keyword=Vintage clothing stores");
        mHasNearbyPlace.put("Surf shops", "keyword=Surf shops");
        mHasNearbyPlace.put("Accessories stores", "keyword=Accessories stores");
        mHasNearbyPlace.put("Shoe stores", "keyword=Shoe stores");
        mHasNearbyPlace.put("Costume stores", "keyword=Costume stores");
        mHasNearbyPlace.put("Costume jewellery stores", "keyword=Costume jewellery stores");
        mHasNearbyPlace.put("Sport shops", "keyword=Sport shops");
        mHasNearbyPlace.put("Factory shops", "keyword=Factory shops");
        mHasNearbyPlace.put("Hemp clothing stores", "keyword=Hemp clothing stores");
        mHasNearbyPlace.put("Fabric shops", "keyword=Fabric shops");
        mHasNearbyPlace.put("Bamboo clothing", "keyword=Clothing bamboo stores");
        mHasNearbyPlace.put("Denim stores", "keyword=Denim stores");
        mHasNearbyPlace.put("Kids clothing stores", "keyword=Kids clothing stores");
        mHasNearbyPlace.put("Baby clothing stores", "keyword=Baby clothing stores");
        mHasNearbyPlace.put("Clothing stores", "keyword=Clothing stores");

        //   Get Spas List
        mHasNearbyPlace.put("Spas", "keyword=spa");
        mHasNearbyPlace.put("Massage salons", "keyword=Massage salons");
        mHasNearbyPlace.put("Swedish massage", "keyword=Swedish");
        mHasNearbyPlace.put("Aromatherapy massage", "keyword=Aromatherapy massage");
        mHasNearbyPlace.put("Hot Stone massage", "keyword=HotStone massagee");
        mHasNearbyPlace.put("Deep Tissue massage", "keyword=Deep Tissue massage");
        mHasNearbyPlace.put("Shiatsu massage", "keyword=Shiatsu massage");
        mHasNearbyPlace.put("Thai massage", "keyword=Thai massage");
        mHasNearbyPlace.put("Pregnancy massage", "keyword=Pregnancy massage");
        mHasNearbyPlace.put("Reflexology massage", "keyword=Reflexology massage");
        mHasNearbyPlace.put("Cranial Sacral Therapy", "keyword=Cranial Sacral Therapy");
        mHasNearbyPlace.put("Reiki massage", "keyword=Reiki massage");

        // Hair and beauty
        mHasNearbyPlace.put("Hair & Beauty", "keyword=Hair & Beauty");
        mHasNearbyPlace.put("Nail salons", "keyword=Nail salons");
        mHasNearbyPlace.put("Hair stylist", "keyword=Hair stylist");
        mHasNearbyPlace.put("Hair salons", "keyword=Hair salons");
        mHasNearbyPlace.put("Stylist", "keyword=Stylist");
        mHasNearbyPlace.put("Beauty salons", "keyword=Beauty salons");
        mHasNearbyPlace.put("Makeup salons", "keyword=Makeup salons");

        // Travels & Tour
        mHasNearbyPlace.put("Travel & Tours", "keyword=Travel & Tours");
        mHasNearbyPlace.put("Tour Guide", "keyword=Tour Guide");
        mHasNearbyPlace.put("Travel Agent", "keyword=Travel Agent");
        mHasNearbyPlace.put("Travel agencies", "keyword=Travel agencies");
        mHasNearbyPlace.put("Tour operators", "keyword=Tour operators");
        mHasNearbyPlace.put("Day Tours", "keyword=Day Tours");


        return mHasNearbyPlace.get(key);
    }

    public String[] getHairAndBeauty() {
        String[] hairNBeauty = {
                "Nail salons", "Hair stylist", "Hair salons",
                "Stylist", "Beauty salons", "Makeup salons"};

        Arrays.sort(hairNBeauty);
        return hairNBeauty;
    }


    public String[] getSpaList() {

        String[] spa = {
                "Spas", "Massage salons", "Swedish massage", "Aromatherapy massage",
                "Hot Stone massage", "Deep Tissue massage", "Shiatsu massage",
                "Thai massage", "Pregnancy massage", "Reflexology massage",
                "Cranial Sacral Therapy", "Reiki massage"};

        Arrays.sort(spa);
        return spa;
    }

    public String[] getTourAndTravels() {
        String[] toursNtravels = {"Tour Guide", "Travel Agent",
                "Travel agencies",
                "Tour operators",
                "Day Tours",
                "Snowboard hire",
                "Tourist information center"};

        Arrays.sort(toursNtravels);
        return toursNtravels;
    }

    public String[] getRetailTherapy() {
        //Todo: paste dialog values here

        String[] therapy = {"Denim stores", "Kids clothing stores", "Baby clothing stores",
                "High fashion stores", "Jewellery stores", "Designer fashion stores", "Vintage clothing stores",
                "Surf shops", "Accessories stores", "Shoe stores", "Costume stores", "Costume jewellery stores", "Sport shops",
                "Factory shops", "Hemp clothing stores", "Fabric shops", "Bamboo clothing", "Clothing stores"};
        Arrays.sort(therapy);
        return therapy;
    }


    public String[] getHomeImprovers() {

        //Todo: paste dialog values here
        String[] improvers = {"Awnings", "Building supplies", "Building contractors", "Home services",
                "Carports", "Home automation services", "Home audio & visual services",
                "CCTV home installers", "Home décor services",
                "Home furniture stores", "Home appliance stores", "Doors", "Glass & Windows",
                "Home security", "Gates & Fencing", "Pool installers", "Garden & Landscape services",
                "Home air conditioning & heating", "Kitchen design & renovations", "Bathroom design & renovations",
                "Roofing & Ceilings", "Home paving", "Home flooring", "Home lighting", "Home fabrics"};
        Arrays.sort(improvers);
        return improvers;
    }

    public String[] getHotelAccommodation() {
        String[] hotel = {
                "3 Star hotels", "4 Star hotels", "5 Star hotels",
                "Bed and breakfast", "Guest houses", "Self-Catering",
                "Golf estate accommodation", "Villa accommodation", "Backpackers", "Resorts",
                "Lodges", "Holiday Homes", " Camping", "Caravan", "Health Resorts",
                "Honeymoon retreats", "Game Lodges", "Apartments", "Ski Resorts"};
        Arrays.sort(hotel);
        return hotel;
    }

    public String[] getThingsToDo() {

        String[] things = {"Things to do", "SUP boarding", "Paragliding", "Bike Trail Tours", "Hiking Trail Tours", "Canoe Adventure",
                "Kayak rides & hire", "Zip Lining", "Quad Biking",
                "Shooting Range", "Adventure Outdoor Centres",
                "Shark Cage Diving", "Yacht Trips", "Boat Trips",
                "Paint Ball Adventure Games", "Team Building",
                "Kart Racing", "Horse Riding", "Sky Diving", /*"Parachute",*/
                "Para Sailing", /*"Gyrocopter",*/ "Hot Air Balloon Rides",
                "Jet Ski Hire", "Bike hire", "Drone Racing",
               /* "Eco Racing",*/ "Fly Fishing", "Fishing",
                "Wind Surfing", "Helicopter tours", "Kite Surfing",
                "Mountaineering", "Water Skiing", "Rock Climbing", "Surfing", "Surf Schools", "Ski schools"};
        Arrays.sort(things);

        return things;


    }

    public String[] getNightLifes() {

        String[] night = {
                "Nightlife", "Bars", "Wine Bars",
                "Whisky Bars", "Gin Bars", "Night Clubs",
                "Gay Bars", "Revue Bars", "Casinos"};
        Arrays.sort(night);
        return night;
    }

    public String[] getGymLis() {

        String[] gyms = {
                "Gym", "Pilates", "Crossfit Gym", "Boot Camp", "Calisthenics training", "Functional training gym",
                "Outdoor cardio training", "Boxing gyms", "Brazilian jiu jitsu gym", "Capoeira",
                "MMA Gym", "Krav Maga", "Kick Boxing", "Ballet", "Pole Dancing",
                "Zumba Classes", "Street workout", "Dance studios",
                "Tai Chi classes", "AcroYoga"};

        Arrays.sort(gyms);
        return gyms;
    }

    public String[] getRestaurantsList() {

        String[] restaurants = {"Restaurants", "African", "American", "Argentinean", "Asian", "Austrian", "Bakery", "Banting",
                "Belgian", "Bistro", "Braai", "Brazilian", "Breakfast", "Buffet", "Cajun", "Californian",
                "Cape Malay", "Cape Provencale", "Caribbean", "Chinese", "Cocktails", "Coffee shop", "Contemporary",
                "Continental", "Country", "Cuban", "Deli", "Dim Sum", "Dutch", "Egyptian",
                "Ethiopian", "European", "Fine Dining", "French", "Fusion", "Game", "German", "Greek",
                "Grills", "Halaal", "Health", "Indian", "Indonesian", "International",
                "Italian", "Japanese", "Kosher", "Kurdish", "Lebanese", "Light meals", "Malaysian",
                "Mauritian", "Medieval Feast", "Mediterranean", "Mexican",
                "Moroccan", "Mozambican", "Organic", "Pancakes", "Picnics", "Pizza", "Portuguese",
                "Pub Meals", "Russian", "Seafood", "South African", "Spanish", "Steakhouse", "Sushi",
                "Swiss", "Tapas", "Teppanyaki", "Thai", "Traditional",
                "Turkish", "Vegan", "Vegetarian", "Vietnamese", "Wine Bar", "Fast Food Restaurant"


        };
        Arrays.sort(restaurants);
        return restaurants;
    }

    public String[] getAllCatagory() {

        String[] nearByPlaces = {
                "Things to do", "Attractions", "Hotels & Accommodation", "Cheap Flights",
                "Car Rentals", "Restaurants", "Sushi", "Pizza", "Craft Beer", "Coffee",
                "Burger Joints", "Nightlife", "Vegan & Vegetarian", "Game reserves", "Vineyards & Tastings",
                "Art & Crafts", "Events & Concerts", "Retail therapy",
                "Shopping malls", "Organic shops", "Kids Activities", "Transport & Taxi", "Golf Venues",
                "Tennis courts", "Yoga Studios", "Conference Venues",
                "Travel & Tours", "Hair & Beauty", "Spas", "Men's Grooming & Barber", "Home makers & improvers",
                "Electricians", "Plumbers", "Handyman", "Banks",
                "ATM Machines", "Embassies", "Hospitals", "Pharmacies", "Wedding Venues",
                "Ticket Purchase", "Movie Theaters",
                "Gym", "Gas Stations", "Pilates", "Crossfit Gym", "Boot Camp", "Calisthenics training", "Functional training gym",
                "Outdoor cardio training", "Boxing gyms", "Brazilian jiu jitsu gym", "Capoeira",
                "MMA Gym", "Krav Maga", "Kick Boxing", "Ballet", "Pole Dancing",
                "Zumba Classes", "Street workout", "Dance studios",
                "Tai Chi classes", "AcroYoga", "African", "American", "Argentinean", "Asian", "Austrian", "Bakery", "Banting",
                "Belgian", "Bistro", "Braai", "Brazilian", "Breakfast", "Buffet", "Cajun", "Californian",
                "Cape Malay", "Cape Provencale", "Caribbean", "Chinese", "Cocktails", "Coffee shop", "Contemporary",
                "Continental", "Country", "Cuban", "Deli", "Dim Sum", "Dutch", "Egyptian",
                "Ethiopian", "European", "Fine Dining", "French", "Fusion", "Game", "German", "Greek",
                "Grills", "Halaal", "Health", "Indian", "Indonesian", "International",
                "Italian", "Japanese", "Kosher", "Kurdish", "Lebanese", "Light meals", "Malaysian",
                "Mauritian", "Medieval Feast", "Mediterranean", "Mexican",
                "Moroccan", "Mozambican", "Organic", "Pancakes", "Picnics", "Pizza", "Portuguese",
                "Pub Meals", "Russian", "Seafood", "South African", "Spanish", "Steakhouse", "Sushi",
                "Swiss", "Tapas", "Teppanyaki", "Thai", "Traditional",
                "Turkish", "Vegan", "Vegetarian", "Vietnamese", "Wine Bar", "Bars", "Wine Bars",
                "Whisky Bars", "Gin Bars", "Night Clubs",
                "Gay Bars", "Revue Bars", "Casinos", "SUP boarding", "Paragliding", "Bike Trail Tours", "Hiking Trail Tours", "Canoe Adventure",
                "Kayak rides & hire", "Zip Lining", "Quad Biking",
                "Shooting Range", "Adventure Outdoor Centres",
                "Shark Cage Diving", "Yacht Trips", "Boat Trips",
                "Paint Ball Adventure Games", "Team Building",
                "Kart Racing", "Horse Riding", "Sky Diving", /*"Parachute",*/
                "Para Sailing", /*"Gyrocopter",*/ "Hot Air Balloon Rides",
                "Jet Ski Hire", "Bike hire", "Drone Racing",
               /* "Eco Racing",*/ "Fly Fishing", "Fishing",
                "Wind Surfing", "Helicopter tours", "Kite Surfing",
                "Mountaineering", "Water Skiing", "Rock Climbing", "3 Star hotels", "4 Star hotels", "5 Star hotels",
                "Bed and breakfast", "Guest houses", "Self-Catering",
                "Golf estate accommodation", "Villa accommodation", "Backpackers", "Resorts",
                "Lodges", "Holiday Homes", " Camping", "Caravan", "Health Resorts",
                "Honeymoon retreats", "Game Lodges", "Apartments", "Ski Resorts", "Awnings", "Building supplies", "Building contractors", "Home services",
                "Carports", "Home automation services", "Home audio & visual services",
                "CCTV home installers", "Home décor services",
                "Home furniture stores", "Home appliance stores", "Doors", "Glass & Windows",
                "Home security", "Gates & Fencing", "Pool installers", "Garden & Landscape services",
                "Home air conditioning & heating", "Kitchen design & renovations", "Bathroom design & renovations",
                "Roofing & Ceilings", "Home paving", "Home flooring", "Home lighting", "Home fabrics", "Denim stores", "Kids clothing stores", "Baby clothing stores",
                "High fashion stores", "Jewellery stores", "Designer fashion stores", "Vintage clothing stores",
                "Surf shops", "Accessories stores", "Shoe stores", "Costume stores", "Costume jewellery stores", "Sport shops",
                "Factory shops", "Hemp clothing stores", "Fabric shops", "Bamboo clothing", "Clothing stores",
                "Tour Guide", "Travel Agent",
                "Travel agencies",
                "Tour operators",
                "Day Tours", "Spas", "Massage salons", "Swedish massage", "Aromatherapy massage",
                "Hot Stone massage", "Deep Tissue massage", "Shiatsu massage",
                "Thai massage", "Pregnancy massage", "Reflexology massage",
                "Cranial Sacral Therapy", "Reiki massage", "Nail salons", "Hair stylist", "Hair salons",
                "Stylist", "Beauty salons", "Makeup salons", "Snowboard hire",
                "Tourist information center", "Surfing", "Surf Schools", "Ski schools", "Fast Food Restaurant"};
        return nearByPlaces;
    }

    public int[] getCategoryDrawables() {
        int[] iconSet = {
                R.mipmap.menu_icon_24, R.mipmap.menu_icon_43, R.mipmap.menu_icon_26, R.mipmap.menu_icon_25,
                R.mipmap.menu_icon_21, R.mipmap.menu_icon_20, R.mipmap.menu_icon_44, R.mipmap.menu_icon_45,
                R.mipmap.menu_icon_19, R.mipmap.menu_icon_41, R.mipmap.menu_icon_40, R.mipmap.menu_icon_15,
                R.mipmap.menu_icon_42, R.mipmap.menu_icon_39,
                R.mipmap.menu_icon_27, R.mipmap.menu_icon_38, R.mipmap.menu_icon_37, R.mipmap.menu_icon_52, R.mipmap.menu_icon_16,
                R.mipmap.menu_icon_organic, R.mipmap.menu_icon_12, R.mipmap.menu_icon_11, R.mipmap.menu_icon_01,
                R.mipmap.menu_icon_36, R.mipmap.menu_icon_35, R.mipmap.menu_icon_10,
                R.mipmap.menu_icon_09, R.mipmap.menu_icon_34, R.mipmap.menu_icon_08, R.mipmap.menu_icon_33, R.mipmap.menu_icon_07, R.mipmap.menu_icon_31,
                R.mipmap.ic_plumber, R.mipmap.menu_icon_32,
                R.mipmap.menu_icon_30, R.mipmap.menu_icon_29, R.mipmap.menu_icon_18,
                R.mipmap.menu_icon_02, R.mipmap.menu_icon_48, R.mipmap.menu_icon_14,
                R.mipmap.menu_icon_23, R.mipmap.menu_icon_47,
                R.mipmap.menu_icon_49, R.mipmap.menu_icon_51, R.mipmap.blue_circle_};

        return iconSet;
    }

}
