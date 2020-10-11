package com.ex.dao;

import com.ex.model.Sponsor;

public interface SponsorDAO {

    /* Registers from website as a sponsor */
    public void registerSponsor(Sponsor sponsor) throws Exception;
}
