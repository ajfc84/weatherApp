package com.passageweather.utils;

public final class Constants {

    /* Navigation Menu */
    public static final int OPTION_ROOT_INDEX = 0;
    public static final int OPTION_MEDITERRANEAN_INDEX = 1;
    public static final int OPTION_WEST_INDIES_INDEX = 2;
    public static final int OPTION_NORTH_ATLANTIC_INDEX = 3;
    public static final int OPTION_SOUTH_ATLANTIC_INDEX = 4;
    public static final int OPTION_NORTH_PACIFIC_INDEX = 5;
    public static final int OPTION_SOUTH_PACIFIC_INDEX = 6;
    public static final int OPTION_INDIAN_INDEX = 7;
    public static final int OPTION_REGATTA_INDEX = 8;
    public static final int OPTION_ARRAY_SIZE = 9;


    /* Intents */
    public static final String INTENT_MAP_KEY = "map_key";


    /* passageWeather */
    // TODO eliminate enum map
    enum map {
        MEDITERRANEAN_SEA_WIND_GFS,
        MEDITERRANEAN_SEA_WIND_COAMPS,
        MEDITERRANEAN_SEA_WIND_WRF,
        MEDITERRANEAN_SEA_SURFACE_PRESSURE,
        MEDITERRANEAN_SEA_WAVES,
        MEDITERRANEAN_SEA_VISIBILITY,
        MEDITERRANEAN_SEA_PRECIPITATION,
        MEDITERRANEAN_SEA_CLOUD_COVER
    }

