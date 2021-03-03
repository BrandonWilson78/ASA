package za.asa_media.awesome_sa.modules_.registered_users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Snow-Dell-05 on 6/23/2017.
 */

public class PostNewAdPresenter {

    private HashMap<String, String> mHasNearbyPlace;

    public PostNewAdPresenter() {
        mHasNearbyPlace = new HashMap<>();
    }


    public ArrayList<String> getNearByGooglePlaces() {

        mHasNearbyPlace.put("Things to do", "Thingstodo&keyword=Thingstodo");
        mHasNearbyPlace.put("Attractions", "Attractions&keyword=Attractions");
        mHasNearbyPlace.put("Cheap Flights", "CheapFlights&keyword=CheapFlights");
        mHasNearbyPlace.put("Car Rentals", "car_rental&keyword=car_rental");
        mHasNearbyPlace.put("Restaurants", "restaurant&keyword=restaurant");
        mHasNearbyPlace.put("Sushi", "sushi&keyword=sushi");
        mHasNearbyPlace.put("Pizza", "pizza&keyword=pizza");
        mHasNearbyPlace.put("Vegan & Vegetarian", "vegetarian&keyword=vegetarian");
        mHasNearbyPlace.put("Coffee", "Coffee&keyword=Coffee");
        mHasNearbyPlace.put("Burger Joints", "Burger&keyword=Burger");

        mHasNearbyPlace.put("Vineyards & Tastings", "Winefarms&keyword=Winefarms");

        mHasNearbyPlace.put("Craft Beer", "craftbeer&keyword=craftbeer");
        mHasNearbyPlace.put("Safari & Game reserves", "gamereserves&keyword=gamereserves");
        mHasNearbyPlace.put("Nightlife", "nightlife&keyword=nightlife");
        mHasNearbyPlace.put("Art & Crafts", "art_gallery");

        mHasNearbyPlace.put("Hotels & Accommodation", "hotel&accomodation&keyword=hotel&accomodation");

        mHasNearbyPlace.put("Events & Concerts", "event&concert&keyword=event&concert");

        mHasNearbyPlace.put("Shopping malls", "Shoppingmalls&keyword=Shoppingmalls");
        mHasNearbyPlace.put("Kids Activities", "KidsActivities&keyword=KidsActivities");
        mHasNearbyPlace.put("Transport & Taxi", "airportshuttleservice&keyword=airportshuttleservice");
        mHasNearbyPlace.put("Golf Venues", "golf&keyword=golfclub");
        mHasNearbyPlace.put("Tennis courts", "tennis&keyword=tennis");
        mHasNearbyPlace.put("Yoga Studios", "yoga&keyword=yogastudio");
        mHasNearbyPlace.put("Conference Venues", "Conference&keyword=Conference");
        mHasNearbyPlace.put("Travel & Tours", "travel_agency&keyword=travel_agency");
        mHasNearbyPlace.put("Hair & Beauty", "Hair&Beauty&keyword=Hair&Beauty");
        mHasNearbyPlace.put("Spas", "spa&keyword=spa");
        mHasNearbyPlace.put("Organic shops", "organicshops&keyword=organicshops");

        mHasNearbyPlace.put("Men's Grooming & Barber", "MensBarber&keyword=MensBarber");

        mHasNearbyPlace.put("Home makers & improvers", "HomeImprovers&keyword=HomeImprovers");

        mHasNearbyPlace.put("Handyman", "Handyman&keyword=Handyman");
        mHasNearbyPlace.put("Electricians", "electrician&keyword=electrician");
        mHasNearbyPlace.put("Plumbers", "plumber&keyword=plumber");
        mHasNearbyPlace.put("Banks", "bank&keyword=bank");
        mHasNearbyPlace.put("ATM Machines", "atm&keyword=atm");
        mHasNearbyPlace.put("Embassies", "embassy&keyword=embassy");
        //    mHasNearbyPlace.put("Currency Converter", "CurrencyConverter&keyword=CurrencyConverter");

        //   mHasNearbyPlace.put("Nearby", "Nearby&keyword=Nearby");
        mHasNearbyPlace.put("Hospitals", "hospital&keyword=hospital");
        mHasNearbyPlace.put("Wedding Venues", "Wedding&keyword=Wedding");
        mHasNearbyPlace.put("Ticket Purchase", "ticket&keyword=ticket");
        mHasNearbyPlace.put("Movie Theaters", "movie_theater&keyword=movie_theater");
        mHasNearbyPlace.put("Pharmacies", "pharmacy&keyword=pharmacy");
        mHasNearbyPlace.put("Gym", "gym&keyword=gym");

        //  mHasNearbyPlace.put("Zoo", "zoo");
        mHasNearbyPlace.put("Gas Stations", "gas_station&keyword=gas_station");
        mHasNearbyPlace.put("Retail therapy", "clothingstore&keyword=clothingstore");

        //  hotels and accommodations dropdown lis
        mHasNearbyPlace.put("3 Star hotels", "3starhotels&keyword=3starhotels");
        mHasNearbyPlace.put("4 Star hotels", "4starhotels&keyword=4starhotels");
        mHasNearbyPlace.put("5 Star hotels", "5starhotels&keyword=5starhotels");
        mHasNearbyPlace.put("Bed and breakfast", "breakfast&keyword=breakfast");
        mHasNearbyPlace.put("Guest houses", "guesthouse&keyword=guesthouse");
        mHasNearbyPlace.put("Self-Catering", "selfcatering&keyword=selfcatering");
        mHasNearbyPlace.put("Golf estate accommodation", "GolfEstate&keyword=GolfEstateamodation");
        mHasNearbyPlace.put("Villa accommodation", "villarentals&keyword=villarentals");
        //  mHasNearbyPlace.put("Pet friendly accommodation", "petfriendlyaccommodation&keyword=petfriendlyaccommodation");
        mHasNearbyPlace.put("Backpackers", "backpackers&keyword=backpackers");

        // things to do futher types.......
        mHasNearbyPlace.put("Bike Trail Tours", "biketrailtours&keyword=biketrailtours");
        mHasNearbyPlace.put("Hiking Trail Tours", "HikingTrailTours&keyword=HikingTrailTours");
        mHasNearbyPlace.put("Canoe Adventure", "CanoeAdventure&keyword=CanoeAdventure");
        mHasNearbyPlace.put("Kayak Adventure", "kayakAdventure&keyword=kayakAdventure");
        mHasNearbyPlace.put("Zip Lining", "ZipLining&keyword=ZipLining");
        mHasNearbyPlace.put("Quad Biking", "Biking&keyword=Biking");
        mHasNearbyPlace.put("Clay Pigeon Shooting", "claypigeonshooting&keyword=claypigeonshooting");
        mHasNearbyPlace.put("Adventure Outdoor Centres", "AdventureOutdoorCentres&keyword=AdventureOutdoorCentres");
        mHasNearbyPlace.put("Shark Cage Diving", "SharkCageDiving&keyword=SharkCageDiving");
        mHasNearbyPlace.put("Yacht Trips", "YachtTrips&keyword=YachtTrips");
        mHasNearbyPlace.put("Boat Trips", "BoatTrips&keyword=BoatTrips");
        mHasNearbyPlace.put("Paint Ball Adventure Games", "PaintBallAdventureGames&keyword=PaintBalladventureGames");
        mHasNearbyPlace.put("Team Building", "TeamBuilding&keyword=TeamBuilding");
        mHasNearbyPlace.put("Kart Racing", "KartRacing&keyword=KartRacing");
        mHasNearbyPlace.put("Horse Riding", "HorseRiding&keyword=HorseRiding");
        mHasNearbyPlace.put("Sky Diving", "skyDiving&keyword=skyDiving");
//        mHasNearbyPlace.put("Parachute", "");
        mHasNearbyPlace.put("Para Sailing", "Paragliding&keyword=Paragliding");
        //  mHasNearbyPlace.put("Gyrocopter", "Paragliding&keyword=Paraglidin");
        mHasNearbyPlace.put("Hot Air Balloon Adventure", "hotairballoonadventure&keyword=hotairballoonadventure");
        mHasNearbyPlace.put("Jet Ski Hire", "jetski&keyword=jetski");
        mHasNearbyPlace.put("Bike Hire", "bikehire&keyword=bikehire");
        mHasNearbyPlace.put("Drone Racing", "droneracing&keyword=droneracing");
        //   mHasNearbyPlace.put("Eco Racing", "Paragliding&keyword=Paraglidin");
        mHasNearbyPlace.put("Fly Fishing", "flyfishing&keyword=flyfishing");
        mHasNearbyPlace.put("Kite Surfing", "kite&keyword=kite");
        mHasNearbyPlace.put("Mountaineering", "mountaineering&keyword=mountaineering");
        mHasNearbyPlace.put("Water Ski-ing", "WaterSki-ing&keyword=WaterSki-ing");
        mHasNearbyPlace.put("Rock Climbing", "RockClimbing&keyword=RockClimbing");

        mHasNearbyPlace.put("SUP boarding", "SUP&keyword=SUP");
        mHasNearbyPlace.put("Paragliding", "paragliding&keyword=paragliding");
        //  mHasNearbyPlace.put("Retail therapy","Retailtherapy&keyword=Retailtherapy");


        //hashmap home improvers
        mHasNearbyPlace.put("Awnings", "Awnings&keyword=Awnings");
        mHasNearbyPlace.put("Building supplies", "Buildingsupplies&keyword=Buildingsupplies");
        mHasNearbyPlace.put("Building contractors", "Buildingcontractors&keyword= Buildingcontractors");
        mHasNearbyPlace.put("Home services", "homeservice&keyword=homeservice");
        mHasNearbyPlace.put("Carports", "Carport&keyword=Carport");
        mHasNearbyPlace.put("Home automation services", "Homeautomationservice&keyword=Homeautomationservice");
        mHasNearbyPlace.put("Home audio & visual services", "Homeaudio&visualservice&keyword=Homeaudio&visualservice");
        mHasNearbyPlace.put("CCTV home installers", "CCTV&keyword=CCTV");
        mHasNearbyPlace.put("Home décor services", "homedecorator");
        mHasNearbyPlace.put("Home furniture stores", "Homefurniturestores&keyword=Homefurniturestores");
        mHasNearbyPlace.put("Home appliance stores", "Homeappliancestores&keyword=Homeappliancestores");
        mHasNearbyPlace.put("Doors", "Doors&keyword=Doors");
        mHasNearbyPlace.put("Glass & Windows", "Glass&Windows&keyword=Glass&Windows");
        mHasNearbyPlace.put("Home security", "Homesecurity&keyword=Homesecurity");
        mHasNearbyPlace.put("Gates & Fencing", "Gates&Fencing&keyword=Gates&Fencing");
        mHasNearbyPlace.put("Pools & Spas installers", "Pools&Spasinstallers&keyword=Pools&Spasinstallers");
        mHasNearbyPlace.put("Garden & Landscape services", "Garden&Landscapeservices&keyword=Garden&Landscapeservices");
        mHasNearbyPlace.put("Home air conditioning & heating", "Homeairconditioning&heating&keyword=Homeairconditioning&heating");
        mHasNearbyPlace.put("Kitchen design & renovations", "Kitchendesign&renovations&keyword=Kitchendesign&renovations");
        mHasNearbyPlace.put("Bathroom design & renovations", "Bathroomdesign&renovations&keyword=Bathroomdesign&renovations");
        mHasNearbyPlace.put("Roofing & Ceilings", "Roofing&Ceilings&keyword=Roofing&Ceilings");
        mHasNearbyPlace.put("Home paving", "Homepaving&keyword=Homepaving");
        mHasNearbyPlace.put("Home flooring", "Homeflooring&keyword=Homeflooring");
        mHasNearbyPlace.put("Home lighting", "Homelighting&keyword=Homelighting");
        mHasNearbyPlace.put("Home fabrics", "Homefabrics&keyword=Homefabrics");

        ArrayList<String> sorted = new ArrayList<>(mHasNearbyPlace.keySet());
        Collections.sort(sorted, String.CASE_INSENSITIVE_ORDER);

        return (sorted);
    }
}



