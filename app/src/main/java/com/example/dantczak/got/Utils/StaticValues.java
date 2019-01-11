package com.example.dantczak.got.Utils;

import java.util.ArrayList;
import java.util.List;

public class StaticValues
{
    private static List<Long> authorizedGroups = new ArrayList<Long>(){{
        add(1L);
    }};

    public static Long loggedInPrzodownikId = 1L;

    public static Long loggedInTurystaId =  1L;
}
