package com.example.licenceservice.controller;

import com.example.licenceservice.model.License;
import com.example.licenceservice.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value="v1/organisation/{organisationId}/license")
public class LicenceController {

    @Autowired
    LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organisationId")String organisationId,
            @PathVariable("licenceId")String licenseId){

        License license = licenseService.getLicense(licenseId,organisationId);

        license.add(linkTo(methodOn(LicenceController.class)
                .getLicense(organisationId,license.getLicenseId()))
                .withSelfRel(),
                linkTo(methodOn(LicenceController.class)
                        .createLicense(organisationId,license,null))
                        .withRel("createLicense"),
                        linkTo(methodOn(LicenceController.class)
                                .updateLicense(organisationId,license))
                                .withRel("updateLicense"),
                                linkTo(methodOn(LicenceController.class)
                                        .deleteLicense(organisationId,license.getLicenseId()))
                                        .withRel("deleteLicence"));
                return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable("organisationId")String organisationId,
            @RequestBody License request){

        return ResponseEntity.ok(licenseService.updateLicense(request, organisationId));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable("organisationId")String organisationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language",required = false)Locale locale){

        return ResponseEntity.ok(licenseService.createLicense(request, organisationId,locale));
    }

    @DeleteMapping(value = "/{licenceId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organisationId")String organisationId,
            @PathVariable("licenseId") String licenceId){

        return ResponseEntity.ok(licenseService.deleteLicense(licenceId, organisationId));
    }



}