    public static final String BASE_URL = "https://www.passageweather.com/maps/";
    /* URL Path Elements */
    public static final String VAR_WIND_GFS = "m_gfs";
    public static final String VAR_WIND_COAMPS = "m_coamps";
    public static final String VAR_WIND_WRF = "m_wrf";
    public static final String VAR_WIND_NAM = "m_nam";
    public static final String VAR_SURFACE_PRESSURE = "m_pressure";
    public static final String VAR_WAVES = "m_medwaves";
    public static final String VAR_VISIBILITY = "m_visibility";
    public static final String VAR_PRECIPITATION = "m_rain";
    public static final String VAR_CLOUD_COVER = "m_clouds";
    public static final String VAR_GULF_STREAM = "m_ncom";
    public static final String REGION_MEDITERRANEAN_SEA = "med";
    public static final String REGION_WESTERN_MEDITERRANEAN = "westmed";
    public static final String REGION_CENTRAL_MEDITERRANEAN = "centralmed";
    public static final String REGION_EASTERN_MEDITERRANEAN = "eastmed";
    public static final String REGION_BLACK_SEA = "blacksea";
    public static final String REGION_ADRIATIC_AND_AEGEAN = "adriatic";
    public static final String REGION_STRAIT_OF_GIBRALTAR = "tarifa";
    public static final String REGION_BALEARIC_ISLANDS = "baleares";
    public static final String REGION_LIGURIAN_SEA = "giraglia";
    public static final String REGION_COSICA_AND_SARDINIA = "bonifacio";
    public static final String REGION_STRAIT_OF_BONIFACIO = "bomballey";
    public static final String REGION_SICILY_AND_MALTA = "middlesea";
    public static final String REGION_BERMUDA_TO_WEST_INDIES = "westindies";
    public static final String REGION_SOUTH_FLORIDA = "florida";
    public static final String REGION_FLORIDA_TO_WEST_INDIES = "bahamas";
    public static final String REGION_GULF_OF_MEXICO = "gulfmexico";
    public static final String REGION_CARIBBEAN_SEA = "florida";
    public static final String REGION_VERGIN_ISLANDS_TO_DOMINICA = "carib600";
    public static final String REGION_LEEWARD_ISLANDS = "leeward";
    public static final String REGION_WINDWARD_ISLANDS = "windward";
    public static final String REGION_APPROACHES_TO_PANAMA = "panama";
    public static final String REGION_NORTH_ATLANTIC_OCEAN = "natlantic";
    public static final String REGION_TROPICAL_ATLANTIC_OCEAN = "tropatlantic";
    public static final String REGION_SOUTH_TRANSATLANTIC = "arc";
    public static final String REGION_MEDITERRANEAN_TO_CARIBBEAN = "medtocarib";
    public static final String REGION_AZORES_TO_MEDITERRANEAN = "azores";
    public static final String REGION_NORTH_EUROPE_TO_ICELAND = "neurope";
    public static final String REGION_BALTIC_SEA = "baltic";
    public static final String REGION_NORTH_SEA = "northsea";
    public static final String REGION_BRITISH_ISLES = "britisles";
    public static final String REGION_IRELAND_TO_ENGLISH_CHANNEL = "fastnet";
    public static final String REGION_ENGLISH_CHANNNEL = "engchannel";
    public static final String REGION_BAY_OF_BISCAY = "biscay";
    public static final String REGION_PORTUGAL_TO_GIBRALTAR = "gibraltar";
    public static final String REGION_CANARY_ISLANDS = "canaries";
    public static final String REGION_LABRADOR_AND_GREENLAND = "labrador";
    public static final String REGION_NOVA_SCOTIA_AND_NEWFOUNDLAND = "novascotia";
    public static final String REGION_NEW_ENGLAND = "newengland";
    public static final String REGION_CHESAPEAKE_AND_DELAWERE = "chesdel";
    public static final String REGION_CAPE_HATTERAS_TO_FLORODA = "hatteras";
    public static final String REGION_NEWPORT_TO_BERMUDA = "bermuda";
    public static final String REGION_GREAT_LAKES = "greatLakes";
    public static final String REGION_LAKE_SUPERIOR = "superior";
    public static final String REGION_LAKE_MICHIGAN_AND_HURON = "michuron";
    public static final String REGION_LAKE_ONTARIO_AND_ERIE = "onterie";
    public static final String REGION_SOUTH_ATLANTIC_OCEAN = "satlantic";
    public static final String REGION_CAPE_TOWN_TO_RIO_DE_JANEIRO = "capebahia";
    public static final String REGION_BUENOS_AIRES_TO_RIO_DE_JANEIRO = "bario";
    public static final String REGION_DRAKE_PASSAGE = "capehorn";
    public static final String REGION_SOUTH_AFRICA_TO_SEYCHELLES = "safrica";
    public static final String REGION_NORTH_PACIFIC_OCEAN = "npacific";
    public static final String REGION_GULF_OF_ALASKA = "alaska";
    public static final String REGION_CALIFORNIA_TO_HAWAII = "transpac";
    public static final String REGION_HAWAII = "hawaii";
    public static final String REGION_USA_WEST_COAST = "westcoast";
    public static final String REGION_STRAIT_OF_JUAN_DE_FUCA = "puget";
    public static final String REGION_SOUTHERN_CALIFORNIA = "socal";
    public static final String REGION_CALIFORNIA_TO_MEXICO = "mexico";
    public static final String REGION_BAJA_CALIFORNIA = "baja";
    public static final String REGION_MEXICO_TO_PANAMA = "panama";
    public static final String REGION_PANAMA_TO_THE_GALAPAGOS = "galapagos";
    public static final String REGION_NORTH_AMERICA_TO_POLYNESIA = "tahiti";
    public static final String REGION_SEA_OF_JAPAN = "japan";
    public static final String REGION_JAPAN_TO_MICRONESIA = "micronesia";
    public static final String REGION_STRAIT_OF_MALACCA = "malacca";
    public static final String REGION_SOUTH_CHINA_SEA = "chinasea";
    public static final String REGION_SOUTH_PACIFIC_OCEAN = "spacific";
    public static final String REGION_OCEANIA = "oceania";
    public static final String REGION_FIJI_TO_MARQUESAS = "marquesas";
    public static final String REGION_NORTHEAST_AUSTRALIA_AND_CORALSEA = "coralsea";
    public static final String REGION_SOUTHEAST_AUSTRALIA = "hobart";
    public static final String REGION_SOUTHWEST_AUSTRALIA = "leeuwin";
    public static final String REGION_TASMAN_SEA = "tasman";
    public static final String REGION_NEW_ZEALAND = "newzealand";
    public static final String REGION_NORTH_INDIAN_OCEAN = "indian";
    public static final String REGION_SOUTH_INDIAN_OCEAN = "sindian";
    public static final String REGION_RED_AND_ARABIAN_SEAS = "arabian";
    public static final String REGION_ATLANTIC_RALLY = "arc";
    public static final String REGION_CABO_SAN_LUCAS_RACE = "baja";
    public static final String REGION_CAPE_TO_RIO_RACE = "capebahia";
    public static final String REGION_CHINA_SEA_RACE = "chinasea";
    public static final String REGION_GIRAGLIA_RACE = "giraglia";
    public static final String REGION_MARION_BERMUDA_RACE = "bermuda";
    public static final String REGION_MIDDLE_SEA_RACE = "middlesea";
    public static final String REGION_MONTEGO_BAY_RACE = "bahamas";
    public static final String REGION_NEWPORT_BERMUDA_RACE = "bermuda";
    public static final String REGION_PACIFIC_CUP = "trasnpac";
    public static final String REGION_ROLEX_FASTNET_RACE = "fastnet";
    public static final String REGION_RORC_CARIBBEAN_600 = "carib600";
    public static final String REGION_RORC_TRANSATLANTIC_RACE = "arc";
    public static final String REGION_SYDNEY_HOBART_RACE = "hobart";
    public static final String REGION_TRANSATLANTIC_RACE = "transatlantic";
    public static final String REGION_TRANSPAC_RACE = "transpac";

}
