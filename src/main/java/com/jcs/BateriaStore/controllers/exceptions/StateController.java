package com.jcs.BateriaStore.controllers.exceptions;

import com.jcs.BateriaStore.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {

    @Autowired
    private StateService service;
}
