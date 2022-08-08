package com.example.payment.Repository;

import com.example.payment.Model.EFTorTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEFTorTransferRepository extends JpaRepository<EFTorTransfer,Long> {


}
