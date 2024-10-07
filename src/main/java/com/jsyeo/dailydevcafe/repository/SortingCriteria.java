package com.jsyeo.dailydevcafe.repository;

public enum SortingCriteria {
    LATEST, TITLE, POPULARITY;

    public boolean isLatest() {
        return this == LATEST;
    }

    public boolean isTitle() {
        return this == TITLE;
    }

    public boolean isPopularity() {
        return this == POPULARITY;
    }
}
