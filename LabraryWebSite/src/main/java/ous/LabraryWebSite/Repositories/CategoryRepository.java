package ous.LabraryWebSite.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ous.LabraryWebSite.models.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category , Long> {
}
