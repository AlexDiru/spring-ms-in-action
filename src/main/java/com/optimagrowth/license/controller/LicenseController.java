package com.optimagrowth.license.controller;

import com.optimagrowth.license.model.License;
import com.optimagrowth.license.service.LicenseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "v1/organisation/{organisationId}/license")
public class LicenseController {

    @Autowired
    LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organisationId") String organisationId,
            @PathVariable("licenseId") String licenseId) {
        var license = licenseService.getLicense(licenseId, organisationId);

        license.add(getLicenseLinks(organisationId, license));

        return ResponseEntity.ok(license);
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable("organisationId") String organisationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.updateLicense(request, organisationId, locale));
    }

    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable("organisationId") String organisationId,
            @RequestBody License request,
            @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.createLicense(request, organisationId, locale));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organisationId") String organisationId,
            @PathVariable("licenseId") String licenseId,
            @RequestHeader(value = "Accept-Language",required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organisationId, locale));
    }

    private List<Link> getLicenseLinks(String organisationId, License license) {
        return List.of(
                linkTo(methodOn(LicenseController.class)
                    .getLicense(organisationId, license.getLicenseId()))
                    .withSelfRel(),
                linkTo(methodOn(LicenseController.class)
                        .createLicense(organisationId, license, null))
                        .withRel("createLicense"),
                linkTo(methodOn(LicenseController.class)
                        .updateLicense(organisationId, license, null))
                        .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class)
                        .deleteLicense(organisationId, license.getLicenseId(), null))
                        .withRel("deleteLicense"));
    }
}
