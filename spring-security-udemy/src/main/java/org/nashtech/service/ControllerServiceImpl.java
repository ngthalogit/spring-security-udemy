package org.nashtech.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ControllerServiceImpl implements ControllerService{

    @Override
    //    @PreAuthorize("hasRole('ADMIN')")
    @PostAuthorize("hasRole('CUSTOMER')")
    public String data3() {
        return "This is data 3";
    }
}
