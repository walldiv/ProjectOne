package com.ex.service;

import com.ex.dao.SponsorDAO;
import com.ex.dao.SponsorDAOImpl_PGR;
import com.ex.model.Sponsor;

/**
 * This service class is responsible for all things related to Sponsor CRUD
 * calls to the database.  Its a generic DAO interface caller - passing specific
 * DAO implementation SINGULARLY in this class - - allowing for interchangeability
 *  of database types in future by simply adding a new class type here for the following:
 * @param sponsorDAO - this class archetype is for vendor-specific SponsorDAO implementations
 */
public class SponsorService {
    private SponsorDAO sponsorDAO;

    public SponsorService() {
        this.sponsorDAO = new SponsorDAOImpl_PGR();     //change this impl for different vendor types
    }

    public SponsorService(SponsorDAO sponsorDAO) {
        this.sponsorDAO = sponsorDAO;
    }

    /* Registers from website as a sponsor */
    public boolean registerSponsor(Sponsor sponsor) {
        try {
            sponsorDAO.registerSponsor(sponsor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
