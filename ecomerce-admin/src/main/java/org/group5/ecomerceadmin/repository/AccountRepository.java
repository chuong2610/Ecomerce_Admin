package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
