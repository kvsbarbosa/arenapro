package com.web2.arenapro.domain.repositories;

import com.web2.arenapro.domain.entities.Horarios;
import com.web2.arenapro.domain.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorariosRepository extends JpaRepository<Horarios, Long> {
}
