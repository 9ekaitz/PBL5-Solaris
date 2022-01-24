package eus.solaris.solaris.repository.filters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.SolarPanelModel;

@Repository
public interface ModelRepository extends JpaRepository<SolarPanelModel, Long>{

}
