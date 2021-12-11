package com.enikolov.netitbackendhr.repositories.general;

import com.enikolov.netitbackendhr.models.general.Applies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppliesRepository extends JpaRepository<Applies, Integer> {
    public List<Applies> findAllByEmployeeId(int employeeId);
    public List<Applies> findAllByCampaignId(int campaignId);
    public Optional<Applies> findByCampaignId(int campaignId);
}
