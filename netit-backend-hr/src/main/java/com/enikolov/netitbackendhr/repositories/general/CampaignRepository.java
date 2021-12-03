package com.enikolov.netitbackendhr.repositories.general;

import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    public List<Campaign> findAllByEmployerId(int employer_id);

    @Query(value = "SELECT "
            + "	a.*"
            + " FROM "
            + "	td_campaigns a, "
            + " td_employers b, "
            + " td_users c "
            + " WHERE \r\n"
            + "	(a.employer_id = b.id)"
            + "    AND"
            + "    (b.user_id = c.id)"
            + "	AND "
            + "    (a.id = ?)"
            + "    AND"
            + "    (c.id = ?)", nativeQuery = true)
    public Optional<Campaign> getMyCampaign(int id, int user_id);
}