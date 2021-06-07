package com.e.tripplanner.Common;

import com.e.tripplanner.Model.Results;
import com.e.tripplanner.Remote.IGoogleAPIService;
import com.e.tripplanner.Remote.RetrofitClient;
import com.e.tripplanner.Remote.RetrofitScalarClient;

public class Common {

    public static Results currentResults;

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static IGoogleAPIService getGoogleAPIService()   {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }

    public static IGoogleAPIService getGoogleAPIServiceScalars()   {
        return RetrofitScalarClient.getScalarClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
