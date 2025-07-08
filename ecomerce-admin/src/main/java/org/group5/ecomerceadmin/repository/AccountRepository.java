package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

    List<Account> findByRole(Role role);

}
