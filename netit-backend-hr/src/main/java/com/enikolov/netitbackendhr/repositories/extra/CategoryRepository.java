package com.enikolov.netitbackendhr.repositories.extra;

import com.enikolov.netitbackendhr.models.extra.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
