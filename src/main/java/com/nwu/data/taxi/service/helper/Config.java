package com.nwu.data.taxi.service.helper;

import com.nwu.data.taxi.domain.model.GPSData;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Config {
    public static final String[] MODERATE_DRIVERS = {"new_udtiogi", "new_ekfrab", "new_idjojwa", "new_oknscocy", "new_aysmsla", "new_epabcadu", "new_oowfmu", "new_oyclsdas", "new_ubgihoot", "new_ochtin", "new_ockihoba", "new_imwrytsy", "new_eegcef", "new_iphespha", "new_agvexpia", "new_ognowwoz", "new_inlica", "new_udwadla", "new_odklobci", "new_ebdafya", "new_akyeerbi", "new_oljnek", "new_icodmup", "new_ewufri", "new_onwohyss", "new_oggluv", "new_eapceou", "new_urfhod", "new_agsuec", "new_atnokvo", "new_owufrey", "new_eotcue", "new_eadectio", "new_eufdod", "new_ickguwa", "new_ailbcuv", "new_igglitby", "new_atfrim", "new_amnurgji", "new_uthiss", "new_onghoji", "new_ijdeyd", "new_ufdoyno", "new_uthsyis", "new_ioivkn", "new_obceoo", "new_okchympy", "new_elhuyoi", "new_afpansh", "new_orkbats", "new_efcati", "new_ascley", "new_ushfeoj", "new_eoccoirv", "new_icdaheos", "new_ifefrof", "new_ioytgor", "new_easnsvu", "new_effneomi", "new_udveoyx", "new_ancorjo", "new_ejkipub", "new_eemafbij", "new_eiboidne", "new_eddreba", "new_irjfen", "new_osoccef", "new_eshroa", "new_uvjeahot", "new_ojachme", "new_ivheoo", "new_efspheco", "new_ajixdo", "new_ogijtri", "new_iaggrijo", "new_askmecle", "new_ibflsruc", "new_ovnacgad", "new_anucks", "new_ajsnedsi", "new_itquirla", "new_inshfola", "new_elvgid", "new_owbsuscs", "new_orptme", "new_omluaj", "new_asigno", "new_ankped", "new_aslagni", "new_arhilby", "new_indtwrat", "new_ucbiyaym", "new_oktulsy", "new_uvreoipy", "new_osckro", "new_eybyay", "new_ockolral", "new_ebrulra", "new_anidgib", "new_esnddceb", "new_ejcrur", "new_otvasdar", "new_iodjiesh", "new_eoivqued", "new_amciuca", "new_usbekhia", "new_ayquexar", "new_enjubpl", "new_iawxben", "new_eacderph", "new_iapberci", "new_odoywug", "new_awnbeb", "new_eersdo", "new_ichankru", "new_ijpiawl", "new_enyonsn", "new_uchjafy", "new_abdremlu", "new_unquekov", "new_oquiat", "new_elbnaxa", "new_utwoab", "new_ekenucki", "new_umsmtha", "new_ojfequia", "new_indyep", "new_oncalku", "new_uklsdryo", "new_apsskyul", "new_odlorhem", "new_ideutgoa", "new_umhenwed", "new_edrafs", "new_edodblea", "new_okquakar", "new_abyalwif", "new_ogjoch", "new_icdapnu", "new_aivtytan", "new_upchimy", "new_oafhynu", "new_aggjuo", "new_itbejsco", "new_ikujfurk", "new_itcemra", "new_ubjaju", "new_ujtosh", "new_ijprukfr", "new_ecjeody", "new_ifanfadd", "new_iphybcay", "new_alsheth", "new_ibgryk", "new_eghanwib", "new_agcowktu", "new_utthwaju", "new_ononma", "new_evjoko", "new_ilyutje", "new_oxdoghic", "new_udthon", "new_ujnawwe", "new_ejshigib", "new_ubnankke", "new_obheujvo", "new_eincufpy", "new_onannsov", "new_erjcrey", "new_imoikcha", "new_idjoat", "new_iofplik", "new_ednillo", "new_iafbagci", "new_epemvagu", "new_amvefarl", "new_aupclik", "new_ogikligu", "new_atfrav", "new_erulghiv", "new_abtyff", "new_ijcoga", "new_erwumu", "new_eytups", "new_equoaw", "new_ogawjem", "new_edglevi", "new_ouvdank", "new_ijhipvo", "new_icedpa", "new_ocshitcl", "new_edjiye", "new_erjtail", "new_ayshowg", "new_ajsips", "new_iahorou", "new_ifelas", "new_avpavi", "new_orocdu", "new_ugthfu", "new_iovhar", "new_iodlac", "new_arlwras", "new_anupwr", "new_ebcicy", "new_ajthof", "new_idshywr", "new_agjitfa", "new_exskafvo", "new_ubzachy", "new_opnown", "new_oapwycti", "new_ocjeng", "new_otswrank", "new_iackaw", "new_occeyd", "new_ansyut", "new_izowcu", "new_ibwicim", "new_akgicjud", "new_idlorra", "new_itpivoa", "new_eglohabs", "new_evtitdea", "new_abboip", "new_eicmynts", "new_ijthind", "new_eifvid", "new_ojvighta", "new_onvahe", "new_icdultha", "new_oogchyog", "new_eefayb", "new_evfler", "new_afweka", "new_aniajcr", "new_okblahed", "new_ojwyky", "new_iovlito", "new_upsjavci", "new_utlurva", "new_eedigkr", "new_icwiroic", "new_arcuim", "new_iatmeuns", "new_idtwal", "new_idjokva", "new_ogdygdyd", "new_ispawwye", "new_eitpem", "new_otscokdr", "new_ichikiga", "new_evmaja", "new_eybmij", "new_eoffrij", "new_isfikti", "new_ujtrud", "new_abnovkak", "new_aldhidd", "new_etalrab", "new_udwiull", "new_ancedvab", "new_isnthli", "new_acpegho", "new_ikdagcy", "new_ucgewft", "new_utjanre", "new_ibpijda", "new_iorjtwav", "new_ecgojtyt", "new_ojbaso", "new_ictmuog", "new_adnerst", "new_esudcejo", "new_ippfeip", "new_acdiyito", "new_oakpikoo", "new_awribig", "new_ijhigof", "new_abwecnij", "new_uvjova", "new_inckkiv", "new_abmuyawm", "new_aichash", "new_eavflid", "new_ishvirv", "new_egteir", "new_imhacy", "new_ektamy", "new_ayshekki", "new_onyenre", "new_elaflevy", "new_ogofvoit", "new_orsyalf", "new_ioangio", "new_ejesbay", "new_itmeps", "new_eosluip", "new_ecdiwovu", "new_ikkimm", "new_ilkble", "new_arcurbig", "new_aygpha", "new_ijtowhur", "new_ejtobu", "new_okneydy", "new_ancyclsu", "new_egwicjuv", "new_igvidth", "new_uvburki", "new_iatojbo", "new_atsfiv", "new_acgerl", "new_uthomoov", "new_atidfi", "new_amwibs", "new_equioc", "new_oblyaga", "new_uttorhig", "new_ifragcic", "new_ojfanni", "new_idvowwed", "new_eydadgio", "new_artgagvu", "new_opmisa", "new_odscixam", "new_oadwowd", "new_andyol", "new_insodfl", "new_ejnoax", "new_alnihi", "new_enekbl", "new_eesbaj", "new_ockoac", "new_inghoth", "new_ogphagpi", "new_ujhuki", "new_ebenfrag", "new_ayfsdu", "new_odolfev", "new_ojroigna", "new_ovkojy", "new_oiphye", "new_ugdifbl", "new_umvohug", "new_oygvar", "new_ickphiwa", "new_arphaud", "new_oipial", "new_eavcaifo", "new_oshyon", "new_avdyab", "new_ofjeequo", "new_aurjyen", "new_otmegok", "new_aviltly", "new_imneesu", "new_ovyijna", "new_iacbyb", "new_ecnoplsi", "new_arcksy", "new_ioajdig", "new_usadrifo", "new_ogurjupo", "new_evbacclu", "new_idcadgi", "new_omcuky", "new_uggdye", "new_ashgati", "new_aucjun", "new_oydtesa", "new_evdyffu", "new_ucovlato", "new_etirnfew", "new_awrenba", "new_adkavy", "new_otfahona", "new_uljior", "new_osacmu", "new_imwyojy", "new_abgibo", "new_iotklojy", "new_agivle", "new_iafstnue", "new_oasthul", "new_unwrain", "new_oygmebra", "new_otasfier", "new_egreosko", "new_owgves", "new_egnatab", "new_eagbaci", "new_abniar", "new_ocwyit", "new_auctjir", "new_eccest", "new_ecfesy", "new_ilkedve", "new_etnads", "new_eshria", "new_oncixpi", "new_igsoysla", "new_odocjeca", "new_appsri", "new_efgoaku", "new_eryeig", "new_isekdyp", "new_ictlypt", "new_eolgug", "new_itbadpi", "new_oirzgont", "new_udguic", "new_ektuyo", "new_erjboyni", "new_edgrat", "new_idholv", "new_edmugrip", "new_aquawaw", "new_oahudflo", "new_afmorc", "new_ackgrica", "new_elswcky", "new_enidmopt", "new_akvutvo", "new_avglybic", "new_elwesnn", "new_icagpony", "new_okavkau", "new_oivnabyo", "new_ewedkl", "new_atcilav", "new_ophugbe", "new_oocsodpl", "new_ickviya", "new_objoyhi", "new_ujkiwe", "new_ogshtcew", "new_aswift", "new_aitpygg", "new_ipjiert", "new_obsimjio", "new_efonve", "new_idsmuk", "new_omwyek", "new_ofikco", "new_onyacju", "new_ofodwo", "new_ifkalda", "new_ojumna", "new_ucvepnuv", "new_ebstic", "new_idbaybim", "new_ootyecls", "new_ochotcil", "new_ainplin", "new_onkawcel", "new_opgovebo", "new_enarwee", "new_efghakdi", "new_adpaifo", "new_inquay", "new_elfyea", "new_apigcyd", "new_ivkockim", "new_aducrisi", "new_isvayd", "new_inghoghe", "new_aydwaho", "new_acduou", "new_ophgir", "new_ipdioisi", "new_ubjajo", "new_edlyxy", "new_owhagme", "new_eggfrij", "new_ayquasto", "new_ukgoaf", "new_okvats", "new_ekyosjo", "new_enyenewl", "new_orrrar", "new_idodly", "new_aimidd", "new_utvohovy", "new_aypudby", "new_ugatna", "new_obacvau", "new_ewvnil", "new_oksbosfl", "new_omdrid", "new_epkiapme", "new_iblool", "new_edomboys", "new_aiwalb"};
    public static final String[] TOP_DRIVERS = {"new_agyamker", "new_ojpoota", "new_occroksy", "new_ifeshce", "new_utkibedy", "new_itacfl", "new_oilrag", "new_ithpett", "new_opjermp", "new_ootquoa", "new_ilcoby", "new_inhiala", "new_ajtreo", "new_edeejdru", "new_amknywr", "new_ucdewy", "new_eckecky", "new_abjoolaw", "new_acitva", "new_eokracy"};
    public static final String[] BOTTOM_DRIVERS = {"new_eovsyt", "new_afsfat", "new_iagods", "new_ugifmav", "new_agdrea", "new_uvigcho", "new_evijcey", "new_upthin", "new_udwebir", "new_eenjoug", "new_enkkand", "new_ojfieza", "new_ipdraw", "new_efmymo", "new_abcoij", "new_ewbglo", "new_ellimtbu", "new_acvebr", "new_ecforj", "new_atzumbon", "new_eoydba", "new_egoiwroi"};
    public static final int WAITING_TIME = 600;
    public static final long TIME_OFFSET = 300;
    public static final int REAL = 0;
    public static final int NEIGHBOR = 1;
    public static final int MY = 2;
    public static final int MAX_CLUSTER = 3;
    private static File dataFolder;
    public static FilenameFilter FILE_FILTER = (dir1, name) -> name.endsWith(".txt");
    public static final double ANGLE_CHUNK = 0.005;
    public static final double MIN_LAT = 37.6;
    public static final double MAX_LAT = 37.824;
    public static final double MIN_LON = -122.526;
    public static final double MAX_LON = -122.35;
    public static final int TIME_CHUNK = 7200;
    public static final int ONE_HOUR_CHUNK = 3600;
    public static final int NUM_OF_LAT_BINS = (int) ((MAX_LAT - MIN_LAT) / ANGLE_CHUNK) + 1;
    public static final int NUM_OF_LON_BINS = (int) ((MAX_LON - MIN_LON) / ANGLE_CHUNK) + 1;
    public static final long MAX_TIME_INTERVAL = 420;
    public static final double PROBABILITY_THRESHOLD = 0.2;
    public static SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HHmmss");
    public static SimpleDateFormat HOUR_FORMATTER = new SimpleDateFormat("HH");
    public static SimpleDateFormat WEEK_FORMATTER = new SimpleDateFormat("u");
    public static SimpleDateFormat YEAR_FORMATTER = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat HALF_HOUR_FORMATTER = new SimpleDateFormat("yyyyMMddHHmm");
    public static DecimalFormat NUM_FORMATTER=new DecimalFormat("#.##");
    public static File getDataFolder() {
        try {
            if (null == dataFolder) {
                dataFolder = new ClassPathResource("cabspottingdata").getFile();
            }
        } catch (IOException e) {
            System.out.print("failed to load GPSReading data");
        } finally {
            return dataFolder;
        }

    }

    public static int getLatBin(double lat) {
        return (int) ((lat - MIN_LAT) / ANGLE_CHUNK);
    }

    public static int getLonBin(double lon) {
        return (int) ((lon - MIN_LON) / ANGLE_CHUNK);
    }

    public static double getDistance(GPSData start, GPSData end) {
        double conv = Math.PI / 180;
        double phiS = end.getLat() * conv, lambdaS = end.getLon() * conv;
        double phiF = start.getLat() * conv, lambdaF = start.getLon() * conv;
        double t1 = Math.pow(Math.sin((phiF - phiS) / 2), 2);
        double t2 = Math.pow(Math.sin((lambdaF - lambdaS) / 2), 2);
        double d = t1 + Math.cos(phiS) * Math.cos(phiF) * t2;
        d = Math.sqrt(d);
        d = 2 * Math.asin(d);
        return 6372800 * d;
    }


    public static int decodeLatBin(int id) {
        return id / Config.NUM_OF_LON_BINS;
    }

    public static int decodeLonBin(int id) {
        return id % Config.NUM_OF_LON_BINS;
    }

    public static int getGrid(int latBin, int lonBin) {
        return latBin * NUM_OF_LON_BINS + lonBin;
    }
}
