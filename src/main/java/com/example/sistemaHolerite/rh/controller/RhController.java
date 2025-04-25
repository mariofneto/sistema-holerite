package com.example.sistemaHolerite.rh.controller;

import com.example.sistemaHolerite.rh.service.RhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RhController {

    @Autowired
    RhService rhService;
}
