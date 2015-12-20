package io.github.qeesung.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qeesung on 2015/12/8.
 * this class is used to save all pairs' symbol
 */
public class PairSymbol {
    private static final Map<String, String> pairedPairMap = new HashMap<>();
    private static final Map<String, String> reversePairedPairMap = new HashMap<>();
    // round bracket
    public static final String L_ROUND_B = "(";
    public static final String R_ROUND_B = ")";
    // square bracket
    public static final String L_SQUARE_B= "[";
    public static final String R_SQUARE_B = "]";
    // curl bracket
    public static final String L_CURL_B= "{";
    public static final String R_CURL_B = "}";
    // angle bracket
    public static final String L_ANGLE_B= "<";
    public static final String R_ANGLE_B = ">";
    // double quote
    public static final String DOUBLE_Q= "\"";
    // single quote
    public static final String SINGLE_Q= "'";


    // finally add all symbols to the symbolList by order
    static
    {
        addLeftAndRightPair(L_ROUND_B , R_ROUND_B);
        addLeftAndRightPair(L_ANGLE_B , R_ANGLE_B);
        addLeftAndRightPair(L_CURL_B , R_CURL_B);
        addLeftAndRightPair(L_SQUARE_B , R_SQUARE_B);
        addLeftAndRightPair(DOUBLE_Q , DOUBLE_Q);
        addLeftAndRightPair(SINGLE_Q , SINGLE_Q);

        for (String key : pairedPairMap.keySet())
        {
            String value = pairedPairMap.get(key);
            reversePairedPairMap.put(value , key);
        }
    }

    private static void addLeftAndRightPair(String left , String right)
    {
        pairedPairMap.put(left , right);
    }

    public static Map<String ,String> getPairedPairMap()
    {
        return pairedPairMap;
    }

    public static Map<String ,String> getReversePairedPairMap()
    {
        return reversePairedPairMap;
    }
}
