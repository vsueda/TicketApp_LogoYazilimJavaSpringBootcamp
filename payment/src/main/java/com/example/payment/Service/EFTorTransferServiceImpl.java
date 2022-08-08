package com.example.payment.Service;

import com.example.payment.Model.EFTorTransfer;
import com.example.payment.Repository.IEFTorTransferRepository;

public class EFTorTransferServiceImpl implements IEFTorTransferService {

    private IEFTorTransferRepository iefTorTransferRepository;


    @Override
    public void payTicket(EFTorTransfer efTorTransfer) {
        iefTorTransferRepository.save(efTorTransfer);
    }
}
