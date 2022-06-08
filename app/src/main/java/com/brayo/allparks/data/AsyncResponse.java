package com.brayo.allparks.data;

import com.brayo.allparks.models.Park;

import java.util.List;

public interface AsyncResponse {
    void processPark(List<Park> parks);
}
