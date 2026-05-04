package com.project.VISA.controllers;

import com.project.VISA.models.Demande;
import com.project.VISA.services.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bo/demandes")
public class DemandeWebController {

    @Autowired
    private DemandeService demandeService;

    @GetMapping
    public String listerDemandes(Model model) {
        List<Demande> demandes = demandeService.findAll();
        model.addAttribute("demandes", demandes);
        return "demande-list";
    }
}
